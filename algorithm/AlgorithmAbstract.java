package algorithm;

import Chromosome.ChromosomeAbstract;
import Chromosome.MyRandom;
import general.Individual;
import general.JSON;
import general.Population;
import general.console;
import java.util.ArrayList;
import problem.Problem;


public abstract class AlgorithmAbstract {
    private Population population;
    private Problem problem;
    private double probCross;
    private double probMut;
    private boolean running;
    private int step;
    private int maxSteps;
    private boolean solutionFound;
    
    public AlgorithmAbstract(Problem problem, double probCross, double probMut, int maxSteps){
        this.population = null;     
        this.problem = problem;    
        this.probCross = probCross;
        this.probMut=probMut;     
        this.running=false;
        this.step=0;
        this.maxSteps = maxSteps;        
        this.solutionFound=false;    		
    }
    public abstract void initialize(int popSize,int chromLength);
    
    public void load(Population population){
        this.setPopulation(population);   	
    }       
    public Individual selectTournament(Population pop){     
        
    	int p1 = MyRandom.getSingleton().nextInt(pop.getSize());
    	int p2 = 0;
        do{  
            p2 = MyRandom.getSingleton().nextInt(pop.getSize());
        }   
        while (p1==p2);	
        
         if (pop.getIndividual(p1).getChromosome().getSize()==0) throw new RuntimeException("ERROR no debería ser cero"); //TODO
         
         if (pop.getIndividual(p2).getChromosome().getSize()==0) throw new RuntimeException("ERROR no debería ser cero"); //TODO
        
        if (pop.getIndividual(p1).getFitness()>pop.getIndividual(p2).getFitness()){
        	return pop.getIndividual(p1).clone();
        } else { 	
        	return pop.getIndividual(p2).clone();
        }    	
    }
    
    
    public Individual[] selectTournament2(Population pop){
        
        ArrayList<Integer> selectedParentsPos=new ArrayList<Integer>();
        Integer parentPos=null;
        while (selectedParentsPos.size()<4){
            parentPos=MyRandom.getSingleton().nextInt(pop.getSize());
            if (selectedParentsPos.indexOf(parentPos)==-1){
                selectedParentsPos.add(parentPos);
            }
        }
        Individual firstSelected=null;
        Individual secondSelected=null;
        
        if (pop.getIndividual(selectedParentsPos.get(0)).getFitness()>pop.getIndividual(selectedParentsPos.get(1)).getFitness()){
        	firstSelected = pop.getIndividual(selectedParentsPos.get(0)).clone();
        } else { 	
        	firstSelected =  pop.getIndividual(selectedParentsPos.get(1)).clone();
        }         
	
        if (pop.getIndividual(selectedParentsPos.get(2)).getFitness()>pop.getIndividual(selectedParentsPos.get(3)).getFitness()){
        	secondSelected = pop.getIndividual(selectedParentsPos.get(2)).clone();
        } else { 	
        	secondSelected =  pop.getIndividual(selectedParentsPos.get(3)).clone();
        }            
        return new Individual[]{firstSelected,secondSelected};
        
    }    
    
    public Individual cross(Individual indiv1, Individual indiv2, double probCross){
        Individual son = new Individual();
        ChromosomeAbstract chro = indiv1.getChromosome().cross(indiv2.getChromosome(),probCross);
        son.setChromosome(chro);
	      
        if (son.getChromosome().getSize()==0){
            console.log("indiv1 "+JSON.stringify(indiv1));     
            console.log("indiv2 "+JSON.stringify(indiv2));                
            throw new RuntimeException("ERROR no debería ser cero"); //TODO
        } 
        return son;    	  	
    }
    
    public Individual mutate(Individual indiv, double probMut){
        indiv.getChromosome().mutate(probMut);   
        if (indiv.getChromosome().getSize()==0) throw new RuntimeException("ERROR no debería ser cero"); //TODO        
    	return indiv;    	
    }

    
    public int replaceWorst(Individual son){
        int pos = this.getPopulation().replaceWorst(son); 
        
        
        if (pos!=-1){ //Si se ha producido el reemplazo.
            this.getPopulation().computeStats();
            if (this.getPopulation().getBestIndividual().getFitness()>=this.getProblem().getTargetFitness()){ 
                    console.log("this.population.getBestIndividual().getFitness()="+this.getPopulation().getBestIndividual().getFitness());
                    console.log("this.problem.targetFitness="+this.getProblem().getTargetFitness());
                    this.showSolutionFound();
                    this.setSolutionFound(true);
            }                
        } 
        this.setStep(this.getStep() + 1); 	
        return pos; //Devolvemos la posición de reemplazo.
    }

    public void showSolutionFound(){
            console.log("Solution Found! After ");
            console.log(""+this.getProblem().getFitnessCounter());
            console.log(" evaluations");    		
    }

    public Individual showSolution(){
        console.log("FINNNNNNNNNNNNNNNNN");
        console.log("  FITNESS: "+this.getPopulation().getBestIndividual().getFitness());
        console.log(" CHROMOSOME: "+JSON.stringify(this.getPopulation().getBestIndividual())); 
        return this.getPopulation().getBestIndividual();
    }

    public boolean hasFinished(){
        if (this.isSolutionFound() || this.getStep()>this.getMaxSteps()){
            console.log("this.solutionFound="+this.isSolutionFound());
            console.log("this.step>this.maxSteps="+(this.getStep()>this.getMaxSteps()));
            console.log("this.step="+this.getStep());
            console.log("this.maxSteps="+this.getMaxSteps());
            return true;
        } else {
            return false;
        }     		
    }
	    
    public Population getPopulation (){ //Solo lo utiliza MonitorAlgorithm
	return this.population;  	
    }  
//    public void doReplacements (Individual[] replacements){
//    	var myMethod="doReplacements(replacements)"
//    	Output.showInfo(outputPriority,myClass,myMethod,"STEP "+this.step);  
//        for (var i=0;i<replacements.length;i++){
//            this.population.replace(Common.Elements.Individual.fromJSON(replacements[i].indiv), replacements[i].pos);
//        }      			
//    }       
    
    public Individual run (){
		throw new Error("Abstract Method.");    	
    }    

    /**
     * @param population the population to set
     */
    public void setPopulation(Population population) {
        this.population = population;
    }

    /**
     * @return the problem
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * @param problem the problem to set
     */
    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    /**
     * @return the probCross
     */
    public double getProbCross() {
        return probCross;
    }

    /**
     * @param probCross the probCross to set
     */
    public void setProbCross(double probCross) {
        this.probCross = probCross;
    }

    /**
     * @return the probMut
     */
    public double getProbMut() {
        return probMut;
    }

    /**
     * @param probMut the probMut to set
     */
    public void setProbMut(double probMut) {
        this.probMut = probMut;
    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @return the step
     */
    public int getStep() {
        return step;
    }

    /**
     * @param step the step to set
     */
    private void setStep(int step) {
        this.step = step;
    }

    /**
     * @return the maxSteps
     */
    public int getMaxSteps() {
        return maxSteps;
    }

    /**
     * @param maxSteps the maxSteps to set
     */
    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    /**
     * @return the solutionFound
     */
    public boolean isSolutionFound() {
        return solutionFound;
    }

    /**
     * @param solutionFound the solutionFound to set
     */
    public void setSolutionFound(boolean solutionFound) {
        this.solutionFound = solutionFound;
    }
    
	
}
