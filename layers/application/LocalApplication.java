package layers.application;


import algorithm.AlgorithmAbstract;
import algorithm.AlgorithmCVRP;
import general.Individual;
import general.JSON;
import general.ReadFile;
import general.console;
import java.util.Date;
import problem.Problem;
import problem.ProblemImage;


public class LocalApplication {
    private double maxTime;
    private double startTime;
    private double finalTime;
    private AlgorithmCVRP algorithm;
    private boolean running;
    private int[][] image;

//    private int[] getDistance(int fila1,int fila2,int[][] image){
//        int[] distance=new int[image[fila1].length];
//
//        for (int i=0;i<image[fila1].length;i++){
//            distance[i]=Math.abs(image[fila1][i]-image[fila2][i]);
//        }
//        return distance;
//    }   
    
    public static int[][] getDistanceMatrix(int[][] image){
        int[][] matrixDistance=new int[image.length][image.length];
        for (int i=0;i<image.length;i++){
            for (int k=0;k<image.length;k++){
                matrixDistance[i][k]=0;
                for (int j=0;j<image[i].length;j++){
                    matrixDistance[i][k]+=Math.abs(image[i][j]-image[k][j]);
                }                     
            }        
        }
        return matrixDistance;
    }    
    
    
    
    public LocalApplication(int[][] image) {
        this.maxTime=500;
        this.startTime=0;
        this.finalTime=0;
        this.algorithm=null;
        this.running=false;   
        this.image=image;


        int maxSteps=1000;



        double probCross= 0.8;
        double probMut = 0.01;
        double probLS = 0.1;

//        int[][] matrixCost =new int[image.length][image[0].length];
//
//        for (int i=0;i<image.length;i++){
//            for (int k=0;k<image.length;k++){
//                matrixCost[i][k]=0;
//                for (int j=0;j<image.length;j++){
//                    matrixCost[i][k]+=Math.abs(image[i][j]-image[k][j]);  
//                }
//            }
//        }

        int[][] matrixCost =getDistanceMatrix(image);
        JSON.stringify(matrixCost);

        ProblemImage problem = new ProblemImage();
        problem.initialize(image,matrixCost);
        problem.setTargetFitness(999999999);    


        console.log("HASTA LUEGO");
        this.algorithm = new AlgorithmCVRP(problem, probCross,probMut,probLS, maxSteps);
        int popSize=10;
        this.algorithm.initialize(popSize, image.length,image,matrixCost);

    }
    



    public void run(){
        while (this.running){      
            if (!this.algorithm.hasFinished()){
            Individual son = this.algorithm.run();
            this.algorithm.replaceWorst(son);
            } else {  //HAS FINISHED
                  this.end();                       
            } //has finished   
        }            
    }
    
    public void end(){        
        this.finalTime=new Date().getTime();
        double totalTime=this.finalTime-this.startTime;

        console.log("fin");
        this.running=false;

        console.log("  TIME: "+totalTime);
        Individual solution = this.algorithm.showSolution();                            
        int finalStep=this.algorithm.getStep(); 

        
        Integer[] sol1=this.algorithm.getPopulation().getBestIndividual().getChromosome().getAlleles();
        int[] sol2 = new int[sol1.length];
        for (int i=0;i<sol1.length;i++){
            sol2[i]=sol1[i];
        }
        ReadFile.saveImage(sol2,image);
        
    }    
    

    public void start(){
        this.startTime = new Date().getTime();
        this.running=true;            
        this.run();      
    }
     
}
