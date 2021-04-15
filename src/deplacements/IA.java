package deplacements;

import plateau.Bot;
import plateau.Entite;
import plateau.EntiteDynamique;
import plateau.Heros;

import java.util.ArrayList;

public class IA extends RealisateurDeDeplacement{
    //variable permettant de compter le nombre de pas du bot dans un sens
    private int nbrDep = 1;
    //Liste des bots
    private ArrayList<Bot> lesBots;


    /**
     * 3 pas d'un coté et 3 pas de l'autre
     * @return true si il peut bouger
     */
    protected boolean realiserDeplacement() {
        for (EntiteDynamique unEnt : lstEntitesDynamiques) {
            Bot unBot = (Bot) unEnt;
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


    /**
     * Vérifie si le bot s'apprète à manger un héros
     * @param unBot le Bot à checker
     * @param laDirection Direction dans laquelle le bot pointe
     */
    private void checkHero(Bot unBot, Direction laDirection) {
        Entite entite = unBot.regarderDansLaDirection(laDirection);
        if (entite instanceof Heros) {
            unBot.getJeu().getOrdonnanceur().stop = true;
        }
        unBot.avancerDirectionChoisie(laDirection);
    }

}
