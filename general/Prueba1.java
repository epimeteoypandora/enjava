
package general;

import Chromosome.ChromosomeVRP;

public class Prueba1 {
    public static void main(String[] args) {
        Individual i = new Individual();
        i.initialize(10, ChromosomeVRP.class);
        Individual i2 = i.clone();
        
        i.getChromosome().setAllele(3, 99);
        
        System.out.println(i.getChromosome().getAllele(3));
        System.out.println(i2.getChromosome().getAllele(3));
    }
}
