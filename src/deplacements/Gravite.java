package deplacements;

import plateau.Entite;
import plateau.EntiteDynamique;
import plateau.Heros;

public class Gravite extends RealisateurDeDeplacement{
    /**
     * Réalise l'action de gravité sur les entités dynamiques
     * @return true si la gravité génère un déplacement
     */
    @Override
    public boolean realiserDeplacement() {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (e instanceof Heros && !((Heros) e).laGravite) {
                return false;
            }
            Entite eBas = e.regarderDansLaDirection(Direction.bas);
            if (eBas == null || (!eBas.peutServirDeSupport())) {
                if (e.avancerDirectionChoisie(Direction.bas))
                    return true;
            }
        }
        return false;
    }
}
