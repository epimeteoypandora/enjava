
package general;

import java.util.Arrays;
import java.util.Collections;


public class Prueba2 {
    public static void main(String[] args) {
        int size=10;
        int[] alleles=new int[size];
        for (int i=0;i<size;i++){
            alleles[i]=i;
        }          
        
        Collections.shuffle(Arrays.asList(alleles));
        for (int i=0;i<size;i++){
            System.out.println(alleles[i]);
        }          
    }
}
