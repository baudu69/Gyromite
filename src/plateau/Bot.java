package plateau;

import deplacements.Direction;
import deplacements.Gravite;
import deplacements.IA;

import java.util.Random;

public class Bot extends EntiteDynamique{
    private Random r = new Random();
    private IA uneIA;
    private Gravite laGravite;
    private char direction = 'g';

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public Bot(Jeu _jeu) {
        super(_jeu);
        uneIA = new IA();
        jeu.getOrdonnanceur().add(uneIA);
        uneIA.addEntiteDynamique(this);

        laGravite = new Gravite();
        jeu.getOrdonnanceur().add(laGravite);
        laGravite.addEntiteDynamique(this);

    }
    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}
