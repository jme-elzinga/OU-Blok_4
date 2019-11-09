package opdr2core;

import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Klasse verantwoordelijk voor het beheren van een SynoniemenBeheer object.<br>
 * <br>
 * Er is gebruik gemaakt van de implementatieklassen TreeSet en TreeMap.<br>
 * De reden om voor deze klassen te kiezen is omdat beide implementaties
 * zijn van de interfaces SortedSet en SortedMap. Deze staan niet toe
 * dat er duplicaten zijn en beide zijn sorteerbaar.<br>
 * De TreeMap bevat als Key de woorden en als Value een TreeSet
 * welke de synoniemen van dat woord bevat.<br>
 * <br> 
 * TreeMap&lt;String, TreeSet&lt;String&gt;&gt;
 * 
 * @author Johan Elzinga
 */

public class SynoniemenBeheer {
  private SortedMap<String, SortedSet<String>> woordenMap = null;

  /**
   * Constuctor.
   */
  public SynoniemenBeheer() {
    // Initialiseer de woordenMap met een TreeMap die gebruik maakt van de Comparator String.CASE_INSENSITIVE_ORDER
    // https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#CASE_INSENSITIVE_ORDER
    woordenMap = new TreeMap<String, SortedSet<String>>(String.CASE_INSENSITIVE_ORDER);
  }

  /**
   * Voegt het gegeven woord en bijbehorende synoniemen toe.
   * @param woord Het gegeven woord
   * @param synoniemen Een string met daarin 1 of meer synoniemen.<br>LET OP: een synoniem kan maar 1 keer voorkomen bij hetzelfde woord! Het eerste synoniem wordt bij het woord opgeslagen, de overige dubbele synoniemen worden zonder melding verwijderd
   * @throws SynoniemenBeheerException Wanneer het woord al voorkomt of wanneer woord en/of synoniemen string null of leeg is
   */
  public void voegToe(String woord, String synoniemen) throws SynoniemenBeheerException {
    if (woord != null && !woord.contentEquals("") && synoniemen != null && !synoniemen.contentEquals("")) {
      // Alhoewel een SortedMap geen duplicaten toestaat is het netter om dit zelf af te vangen.
      // Controleer of een woord (ongeacht het gebruik van hoofd- en/of kleineleters!) al in de map voorkomt,
      // gooi in dat geval een exceptie op.
      if (!woordenMap.isEmpty() && woordenMap.containsKey(woord)) {
        throw new SynoniemenBeheerException("Het woord '" + woord + "' komt al voor in de lijst als '" + woordenMap.tailMap(woord).firstKey() + "'");
      } else {
        // Lijst is leeg of het woordt komt nog niet voor in de lijst, voeg het dan toe met de gegeven synoniemen.
        woordenMap.put(woord, splitSynoniemen(synoniemen));
      }
    } else {
      // Deze situatie zou niet voor mogen komen, gebeurt het wel gooi dan een exceptie op.
      throw new SynoniemenBeheerException("Woord of synoniemen zijn null of leeg!");
    }
  }

  /**
   * Geeft een array met strings terug met daarin de woorden of gooit een exceptie op wanneer de lijst nog leeg is.
   * @return Een array van strings met woorden.
   * @throws SynoniemenBeheerException wanneer de lijst met woorden leeg is.
   */
  public String[] geefWoorden() throws SynoniemenBeheerException {
    if (woordenMap.isEmpty()) {
      throw new SynoniemenBeheerException("De lijst met woorden is leeg!");
    } else {
      String[] woorden = woordenMap.keySet().toArray(new String[woordenMap.keySet().size()]);
      return woorden;
    }
  }

  /**
   * Geeft een array met strings terug met de synoniemen welke bij het gegeven woord horen.
   * @param woord Het woord waarvan de synoniemem terug gegeven moeten worden.
   * @return Een array van strings met de bij het woord behorende synoniemen.
   * @throws SynoniemenBeheerException Wanneer woord gelijk aan null of leeg is.
   */
  public String[] geefSynoniemen(String woord) throws SynoniemenBeheerException {
    if (woord == null || woord.contentEquals("")) {
      throw new SynoniemenBeheerException("FOUT! Er is geen woord geselecteerd of gegeven!");
    } else {
      return woordenMap.get(woord).toArray(new String[woordenMap.get(woord).size()]);
    }
  }

  // Splits een string met synoniemen op en voegt deze toe aan een TreeSet
  private SortedSet<String> splitSynoniemen(String synoniemen) {
    // De synoniemen worden in een TreeSet opgeslagen welke eerst op lengte en vervolgens op
    // alfabetische volgorde wordt gesorteerd door gebruik te maken van de thenComparing methode
    // van de Comparator interface 
    // https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#thenComparing-java.util.Comparator-
    SortedSet<String> synoniemList = new TreeSet<String>(Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER));
    String[] syns = synoniemen.split(" ");
    synoniemList.addAll(Arrays.asList(syns));
    return synoniemList;
  }

}
