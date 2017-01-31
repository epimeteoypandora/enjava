package global;

import java.util.ArrayList;
import java.util.Random;

public class ImageStatic {
    public static int[][] image;
    public static int[][] matrixCost;
   
    public static int getNextRow(int row, ArrayList<Integer> elements){
        int chosen=-1;
        int minDistance=999999999;
        
        for (int i=0;i<elements.size();i++){
            if (matrixCost[row][elements.get(i)]<minDistance){
                if (row!=elements.get(i)){
                    minDistance=matrixCost[row][elements.get(i)];
                    chosen=elements.get(i);                
                }
            }
        }
        return chosen;     
    }
    public static Integer[] getOrderedRows(int[][]  image){
        Random r = new Random();
        ArrayList<Integer> elements = new ArrayList<Integer>();
        for (int i=0;i<image.length;i++){
            elements.add(i);
        }    
        
//        System.out.println(elements.toString());
        ArrayList<Integer> result = new ArrayList<Integer>();    
        int nextRow = r.nextInt(image.length);
        elements.remove(new Integer(nextRow));
        result.add(nextRow);
        while (elements.size()>0){
            nextRow=getNextRow(nextRow,elements);
//            System.out.println("next row "+nextRow);
//            System.out.println(elements.size());
//            System.out.println(nextRow);
            elements.remove(new Integer(nextRow));
//            System.out.println(elements.toString());
            result.add(nextRow);            
        }
        Integer[] returnType=new Integer[result.size()];
        return result.toArray(returnType);
    }     
    
}