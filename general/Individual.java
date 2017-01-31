
package general;

import Chromosome.ChromosomeAbstract;

public class Individual implements Cloneable {
    private ChromosomeAbstract chromosome;
    private double fitness;
    
//    public static fromJSON(String json){
//        //TODO
//    }  
    
    //toString

    


    public Individual clone(){
        Individual clone = null;
        try {
            clone = (Individual )super.clone();
            clone.setChromosome(this.chromosome.clone());
            clone.setFitness(this.fitness);        
        } catch (Exception e){
            e.printStackTrace();
            new RuntimeException();
        }

        return clone;
    }
    public  void initialize(int size, Class c){
        try {        
            this.setChromosome((ChromosomeAbstract) c.newInstance());
            this.getChromosome().initialize(size);
        } catch (Exception e){
            e.printStackTrace();
            new RuntimeException();
        }
        
    }       
    

    /**
     * @return the chromosome
     */
    public ChromosomeAbstract getChromosome() {
        return chromosome;
    }

    /**
     * @param chromosome the chromosome to set
     */
    public void setChromosome(ChromosomeAbstract chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * @return the fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

}
