package deplacements;

import plateau.EntiteDynamique;

import java.util.ArrayList;

public abstract class RealisateurDeDeplacement {
    public ArrayList<EntiteDynamique> lstEntitesDynamiques = new ArrayList<EntiteDynamique>();
    protected abstract boolean realiserDeplacement();


    public void addEntiteDynamique(EntiteDynamique ed) {lstEntitesDynamiques.add(ed);};

    public void delEntiteDyn(EntiteDynamique ed) {lstEntitesDynamiques.remove(ed);};
}
