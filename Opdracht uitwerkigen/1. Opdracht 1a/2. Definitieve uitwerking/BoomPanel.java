package opdr1a;

/**
 * Klasse voor het panel waarop de boom wordt getekend.
 * De lengte van de stam, de hoek tussen de takken
 * en de diepte van de boom zijn parameters.
 * Er wordt gebruik gemaakt van een pen die relatief tekent
 * (vanuit een huidige positie en richting).
 */

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class BoomPanel extends JPanel {

  private static final double FACTOR = 0.7; // de verkortingsfactor van
  // de takken

  private double hoek; // hoek tussen de takken
  private int diepte; // de diepte van de boom
  private int stamLengte; // lengte van de stam
  private Pen pen; // de pen waarmee getekend wordt

  /**
   * Maak een nieuw BoomPanel.
   *
   * @param stamlengte, de initiële lengte van de stam
   * @param hoek, de hoek tussen de takken
   * @param diepte, de diepte van de boom.
   */
  BoomPanel(int stamLengte, int hoek, int diepte) {
    this.stamLengte = stamLengte;
    this.hoek = hoek;
    this.diepte = diepte;
    this.setSize(350, 300);
    this.setBackground(Color.white);
  }

  /**
   * paintComponent maakt een nieuwe pen en roept de recursieve tekenmethode
   * tekenBoom aan.
   *
   * @param g
   */
  public void paintComponent(Graphics g) {
    // Maak een nieuwe pen, die iets van de rand onderin het midden van
    // het panel staat en naar boven wijst.
    pen = new Pen(g, getSize().width / 2, getSize().height - 20, 270);
    pen.setKleur(Color.red);

    // hier moet de aanroep naar tekenBoom worden ingevuld.

    // huidigeDiepte begint altijd bij 1
    int huidigeDiepte=1;

    // de abs functie wordt gebruikt om een negatieve stamlengte om te zetten naar een positieve stamlengte. Technisch gezien werkt een negatieve stamlengte wel
    // maar het tekengebied is daarvoor niet toereikend (startpunt ligt niet precies in het midden).
    // Door een negatieve stamlengte om te zetten naar een posititeve lengte wordt dit omzeild.
    // het uiteindelijke resultaat van een negatieve stamlengte is gelijk aan een positieve stamlengte.
    tekenBoom(pen, Math.abs(stamLengte), huidigeDiepte);
  }

  private void tekenBoom(Pen pen, double stamLengte, int huidigeDiepte) {
    // Diepte (=gebruikersinvoer) moet altijd groter dan 0 zijn, wanneer een diepte van 0 (of een negatief getal) wordt ingevoerd dan wordt er niets getekent
    if (diepte > 0) {
      if (huidigeDiepte == diepte) {
        // bijzonder probleem
        // Diepste tak
        pen.aan();
        pen.vooruit(stamLengte);
        pen.uit();
        pen.vooruit(-stamLengte);
      } else {
        // Algemeen probleem
        pen.aan();
        pen.vooruit(stamLengte);
        pen.uit();
        pen.draai(hoek/2);
        // Tak naar rechts
        tekenBoom(pen, stamLengte*FACTOR,huidigeDiepte+1);
        pen.draai(-hoek);
        // Tak naar links
        tekenBoom(pen, stamLengte*FACTOR,huidigeDiepte+1);
        pen.draai(hoek/2);
        pen.vooruit(-stamLengte);
      }
    }
  }
}

