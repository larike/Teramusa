package kanta;

/**
 * Tarkistaja joka tarkistaa ett‰ jono vastaa regexpi‰
 * Hyv‰ksyy tyhj‰n jonon.
 * @author vesal
 *
 */
public class RegExpTarkistaja implements Tarkistaja {

    private final String regexp;
    private String viesti = null;


    /**
     * Luodaan tarkistaja joka hyv‰ksyy sallitut merkit
     * @param regexp hyv‰ksytt‰v‰t merkit
     */
    public RegExpTarkistaja(String regexp) {
        this(regexp,null);
    }


    /**
     * Luodaan tarkistaja joka hyv‰ksyy sallitut merkit
     * @param regexp hyv‰ksytt‰v‰t merkit
     * @param viesti viesti joka n‰ytet‰‰n jos jonon ehto ei t‰yty  
     */
    public RegExpTarkistaja(String regexp, String viesti) {
        this.regexp = regexp;
        if ( viesti != null ) this.viesti = viesti;
        else this.viesti = "Ei vastaa maskia: " + regexp;
    }


    /**
     * Tarkistaa ett‰ jono sis‰lt‰‰ vain valittuja numeroita
     * @param jono tutkittava jono
     * @example
     * <pre name="test">
     *   RegExpTarkistaja tar = new RegExpTarkistaja("[1-4]*");
     *   tar.tarkista("12") === null;
     *   tar.tarkista("15") === "Ei vastaa maskia: [1-4]*";
     *   tar.tarkista("")   === null;
     *   tar = new RegExpTarkistaja("[1-4]+","Vain numeroita 1-4");
     *   tar.tarkista("15") === "Vain numeroita 1-4";
     *   tar.tarkista("") === "Vain numeroita 1-4";
     *   tar.tarkista("1") === null;
     * </pre>
     */
    @Override
    public String tarkista(String jono) {
        if ( jono.matches(regexp) ) return null;
        return viesti;
    }

}
