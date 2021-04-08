package plateau;

import deplacements.Direction;

import java.awt.*;
import java.util.ArrayList;

public class Tuyau{
    private char couleur;
    private char position;
    private ArrayList<TuyauMorceau> lesMorceaux;
    protected Jeu jeu;
    private int x;
    private int y;
    private int taille;

    public Tuyau(Jeu _jeu, int x, int y, char c, char p, int taille) {
        jeu = _jeu;
        this.x = x;
        this.y = y;
        this.taille = taille;
        this.couleur = c;
        this.position = p;
        lesMorceaux = new ArrayList<>();
        genererMorceaux();
    }

    private void genererMorceaux() {
        for (int i = 0; i < taille; i++) {
            TuyauMorceau unMorceau = new TuyauMorceau(jeu);
            unMorceau.setCouleur(this.getCouleur());
            unMorceau.setPosition(this.getPosition());
            jeu.addEntiteDynamique(unMorceau, x, y + i);
            lesMorceaux.add(unMorceau);
        }
    }

    private void monter() {
        for (TuyauMorceau unMorceau: lesMorceaux) {
            Point pointActuel = new Point();
            pointActuel.x = unMorceau.x;
            pointActuel.y = unMorceau.y;
            Point pointCible = new Point();
            pointCible.x = unMorceau.x;
            pointCible.y = unMorceau.y + 1;

            jeu.deplacerEntite(pointActuel, pointCible, unMorceau);
        }
    }

    public char getCouleur() {
        return couleur;
    }

    public void setCouleur(char couleur) {
        this.couleur = couleur;
    }

    public char getPosition() {
        return position;
    }

    public void setPosition(char position) {
        this.position = position;
    }


}
