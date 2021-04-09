package plateau;

public class Heros extends EntiteDynamique{
    public Heros(Jeu _jeu) {
        super(_jeu);
    }

    private char direction = 'g';

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public boolean laGravite= true;

    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }


}
