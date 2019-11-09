package adOpdr1bCore;
import java.util.Vector;

/**
 * Eigen Stack klasse met een beperkt aantal methoden. Maakt gebruik van de vector klasse voor opslag van de elementen.
 * @author Johan Elzinga
 *
 */
public class PostfixExpressieStack<E> {
  private Vector<E> vector = null;

  /**
   * Constructor
   */
  public PostfixExpressieStack() {
    vector = new Vector<E>();
  }

  /**
   * Voegt een element toe aan de bovenkant van de stack.
   * @param e Het element wat toegevoegd moet worden.
   */
  public void push(E e) {
    vector.addElement(e);
  }

  /**
   * Verwijdert het bovenste element van de stack en geeft deze terug.
   * @return Het verwijderde element.
   * @throws PostfixException Wordt opgegooid wanneer de stack leeg is.
   */
  public E pop() throws PostfixException {
    if (isEmpty()) {
      throw new PostfixException("Stack is leeg");
    }
    E e = vector.elementAt(size() -1);
    vector.removeElementAt(size() -1);
    return e;
  }

  /**
   * Geeft de omvang van de stack terug.
   * @return
   */
  public int size() {
    return vector.size();
  }

  /**
   * Geeft terug of de stack leeg is.
   * @return True wanneer de stack leeg is, anders false.
   */
  public boolean isEmpty() {
    return vector.isEmpty();
  }

  /**
   * Geeft het bovenste element van de stack terug zonder deze te verwijderen.
   * @return Het bovenste element op de stack.
   * @throws PostfixException Wordt opgegooid wanneer de stack leeg is.
   */
  public E top() throws PostfixException {
    if (isEmpty()) {
      throw new PostfixException("Stack is leeg");
    }
    return vector.elementAt(size() -1);
  }
}
