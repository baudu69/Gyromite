package deplacements;

import plateau.Bot;
import plateau.EntiteDynamique;

import java.util.ArrayList;

public class IA extends RealisateurDeDeplacement{
    private int nbrDep = 1;
    private ArrayList<Bot> lesBots;
    protected boolean realiserDeplacement() {
        getListeBot();
        for (Bot unBot : lesBots) {
            if (nbrDep<= 3) {
                unBot.avancerDirectionChoisie(Direction.droite);
                unBot.setDirection('d');
            } else {
                unBot.avancerDirectionChoisie(Direction.gauche);
                unBot.setDirection('g');
            }
            nbrDep++;
            if (nbrDep >=7)
                nbrDep=1;
        }
        return true;
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
