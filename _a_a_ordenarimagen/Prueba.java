
package _a_a_ordenarimagen;

import java.util.ArrayList;


public class Prueba {
    public static void main(String[] args){
        ArrayList<Integer> elements = new ArrayList<Integer>();
        for (int i=0;i<100;i++){
            elements.add(i*10);
        }    
        System.out.println(elements.size());        
        elements.remove(new Integer(100));
        System.out.println(elements.size());
    }
}
