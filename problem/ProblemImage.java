package problem;

import Chromosome.ChromosomeAbstract;
import general.Individual;
import general.console;



public class ProblemImage extends Problem{
    private int[][] image;
    private int[][] matrixCost;

    private boolean tfKnown = false;

    
    public void initialize(int[][] image,int[][] matrixCost){
        this.image=image;
        this.matrixCost=matrixCost;
        //console.log(this.matrixCost);
   
        this.setTargetFitness(-999999.9);
        this.tfKnown = false;
        this.setFitnessCounter(0);    
        console.log("FIN INITIALIZE");    
    }

    public int getCellsFitness(ChromosomeAbstract chromosome,int posChro1,int posChro2){
    int f=0;
    int p1=chromosome.getAllele(posChro1);
      int p2=chromosome.getAllele(posChro2);
      // console.log("posChro1"+posChro1)
      // console.log("posChro2"+posChro2)    
      // console.log("p1"+p1)
      // console.log("p2"+p2)  
      int maxDiff=255;  
      double diff=0;   
      int minDiff=5;   
      for (int i=0;i<this.image[p1].length;i++){
        diff=Math.abs(this.image[p1][i]-this.image[p2][i]);
        
        if (diff<=minDiff)diff=-Math.pow((minDiff-diff),2);
        if (diff>maxDiff)diff=maxDiff;
        f+=diff;
      }
//      if (isNaN(f)) throw "no vale esto"
      return f;
    }
    
    public double getRowFitness(ChromosomeAbstract chromosome){
        double f=0;
        int anterior5=-1;     
        int anterior4=-1;     
        int anterior3=-1;
        int anterior2=-1;
        int anterior1=-1;
        int posterior1=-1;
        int posterior2=-1;
        int posterior3=-1;    
        int posterior4=-1;    
        int posterior5=-1;         

        for (int i=0;i<chromosome.getSize();i++){
            anterior5=i-5;     
            anterior4=i-4;     
            anterior3=i-3;
            anterior2=i-2;
            anterior1=i-1;
            posterior1=i+1;
            posterior2=i+2;
            posterior3=i+3; 
            posterior4=i+4; 
            posterior5=i+5;         
            if (anterior5>=0){
                f+=this.getCellsFitness(chromosome,anterior5,anterior4);
            }
            if (anterior4>=0){
                f+=this.getCellsFitness(chromosome,anterior4,anterior3);
            }
            if (anterior3>=0){
                f+=this.getCellsFitness(chromosome,anterior3,anterior2);
            }
            if (anterior2>=0){
                f+=this.getCellsFitness(chromosome,anterior2,anterior1);
            }
            if (anterior1>=0){
                f+=this.getCellsFitness(chromosome,anterior1,i);
                //console.log("nuevo fff"+f)
            }     
            if (posterior1<chromosome.getSize()){
                f+=this.getCellsFitness(chromosome,i,posterior1);
            } 
            if (posterior2<chromosome.getSize()){
                f+=this.getCellsFitness(chromosome,posterior1,posterior2);
            }
            if (posterior3<chromosome.getSize()){
                f+=this.getCellsFitness(chromosome,posterior2,posterior3);
            } 
            if (posterior4<chromosome.getSize()){
                f+=this.getCellsFitness(chromosome,posterior3,posterior4);
            }  
            if (posterior5<chromosome.getSize()){
                f+=this.getCellsFitness(chromosome,posterior4,posterior5);
            }                       
        }

        return f;
    }
      

    @Override
    public double evaluateStep(Individual indiv){
        double f=this.getRowFitness(indiv.getChromosome());

        //console.log("nuevo fitness "+f)
       this.setFitnessCounter(this.getFitnessCounter()+1);
      //console.log("nuevo fitness "+f)
       indiv.setFitness(-f);
            return -f;
            //return f;
    }    
}
