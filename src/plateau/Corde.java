package plateau;

public class Corde extends EntiteDynamique {
    public Corde(Jeu _jeu) { super(_jeu); }

    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return false; }
    public boolean peutPermettreDeMonterDescendre() { return true; };
}
