/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author SinPermisos
 */
public class OtraPrueba {
    public static void main(String[] args) {
        Integer[] a = new Integer[10];
        for (int i=0;i<10;i++){
            a[i]=i;
        }
        Integer[] b = a.clone();
        ArrayList<Integer> aa = new ArrayList(Arrays.asList(a));
        ArrayList<Integer> bb = (ArrayList<Integer>)aa.clone();
        
        a[3]=99;
        System.out.println(a[3]);
        System.out.println(b[3]);
                aa.set(3, 99);
        System.out.println(aa.get(3));
        System.out.println(bb.get(3));
    }
}
