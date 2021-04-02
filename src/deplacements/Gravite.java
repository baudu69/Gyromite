package deplacements;

import plateau.Entite;
import plateau.EntiteDynamique;

public class Gravite extends RealisateurDeDeplacement{
    @Override
    public boolean realiserDeplacement() {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            Entite eBas = e.regarderDansLaDirection(Direction.bas);
            if (eBas == null || (!eBas.peutServirDeSupport())) {
                if (e.avancerDirectionChoisie(Direction.bas))
                    return true;
            }
        }
        return false;
    }
}
