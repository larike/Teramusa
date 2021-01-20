package kanta;

/**
 * Kenttä tavallisia merkkijonoja varten.
 * @author vesal
 * @version 31.3.2008
 *
 */
public class JonoKentta extends PerusKentta {
    private String jono = "";

    /**
     * Alustetaan kenttä kysymyksen tiedoilla.
     * @param kysymys joka esitetään kenttää kysyttäessä.
     * @example
     * <pre name="test">
     *    JonoKentta jono = new JonoKentta("nimi");
     *    jono.getKysymys() === "nimi";
     *    jono.toString() === "";
     *    jono.aseta("Aku");
     *    jono.toString() === "Aku";
     * </pre>
     */
    public JonoKentta(String kysymys) { super(kysymys); }


    /**
     * Alustetaan kysymyksellä ja tarkistajalla.
     * @param kysymys joka esitetään kenttää kysyttäessä.
     * @param tarkistaja tarkistajaluokka joka tarkistaa syötön oikeellisuuden
     */
    public JonoKentta(String kysymys,Tarkistaja tarkistaja) {
        super(kysymys,tarkistaja);
    }

    /**
     * @return Palauetaan kentän sisältö
     * @see kanta.PerusKentta#toString()
     */
    @Override
    public String toString() { return jono; }

    /** 
     * @param s merkkijono joka asetetaan kentän arvoksi
     * @see kanta.PerusKentta#aseta(java.lang.String)
     */
    @Override
    public String aseta(String s) {
        if ( tarkistaja == null ) {
            this.jono = s; 
            return null;
        }

        String virhe = tarkistaja.tarkista(s);
        if ( virhe == null ) {
            this.jono = s; 
            return null;
        }
        return virhe;
    }

}
