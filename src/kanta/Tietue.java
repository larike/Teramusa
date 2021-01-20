package kanta;

/**
 * Rajapinta tietueelle
 * @author vesal
 * @version 11.2.2017
 *
 */
public interface Tietue {

    /**
     * Palauttaa j�senen kenttien lukum��r�n
     * @return kenttien lukum��r�
     */
    int getKenttia();


    /**
     * Eka kentt� joka on mielek�s kysytt�v�ksi
     * @return eknn kent�n indeksi
     */
    int ekaKentta();


    /**
     * Antaa k:n kent�n sis�ll�n merkkijonona
     * @param k monenenko kent�n sis�lt� palautetaan
     * @return kent�n sis�lt� merkkijonona
     */
    String anna(int k);


    /**
     * Asettaa k:n kent�n arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kent�n arvo asetetaan
     * @param jono jonoa joka asetetaan kent�n arvoksi
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
     * Palauttaa k:tta j�senen kentt�� vastaavan kysymyksen
     * @param k kuinka monennen kent�n kysymys palautetaan (0-alkuinen)
     * @return k:netta kentt�� vastaava kysymys
     */
    String getKysymys(int k);

}
