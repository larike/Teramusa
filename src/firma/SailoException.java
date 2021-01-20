package firma;
/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Lari Kettunen
 * @version 1.0, 26.06.2017
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}