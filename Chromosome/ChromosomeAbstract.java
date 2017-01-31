
package Chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class ChromosomeAbstract implements Cloneable{
    private Integer[] alleles;
    private int nextPositionToAdd;
    
    public ChromosomeAbstract clone(){
        ChromosomeAbstract clone = null;
        try {
            clone = (ChromosomeAbstract )super.clone();
            clone.alleles=this.alleles.clone();       
        } catch (Exception e){
            e.printStackTrace();
            new RuntimeException();
        }
        return clone;
    }    
    
    public void initialize(int size) {
        this.alleles=new Integer[size];
        for (int i=0;i<size;i++){
            this.alleles[i]=i;
        }   
        Collections.shuffle(Arrays.asList(this.alleles));      //TODO -> CAMBIAR A ALGO MÁS EFICIENTE
    }
    
    public int getAllele(int index){
        return alleles[index];
    }
    public void setAllele(int index, int value){
        this.alleles[index]=value;
    }
    public int getSize(){
        return this.alleles.length;
    }
    public String toString(){
        //GSON
        return super.toString();
    }
    
    public abstract ChromosomeAbstract cross(ChromosomeAbstract cro2, double probCross);

    public abstract ChromosomeAbstract mutate(double probMut);    
    
    public Integer[] getAlleles(){
        return alleles;
    }
    public void setAlleles(Integer[] alleles){
        this.alleles=alleles;
    }    
    
    public int findAllelePos( int value ){     
        return Arrays.asList(alleles).indexOf(value);     	
    }    
    public void reverseSubGroup( int index1, int index2 ){
        //+1 porque slice no incluye última posición y yo la necesito.
        
        ArrayList<Integer> subarray=new ArrayList<Integer>(Arrays.asList(alleles).subList(index1, index2+1));
        Collections.reverse(subarray);
        for (int i=0;i<subarray.size();i++){
            this.alleles[i+index1]=subarray.get(i);            
        }
	
    }    
    
    public void addAllele(int all){ //TODO -> arreglar esta chapucilla
        if (this.alleles==null){
            this.alleles=new Integer[1];
        } else {
            Integer[] copy = this.alleles.clone();
            this.alleles = new Integer[copy.length+1];
            for (int k=0;k<copy.length;k++){
                this.alleles[k]=copy[k];
            }
        }
        this.alleles[this.nextPositionToAdd]=all;
        this.nextPositionToAdd++;
    }
}
