package plateau;

import deplacements.Gravite;
import deplacements.IA;

public class Bot extends EntiteDynamique{
    private char direction = 'g';

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public Bot(Jeu _jeu) {
        super(_jeu);
        IA uneIA = new IA();
        jeu.getOrdonnanceur().add(uneIA);
        uneIA.addEntiteDynamique(this);

        Gravite laGravite = new Gravite();
        jeu.getOrdonnanceur().add(laGravite);
        laGravite.addEntiteDynamique(this);

    }
    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }
}
