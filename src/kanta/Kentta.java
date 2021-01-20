package kanta;

/**
 * Rajapinta tietueen yhdelle kent‰lle.
 * @author vesal
 *
 */
public interface Kentta extends Cloneable, Comparable<Kentta>{

    /**
     * kent‰n arvo merkkijonona.
     * @return kentt‰ merkkkijonona
     * @example
     * <pre name="test">
     *  IntKentta kentta = new IntKentta("m‰‰r‰");
     *  kentta.aseta("12") === null;
     *  kentta.toString() === "12";
     * </pre>
     */
    @Override
    String toString();

    /**
     * Palauttaa kent‰‰n liittyv‰n kysymyksen.
     * @return kentt‰n liittyv‰ kysymys.
     * @example
     * <pre name="test">
     *  IntKentta kentta = new IntKentta("m‰‰r‰");
     *  kentta.getKysymys() === "m‰‰r‰";
     * </pre>
     */
    String getKysymys();

    /**
     * Asettaa kent‰n sis‰llˆn ottamalla tiedot
     * merkkijonosta.
     * @param jono jono josta tiedot otetaan.
     * @return null jos sis‰ltˆ on hyv‰, muuten merkkijonona virhetieto
     * @example
     * <pre name="test">
     *  IntKentta kentta = new IntKentta("m‰‰r‰");
     *  kentta.aseta("12") === null; kentta.getValue() === 12;
     *  kentta.aseta("k") === "Ei kokonaisluku (k)"; kentta.getValue() === 12;
     * </pre>
     */
    String aseta(String jono);


    /**
     * Palauttaa kent‰n tiedot veratiltavana merkkijonona
     * @return vertailtava merkkijono kent‰st‰
     * @example
     * <pre name="test">
     *  IntKentta k1 = new IntKentta("m‰‰r‰");
     *  IntKentta k2 = new IntKentta("m‰‰r‰");
     *  k1.aseta("12"); k2.aseta("5");
     *  k1.getAvain().compareTo(k2.getAvain()) > 0 === true;
     * </pre>
     */
    String getAvain();


    /**
     * @return syv‰kopio kent‰st‰, teht‰v‰ jokaiseen luokkaa toimivaksi
     * @throws CloneNotSupportedException
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     *  IntKentta k1 = new IntKentta("m‰‰r‰");
     *  k1.aseta("12");
     *  IntKentta k2 = k1.clone();
     *  k1.toString() === k2.toString();
     *  k1.aseta("5");
     *  k1.toString() == k2.toString() === false;
     * </pre>
     */
    Kentta clone() throws CloneNotSupportedException ;

    
    /**
     * @return vaakasuuntainen sijainti kent‰lle
     * @example
     * <pre name="test">
     * #import javax.swing.SwingConstants;
     *  IntKentta k1 = new IntKentta("m‰‰r‰");
     *  k1.getSijainti() === SwingConstants.RIGHT; 
     * </pre>
     */
    int getSijainti();
    
}
