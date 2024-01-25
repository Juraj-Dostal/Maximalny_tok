/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package atginput;

import java.util.ArrayList;

/**
 *
 * @author Maroš
 */
public class ATGInput {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Input input = new Input();
        String name = "pr1.hrn";
        input.readData(name);
        System.out.println("Hran: " + input.getPocetHran());
        System.out.println("Vrcholov: " + input.getPocetVrchol());

        //////////////////////
        //Použitie polí H a S od doc. palúcha 
        //////////////////////
        int[][] h = input.getH();
        System.out.println("Hrany:");
        for (int i = 0; i < h.length; i++) {
            System.out.println(i + ": " + h[i][0] + " " + h[i][1] + " " + h[i][2]);
        }
        System.out.println("Smerniky:");
        int[] s = input.getS();
        for (int i = 0; i < s.length; i++) {
            System.out.println(i + " " + s[i]);
        }
        //zoradenie podľa 3. stĺpca(cena hrany)
        input.sortHByThirdColumn();
        h = input.getH();
        System.out.println("Hrany podla 3. stlpca:");
        for (int i = 0; i < h.length; i++) {
            System.out.println(i + ": " + h[i][0] + " " + h[i][1] + " " + h[i][2]);
        }

        //////////////////////
        //Použitie zoznamu hrán - objekty
        ////////////////////// 
        ArrayList<Hrana> zoznamHran = input.getZoznamHran();
        System.out.println("Hrany:");
        for (int i = 0; i < zoznamHran.size(); i++) {
            Hrana hrana = zoznamHran.get(i);
            System.out.println(i + ": " + hrana.getVrcholZ() + " " + hrana.getVrcholDo() + " " + hrana.getPriepustnost());
        }
        System.out.println("Smerniky:");
        ArrayList<Integer> smernikVrcholov = input.getSmernikVrcholov();
        for (int i = 0; i < smernikVrcholov.size(); i++) {
            System.out.println(i + " " + smernikVrcholov.get(i));
        }
        input.sortZoznamHranByThirdColumn();
        zoznamHran = input.getZoznamHran();
        System.out.println("Hrany podla 3. stlpca:");
        for (int i = 0; i < zoznamHran.size(); i++) {
            Hrana hrana = zoznamHran.get(i);
            System.out.println(i + ": " + hrana.getVrcholZ() + " " + hrana.getVrcholDo() + " " + hrana.getPriepustnost());
        }
    }

}
