package plateau;

import deplacements.Controle4Directions;
import deplacements.Direction;
import deplacements.Gravite;
import deplacements.Ordonnanceur;

import java.awt.*;
import java.util.HashMap;

public class Jeu {
    public static final int SIZE_X = 32;
    public static final int SIZE_Y = 32;

    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private final HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private final HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private Heros hector;

    public Gravite g;

    private final HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private final Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées
    private final Entite[][] grilleEntitesDynamique = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées

    private final Ordonnanceur ordonnanceur = new Ordonnanceur(this);

    public Jeu() {
        initialisationDesEntites();
    }

    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void start(long _pause) {
        ordonnanceur.start(_pause);
    }

    public Entite[][] getGrille() {
        return grilleEntites;
    }

    public Entite[][] getGrilleEntitesDynamique() {
        return grilleEntitesDynamique;
    }

    public Heros getHector() {
        return hector;
    }

    private void initialisationDesEntites() {
        hector = new Heros(this);
        hector.x=5;
        hector.y=9;
        addEntite(hector, hector.x, hector.y);

        g = new Gravite();
        g.addEntiteDynamique(hector);
        ordonnanceur.add(g);

        Controle4Directions.getInstance().addEntiteDynamique(hector);
        ordonnanceur.add(Controle4Directions.getInstance());


        // Sol
        for (int x = 0; x < SIZE_X; x++) {
            addEntite(new Sol(this), x, SIZE_Y-2);
            addEntite(new Sol(this), x, SIZE_Y-1);
        }

        // Murs extérieurs
        for (int y = 0; y < SIZE_Y-2; y++) {
            addEntite(new Mur(this), 0, y);
            addEntite(new Mur(this), SIZE_X-1, y);
        }

        // Plafond
        for (int x = 1; x < SIZE_X-1; x++) {
            addEntite(new Poutre(this), x,0);
        }

        // Murs interieurs
        for (int y = 19; y < 21; y++) {
            addEntite(new Mur(this), 29, y);
        }
        for (int y = 8; y < 11; y++) {
            addEntite(new Mur(this), 20, y);
            addEntite(new Mur(this), 25, y);
            addEntite(new Mur(this), 20, y+19);
        }
        for (int y = 1; y < 5; y++) {
            addEntite(new Mur(this), 11, y);
            addEntite(new Mur(this), 23, y);
            addEntite(new Mur(this), 19, y+3);
        }
        for (int y = 20; y < 25; y++) {
            addEntite(new Mur(this), 16, y);
        }
        for (int y = 5; y < 11; y++) {
            addEntite(new Mur(this), 4, y);
        }
        for (int y = 4; y < 16; y++) {
            addEntite(new Mur(this), 27, y);
        }

        // Poutres
        addEntite(new Poutre(this), 6,4);
        addEntite(new Poutre(this), 17,4);
        addEntite(new Poutre(this), 12,7);
        addEntite(new Poutre(this), 8,10);
        addEntite(new Poutre(this), 12,16);
        addEntite(new Poutre(this), 30,16);
        addEntite(new Poutre(this), 30,20);

        for (int x = 9; x < 11; x++) {
            addEntite(new Poutre(this), x,4);
            addEntite(new Poutre(this), x+15,4);
            addEntite(new Poutre(this), x+10,7);
            addEntite(new Poutre(this), x+13,7);
            addEntite(new Poutre(this), x+16,7);
            addEntite(new Poutre(this), x-4,10);
            addEntite(new Poutre(this), x+15,16);
            addEntite(new Poutre(this), x+18,16);
        }
        for (int x = 2; x<5; x++) {
            addEntite(new Poutre(this), x,4);
            addEntite(new Poutre(this), x+18,4);
            addEntite(new Poutre(this), x+13,7);
            addEntite(new Poutre(this), x-1,14);
            addEntite(new Poutre(this), x+3,14);
            addEntite(new Poutre(this), x+7,14);
            addEntite(new Poutre(this), x+5,19);
            addEntite(new Poutre(this), x+19,27);
        }
        for (int x = 12; x < 16; x++) {
            addEntite(new Poutre(this), x,4);
            addEntite(new Poutre(this), x+9,10);
        }
        for (int x = 6; x < 11; x++) {
            addEntite(new Poutre(this), x,7);
            addEntite(new Poutre(this), x+18,19);
            addEntite(new Poutre(this), x-5,25);
            addEntite(new Poutre(this), x+1,27);
            addEntite(new Poutre(this), x+8,27);
            addEntite(new Poutre(this), x+19,27);
        }
        for (int x = 17; x < 23; x++) {
            addEntite(new Poutre(this), x,16);
            addEntite(new Poutre(this), x-7,24);
        }
        for (int x = 10; x < 18; x++) {
            addEntite(new Poutre(this), x,10);
        }
        for (int x = 20; x < 31; x++) {
            addEntite(new Poutre(this), x,23);
        }
        for (int x = 11; x <23; x++) {
            addEntite(new Poutre(this), x,19);
        }

        // Cordes
        for (int y = 3; y < 7; y++) {
            addEntiteDynamique(new Corde(this), 16, y);
            addEntiteDynamique(new Corde(this), 20, y);
            addEntiteDynamique(new Corde(this), 26, y);
            addEntiteDynamique(new Corde(this), 11, y+3);
            addEntiteDynamique(new Corde(this), 21, y+3);
            addEntiteDynamique(new Corde(this), 24, y+3);
            addEntiteDynamique(new Corde(this), 30, y+23);
        }
        for (int y = 18; y < 24; y++) {
            addEntiteDynamique(new Corde(this), 10, y);
        }
        for (int y = 3; y < 10; y++) {
            addEntiteDynamique(new Corde(this), 5, y);
            addEntiteDynamique(new Corde(this), 18, y+6);
        }
        for (int y = 15; y < 23; y++) {
            addEntiteDynamique(new Corde(this), 23, y);
            addEntiteDynamique(new Corde(this), 19, y+7);
        }
        for (int y = 3; y < 16; y++) {
            addEntiteDynamique(new Corde(this), 28, y);
        }
        //Dynamite
        addEntiteDynamique(new Dynamite(this), 2,24);
        addEntiteDynamique(new Dynamite(this), 22,29);
        addEntiteDynamique(new Dynamite(this), 9,18);
        addEntiteDynamique(new Dynamite(this), 30,15);
        addEntiteDynamique(new Dynamite(this), 3,3);



        //bot
        addEntite(new Bot(this), 14, 18);
        addEntite(new Bot(this), 12,23);


    }

