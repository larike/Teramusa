package kanta;

/**
 * Rajapinta tietueelle
 * @author vesal
 * @version 11.2.2017
 *
 */
public interface Tietue {

    /**
     * Palauttaa jäsenen kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    int getKenttia();


    /**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return eknn kentän indeksi
     */
    int ekaKentta();


    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    String anna(int k);


    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     * #import kerho.Jasen;
     *   Jasen jasen = new Jasen();
     *   jasen.aseta(1,"Ankka Aku") === null;
     *   jasen.aseta(2,"kissa") =R= "Hetu liian lyhyt"
     *   jasen.aseta(2,"030201-1111") === "Tarkistusmerkin kuuluisi olla C"; 
     *   jasen.aseta(2,"030201-111C") === null; 
     *   jasen.aseta(9,"kissa") === "Ei kokonaisluku (kissa)";
     *   jasen.aseta(9,"1940") === null;
     * </pre>
     */
    String aseta(int k, String jono);


    /**
     * Palauttaa k:tta jäsenen kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */
    String getKysymys(int k);

}
