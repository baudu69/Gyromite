package vuecontroleur;

import deplacements.Controle4Directions;
import deplacements.Direction;
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
    private ImageIcon icoHero;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoColonne;
    private ImageIcon icoCorde;

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
                }
            }
        });
    }


    private void chargerLesIcones() {
        icoHero = chargerIcone("Images/Pacman.png");
        icoVide = chargerIcone("Images/Vide.png");
        icoColonne = chargerIcone("Images/Colonne.png");
        icoMur = chargerIcone("Images/Mur.png");
        icoCorde = chargerIcone("Images/Fantome.png");
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
        setSize(900, 900);
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
                if (jeu.getGrilleEntitesDynamique()[x][y] == null) {
                    if (jeu.getGrille()[x][y] instanceof Heros) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                        // System.out.println("Héros !");
                        tabJLabel[x][y].setIcon(icoHero);
                    } else if (jeu.getGrille()[x][y] instanceof Mur) {
                        tabJLabel[x][y].setIcon(icoMur);
                    } else if (jeu.getGrille()[x][y] instanceof Colonne) {
                        tabJLabel[x][y].setIcon(icoColonne);
                    } else {
                        tabJLabel[x][y].setIcon(icoVide);
                    }
                } else {
                    ImageIcon IconeDyn = null;
                    if (jeu.getGrilleEntitesDynamique()[x][y] instanceof Corde) {
                        IconeDyn = icoCorde;
                    }
                    if (jeu.getGrille()[x][y] instanceof Heros) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                        // System.out.println("Héros !");
                        tabJLabel[x][y].setIcon(new ImageIcon( fusion2Images(icoHero.getImage(), IconeDyn.getImage())));
                    }
                    else if (jeu.getGrille()[x][y] instanceof Mur) {
                        tabJLabel[x][y].setIcon(new ImageIcon( fusion2Images(icoMur.getImage(), IconeDyn.getImage())));
                    }
                    else if (jeu.getGrille()[x][y] == null) {
                        tabJLabel[x][y].setIcon(IconeDyn);
                    }else {
                        tabJLabel[x][y].setIcon(icoVide);
                    }
                }

            }
        }
    }

    /**
     * @param img1 Image 1
     * @param img2 Image 2
     * @return Fusion entre les deux images
     */
    public static Image fusion2Images(Image img1, Image img2) {
        BufferedImage buf = null;
        if(img1 != null && img2 != null) {
            int w1 = img1.getWidth(null);
            int h1 = img1.getHeight(null);
            int w2 = img2.getWidth(null);
            int h2 = img2.getHeight(null);
            int hMax = 0;
            int wMax = 0;

            hMax = Math.max(h1, h2);
            wMax = w1+w2;
            buf = new BufferedImage(wMax, hMax, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buf.createGraphics();
            g2.drawImage(img1, 0, 0, null);
            g2.drawImage(img2, w1, 0, null);
        }
        return buf;
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
