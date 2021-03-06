package kanta;

/**
 * Rajapinta yleiselle tarkistajalle.
 * Tarkistajan teht�v� on tutkia onko annettu
 * merkkijono kelvollinen kent�n sis�ll�ksi ja jos on.
 * palautetaan null.
 * Virhetapauksessa palautetaan virhett� mahdollisimman
 * hyvin kuvaava merkkijono.
 * @author vesal
 * @version 31.3.2008
 *
 */
public interface Tarkistaja {
    /**
     * Tutkitaan k�yk� annettu merkkijono kent�n sis�ll�ksi.
     * @param jono tutkittava merkkijono
     * @return null jos jono oikein, muuten virheilmoitusta vastaava merkkijono
     */
    String tarkista(String jono);
}