    public void FinDuJeu() {

    }

    /**
     * @param entite Entite dynamique ou non
     */
    public void removeEntite(Entite entite) {
        if (entite instanceof EntiteDynamique) {
            for (int i = 0; i < SIZE_X; i++) {
                for (int j = 0; j < SIZE_Y; j++) {
                    if (grilleEntitesDynamique[i][j] == entite) {
                        grilleEntitesDynamique[i][j] = null;
                    }
                }
            }
        } else {
            for (int i = 0; i < SIZE_X; i++) {
                for (int j = 0; j < SIZE_Y; j++) {
                    if (grilleEntites[i][j] == entite) {
                        grilleEntites[i][j] = null;
                    }
                }
            }
        }
    }

    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
        map.put(e, new Point(x, y));
    }

    private void addEntiteDynamique(Entite e, int x, int y) {
        grilleEntitesDynamique[x][y] = e;
        map.put(e, new Point(x, y));
    }

    private void addTuyau(int x, int y,char c, char d) {
        Tuyau t = new Tuyau(this);
        t.setCouleur(c);
        t.setPosition(d);
        addEntiteDynamique(t, x, y);
    }

    /** Permet par exemple a une entité  de percevoir sont environnement proche et de définir sa stratégie de déplacement
     *
     */
    public Entite regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }

    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = false;

        Point pCourant = map.get(e);

        Point pCible = calculerPointCible(pCourant, d);

        if (contenuDansGrille(pCible) && objetALaPosition(pCible) == null) { // a adapter (collisions murs, etc.)
            // compter le déplacement : 1 deplacement horizontal et vertical max par pas de temps par entité
            switch (d) {
                case bas:
                case haut:
                    if (cmptDeplV.get(e) == null) {
                        cmptDeplV.put(e, 1);

                        retour = true;
                    }
                    break;
                case gauche:
                case droite:
                    if (cmptDeplH.get(e) == null) {
                        cmptDeplH.put(e, 1);
                        retour = true;

                    }
                    break;
            }
        }

        if (retour) {
            deplacerEntite(pCourant, pCible, e);
        }

        return retour;
    }


    private Point calculerPointCible(Point pCourant, Direction d) {

        return switch (d) {
            case haut -> new Point(pCourant.x, pCourant.y - 1);
            case bas -> new Point(pCourant.x, pCourant.y + 1);
            case gauche -> new Point(pCourant.x - 1, pCourant.y);
            case droite -> new Point(pCourant.x + 1, pCourant.y);
            default -> null;
        };
    }

    private void deplacerEntite(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }

    private void deplacerEntiteDynamique(Point pCourant, Point pCible, Entite e) {
        grilleEntitesDynamique[pCourant.x][pCourant.y] = null;
        grilleEntitesDynamique[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }

    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }

    private Entite objetALaPosition(Point p) {
        Entite retour = null;

        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }

        return retour;
    }

    private Entite objetDynamiqueALaPosition(Point p) {
        Entite retour = null;

        if (contenuDansGrille(p)) {
            retour = grilleEntitesDynamique[p.x][p.y];
        }

        return retour;
    }

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }
}
