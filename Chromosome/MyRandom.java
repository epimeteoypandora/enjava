package Chromosome;

import java.util.Random;

public class MyRandom {
    private static Random r=null;
    private MyRandom(){}
    
    public static Random getSingleton(){
        if (r==null) r=new Random();
        return r;
    }
}
