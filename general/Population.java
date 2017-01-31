package general;

import java.util.ArrayList;


public class Population {
    private ArrayList<Individual> pop;
    int bestp;
    int worstp;
    double bestf;
    double worstf;
    double bestfEver;
    double avgf;
    int iteration;
//    public static fromJSON(String json){
//        //TODO
//    }  
    
    //toString
    public void initialize(int popSize,int size,Class c){
        pop=new ArrayList();
        Individual indiv;
        for (int i = 0; i < popSize; i++){
            indiv=new Individual();
                this.pop.add(indiv);
                indiv.initialize(size,c);
        }

        // Initialize statistics
        bestp = 0;     
        worstp = 0;
        bestf = 0.0;   
        avgf   = 0.0;   
        worstf = 9999999999.0;    
        bestfEver = 0.0;
        iteration = 0;    
    }
    public int getSize(){
        return this.pop.size();        
    }
    public Individual getIndividual(int index){
        return this.pop.get(index);        
    }
    public void setIndividual(int index, Individual indiv){
        pop.set(index, indiv);        
    }    
    public int replaceWorst(Individual indiv){
        //Comprobar si el que se inserta es mejor que el peor ¿comprobarlo fuera o dentro?         
      //  if (indiv.getFitness()>this.pop[this.worstp].getFitness()){
           pop.set(worstp,indiv); 
           return this.worstp;
      //  } else {
      //      return -1;
      //  }        
    }    
    public void replace(int index, Individual indiv){//Este método se utiliza sólo cuando Monitor-Esclavo para que el esclavo pueda reemplazar
        this.pop.set(index, indiv);
    }
    public Individual getBestIndividual(){
        return this.pop.get(this.bestp);        
    }
    public void showBestFitness(){
        System.out.println(this.iteration+"-BEST FITNESS "+this.bestf);   
        System.out.println(JSON.stringify(this.getIndividual(this.bestp)));         
    }   
    
    public void computeStats(){

        double borrarBESTBEFORE=this.bestf;

        
        double antiguoFitness = this.bestf;
        
	double total = 0.0;
	double f = 0.0;
	this.worstf = this.pop.get(0).getFitness();
	this.worstp = 0;
	this.bestf = this.pop.get(0).getFitness();
	this.bestp = 0;

	for (int i = 0; i < this.pop.size(); i++){
		f = this.pop.get(i).getFitness();
		if (f<=this.worstf) {
			this.worstf=f;
			this.worstp=i;
		}
		if (f>this.bestf){ 
			this.bestf = f; 
			this.bestp = i;			
		}
		if (f>=this.bestfEver){ this.bestfEver = f;}
		total+=f;
	}	
	this.avgf = total/this.pop.size();              
                
                double borrarBESTLATER=this.bestf;
                
                if (borrarBESTBEFORE !=0 && borrarBESTLATER<borrarBESTBEFORE){
                    console.log("ESTO NO DEBERÍA ESTAR PASANDO");
                    console.log("borrarBESTBEFORE="+borrarBESTBEFORE);
                    console.log("borrarBESTLATER="+borrarBESTLATER); 
                    throw new RuntimeException("ERROR");            
                } 

	if (this.bestf>antiguoFitness){
            double newFitness=Math.floor(this.bestf * 100) / 100;
            console.log(this.iteration+"-NUEVO FITNESS "+newFitness);   
            console.log(JSON.stringify(this.getIndividual(this.bestp)));         
	}

	this.iteration++;          
    }    
    
}
