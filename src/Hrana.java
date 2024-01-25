/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package atginput;

/**
 *
 * @author Maroš
 */
public class Hrana {

    private int vrcholZ;
    private int vrcholDo;
    private int priepustnost;
    private int tok;

    public Hrana(int vrcholZ, int vrcholDo, int priepustnost) {
        this.vrcholZ = vrcholZ;
        this.vrcholDo = vrcholDo;
        this.priepustnost = priepustnost;
    }

    public Hrana(int vrcholZ, int vrcholDo) {
        this.vrcholZ = vrcholZ;
        this.vrcholDo = vrcholDo;
    }

    public int getVrcholZ() {
        return vrcholZ;
    }

    public int getVrcholDo() {
        return vrcholDo;
    }

    public int getPriepustnost() {
        return priepustnost;
    }

    public int getTok() {
        return this.tok;
    }

    public void setTok(int tok) {
        this.tok = tok;
    }
}
