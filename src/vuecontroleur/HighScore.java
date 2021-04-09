package vuecontroleur;
import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.Flow;

public class HighScore {

    private final double seconds;
    private final ArrayList<Double> lesTemps;


    /**
     * @param seconds score en secondes
     */
    public HighScore(double seconds) {
        this.seconds = seconds;
        lesTemps = new ArrayList<>();
        ajouterScore();
        lireFichier();
        Collections.sort(lesTemps);
        afficherScore();
    }

    /**
     * Affiche le score du jeu + les 10 meilleurs scores
     */
    private void afficherScore() {
        JFrame frame = new JFrame("Résultat");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(2);
        int j = 1;
        JLabel text = new JLabel("Votre score : " + f.format(seconds).toString());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = 1;
        constraint.gridy = 1;
        frame.add(text, constraint);
        int taillemax;
        if (lesTemps.size() >= 10) {
            taillemax = 10;
        } else {
            taillemax = lesTemps.size();
        }
        for (int i = 0; i < taillemax; i++) {
            text = new JLabel(j + " : \n" + f.format(lesTemps.get(i)));
            constraint = new GridBagConstraints();
            constraint.gridx = 1;
            constraint.gridy = j + 2;
            frame.add(text, constraint);
            //frame.add(text, BorderLayout.CENTER);
            j++;
        }
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Ajoute un score au fichier highScore.txt
     */
    private void ajouterScore() {
        try
        {
            String filename="highScore.txt";
            FileWriter fw = new FileWriter(filename,true);
            fw.write((Double.toString(seconds) + "\n"));
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println(ioe.getMessage());
        }
    }

    /**
     * Stocke le contenu de highScore.txt dans lesTemps
     */
    private void lireFichier() {
        try
        {
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream("highScore.txt");
            Scanner scanner = new Scanner(file);

            //renvoie true s'il y a une autre ligne à lire
            while(scanner.hasNextLine())
            {
                lesTemps.add(Double.parseDouble(scanner.nextLine()));
            }
            scanner.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
