
package problem;

import general.Individual;

public abstract class Problem {
    private double targetFitness;
    private int fitnessCounter = 0;      
    public abstract double evaluateStep(Individual indiv);

    /**
     * @return the targetFitness
     */
    public double getTargetFitness() {
        return targetFitness;
    }

    /**
     * @param targetFitness the targetFitness to set
     */
    public void setTargetFitness(double targetFitness) {
        this.targetFitness = targetFitness;
    }

    /**
     * @return the fitnessCounter
     */
    public int getFitnessCounter() {
        return fitnessCounter;
    }

    /**
     * @param fitnessCounter the fitnessCounter to set
     */
    public void setFitnessCounter(int fitnessCounter) {
        this.fitnessCounter = fitnessCounter;
    }
}
