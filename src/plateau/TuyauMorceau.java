package plateau;



public class TuyauMorceau extends EntiteDynamique{

    private char couleur;
    private char position;
    private final Tuyau leTuyau;

    public TuyauMorceau(Jeu _jeu, Tuyau leTuyau) {
        super(_jeu);
        this.leTuyau = leTuyau;
    }

    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };

    public char getCouleur() {
        return couleur;
    }

    public void setCouleur(char couleur) {
        this.couleur = couleur;
    }


    public void setPosition(char position) {
        this.position = position;
    }

    public Tuyau getLeTuyau() {
        return leTuyau;
    }
}
