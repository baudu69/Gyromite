package plateau;

import deplacements.Direction;
import deplacements.IA;

public abstract class EntiteDynamique extends Entite{
    public EntiteDynamique(Jeu _jeu) { super(_jeu); }


    public boolean avancerDirectionChoisie(Direction d) {
        return getJeu().deplacerEntite(this, d);
    }
    public Entite regarderDansLaDirection(Direction d) {return getJeu().regarderDansLaDirection(this, d);}
}
