package adOpdr1bGui;

import javax.swing.SwingUtilities;

import adOpdr1bCore.PostfixCalc;
import adOpdr1bCore.PostfixException;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ExpressieFrame extends JFrame {

//  private PostfixCalc postfixCalc = new PostfixCalc();
  private PostfixCalc postfixCalc = null;
  private static final long serialVersionUID = 1L;
  private JPanel jContentPane = null;
  private JLabel expressieLabel = null;
  private JLabel waardeLabel = null;
  private JTextField expressieVeld = null;
  private JTextField waardeVeld = null;
  private JButton berekenKnop = null;
  private JLabel foutLabel = null;

  /**
   * @param args
   */
  public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ExpressieFrame thisClass = new ExpressieFrame();
        thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        thisClass.setVisible(true);
      }
    });
  }

  /**
   * This is the default constructor
   */
  public ExpressieFrame() {
    super();
    initialize();
  }

  /**
   * This method initializes this
   *
   * @return void
   */
  private void initialize() {
    this.setSize(485, 139);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(getJContentPane());
    this.setTitle("JFrame");
  }

  /**
   * This method initializes jContentPane
   *
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane() {
    if (jContentPane == null) {
      foutLabel = new JLabel();
      foutLabel.setBounds(new Rectangle(5, 75, 450, 19));
      foutLabel.setText("");
      waardeLabel = new JLabel();
      waardeLabel.setBounds(new Rectangle(5, 47, 62, 19));
      waardeLabel.setText("Waarde:");
      expressieLabel = new JLabel();
      expressieLabel.setBounds(new Rectangle(5, 15, 74, 19));
      expressieLabel.setText("Expressie:");
      jContentPane = new JPanel();
      jContentPane.setLayout(null);
      jContentPane.add(expressieLabel, null);
      jContentPane.add(waardeLabel, null);
      jContentPane.add(getExpressieVeld(), null);
      jContentPane.add(getWaardeVeld(), null);
      jContentPane.add(getBerekenKnop(), null);
      jContentPane.add(foutLabel, null);
    }
    return jContentPane;
  }

  private void berekenKnopAction() {
    // Voeg hier eigen code toe

    try {
      // Maak een nieuw PostfixCalc object aan
      postfixCalc = new PostfixCalc(expressieVeld.getText());

      try {
        // Bereken de waarde van de postfix expressie en toon deze in het waardeVeld
        waardeVeld.setText(String.valueOf(postfixCalc.calc()));
      }
      catch (PostfixException e1) {
        // Toon een eventuele foutmelding van de methode bereken in het foutLabel
        foutLabel.setText(e1.getMessage());
      }
    }
    catch (PostfixException e) {
      // Toon een eventuele foutmelding van de methode bereken in het foutLabel
      foutLabel.setText(e.getMessage());
    }
    catch (IOException e) {
      // Toon een eventuele foutmelding van de methode bereken in het foutLabel
      foutLabel.setText(e.getMessage());
    }

  }

  /**
   * This method initializes expressieVeld
   *
   * @return javax.swing.JTextField
   */
  private JTextField getExpressieVeld() {
    if (expressieVeld == null) {
      expressieVeld = new JTextField();
      expressieVeld.setBounds(new Rectangle(87, 15, 242, 19));
    }
    
    // Maak het foutLabel en waardeVeld leeg zodra er in het expressieVeld word geklikt.
    expressieVeld.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        foutLabel.setText("");
        waardeVeld.setText("");
      }
      @Override
      public void focusLost(FocusEvent e) {
        // Verplichte implementatie, wordt verder niet gebruikt.
      }
    });
    return expressieVeld;
  }

  /**
   * This method initializes waardeVeld
   *
   * @return javax.swing.JTextField
   */
  private JTextField getWaardeVeld() {
    if (waardeVeld == null) {
      waardeVeld = new JTextField();
      waardeVeld.setBounds(new Rectangle(87, 47, 69, 19));
    }
    return waardeVeld;
  }

  /**
   * This method initializes berekenKnop
   *
   * @return javax.swing.JButton
   */
  private JButton getBerekenKnop() {
    if (berekenKnop == null) {
      berekenKnop = new JButton();
      berekenKnop.setBounds(new Rectangle(184, 47, 147, 19));
      berekenKnop.setText("Bereken waarde");
      berekenKnop.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          // System.out.println("actionPerformed()");
          // Event stub actionPerformed()
          berekenKnopAction();
        }
      });
    }
    return berekenKnop;
  }


}
