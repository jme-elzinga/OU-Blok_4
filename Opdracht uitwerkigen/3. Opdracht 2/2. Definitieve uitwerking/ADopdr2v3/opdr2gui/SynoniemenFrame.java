package opdr2gui;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JList;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import opdr2core.SynoniemenBeheer;
import opdr2core.SynoniemenBeheerException;

import javax.swing.JButton;

public class SynoniemenFrame extends JFrame {

  private static final long serialVersionUID = 1L;
  private JPanel jContentPane = null;
  private JLabel woordLabel = null;
  private JLabel synoniemenLabel = null;
  private JScrollPane woordScrollPane = null;
  private JScrollPane synomiemenScrollPane = null;
  private JList<String> woordList = null;
  private JList<String> synoniemenList = null;
  private JTextField woordVeld = null;
  private JTextField synoniemenVeld = null;
  private JButton voegtoeKnop = null;
  private JLabel foutLabel = null;
  private SynoniemenBeheer beheerObject = null;

  /**
   * This is the default constructor
   */
  public SynoniemenFrame() {
    super();
    initialize();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize() {
    this.setSize(480, 329);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(getJContentPane());
    this.setTitle("JFrame");
    beheerObject = new SynoniemenBeheer();
    // Onderstaande regel kan gebruikt worden de lijst alvast met data te vullen en te testen 
//    prefabData();
  }


  private void prefabData() {
    try {
      beheerObject.voegToe("praten", "babbelen kouten kwebbelen Spreken kwekken");
      beheerObject.voegToe("zeggen", "meedelen verklaren");
      beheerObject.voegToe("studeren", "blokken leren");
      beheerObject.voegToe("blokken", "ploeteren");
      beheerObject.voegToe("Gillen", "Krijsen brullen schreeuwen Schreeuwen");
      // onderstaande twee regels zijn test cases
//      beheerObject.voegToe("gillen", "Krijsen brullen schreeuwen Schreeuwen");
//      beheerObject.voegToe("", "test");

      // Onderstaande regel kan nog handmatig toegevoegd worden.
//      beheerObject.voegToe("stoel", "zetel fauteuil zitplaats");
      woordList.setListData(beheerObject.geefWoorden());
      geenSelectie();
      veldenLeeg();
      } catch (SynoniemenBeheerException e) {
      foutLabel.setText(e.getMessage());
    }
  }

  // Methode die ervoor zorgt dat er geen selectie is van een woord met bijbehorende synoniemen. 
  private void geenSelectie() {
    // Maak de synoniemenList leeg
    synoniemenList.setListData(new String[0]);
    // Zorg ervoor dat er geen woord geselecteerd is in de woordList.
    woordList.clearSelection();
  }

  // Methode die ervoor zorgt dat het woordveld en synoniemend veld leeg is en een eventuele foutmelding
  // gewist wordt.
  private void veldenLeeg() {
    // Maak woordVeld leeg.
    woordVeld.setText("");
    // Maak synoniemenVeld leeg.
    synoniemenVeld.setText("");
    // Wis een eventuele foutmelding.
    foutLabel.setText("");
  }

  /**
   * Onvolledige event handler voor toevoegen.
   * Er wordt gecontroleerd of een woord uit letters bestaat, en een
   * woordenlijst uit woorden gescheiden door spaties.
   * @throws IOException 
   */
  private void voegtoeKnopAction() {
    String woord = woordVeld.getText().trim();
    if (!Pattern.matches("[a-zA-Z]+", woord)){
      foutLabel.setText("Woord bestaat niet uit letters");
      return;
    }
    String alleSynoniemen = synoniemenVeld.getText().trim() + ' ';
    if (!Pattern.matches("([a-zA-Z]+ +)+", alleSynoniemen)){
      foutLabel.setText("Synoniemenlijst bestaat niet uit woorden gescheiden door spaties");
      return;
    }

    // Voeg hier eigen code toe
    try {
      // Voeg het woord en de bijbehoorende synoniemen toe
      beheerObject.voegToe(woord, alleSynoniemen);
      // Toon een actuele woordenlijst
      woordList.setListData(beheerObject.geefWoorden());
      // Zorg ervoor dat de invoervelden leeg zijn en er geen woord geselecteerd is en dus ook geen synoniemen zichtbaar zijn.
      geenSelectie();
      veldenLeeg();
    } catch (SynoniemenBeheerException e) {
      // Toon een foutmelding wanneer b.v. een woord wordt toegevoegd welke al in de lijst voorkomt.
      foutLabel.setText(e.getMessage());
    }
  }

  /**
   * Lege event handler voor klikken in woordList
   */
  private void woordListPressed() {
  // Voeg hier eigen code toe
    try {
      veldenLeeg();
      synoniemenList.setListData(beheerObject.geefSynoniemen((String) woordList.getSelectedValue()));
    } catch (SynoniemenBeheerException e) {
      foutLabel.setText(e.getMessage());
    }
  }

  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane() {
    if (jContentPane == null) {
      foutLabel = new JLabel();
      foutLabel.setBounds(new Rectangle(14, 252, 436, 25));
      foutLabel.setText("");
      synoniemenLabel = new JLabel();
      synoniemenLabel.setBounds(new Rectangle(250, 8, 200, 20));
      synoniemenLabel.setText("Synoniemen");
      woordLabel = new JLabel();
      woordLabel.setBounds(new Rectangle(14, 8, 200, 20));
      woordLabel.setText("Woorden");
      jContentPane = new JPanel();
      jContentPane.setLayout(null);
      jContentPane.add(woordLabel, null);
      jContentPane.add(synoniemenLabel, null);
      jContentPane.add(getWoordScrollPane(), null);
      jContentPane.add(getSynomiemenScrollPane(), null);
      jContentPane.add(getWoordVeld(), null);
      jContentPane.add(getSynoniemenVeld(), null);
      jContentPane.add(foutLabel, null);
      jContentPane.add(getVoegtoeKnop(), null);
    }
    return jContentPane;
  }

  /**
   * This method initializes woordScrollPane    
   *    
   * @return javax.swing.JScrollPane    
   */
  private JScrollPane getWoordScrollPane() {
    if (woordScrollPane == null) {
      woordScrollPane = new JScrollPane();
      woordScrollPane.setBounds(new Rectangle(12, 40, 200, 125));
      woordScrollPane.setViewportView(getWoordList());
    }
    return woordScrollPane;
  }

  /**
   * This method initializes synomiemenScrollPane       
   *    
   * @return javax.swing.JScrollPane    
   */
  private JScrollPane getSynomiemenScrollPane() {
    if (synomiemenScrollPane == null) {
      synomiemenScrollPane = new JScrollPane();
      synomiemenScrollPane.setBounds(new Rectangle(250, 40, 200, 125));
      synomiemenScrollPane.setViewportView(getSynoniemenList());
    }
    return synomiemenScrollPane;
  }

  /**
   * This method initializes woordList  
   *    
   * @return javax.swing.JList  
   */
  private JList<String> getWoordList() {
    if (woordList == null) {
      woordList = new JList<String>();
      woordList.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent e) {
          woordListPressed();
        }
      });
    }
    return woordList;
  }

  /**
   * This method initializes synoniemenList     
   *    
   * @return javax.swing.JList  
   */
  private JList<String> getSynoniemenList() {
    if (synoniemenList == null) {
      synoniemenList = new JList<String>();
    }
    return synoniemenList;
  }

  /**
   * This method initializes woordVeld  
   *    
   * @return javax.swing.JTextField     
   */
  private JTextField getWoordVeld() {
    if (woordVeld == null) {
      woordVeld = new JTextField();
      woordVeld.setBounds(new Rectangle(12, 177, 200, 21));

      // Zorgt ervoor dat wanneer dit veld de focus krijgt er een actie wordt uitgevoerd.
      woordVeld.addFocusListener(new FocusListener() {
        public void focusGained(FocusEvent e) {
          // Zorg ervoor dat de invoervelden leeg zijn en er geen woord geselecteerd is en
          // dus ook geen synoniemen zichtbaar zijn.
          geenSelectie();
          veldenLeeg();
        }
        @Override
        public void focusLost(FocusEvent e) {
          // Verplichte implementatie, wordt verder niet gebruikt.
        }
      });
    }
    return woordVeld;
  }

  /**
   * This method initializes synoniemenVeld     
   *    
   * @return javax.swing.JTextField     
   */
  private JTextField getSynoniemenVeld() {
    if (synoniemenVeld == null) {
      synoniemenVeld = new JTextField();
      synoniemenVeld.setBounds(new Rectangle(250, 177, 200, 21));
    }
    return synoniemenVeld;
  }

  /**
   * This method initializes voegtoeKnop        
   *    
   * @return javax.swing.JButton        
   */
  private JButton getVoegtoeKnop() {
    if (voegtoeKnop == null) {
      voegtoeKnop = new JButton();
      voegtoeKnop.setText("Toevoegen");
      voegtoeKnop.setBounds(new Rectangle(183, 216, 96, 26));
      voegtoeKnop.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          voegtoeKnopAction();
        }
      });
    }
    return voegtoeKnop;
  }
  
  
  public static void main(String[] args) {
    SynoniemenFrame fr = new SynoniemenFrame();
    fr.setVisible(true);
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"
