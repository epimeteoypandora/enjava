package algorithm;

import Chromosome.ChromosomeVRP;
import general.Individual;
import general.Population;
import java.util.ArrayList;
import java.util.Random;
import problem.Problem;


public class AlgorithmCVRP extends AlgorithmCVRPAbstract{
    
    public AlgorithmCVRP(Problem problem, double probCross, double probMut, double probLS, int maxSteps) {
        super(problem, probCross, probMut, probLS, maxSteps);
    }
    

    public static int getNextRow(int row,int[][] distanceMatrix, ArrayList<Integer> elements){
        int chosen=-1;
        int minDistance=999999999;
        
        for (int i=0;i<elements.size();i++){
            if (distanceMatrix[row][elements.get(i)]<minDistance){
                if (row!=elements.get(i)){
                    minDistance=distanceMatrix[row][elements.get(i)];
                    chosen=elements.get(i);                
                }
            }
        }
        return chosen;     
    }
    public static Integer[] getOrderedRows(int[][]  image,int[][] distanceMatrix){
        Random r = new Random();
        ArrayList<Integer> elements = new ArrayList<Integer>();
        for (int i=0;i<image.length;i++){
            elements.add(i);
        }    
        
//        System.out.println(elements.toString());
        ArrayList<Integer> result = new ArrayList<Integer>();    
        int nextRow = r.nextInt(image.length);
        elements.remove(new Integer(nextRow));
        result.add(nextRow);
        while (elements.size()>0){
            nextRow=getNextRow(nextRow,distanceMatrix,elements);
//            System.out.println("next row "+nextRow);
//            System.out.println(elements.size());
//            System.out.println(nextRow);
            elements.remove(new Integer(nextRow));
//            System.out.println(elements.toString());
            result.add(nextRow);            
        }
        Integer[] returnType=new Integer[result.size()];
        return result.toArray(returnType);
    } 
    
    
    public void initialize(int popSize, int chromLength,int[][] image,int[][] matrixCost){
//        initialize(popSize,chromLength);
        
       this.setPopulation(new Population());   
        this.getPopulation().initialize(popSize, chromLength, ChromosomeVRP.class);           
        for (int i = 0; i < this.getPopulation().getSize(); i++){
            this.getPopulation().getIndividual(i).getChromosome().setAlleles(getOrderedRows(image,matrixCost));
            this.getProblem().evaluateStep(this.getPopulation().getIndividual(i));
        }
        this.getPopulation().computeStats();           
 	
    }
    public Individual run(){
        Individual [] arrayParents=this.selectTournament2(this.getPopulation());

        Individual son = this.cross(arrayParents[0],arrayParents[1],this.getProbCross());   

        son = this.mutate(son,this.getProbMut());

        this.getProblem().evaluateStep(son);
        
        Individual bestLS = this.localSearch(this.getProbLS(),son,this.getPopulation(),this.getProblem());
        if (bestLS.getFitness()>son.getFitness()){
            son=bestLS;
        }
	
        return son;        
    }
//    public void runCallback(callback){
//        var arrayParents=null;
//        setImmediate(    ()=>{
//            arrayParents=this.selectTournament2(this.population);
//        }); 
//        
//        var son=null;
//        setImmediate(    ()=>{
//            son = this.cross(arrayParents[0],arrayParents[1],this.probCross);          
//        }); 
//
//        setImmediate(    ()=>{        
//            son = this.mutate(son,this.probMut);          
//        });         
//
//        setImmediate(    ()=>{  
//            this.problem.evaluateStep(son);          
//        });         
//
//        setImmediate(    ()=>{          
//            var bestLS = this.localSearch(this.probLS,son,this.population,this.problem);
//            if (bestLS.getFitness()>son.getFitness()){
//                son=bestLS;
//            }        
//            callback(son);             
//        });              
//    } 
    
}
