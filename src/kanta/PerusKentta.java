package kanta;

import javax.swing.SwingConstants;

/**
 * Peruskentt‰ joka implementoi kysymyksen k‰sittelyn
 * ja tarkistajan k‰sittelyn.
 *
 * @author Vesa Lappalainen
 * @version 1.0, 22.02.2003
 * @version 1.3, 02.04.2003
 */
public abstract class PerusKentta implements Kentta { // NOPMD
    private final String kysymys;

    /**
     * Yleisen tarkistajan viite
     */
    protected Tarkistaja tarkistaja = null;

    /**
     * Alustetaan kentt‰ kysymyksen tiedoilla.
     * @param kysymys joka esitet‰‰n kentt‰‰ kysytt‰ess‰.
     */
    public PerusKentta(String kysymys)  { this.kysymys = kysymys; }

    /**
     * Alustetaan kysymyksell‰ ja tarkistajalla.
     * @param kysymys joka esitet‰‰n kentt‰‰ kysytt‰ess‰.
     * @param tarkistaja tarkistajaluokka joka tarkistaa syˆt‰n oikeellisuuden
     */
    public PerusKentta(String kysymys,Tarkistaja tarkistaja) {
        this.kysymys = kysymys;
        this.tarkistaja = tarkistaja;
    }

    /**
     * @return kent‰n arvo merkkijonona
     * @see kanta.Kentta#toString()
     */
    @Override
    public abstract String toString();

    /**
     * @return Kentt‰‰ vastaava kysymys
     * @see kanta.Kentta#getKysymys()
     */
    @Override
    public String getKysymys() { // NOPMD (jostain syyst‰ luulee abstraktiksi metodiksi)
        return kysymys;
    }

    /**
     * @param jono josta otetaan kent‰n arvo
     * @see kanta.Kentta#aseta(java.lang.String)
     */
    @Override
    public abstract String aseta(String jono);

    /**
     * Palauttaa kent‰n tiedot veratiltavana merkkijonona
     * @return vertailtava merkkijono kent‰st‰
     */
    @Override
    public String getAvain() { // NOPMD (jostain syyst‰ luulee abstraktiksi metodiksi)
        return toString().toUpperCase();
    }


    /**
     * @return syv‰kopio oliosta
     */
    @Override
    public Kentta clone() throws CloneNotSupportedException {
        return (Kentta)super.clone();
    }


    /**
     * @return vertailee kahta oliota kesken‰‰n
     */
    @Override
    public int compareTo(Kentta k) {
            return getAvain().compareTo(k.getAvain());
    }

    
    /**
     * @return vaakasuuntainen sijainti kent‰lle
     */
    @Override
    public int getSijainti() {
        return SwingConstants.LEFT;        
    }
}