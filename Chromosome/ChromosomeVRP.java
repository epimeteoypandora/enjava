package Chromosome;

import general.console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;


public class ChromosomeVRP extends ChromosomeAbstract {
//    public static fromJSON(String json){
//        //TODO
//    }

public int addSmaller(int position,ArrayList<HashSet> axisList){
    Integer valueBorrar=null;
    HashSet axis = axisList.get(position);
    int smaller=-1;
    try {
        Iterator<Integer> it = axis.iterator();
        while (it.hasNext()){
            Integer value=it.next();
            valueBorrar=value;
            if (smaller==-1){          
                smaller=value;
            } else if(axisList.get(value).size()<axisList.get(smaller).size()){            
                smaller=value;
            }        
        }            
    } catch (Exception e){
        e.printStackTrace();
        console.log(e.toString());
        throw new RuntimeException(e);
    }    
 
    return smaller;
}
    
    
public void abcdef(int lastAdded, ArrayList<HashSet> axisList, ChromosomeVRP son){
    while (axisList.get(lastAdded).size()>0){
        lastAdded=addSmaller(lastAdded,axisList);
        for (HashSet ax : axisList){
           ax.remove(lastAdded);
        }             
        son.addAllele(lastAdded); 
        abcdef(lastAdded, axisList, son);
    } 
        return; 
}    

    @Override
    public ChromosomeAbstract cross(ChromosomeAbstract ccc, double probCross) {
        ////////////////////////////////////////
        //Operador de recombinación de ejes(ERX)
         //////////////////////////////////////// 
         ChromosomeVRP chro2=(ChromosomeVRP)ccc;
        if (Math.random()<probCross){
            ArrayList<HashSet> axisList = new ArrayList<HashSet>();
            for (int i=0;i<this.getSize();i++){
                HashSet axis = new HashSet();
                getNeighbors(this,i,axis);       
                getNeighbors(chro2,i,axis);    
                axisList.add(axis);
            }

            //Cogemos el primer gen de uno de los padres(el que tenga menor número de ejes).
            ChromosomeVRP son = new ChromosomeVRP();
            int firstParent1 = this.getAllele(0);
            int firstParent2 = chro2.getAllele(0); 
            int lastAdded=-1;      
            if (axisList.get(firstParent1).size()<=axisList.get(firstParent2).size()){
                son.addAllele(firstParent1);
                for (HashSet ax : axisList){
                    ax.remove(firstParent1);
                }
                lastAdded=firstParent1;
            } else {
                son.addAllele(firstParent2);
                for (HashSet ax : axisList){
                    ax.remove(firstParent2);
                }                
                lastAdded=firstParent2;        
            }
            
            //Luego se va al eje de la última posición añadida
            //Se recorre dicho eje y se cogen los valores para ver que posición tiene menos ejes.
            //
  
            abcdef(lastAdded, axisList, son);

            return son;
        } else {
            //Return chro1 o chro2 en caso de que no haya cruce.
           return Math.random()>=0.5?this.clone():chro2.clone();
        }
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
