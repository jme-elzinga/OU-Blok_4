package adOpdr1bCore;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

/**
 * Klasse verantwoordelijk voor het berekenen van een postfix expressie. Er wordt gebruik gemaakt van de PostfixExpressieStack klasse en de Operation Enum klasse.
 * @author Johan Elzinga
 *
 */
public class PostfixCalc {

  private PostfixExpressieStack<String> postfixStack = null;

  /**
   * Constructor.
   * @param expressie De postfix exppressie die berekend moet worden
   * @throws PostfixException Wordt opgegeooid wanneer de postfix expressie fouten bevat.
   * @throws IOException Eventuele fouten uit de StreamTokenizer klasse
   */
  public PostfixCalc(String expresion) throws PostfixException, IOException {
    createPostfixStack(expresion);
  }

  // Deze methode maak een nieuwe stack aan met daarin de gegeven expressie. Ongeldige tekens leveren een PostfixException.
  private void createPostfixStack(String expresion) throws PostfixException, IOException {

    // Maak een neiuwe Stack aan
    postfixStack = new PostfixExpressieStack<String>();

    // Maak een nieuwe tokenstream aan met de ingevoerde tekst
    StreamTokenizer tokenReader = new StreamTokenizer(new StringReader(expresion));

    // Zorg ervoor dat het speciale teken '/' als een token wordt gezien
    tokenReader.ordinaryChar('/');

    int token;
    boolean eof = false;

    do {
      token = tokenReader.nextToken();
      switch (token) {
        case StreamTokenizer.TT_EOF:
          // Einde van de TokenStream
          eof = true;
          break;
        case StreamTokenizer.TT_NUMBER:
          // Token bevat een getal
          int val = (int)tokenReader.nval;
          postfixStack.push(String.valueOf(val));
          break;
        default:
          // Tokens bevat iets anders dan een Spatie, Tab, EOL

          // Alleen de operatoren uit enum Operation worden op de stack gezet.
          if (isOperator(Character.toString((char)token))) {
            postfixStack.push(Character.toString((char)token));
          } else {
            // overige tekens worden als ongeldig aangemerkt.
            throw new PostfixException("FOUT! Postfix expressie bevat een ongeldig teken!");
          }
      }
    } while (!eof);
  }

  /**
   * Deze methode berekent de uitkomst van een postfix expressie (gegeven bij de aanroep van de constructor)
   * @return Geheel getal met de uitkomst van de berekening
   * @throws PostfixException Eventuele fouten in de postfix expressie.
   */
  public int calc() throws PostfixException {
    int resultaat = calc2();
    if (postfixStack.size() != 0) {
      throw new PostfixException("FOUT! Postfix expressie bevat meer getallen dan operatoren");
    }
    return resultaat;
  }

  // Deze methode berekent daadwerkelijk de uitkomst (eventueel recursief) van de, in postfixStack opgeslagen, postfix expressie
  private int calc2() throws PostfixException {

    // Controleer of de stack maar 1 object bevat. Is dat het geval geef dit dan terug (mits het een getal is!) 
    if (postfixStack.size() == 1) {
      // controleer of het bovenste element van de stack geen operator is
      if (!isOperator(postfixStack.top())) {
        // het bovenste element van de stack is een getal, geef dit als resultaat terug
        return Integer.parseInt(postfixStack.pop());
      } else {
        // het bovenste element is een operator
        throw new PostfixException("FOUT! Postfix expressie mag niet alleen uit een operator bestaan");
      }
    } else if (postfixStack.size() == 2) {
      // de postfix expressie mag niet uit twee elementen bestaan, het moet minimaal 3 bevatten
      throw new PostfixException("FOUT! Postfix expressie is niet correct (minimaal 3 elementen)");
    }

    String operator = null;

    // Het bovenste element op de stack dit moet een operator zijn
    if (isOperator(postfixStack.top())) {
      operator = postfixStack.pop();
    } else {
      // Geen operator dus geef een foutmelding.
      throw new PostfixException("FOUT! Postfix expressie moet eindigen met een operator!");
    }

    int y = 0;

    // top-1 positie op de stack (=Y)
    if (isOperator(postfixStack.top())) {
      // recursieve aanroep!!! Y is een operator dus een nieuwe berekening starten.
      y = calc2();
    } else {
      y = Integer.parseInt(postfixStack.pop());
    }

    // Wanneer er op dit punt geen elementen meer in de stack zitten dan zijn er evenveel of meer operatoren dan getallen, de expressie is dus onjuist!
    if (postfixStack.isEmpty()) {
      throw new PostfixException("FOUT! Postfix expressie bevat evenveel of meer operatoren dan getallen!");
    }

    int x = 0;

    // top-2 positie op de stack (=X)
    if (isOperator(postfixStack.top())) {
      // recursieve aanroep!!! X is een operator dus een nieuwe berekening starten.
      x = calc2();
    } else {
      x = Integer.parseInt(postfixStack.pop());
    }

    return Operation.fromString(operator).apply(x,y);
  }

  // Deze methode controleert of de string s een operator bevat en geeft dan true terug, anders false
  private boolean isOperator(String s) {
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
