package adOpdr1bCore;
/**
 * Eigen Exception klasse
 * @author Johan Elzinga
 *
 */

@SuppressWarnings("serial")

public class PostfixException extends Exception {
  /**
   * Constuctor voor een nieuwe exception zonder bericht 
   */
  public PostfixException() {
    super();
  }

  /**
   * Constuctor voor een nieuwe exception met bericht
   * @param message Het bericht van de exception. Kan met de methode getMessage() opgevraagd worden.
   */
  public PostfixException(String message) {
    super(message);
  }

}
