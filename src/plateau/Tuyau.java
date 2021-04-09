package plateau;

import deplacements.Direction;

import java.awt.*;
import java.util.ArrayList;

public class Tuyau {
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
        ajouterAuDeps();
        genererMorceaux();

    }

    private void ajouterAuDeps() {
        if (couleur == 'r') {
            deplacements.Tuyau.getDepTuyau().addTuyauRouge(this);
        } else {
            deplacements.Tuyau.getDepTuyau().addTuyauBleu(this);
        }
    }

    public TuyauMorceau getTuyauBas() {
        return lesMorceaux.get(taille - 2);
    }

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

    public void changerPosition() {
        if (position == 'h') {
            descendre();
            position = 'b';
        } else {
            monter();
            position = 'h';
        }
    }

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

    private void monter() {
        for (int i = 0; i < lesMorceaux.size(); i++) {
            TuyauMorceau unMorceau = lesMorceaux.get(i);
            Point pointActuel = new Point();
            pointActuel.x = unMorceau.x;
            pointActuel.y = unMorceau.y;
            Point pointCible = new Point();
            pointCible.x = unMorceau.x;
            pointCible.y = unMorceau.y - 2;

            jeu.deplacerEntiteDynamique(pointActuel, pointCible, unMorceau);
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
