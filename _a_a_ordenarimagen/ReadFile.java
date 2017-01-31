package _a_a_ordenarimagen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ReadFile {
    public static void addRow(String[] rowString, int[][] image, int row){
        for (int i=0;i<(rowString.length-1);i++){
            image[row][i]=Integer.parseInt(rowString[i+1]);
        }
    }
    
    public static void addLinesToImage(ArrayList<String[]> lines, int[][] image){
        for (int i=0;i<lines.size();i++){
            addRow(lines.get(i),image,i);
        }
    }
    
    public static int[][] readImage(String file){
        int[][] image;
        ArrayList<String[]> lines = new ArrayList<String[]>();
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader("./"+file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();           
            lines.add(line.split("\\s+"));
            while (line != null) {
                line = br.readLine();
                if (line!=null)lines.add(line.split("\\s+"));               
            }
            image=new int[lines.size()][lines.get(0).length];
            addLinesToImage(lines,image);
            return image;
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }     
        return null;
    }
    
    
    public static String arrayToString(int[] array){        
        String s = "";   
        for (int i=0;i<array.length;i++){
            s+=array[i]+"\t";
        }   
        s+="\n";
        return s;
    }
    
    public static void saveImage(int[] chromosome, int[][] image){
        String s="";
//        System.out.println("chromosome.length="+chromosome.length);
//        System.out.println("rows="+rows);
//        System.out.println("rowLength="+rowLength); 
        
//        ArrayList<String> sol = new ArrayList<String>();
        
           
    
        for (int i=0;i<chromosome.length;i++){
            System.out.println("image.length="+image.length);
            System.out.println("chromosome.length="+chromosome.length);
            System.out.println("i="+i);            
            System.out.println("chromosome[i]="+chromosome[i]);
            s+=arrayToString(image[chromosome[i]]);
        }   
        
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter("resultado.txt"));
            writer.write(s);

        }
        catch ( IOException e){
            e.printStackTrace();
        }finally{
            try{
                if ( writer != null)
                writer.close( );
            }catch ( IOException e){
            }
        }        
        
    }

}
