package kanta;

/**
 * Kentt� tavallisia merkkijonoja varten.
 * @author vesal
 * @version 31.3.2008
 *
 */
public class JonoKentta extends PerusKentta {
    private String jono = "";

    /**
     * Alustetaan kentt� kysymyksen tiedoilla.
     * @param kysymys joka esitet��n kentt�� kysytt�ess�.
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
     * Alustetaan kysymyksell� ja tarkistajalla.
     * @param kysymys joka esitet��n kentt�� kysytt�ess�.
     * @param tarkistaja tarkistajaluokka joka tarkistaa sy�t�n oikeellisuuden
     */
    public JonoKentta(String kysymys,Tarkistaja tarkistaja) {
        super(kysymys,tarkistaja);
    }

    /**
     * @return Palauetaan kent�n sis�lt�
     * @see kanta.PerusKentta#toString()
     */
    @Override
    public String toString() { return jono; }

    /** 
     * @param s merkkijono joka asetetaan kent�n arvoksi
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
