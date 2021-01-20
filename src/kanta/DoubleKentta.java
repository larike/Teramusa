package kanta;

import javax.swing.SwingConstants;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kentt‰ reaalilukuja varten.
 * @author vesal
 * @version 31.3.3008
 *
 */
public class DoubleKentta extends PerusKentta {
    private double arvo;

    /**
     * Alustetaan kentt‰ kysymyksell‰
     * @param kysymys joka esitet‰‰n kent‰n kohdalla
     */
    public DoubleKentta(String kysymys) { super(kysymys,new DesimaaliTarkistaja()); }

    /**
     * @return Kent‰n arvo reaalilukuna
     */
    public double getValue() { return arvo; }

    /**
     * Asetetaan kent‰n arvo reaalilukuna
     * @param value kent‰n uusi arvo reaalilukuna
     */
    public void setValue(double value) { arvo = value; }

    /**
     * @return kent‰n arvo merkkijonona
     * @see kanta.PerusKentta#toString()
     */
    @Override
    public String toString() { return ""+arvo; }


    /**
     * @param jono jono jaoka asetetaan.  V‰‰rist‰ jonoista
     * arvoksi j‰‰ muuttumatta
     * @see kanta.PerusKentta#aseta(java.lang.String)
     * @example
     * <pre name="test">
     * DoubleKentta kentta = new DoubleKentta("summa");
     * kentta.aseta("kissa") === "Ei desimaaliluku (kissa)";  kentta.getValue() ~~~ 0.0;  
     * kentta.aseta("12.7")  === null;  kentta.getValue() ~~~ 12.7; 
     * kentta.aseta("32k7")  === "Ei desimaaliluku (32k7)";  kentta.getValue() ~~~ 12.7;  
     * </pre>
     */
    @Override
    public String aseta(String jono) {
        String virhe = null;
        if ( tarkistaja != null ) virhe = tarkistaja.tarkista(jono);
        if ( virhe != null ) return virhe;
        this.arvo = Mjonot.erotaDouble(jono,0.0);
        return null;
    }

    /**
     * Palauttaa kent‰n tiedot veratiltavana merkkijonona
     * @return vertailtava merkkijono kent‰st‰
     * @example
     * <pre name="test">
     * DoubleKentta kentta = new DoubleKentta("m‰‰r‰");
     *                                         //  123456789012345678
     * kentta.aseta("12");  kentta.getAvain() === "         12.000000";
     * kentta.aseta("1");   kentta.getAvain() === "          1.000000";
     * kentta.aseta("999"); kentta.getAvain() === "        999.000000";
     *   
     * </pre>
     */
    @Override
    public String getAvain() { 
        return Mjonot.fmt(arvo, 18,6); 
    }
    
    
    /** 
     * @return vaakasuuntainen sijainti kent‰lle 
     */ 
    @Override 
    public int getSijainti() { 
        return SwingConstants.RIGHT;         
    } 
}     