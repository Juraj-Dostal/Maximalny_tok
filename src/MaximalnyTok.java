import java.util.ArrayList;

public class MaximalnyTok {

    private int[] x;
    private ArrayList<Hrana> zoznamHran;
    private int zdroj;
    private int ustie;
    private int pocetVrcholov;
    private ArrayList<Hrana> zvacsujucaPolocesta;
    private int rezervaPolocesty;
    private int maximalnyTok;


    public MaximalnyTok(Input input) {
        this.zoznamHran = input.getZoznamHran();
        this.pocetVrcholov = input.getPocetVrchol();

        this.maximalnyTok = 0;
        this.zvacsujucaPolocesta = new ArrayList<>();

        for (var aktualny: this.zoznamHran) {
            aktualny.setTok(0);
        }

        this.x = new int[pocetVrcholov + 1];

        this.najdiZdroj();
        this.najdiUstie();

        this.najdiMaximalnyTok();

        this.vypisMaximalnyTok();

        System.out.println("Zdroj: " + this.zdroj + ", ustie:" + this.ustie);
    }

    private void najdiMaximalnyTok() {
        boolean naslo = this.najdiZvacsujucuPolocestu();
        if(naslo) {
            this.zvacTok();
            this.najdiMaximalnyTok();
        }
    }

    public void zvacTok() {
        this.maximalnyTok += this.rezervaPolocesty;

        int vrchol = this.ustie;

        for (var aktualny: this.zvacsujucaPolocesta) {
            if (this.x[vrchol] > 0) {
                aktualny.setTok(aktualny.getTok() + this.rezervaPolocesty);

            } else if (this.x[vrchol] < 0) {
                aktualny.setTok(aktualny.getTok() - this.rezervaPolocesty);
            }
            vrchol = this.x[vrchol];
        }
    }

    public boolean najdiZvacsujucuPolocestu() {
        var epsilon = new ArrayList<Integer>();

        for (int i = 1; i < this.x.length; i++) {
            this.x[i] = Integer.MAX_VALUE/2;
        }
        this.x[this.zdroj] = 0;
        epsilon.add(this.zdroj);

        while(this.x[this.ustie] == Integer.MAX_VALUE/2) {
            if (epsilon.isEmpty()) {
                return false;
            }

            int vrchol = epsilon.get(0);
            epsilon.remove(0);

            for (var aktualny : this.zoznamHran) {
                var vrcholZ = aktualny.getVrcholZ();
                var vrcholDo = aktualny.getVrcholDo();

                if (vrcholZ == vrchol) {
                    if (this.x[vrcholDo] == Integer.MAX_VALUE / 2 && aktualny.getPriepustnost() > aktualny.getTok()) {
                        this.x[vrcholDo] = vrchol;
                        epsilon.add(vrcholDo);
                    }
                } else if (vrcholDo == vrchol && aktualny.getTok() > 0) {
                    if (this.x[vrcholDo] == Integer.MAX_VALUE / 2) {
                        this.x[vrcholDo] = -vrchol;
                        epsilon.add(vrcholDo);
                    }
                }
            }
        }

        this.zvacsujucaPolocesta.clear();

        var rezervaToku = Integer.MAX_VALUE;

        int i = this.ustie;

        while (i > 0) {
            if (this.x[i] > 0) {
                var vrcholZ = this.x[i];
                var vrcholDo = i;

                for (var aktualny: this.zoznamHran) {
                    if (aktualny.getVrcholZ() == vrcholZ && aktualny.getVrcholDo() == vrcholDo) {
                        this.zvacsujucaPolocesta.add(aktualny);
                        if (rezervaToku > aktualny.getPriepustnost() - aktualny.getTok()) {
                            rezervaToku = aktualny.getPriepustnost() - aktualny.getTok();
                        }
                        continue;
                    }
                }
            } else if (this.x[i] < 0) {
                var vrcholZ = i;
                var vrcholDo = Math.abs(this.x[i]);

                for (var aktualny: this.zoznamHran) {
                    if (aktualny.getVrcholZ() == vrcholZ && aktualny.getVrcholDo() == vrcholDo) {
                        this.zvacsujucaPolocesta.add(aktualny);
                        if (rezervaToku > aktualny.getTok()) {
                            rezervaToku = aktualny.getTok();
                        }
                        continue;
                    }
                }
            }
            i = Math.abs(this.x[i]);
        }

        this.rezervaPolocesty = rezervaToku;

        return true;
    }

    public void najdiZdroj() {
        boolean[] moznyZdroj = new boolean[this.pocetVrcholov + 1];
        boolean[] nieJeZdroj = new boolean[this.pocetVrcholov + 1];
        for (var aktualny: this.zoznamHran) {
            var vrcholZ = aktualny.getVrcholZ();
            var vrcholDo = aktualny.getVrcholDo();
            nieJeZdroj[vrcholDo] = true;
            moznyZdroj[vrcholDo] = false;

            if (!nieJeZdroj[vrcholZ]) {
                moznyZdroj[vrcholZ] = true;
            }
        }

        for (int i = 1; i < moznyZdroj.length; i++) {
            if (moznyZdroj[i]) {
                this.zdroj = i;
            }
        }
    }

    public void najdiUstie() {
        boolean[] mozneUstie = new boolean[this.pocetVrcholov + 1];
        boolean[] nieJeUstie = new boolean[this.pocetVrcholov + 1];
        for (var aktualny: this.zoznamHran) {
            var vrcholZ = aktualny.getVrcholZ();
            var vrcholDo = aktualny.getVrcholDo();
            nieJeUstie[vrcholZ] = true;
            mozneUstie[vrcholZ] = false;

            if (!nieJeUstie[vrcholDo]) {
                mozneUstie[vrcholDo] = true;
            }
        }

        for (int i = 1; i < mozneUstie.length; i++) {
            if (mozneUstie[i]) {
                this.ustie = i;
            }
        }
    }

    public void vypisMaximalnyTok() {
        System.out.println("Maximalny tok siete je " + this.maximalnyTok);

        var sb = new StringBuilder();
        sb.append(String.format("%4s | %4s | %4s | %4s\n", "Z", "DO", "c(h)", "y(h)"));
        for (var aktualny : this.zoznamHran) {
            if (this.zoznamHran.indexOf(aktualny) == 0) {
                continue;
            }
            sb.append(String.format("%4s | %4s | %4s | %4s\n", aktualny.getVrcholZ(), aktualny.getVrcholDo(), aktualny.getPriepustnost(), aktualny.getTok()));
        }
        System.out.println(sb.toString());
    }

}
