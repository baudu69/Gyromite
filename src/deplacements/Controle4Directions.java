package deplacements;

import plateau.*;

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

    /**
     * Modifie le paramètre gravite du héro si il est sur une corde
     * @param e Vérifie si le héro se trouve sur une corde ou non
     */
    private void checkHeroCorde(EntiteDynamique e) {
        if (e instanceof Heros) {
            ((Heros) e).laGravite = (e.getJeu().getGrilleEntitesDynamique()[e.x][e.y] == null) || (!(e.getJeu().getGrilleEntitesDynamique()[e.x][e.y] instanceof Corde));
        }
    }

    /**
     * Permet de gérer le déplacement du héro.
     * Gère aussi la gravité du héro
     * @return true si le déplacement est possible
     */
    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            checkHeroCorde(e);
            if (directionCourante != null)
                switch (directionCourante) {
                    case gauche:
                        ((Heros) e).setDirection('g');
                        if (e.avancerDirectionChoisie(directionCourante))
                            ret = true;
                        break;
                    case droite:
                        ((Heros) e).setDirection('d');
                        if (e.avancerDirectionChoisie(directionCourante))
                            ret = true;
                        break;

                    case bas:
                        if (e instanceof Heros && !((Heros) e).laGravite)
                            if (e.avancerDirectionChoisie(Direction.bas))
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
            checkDynamiteHero(e);
        }

        return ret;

    }

    /**
     * Vérifie si le héro se trouve sur une dynamite et si oui la fait disparaitre
     * @param entite héro
     */
    private void checkDynamiteHero(Entite entite) {
        if (entite instanceof Heros) {
            for (int i = 0; i < Jeu.SIZE_X; i++) {
                for (int j = 0; j < Jeu.SIZE_Y; j++) {
                    Entite entPlateau = entite.getJeu().getGrilleEntitesDynamique()[i][j];
                    if (entPlateau instanceof Dynamite) {
                        if ((entite.x == i) && (entite.y == j)) {
                            entite.getJeu().removeEntite(entPlateau);
                            entite.getJeu().getLesDynamites().remove(entPlateau);
                            System.out.println("Dynamite touchée");
                        }
                    }
                }
            }
        }
    }

    public void resetDirection() {
        directionCourante = null;
    }
}
