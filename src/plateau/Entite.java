package plateau;

public abstract class Entite {
    private Jeu jeu;

    public int x;
    public int y;

    public Entite(Jeu _jeu) {
        jeu = _jeu;
    }

    public abstract boolean peutEtreEcrase(); // l'entité peut être écrasée (par exemple par une colonne ...)
    public abstract boolean peutServirDeSupport(); // permet de stopper la gravité, prendre appui pour sauter
    public abstract boolean peutPermettreDeMonterDescendre();

    public Jeu getJeu() {
        return jeu;
    }
}
