package deplacements;

import plateau.HighScore;
import plateau.Jeu;

import java.util.ArrayList;
import java.util.Observable;

import static java.lang.Thread.sleep;

public class Ordonnanceur extends Observable implements Runnable {
    private final Jeu jeu;
    private final ArrayList<RealisateurDeDeplacement> lstDeplacements = new ArrayList<>();
    private long pause;
    public boolean stop = false;

    public void add(RealisateurDeDeplacement deplacement) {
        lstDeplacements.add(deplacement);
    }
    private String message = "Vous êtes morts";

    public Ordonnanceur(Jeu _jeu) {
        jeu = _jeu;
    }

    public void start(long _pause) {
        pause = _pause;
        new Thread(this).start();
    }

    @Override
    public void run() {
        boolean update = true;
        long tempsDebut = System.currentTimeMillis();
        while(true) {
            if (stop) break;
            jeu.resetCmptDepl();
            for (RealisateurDeDeplacement d : lstDeplacements) {
                if (d.realiserDeplacement())
                    update = true;
            }

            Controle4Directions.getInstance().resetDirection();

            if (update) {
                setChanged();
                notifyObservers();
            }

            try {
                sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Fin du jeu par les dynamites
            if (jeu.getLesDynamites().size() == 0) {
                message="Félicitation les dynamites ont toutes été récupérés";
                break;
            }
        }
        long tempsFin = System.currentTimeMillis();
        if (message.equals("Félicitation les dynamites ont toutes été récupérés")) {
            double seconds = (tempsFin - tempsDebut) / 1000F;
            new HighScore(seconds);
        }
        javax.swing.JOptionPane.showMessageDialog(null,message);
    }
}
