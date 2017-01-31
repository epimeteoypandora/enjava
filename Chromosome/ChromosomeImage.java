package Chromosome;

import general.console;
import global.ImageStatic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;


public class ChromosomeImage extends ChromosomeAbstract {
//    public static fromJSON(String json){
//        //TODO
//    }


  

//    @Override
//    public ChromosomeAbstract cross(ChromosomeAbstract chro, double probCross) {
//        System.out.println("crossimage");
//        ChromosomeAbstract son = this.clone();
//        
//        int pos = MyRandom.getSingleton().nextInt(son.getSize());
//        int row = son.getAllele(pos);
//        ArrayList<Integer> elements = new ArrayList(Arrays.asList(son.getAlleles()));
//        elements.remove(row);
//        
//        int nextRow = ImageStatic.getNextRow(row,  elements);
//        int nextPos=elements.indexOf(nextRow);
//        elements.add(nextPos, row);
//        son.setAlleles(elements.toArray(son.getAlleles()));
//        return this;
//    }
    
//    @Override
//    public ChromosomeAbstract cross(ChromosomeAbstract chro, double probCross) {
////        System.out.println("crossimage");
//        ChromosomeAbstract son = this.clone();
//        
//        int pos = MyRandom.getSingleton().nextInt(son.getSize());
//        ArrayList<Integer> rowsChosen = new ArrayList();
//        int temp=-1;
//        for (int i=0;i<16;i++){
//            temp=pos+i;
//            if (temp>=son.getSize()){
//                temp=temp-son.getSize();
//            }
//            if (temp==-1){
//                System.out.println("pos="+pos);
//                System.out.println("i="+i);
//                System.out.println("temp="+temp);
//                System.out.println("son.getSize()="+son.getSize());
//            }
//            
//            rowsChosen.add(son.getAllele(temp));
//        }
//
//        ArrayList<Integer> elements = new ArrayList(Arrays.asList(son.getAlleles()));
//        elements.removeAll(rowsChosen);
//        
////        int nextRow = ImageStatic.getNextRow(rowsChosen.get(0),  elements);
////        int nextPos=elements.indexOf(nextRow);
//        int nextPos = MyRandom.getSingleton().nextInt(elements.size());
//        elements.addAll(nextPos,rowsChosen);
//        son.setAlleles(elements.toArray(son.getAlleles()));
//        return this;
//    }    

    
    @Override
    public ChromosomeAbstract cross(ChromosomeAbstract chro, double probCross) {
//        System.out.println("crossimage");
        ChromosomeAbstract son = this.clone();
 
        int nGroups=64;
        int nElements=4;        
//        int nGroups=128;
//        int nElements=4;
//        int nGroups=64;
//        int nElements=8;
        
        int groupPos = MyRandom.getSingleton().nextInt(nGroups); //32 grupos de 16
        int pos=groupPos*nElements;
        ArrayList<Integer> rowsChosen = new ArrayList();
        
        int temp=-1;
        for (int i=0;i<nElements;i++){
            temp=pos+i;
            if (temp>=son.getSize()){
                temp=temp-son.getSize();
            }           
            rowsChosen.add(son.getAllele(temp));
        }
        
        ////////////////// ORDENAMOS LAS FILAS ELEGIDAS POR PROXIMIDAD A PARTIR DE UNA AL AZAR
        int randomOrderedRow=MyRandom.getSingleton().nextInt(nElements);
        ArrayList<Integer> orderedRowChosen=new ArrayList<Integer>();
        Integer orderedNext=rowsChosen.get(randomOrderedRow);
        orderedRowChosen.add(orderedNext);
        rowsChosen.remove(orderedNext);
        while (rowsChosen.size()>0){
            orderedNext=ImageStatic.getNextRow(orderedNext, rowsChosen);
            orderedRowChosen.add(orderedNext);
            rowsChosen.remove(orderedNext);            
        }
        /////////////////

        ArrayList<Integer> elements = new ArrayList(Arrays.asList(son.getAlleles()));
        elements.removeAll(rowsChosen);
        
//        int nextRow = ImageStatic.getNextRow(rowsChosen.get(0),  elements);
//        int nextPos=elements.indexOf(nextRow);

        int nextGroupPos = MyRandom.getSingleton().nextInt(nGroups); //32 grupos de 16
        elements.addAll(nextGroupPos*nElements,rowsChosen);
        son.setAlleles(elements.toArray(son.getAlleles()));
        return this;
    }        
    
  //AMBOS INCLUIDOS
  private Integer[] reverseSubarray(int p, int q, Integer[] array){ 
        if (q<p){
            int temp =q;
            q=p;
            p=temp;
        }

        ArrayList<Integer> subarray=new ArrayList<Integer>(Arrays.asList(array).subList(p, q+1));
        Collections.reverse(subarray);
            for (int i=0;i<subarray.size();i++){
                array[i+p]=subarray.get(i);            
            }    
        return array;  
  }    
 
  private Integer[] moveElementOfArray(int p, int q, Integer[] array){ 
    int value = array[p];
    ArrayList<Integer> copy=new ArrayList(Arrays.asList(array));
    copy.remove(p);
    copy.add(q, value);
    return copy.toArray(array); 
  }  
 
  private Integer[] swapElementsOfArray(int p, int q, Integer[] array){ 
    int temp = array[q];
    array[q]=array[p];
    array[p]=temp;     
    return array;    
  }
  
    @Override
    public ChromosomeAbstract mutate(double probMut) {
        for (int i=0; i<this.getSize();i++){
            if (Math.random()<probMut){
                double r = Math.random();
                if (r<0.33){
                    //Inversion Mutate (p,q,array)                 
                    this.setAlleles(reverseSubarray(i,MyRandom.getSingleton().nextInt(this.getAlleles().length),this.getAlleles()));
                } else if (r>0.66){
                    //Insertion Mutate (p,q,array)
                    
                    this.setAlleles(moveElementOfArray(i,MyRandom.getSingleton().nextInt(this.getAlleles().length),this.getAlleles()));
                } else {
                    //SWAP Mutate (p,q,array)
                    this.setAlleles(swapElementsOfArray(i,MyRandom.getSingleton().nextInt(this.getAlleles().length),this.getAlleles()));
                }
            }             
        }         
        return this; 	
    }



    private int getNext(ChromosomeVRP chro, int i){
        if ((i+1)>=chro.getSize()){
            return  chro.getAllele(0);
        } else {
            return  chro.getAllele(i+1);
        }
    }
    private int getPrevious(ChromosomeVRP chro,int i){
        if ((i-1)<0){
            return  chro.getAllele(chro.getSize()-1);
        } else {
            return  chro.getAllele(i-1);
        }
    }
    
    public void getNeighbors(ChromosomeVRP chro, int value, HashSet axis){
        for (int i=0;i<chro.getSize();i++){
            if (chro.getAllele(i)==value){
                axis.add(getNext(chro,i)); 
                axis.add(getPrevious(chro,i));             
               return;
            }
        }
    }
    
}
