/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _a_a_ordenarimagen;

import java.util.ArrayList;

/**
 *
 * @author SinPermisos
 */
public class Prueba2 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(7);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);

        System.out.println(list.toString());


        //CASE 1: We are removing data object 7.
        list.remove(new Integer(7));

        System.out.println(list.toString());

        //CASE 2: Again we are removing data object 7.
        list.remove(new Integer(7));

        System.out.println(list.toString());

        //CASE 3: We are removing data at index 1
        list.remove(1);

        System.out.println(list.toString());
    }
            
}
