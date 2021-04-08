package deplacements;

import plateau.TuyauMorceau;

import java.util.ArrayList;

public class Tuyau extends RealisateurDeDeplacement{

    private static Tuyau tuy;
    private ArrayList<plateau.Tuyau> tuyauxRouges;
    private ArrayList<plateau.Tuyau> tuyauxBleus;

    public Tuyau() {
        tuyauxRouges = new ArrayList<>();
        tuyauxBleus = new ArrayList<>();
    }

    public static Tuyau getDepTuyau() {
        if (tuy == null) {
            tuy = new Tuyau();
        }
        return tuy;
    }

    public void addTuyauBleu(plateau.Tuyau unTuyau) {
        this.tuyauxBleus.add(unTuyau);
    }
    public void addTuyauRouge(plateau.Tuyau unTuyau) {
        this.tuyauxRouges.add(unTuyau);
    }

    public void depTuyauBleu() {
        for (plateau.Tuyau unTuyau: tuyauxBleus) {
            unTuyau.changerPosition();
        }
    }

    public void depTuyauRouge() {
        for (plateau.Tuyau unTuyau: tuyauxRouges) {
            unTuyau.changerPosition();
        }
    }



    @Override
    protected boolean realiserDeplacement() {
        return false;
    }
}
