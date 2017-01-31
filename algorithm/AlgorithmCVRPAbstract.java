package algorithm;

import Chromosome.MyRandom;
import general.Individual;
import general.JSON;
import general.Population;
import general.console;
import java.util.ArrayList;
import java.util.Arrays;
import problem.Problem;


public class AlgorithmCVRPAbstract extends AlgorithmAbstract{
    private double probLS;
   public AlgorithmCVRPAbstract(Problem problem, double probCross, double probMut, double probLS, int maxSteps) {
    	super(problem, probCross, probMut, maxSteps);	
        this.probLS=probLS;          	
    }	
	
    public Individual localSearch(double probLS, Individual indiv, Population pop, Problem problem){
        Individual bestSol = indiv;
        
            if (indiv.getFitness()==0){
                console.log(JSON.stringify(bestSol));
                throw new RuntimeException("ERROR el fitness no está definido al empezar");
            }       
        
        Individual tempSol=null;
        for (int i=0; i<indiv.getChromosome().getSize();i++){
            if (Math.random()<probLS){
                tempSol= this.opt_2(indiv,i,problem);
            if (tempSol.getFitness()==0){
                throw new RuntimeException("ERROR el fitness no está definido opt_2");
            }            
                if (tempSol.getFitness()>bestSol.getFitness()){
                    bestSol=tempSol;
                } 
            }
        }
        for (int i=0;i<pop.getSize();i++){
            for (int j=i+1;j<pop.getSize();j++){
                if (Math.random()<probLS){
                    tempSol=this.interchange_1(pop.getIndividual(i),pop.getIndividual(j),problem);
            if (tempSol.getFitness()==0){
                throw new RuntimeException("ERROR el fitness no está definido interchange_1");
            }                  
                    if (tempSol.getFitness()>bestSol.getFitness()){
                        bestSol=tempSol;
                    }                 
                }          
            }
        }
        
            if (bestSol.getFitness()==0){
                console.log(JSON.stringify(bestSol));
                throw new RuntimeException("ERROR el fitness no está definido ");
            }    
        
        return bestSol;    	
    }
    
    
private boolean areRepeatedNumbers(Individual indiv){   
    ArrayList<Integer> allelesSearch = new ArrayList(Arrays.asList(indiv.getChromosome().getAlleles()));
    ArrayList<Integer> alleles=(ArrayList<Integer>)allelesSearch.clone();
    int repeats =0;
    int index;
    for (int i=0;i<allelesSearch.size();i++){
        repeats=0;
        index=alleles.indexOf(i);       
        while (index>-1){
            repeats++;
            alleles.remove(index);                   
//            alleles.splice(index,1);
            if (repeats>1){
                return true;
            }
            index=alleles.indexOf(i);
        }
    }
    return false;
}    
    
    
    public Individual interchange_1(Individual indiv1, Individual indiv2,Problem problem){
        Individual copy1 = indiv1.clone();
        Individual copy2 = indiv2.clone();
       
        if (areRepeatedNumbers(copy1)){
            console.log("REPEATED NUMBERS "+JSON.stringify(copy1));
            throw new RuntimeException("REPEATED NUMBERS");
        }   
        if (areRepeatedNumbers(copy2)){
            console.log("REPEATED NUMBERS "+JSON.stringify(copy2));
            throw new RuntimeException("REPEATED NUMBERS");
        }     
        
       
        int pos1 = MyRandom.getSingleton().nextInt(copy1.getChromosome().getSize());
        int pos2 = MyRandom.getSingleton().nextInt(copy2.getChromosome().getSize());

        int allele1 = copy1.getChromosome().getAllele(pos1);
   
        int allele2 = copy2.getChromosome().getAllele(pos2);
   
        
        
        
        //Copiamos alelo 1 en individuo 2
        int tempPos = copy2.getChromosome().findAllelePos(allele1);

        copy2.getChromosome().setAllele(pos2,allele1);

        copy2.getChromosome().setAllele(tempPos,allele2); //Copiamos el sustituido por la posición donde estaba el que se ha copiado en ese mismo cromosoma.

         
        //Copiamos alelo 2 en individuo 1
        tempPos = copy1.getChromosome().findAllelePos(allele2);

        copy1.getChromosome().setAllele(pos1,allele2);

        copy1.getChromosome().setAllele(tempPos,allele1);


        problem.evaluateStep(copy1);
  
        problem.evaluateStep(copy2);


        if (areRepeatedNumbers(copy1)){
            console.log("REPEATED NUMBERS "+JSON.stringify(copy1));
            throw new RuntimeException("REPEATED NUMBERS");
        }   
        if (areRepeatedNumbers(copy2)){
            console.log("REPEATED NUMBERS "+JSON.stringify(copy2));
            throw new RuntimeException("REPEATED NUMBERS");
        }  
        
        
        if (copy1.getFitness()>=copy2.getFitness()){
            return copy1;
        } else {     
            return copy2;
        }    		
    }
    
    
    public Individual opt_2(Individual indiv,int eje1_1,Problem problem){
        Individual copy=indiv.clone();
      if (areRepeatedNumbers(copy)){
          console.log("REPEATED NUMBERS "+JSON.stringify(copy));
          throw new RuntimeException(("REPEATED NUMBERS"));
      }    

      
      int  eje2_1 = MyRandom.getSingleton().nextInt(copy.getChromosome().getSize());
      
      //Se ordena de menor a mayor los ejes.
      if (eje1_1>eje2_1){
          int temp = eje1_1;
          eje1_1=eje2_1;
          eje2_1=temp;
      }
      
      int eje1_2 = eje1_1+1;
      if (eje1_2>=copy.getChromosome().getSize())eje1_2=0; 
      
      int eje2_2 = eje2_1+1;
      if (eje2_2>=copy.getChromosome().getSize()) eje2_2=0;   

      copy.getChromosome().reverseSubGroup(eje1_2, eje2_1); 
      
      problem.evaluateStep(copy);
 
      if (areRepeatedNumbers(copy)){
          console.log("REPEATED NUMBERS "+JSON.stringify(copy));
          throw new RuntimeException(("REPEATED NUMBERS"));
      }
      return  copy;    	
    }    

    @Override
    public void initialize(int popSize, int chromLength) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the probLS
     */
    public double getProbLS() {
        return probLS;
    }

    /**
     * @param probLS the probLS to set
     */
    public void setProbLS(double probLS) {
        this.probLS = probLS;
    }
}
