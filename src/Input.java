/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package atginput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Maroš
 */
public class Input {

    private ArrayList<Integer> smernikVrcholov; // smerníky na prvu hranu pre dany vrchol pomocou arraylistu
    private ArrayList<Hrana> zoznamHran; // zoznam hran (arraylist) pomocou objektu
    private int[][] H; // zoznam hran pomocou matice H[i][0] je zaciatocny vrchol, H[i][1] je koncovy vrchol a H[i][3] je cena hrany
    private int[] S; // smerniky na prvu hranu pre dany vrchol
    private int pocetVrchlov;
    private int pocetHran;

    /**
     * Metóda na načítanie hrán, ich zoradenie podľa prvého a druhého stĺpca a
     * vytvorenie poľa smerníkov na vrcholy
     *
     * @param name Názov súboru, z ktorého načítať hrany
     */
    public void readData(String name) {
        readFile(name);
        sortHByFirstAndSecondColumn();
        sortZoznamHranByFirstAndSecondColumn();
        createSmernikVrcholov();
    }

    /**
     * Načítanie súboru s hranami a vyzvorenie zoznamov hrán, respektíve poľa
     * hreán H
     *
     * @param name Názov súboru, z ktorého načítať hrany
     */
    public void readFile(String name) {
        pocetVrchlov = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(name)));
            int lines = 0;
            while (br.readLine() != null) {
                lines++;
            }
            br.close();
            H = new int[lines + 1][3];
            zoznamHran = new ArrayList<>(lines + 1);
            int index = 1;
            br = new BufferedReader(new FileReader(new File(name)));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    String replace = line.trim().replaceAll("\\s+", " ");
                    String[] split = replace.split(" ");
                    int vrchZ = Integer.parseInt(split[0]);
                    int vrchDo = Integer.parseInt(split[1]);
                    int cena = Integer.parseInt(split[2]);
                    zoznamHran.add(new Hrana(vrchZ, vrchDo, cena));
                    H[index][0] = vrchZ;
                    H[index][1] = vrchDo;
                    H[index][2] = cena;
                    index++;
                    if (pocetVrchlov < vrchZ) {
                        pocetVrchlov = vrchZ;
                    }
                    if (pocetVrchlov < vrchDo) {
                        pocetVrchlov = vrchDo;
                    }
                }
            }
            zoznamHran.add(0, new Hrana(0, 0, 0));
            pocetHran = H.length;
            br.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Subor neexistuje");
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        }
    }

    /**
     * Metóda na vytvorenie smerníkov do poľa hrán pre jednotlivé vrcholy
     */
    private void createSmernikVrcholov() {
        smernikVrcholov = new ArrayList<>(pocetVrchlov + 2);
        S = new int[pocetVrchlov + 2];
        for (int i = 0; i < pocetVrchlov + 2; i++) {
            smernikVrcholov.add(0);
        }
        for (int i = 1; i < zoznamHran.size(); i++) {
            int vrchol = zoznamHran.get(i).getVrcholZ();
            if (smernikVrcholov.get(vrchol) == 0) {
                smernikVrcholov.set(vrchol, i);
            }
            if (S[vrchol] == 0) {
                S[vrchol] = i;
            }
        }
        smernikVrcholov.set(pocetVrchlov + 1, zoznamHran.size());
        S[pocetVrchlov + 1] = pocetHran;
        for (int i = pocetVrchlov; i >= 1; i--) {
            if (smernikVrcholov.get(i) == 0) {
                smernikVrcholov.set(i, smernikVrcholov.get(i + 1));
            }
            if (S[i] == 0) {
                S[i] = S[i + 1];
            }
        }
    }

    /**
     * Metóda na zoradenie poľa H podľa prvého stĺpca (vrchol z)
     */
    public void sortHByFirstColumn() {
        Arrays.sort(H, (a, b) -> Integer.compare(a[0], b[0]));
    }

    /**
     * Metóda na zoradenie poľa H podľa prvého a druhého stĺpca (vrchol z,
     * vrchol do)
     */
    public void sortHByFirstAndSecondColumn() {
        Arrays.sort(H, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] < o2[0]) {
                    return -1;
                }
                if (o1[0] > o2[0]) {
                    return 1;
                }
                if (o1[1] < o2[1]) {
                    return -1;
                }
                if (o1[1] > o2[1]) {
                    return 1;
                }
                return 0;
            }
        });
    }

    /**
     * Metóda na zoradenie poľa H podľa druhého stĺpca (vrchol do)
     */
    public void sortHBySecondColumn() {
        Arrays.sort(H, (a, b) -> Integer.compare(a[1], b[1]));
    }

    /**
     * Metóda na zoradenie poľa H podľa tretieho stĺpca (cena hrany)
     */
    public void sortHByThirdColumn() {
        Arrays.sort(H, (a, b) -> Integer.compare(a[2], b[2]));
    }

    /**
     * Metóda na zoradenie zoznamu hrán podľa prvého a druhého stĺpca (vrchol z,
     * vrchol do)
     */
    public void sortZoznamHranByFirstAndSecondColumn() {
        zoznamHran.sort(new Comparator<Hrana>() {
            @Override
            public int compare(Hrana o1, Hrana o2) {
                if (o1.getVrcholZ() < o2.getVrcholZ()) {
                    return -1;
                }
                if (o1.getVrcholZ() > o2.getVrcholZ()) {
                    return 1;
                }
                if (o1.getVrcholDo() < o2.getVrcholDo()) {
                    return -1;
                }
                if (o1.getVrcholDo() > o2.getVrcholDo()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    /**
     * Metóda na zoradenie zoznamu hrán podľa prvého stĺpca (vrchol z)
     */
    public void sortZoznamHranByFirstColumn() {
        zoznamHran.sort(new Comparator<Hrana>() {
            @Override
            public int compare(Hrana o1, Hrana o2) {
                if (o1.getVrcholZ() < o2.getVrcholZ()) {
                    return -1;
                }
                if (o1.getVrcholZ() > o2.getVrcholZ()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    /**
     * Metóda na zoradenie zoznamu hrán podľa druhého stĺpca (vrchol do)
     */
    public void sortZoznamHranBySecondColumn() {
        zoznamHran.sort(new Comparator<Hrana>() {
            @Override
            public int compare(Hrana o1, Hrana o2) {
                if (o1.getVrcholDo() < o2.getVrcholDo()) {
                    return -1;
                }
                if (o1.getVrcholDo() > o2.getVrcholDo()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    /**
     * Metóda na zoradenie zoznamu hrán podľa tretieho stĺpca (cena hrany)
     */
    public void sortZoznamHranByThirdColumn() {
        zoznamHran.sort(new Comparator<Hrana>() {
            @Override
            public int compare(Hrana o1, Hrana o2) {
                if (o1.getPriepustnost() < o2.getPriepustnost()) {
                    return -1;
                }
                if (o1.getPriepustnost() > o2.getPriepustnost()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    /**
     *
     * @return zoznam smerníkov do zoznamu hrán pre všetky vrcholy
     */
    public ArrayList<Integer> getSmernikVrcholov() {
        return smernikVrcholov;
    }

    /**
     *
     * @return zoznam hrán
     */
    public ArrayList<Hrana> getZoznamHran() {
        return zoznamHran;
    }

    /**
     *
     * @return pole hrán H
     */
    public int[][] getH() {
        return H;
    }

    /**
     *
     * @return pole smerníkov S do poľa hrán H
     */
    public int[] getS() {
        return S;
    }

    /**
     *
     * @return počet vrcholov grafu ()
     */
    public int getPocetVrchol() {
        return pocetVrchlov;
    }

    /**
     *
     * @return počet hrán grafu (pripočítaná aj fiktívna hrana 0)
     */
    public int getPocetHran() {
        return pocetHran;
    }
    /**
     * Nacitanie zaciatkov cinnosti
     * @param name
     * @param pocetVrcholov
     * @return 
     */
    public int[] nacitajTrvaniaCinnosti(String name, int pocetVrcholov) {
        int[] trvania = new int[pocetVrcholov + 1];
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(name)));
            br.close();
            br = new BufferedReader(new FileReader(new File(name)));
            String line;
            for (int i = 1; i < pocetVrcholov + 1; i++) {
                line = br.readLine();
                int trv = Integer.parseInt(line);
                trvania[i] = trv;
            }
            br.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Subor neexistuje");
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        }
        return trvania;
    }

    /**
     * Metóda na zoradenie poľa H podľa tretieho stĺpca (cena hrany) od
     * najdrahšej
     */
    public void sortHByThirdColumnDesc() {
        Arrays.sort(H, (a, b) -> Integer.compare(b[2], a[2]));
    }
}
