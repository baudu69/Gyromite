package deplacements;

import plateau.Corde;
import plateau.Entite;
import plateau.EntiteDynamique;
import plateau.Heros;

public class Controle4Directions extends RealisateurDeDeplacement{
    private Direction directionCourante;
    // Design pattern singleton
    private static Controle4Directions c3d;

    public static Controle4Directions getInstance() {
        if (c3d == null) {
            c3d = new Controle4Directions();
        }
        return c3d;
    }

    public void setDirectionCourante(Direction _directionCourante) {
        directionCourante = _directionCourante;
    }

    private void checkHeroCorde(EntiteDynamique e) {
        if (e instanceof Heros) {
            if (e.jeu.getGrilleEntitesDynamique()[e.x][e.y] instanceof Corde) {
                //e.jeu.g.delEntiteDyn(e);
                ((Heros) e).laGravite = false;
            } else {
                ((Heros) e).laGravite = true;
                /*if (!e.jeu.g.lstEntitesDynamiques.contains(e))
                    e.jeu.g.addEntiteDynamique(e);*/
            }
        }
    }

    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            checkHeroCorde(e);
            if (directionCourante != null)
                switch (directionCourante) {
                    case gauche:
                    case droite:
                        if (e.avancerDirectionChoisie(directionCourante))
                            ret = true;
                        break;

                    case haut:
                        // on ne peut pas sauter sans prendre appui
                        // (attention, test d'appui réalisé à partir de la position courante, si la gravité à été appliquée, il ne s'agit pas de la position affichée, amélioration possible)
                        Entite eBas = e.regarderDansLaDirection(Direction.bas);
                        if ((eBas != null && eBas.peutServirDeSupport()) || (e instanceof Heros && !((Heros) e).laGravite)) {
                            if (e.avancerDirectionChoisie(Direction.haut))
                                ret = true;
                        }
                        break;
                }
        }

        return ret;

    }

    public void resetDirection() {
        directionCourante = null;
    }
}
