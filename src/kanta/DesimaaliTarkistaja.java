package kanta;

/**
 * Tarkistaja joka tarkistaa että jono on desimaalilukumuotoa
 * @author vesal
 * @version 3.1.2011
 */
public class DesimaaliTarkistaja implements Tarkistaja {

    /**
     * Tarkistetaan että on desimaaliluku
     * @param jono tarkistettava jono
     * @example
     * <pre name="test">
     *   DesimaaliTarkistaja des = new DesimaaliTarkistaja();
     *   des.tarkista("") === null;
     *   des.tarkista("12.3") === null;
     *   des.tarkista("12,3") === null;
     *   des.tarkista("12") === null;
     *   des.tarkista("12k3") === "Ei desimaaliluku (12k3)"; 
     *   des.tarkista("12..3") === "Ei desimaaliluku (12..3)"; 
     * </pre>
     */
    @Override
    public String tarkista(String jono) {
        if ( jono.length() == 0 ) return null; // sallitaan tyhjä
        if ( !jono.matches("[0-9]*[.,]?[0-9]*") ) return "Ei desimaaliluku (" + jono +")"; 
        return null;
    }

}
