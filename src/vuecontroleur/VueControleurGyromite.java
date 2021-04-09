package vuecontroleur;

import deplacements.Controle4Directions;
import deplacements.Direction;
import deplacements.Tuyau;
import plateau.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VueControleurGyromite extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon icoDynamite;
    private ImageIcon icoHerod;
    private ImageIcon icoHerog;
    private ImageIcon icoHerot;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoTuyaur;
    private ImageIcon icoTuyaub;
    private ImageIcon icoCorde;
    private ImageIcon icoPoutre;
    private ImageIcon icoMonstred;
    private ImageIcon icoMonstreg;
    private ImageIcon icoSol;


    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleurGyromite(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;

        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                // on regarde quelle touche a été pressée
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> Controle4Directions.getInstance().setDirectionCourante(Direction.gauche);
                    case KeyEvent.VK_RIGHT -> Controle4Directions.getInstance().setDirectionCourante(Direction.droite);
                    case KeyEvent.VK_DOWN -> Controle4Directions.getInstance().setDirectionCourante(Direction.bas);
                    case KeyEvent.VK_UP -> Controle4Directions.getInstance().setDirectionCourante(Direction.haut);
                    case KeyEvent.VK_P -> Tuyau.getDepTuyau().depTuyauBleu();
                    case KeyEvent.VK_O -> Tuyau.getDepTuyau().depTuyauRouge();
                }
            }
        });
    }


    private void chargerLesIcones() {
        icoHerod = chargerIcone("Images/Playerd.png");
        icoHerog = chargerIcone("Images/Playerg.png");
        icoHerot = chargerIcone("Images/Playert.png");
        icoVide = chargerIcone("Images/Vide.png");
        icoTuyaur = chargerIcone("Images/TuyauxRouge.png");
        icoTuyaub = chargerIcone("Images/TuyauxBleu.png");
        icoMur = chargerIcone("Images/PoutreV.png");
        icoPoutre = chargerIcone("Images/PoutreH.png");
        icoCorde = chargerIcone("Images/Corde.png");
        icoMonstred = chargerIcone("Images/Furbyd.png");
        icoMonstreg = chargerIcone("Images/Furbyg.png");
        icoSol = chargerIcone("Images/Sol.png");
        icoDynamite = chargerIcone("Images/Dynamite.png");

    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {
        setTitle("Gyromite");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }

    private void majCoordEntite(int x, int y) {
        if (jeu.getGrilleEntitesDynamique()[x][y] != null) {
            jeu.getGrilleEntitesDynamique()[x][y].x = x;
            jeu.getGrilleEntitesDynamique()[x][y].y = y;
        }
        if (jeu.getGrille()[x][y] != null) {
            jeu.getGrille()[x][y].x = x;
            jeu.getGrille()[x][y].y = y;
        }

    }


    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                majCoordEntite(x, y);

                //On regarde si c'est un héro
                if (jeu.getGrille()[x][y] instanceof Heros) {
                    if (jeu.getGrilleEntitesDynamique()[x][y] instanceof Corde)
                        tabJLabel[x][y].setIcon(icoHerot);
                    else if (((Heros) jeu.getGrille()[x][y]).getDirection()=='d')
                        tabJLabel[x][y].setIcon(icoHerod);
                    else tabJLabel[x][y].setIcon(icoHerog);
                }

                //On regarde si c'est un bot
                else if (jeu.getGrille()[x][y] instanceof Bot) {
                    if(((Bot) jeu.getGrille()[x][y]).getDirection()=='d')
                        tabJLabel[x][y].setIcon(icoMonstred);
                    else
                        tabJLabel[x][y].setIcon(icoMonstreg);
                }

                //On regarde si c'est une entité dynamique
                else if (jeu.getGrilleEntitesDynamique()[x][y] != null) {
                    if (jeu.getGrilleEntitesDynamique()[x][y] instanceof Corde)
                        tabJLabel[x][y].setIcon(icoCorde);
                    else if (jeu.getGrilleEntitesDynamique()[x][y] instanceof TuyauMorceau) {
                        if (((TuyauMorceau) jeu.getGrilleEntitesDynamique()[x][y]).getCouleur()=='r')
                            tabJLabel[x][y].setIcon(icoTuyaur);
                        else
                            tabJLabel[x][y].setIcon(icoTuyaub);
                    }
                    else if (jeu.getGrilleEntitesDynamique()[x][y] instanceof Dynamite) {
                        tabJLabel[x][y].setIcon(icoDynamite);
                    }

                }

                //On regarde si c'est une entité statique
                else if (jeu.getGrille()[x][y] != null) {
                    if (jeu.getGrille()[x][y] instanceof Poutre) {
                        tabJLabel[x][y].setIcon(icoPoutre);
                    }
                    else if (jeu.getGrille()[x][y] instanceof Sol) {
                        tabJLabel[x][y].setIcon(icoSol);
                    }
                    else if (jeu.getGrille()[x][y] instanceof Mur) {
                        tabJLabel[x][y].setIcon(icoMur);
                    }
                }

                //Sinon on met une icone vide
                else {
                    tabJLabel[x][y].setIcon(icoVide);
                }





                /*if (jeu.getGrilleEntitesDynamique()[x][y] == null) {
                    if (jeu.getGrille()[x][y] instanceof Heros) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                        if (((Heros) jeu.getGrille()[x][y]).getDirection()=='d')
                            tabJLabel[x][y].setIcon(icoHerod);
                        else tabJLabel[x][y].setIcon(icoHerog);
                    } else if (jeu.getGrille()[x][y] instanceof Bot) {
                        if(((Bot) jeu.getGrille()[x][y]).getDirection()=='d')
                            tabJLabel[x][y].setIcon(icoMonstred);
                        else tabJLabel[x][y].setIcon(icoMonstreg);

                    } else if (jeu.getGrille()[x][y] instanceof Mur) {
                        tabJLabel[x][y].setIcon(icoMur);
                    }else if (jeu.getGrilleEntitesDynamique()[x][y] instanceof Dynamite) {
                        tabJLabel[x][y].setIcon(icoDynamite);
                    }else if (jeu.getGrilleEntitesDynamique()[x][y] instanceof TuyauMorceau) {
                        if (((TuyauMorceau) jeu.getGrilleEntitesDynamique()[x][y]).getCouleur()=='r')
                        tabJLabel[x][y].setIcon(icoTuyaur);
                        else tabJLabel[x][y].setIcon(icoTuyaub);
                    }
                    else if (jeu.getGrille()[x][y] instanceof Poutre) {
                        tabJLabel[x][y].setIcon(icoPoutre);
                    }
                    else if (jeu.getGrille()[x][y] instanceof Sol) {
                        tabJLabel[x][y].setIcon(icoSol);
                    } else {
                        tabJLabel[x][y].setIcon(icoVide);
                    }

                } else {
                    if ((jeu.getGrilleEntitesDynamique()[x][y] instanceof Corde) && !(jeu.getGrille()[x][y] instanceof Heros) && !(jeu.getGrille()[x][y] instanceof Bot)) {
                        tabJLabel[x][y].setIcon(icoCorde);
                    }
                    else if ((jeu.getGrilleEntitesDynamique()[x][y] instanceof Corde) && !(jeu.getGrille()[x][y] instanceof Heros)) {
                        tabJLabel[x][y].setIcon(icoMonstred);
                    }
                    else if (jeu.getGrilleEntitesDynamique()[x][y] instanceof TuyauMorceau) {
                        if (((TuyauMorceau) jeu.getGrilleEntitesDynamique()[x][y]).getCouleur()=='r')
                            tabJLabel[x][y].setIcon(icoTuyaur);
                        else tabJLabel[x][y].setIcon(icoTuyaub);
                    }
                    else if (jeu.getGrilleEntitesDynamique()[x][y] instanceof Dynamite) {
                        tabJLabel[x][y].setIcon(icoDynamite);
                    }
                        else tabJLabel[x][y].setIcon(icoHerot);

                }*/


            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                });
        */

    }
}
