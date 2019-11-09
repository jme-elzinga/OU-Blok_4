package adOpdr1bCore;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

/**
 * Utility klasse verantwoordelijk voor het berekenen van een (geldige) postfix expressie.
 * Wanneer de postfix expressie ongeldig is wordt een PostfixException opgegeooid.
 * Deze klasse gebruikt de PostfixExpressieStack klasse en de Operation Enum klasse.
 * @author Johan Elzinga
 *
 */
public class PostfixCalc {

  // Voorkom dat java een default constructor toevoegt aan deze static class
  private PostfixCalc() {}
  
  // Deze methode maak een nieuwe stack aan met daarin de gegeven expressie. Ongeldige tekens leveren een PostfixException.

  /**
   * Berekent de uitkomst van een geldige postfix expressie
   * @param expresion De te berekenen postfix expressie
   * @return De uitkomst van de geldige postfix expressie
   * @throws PostfixException Wordt opgegooid wanneer de postfix expressie ongeldig is (de fout staat in de exception message)
   * @throws IOException Kan worden opgegooid tijdens het verwerken van de expressie door de StreamTokenizer
   */
  public static String calcPostfixExpressie(String expresion) throws PostfixException, IOException {

    // Controleer of de expressie leeg is of gelijk is aan null, gooi dan een exception op. 
    if(expresion == null || expresion.contentEquals("")) {
      throw new PostfixException("FOUT! Postfix expressie is leeg");
    }

    // Maak een nieuwe stack aan
    PostfixExpressieStack<Integer> postfixStack = new PostfixExpressieStack<Integer>();

    // Maak een nieuwe tokenstream aan met de ingevoerde expressie
    StreamTokenizer tokenReader = new StreamTokenizer(new StringReader(expresion));
    // Zorg ervoor dat het speciale teken '/' als een token wordt gezien
    tokenReader.ordinaryChar('/');

    int token=0;
    boolean eof = false;

    // Verwerk de expressie, bereken de uitkomst en zet deze op de stack
    do {
      // Lees een token
      token = tokenReader.nextToken();

      switch (token) {
        case StreamTokenizer.TT_EOF:
          // Einde van de TokenStream
          eof = true;
          break;
        case StreamTokenizer.TT_NUMBER:
          // Token bevat een getal, zet deze op de stack
          postfixStack.push((int)tokenReader.nval);
          break;
        default:
          // Tokens bevat iets anders dan een Spatie, Tab, EOL

          String operator = Character.toString((char)token);
          // Controleer of het een geldige operator betreft, zo ja bereken dan de uitkomst van dit deel van de expressie
          if (isOperator(operator)) {
            bereken(postfixStack,operator);
          } else {
            // overige tekens worden als ongeldig aangemerkt.
            throw new PostfixException("FOUT! Postfix expressie bevat een ongeldig teken!");
          }
      }
    } while (!eof);
    
    // Nu zou er nog maar 1 getal op de stack mogen staan, namelijk de eind uitkomst van de expressie.
    if (postfixStack.size() == 1) {
      // Geef de uitkomst van de hele expressie terug
      return Integer.toString(postfixStack.pop());
    } else {
      // Er staan meer getallen op de stack dus bevat de expressie te weinig operatoren, gooi een exceptie op.
      throw new PostfixException("FOUT! Postfix expressie bevat te weinig operatoren");
    }
  }


  // Bereken een deel van de expressie
  private static void bereken(PostfixExpressieStack<Integer> postfixStack, String operator) throws PostfixException {
    // er moeten minimaal 2 getallen op de stack zijn anders zijn er meer operatoren dan getallen
    if (postfixStack.size()>1) {
      int y = postfixStack.pop();
      int x = postfixStack.pop();
      // Breken de uitkomst van dit deel van de expressie en plaats deze weer op de stack
      postfixStack.push(Operation.fromString(operator).apply(x,y));
    } else {
      // Te weinig getallen in de expressie, gooi een exceptie op.
      throw new PostfixException("FOUT! Postfix expressie bevat te weinig getallen!");
    }
  }


  // Controleert of de string s een operator bevat en geeft dan true terug, anders false
  private static boolean isOperator(String s) {
    if (!(s == null)) {
      if (Operation.fromString(s) == null) {
        // string s bevat geen operator welke in de enum Operation voorkomt
        return false;
      } else {
        // String s bevat een geldige operator
        return true;
      }
    } else {
      // String s is null
      return false;
    }
  }

}
