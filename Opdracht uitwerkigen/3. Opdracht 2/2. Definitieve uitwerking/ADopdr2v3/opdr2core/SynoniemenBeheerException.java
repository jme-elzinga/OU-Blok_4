package opdr2core;
/**
 * Eigen Exception klasse
 * @author Johan Elzinga
 *
 */

@SuppressWarnings("serial")

public class SynoniemenBeheerException  extends Exception {
  /**
   * Constuctor voor een nieuwe exception zonder bericht 
   */
  public SynoniemenBeheerException() {
    super();
  }

  /**
   * Constuctor voor een nieuwe exception met bericht
   * @param message Het bericht van de exception. Kan met de methode getMessage() opgevraagd worden.
   */
  public SynoniemenBeheerException(String message) {
    super(message);
  }

}
