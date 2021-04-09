package plateau;

import deplacements.Direction;

import java.awt.*;
import java.util.ArrayList;

public class Tuyau {
    private char couleur;
    private char position;
    private final ArrayList<TuyauMorceau> lesMorceaux;
    protected Jeu jeu;
    private final int x;
    private final int y;
    private final int taille;

    /**
     * @param _jeu Jeu
     * @param x position x du bloc du haut
     * @param y position y du bloc du haut
     * @param c couleur du tuyau
     * @param p position initiale du tuyau
     * @param taille taille du tuyau
     */
    public Tuyau(Jeu _jeu, int x, int y, char c, char p, int taille) {
        jeu = _jeu;
        this.x = x;
        this.y = y;
        this.taille = taille;
        this.couleur = c;
        this.position = p;
        lesMorceaux = new ArrayList<>();
        ajouterAuDeps();
        genererMorceaux();

    }

    /**
     * Ajoute les tuyaux a la classe tuyau gérant leur déplacement
     */
    private void ajouterAuDeps() {
        if (couleur == 'r') {
            deplacements.Tuyau.getDepTuyau().addTuyauRouge(this);
        } else {
            deplacements.Tuyau.getDepTuyau().addTuyauBleu(this);
        }
    }

    /**
     * @return le morceau de tuyau du bas
     */
    public TuyauMorceau getTuyauBas() {
        return lesMorceaux.get(taille - 2);
    }

    /**
     * Génère les morceaux de chaque tuyaux
     */
    private void genererMorceaux() {
        for (int i = 0; i < taille; i++) {
            TuyauMorceau unMorceau = new TuyauMorceau(jeu, this);
            unMorceau.setCouleur(this.getCouleur());
            unMorceau.setPosition(this.getPosition());
            jeu.addEntiteDynamique(unMorceau, x, y + i);
            deplacements.Tuyau.getDepTuyau().addEntiteDynamique(unMorceau);
            lesMorceaux.add(unMorceau);
        }
    }

    /**
     * Change la position du tuyau
     */
    public void changerPosition() {
        if (position == 'h') {
            descendre();
            position = 'b';
        } else {
            monter();
            position = 'h';
        }
    }


    /**
     * Fait descendre le tuyau
     */
    private void descendre() {
        for (int i = lesMorceaux.size() - 1; i >= 0; i--) {
            TuyauMorceau unMorceau = lesMorceaux.get(i);
            Point pointActuel = new Point();
            pointActuel.x = unMorceau.x;
            pointActuel.y = unMorceau.y;
            Point pointCible = new Point();
            pointCible.x = unMorceau.x;
            pointCible.y = unMorceau.y + 2;

            jeu.deplacerEntiteDynamique(pointActuel, pointCible, unMorceau);
        }
    }

    /**
     * Fait monter le tuyau
     */
    private void monter() {
        for (TuyauMorceau unMorceau : lesMorceaux) {
            Point pointActuel = new Point();
            pointActuel.x = unMorceau.x;
            pointActuel.y = unMorceau.y;
            Point pointCible = new Point();
            pointCible.x = unMorceau.x;
            pointCible.y = unMorceau.y - 2;
            jeu.deplacerEntiteDynamique(pointActuel, pointCible, unMorceau);
        }
    }

    /**
     * @return la couleur du tuyau
     */
    public char getCouleur() {
        return couleur;
    }

    public char getPosition() {
        return position;
    }
}
