
package _a_a_ordenarimagen;

import java.util.ArrayList;
import java.util.Random;

public class _A_A_ordenarImagen {

    public static void printMatrix(int[][] matrix){
        for (int i=0;i<matrix.length;i++){
            for (int k=0;k<matrix.length;k++){
                System.out.print(matrix[i][k]);
            }            
            System.out.println();
        }
    }
    
    
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
    public static int getNextRow(int row,int[][] distanceMatrix, ArrayList<Integer> elements){
        int chosen=-1;
        int minDistance=999999999;
        
        for (int i=0;i<elements.size();i++){
            if (distanceMatrix[row][elements.get(i)]<minDistance){
                if (row!=elements.get(i)){
                    minDistance=distanceMatrix[row][elements.get(i)];
                    chosen=elements.get(i);                
                }
            }
        }
        return chosen;     
    }
    public static ArrayList<Integer> getOrderedRows(int[][]  image,int[][] distanceMatrix){
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
            nextRow=getNextRow(nextRow,distanceMatrix,elements);
//            System.out.println("next row "+nextRow);
//            System.out.println(elements.size());
//            System.out.println(nextRow);
            elements.remove(new Integer(nextRow));
//            System.out.println(elements.toString());
            result.add(nextRow);            
        }
        return result;
    }
    
    
    
    public static void main(String[] args) {
        int[][] image=ReadFile.readImage("./imagen2.txt");
        //System.out.println(image);
        //printMatrix(image);
        

        int[][] distanceMatrix=getDistanceMatrix(image);
        //printMatrix(distanceMatrix);
        ArrayList<Integer> result = getOrderedRows(image,distanceMatrix);
        int [] r=new int[image.length];
        for (int i=0;i<r.length;i++){
            r[i]=result.get(i);
        }
        System.out.println("tamaño del resultado ="+r.length);
        ReadFile.saveImage(r, image);
    }
    
}
