package deplacements;

import plateau.Bot;
import plateau.Entite;
import plateau.EntiteDynamique;
import plateau.Heros;

import java.util.ArrayList;

public class IA extends RealisateurDeDeplacement{
    private int nbrDep = 1;
    private ArrayList<Bot> lesBots;
    protected boolean realiserDeplacement() {
        getListeBot();
        for (Bot unBot : lesBots) {
            if (nbrDep<= 3) {
                checkHero(unBot, Direction.droite);
                unBot.setDirection('d');
            } else {
                checkHero(unBot, Direction.gauche);
                unBot.setDirection('g');
            }
            nbrDep++;
            if (nbrDep >=7)
                nbrDep=1;
        }
        return true;
    }

    private void checkHero(Bot unBot, Direction laDirection) {
        Entite entite = unBot.regarderDansLaDirection(laDirection);
        if (entite instanceof Heros) {
            unBot.jeu.getOrdonnanceur().stop = true;
            System.out.println("Héro touché");
        }
        unBot.avancerDirectionChoisie(laDirection);
    }

    private void getListeBot() {
        lesBots = new ArrayList<>();
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (e instanceof Bot) {
                lesBots.add((Bot) e);
            }
        }
    }

}
