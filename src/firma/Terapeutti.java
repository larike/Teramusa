package firma;

import java.io.*;
import fi.jyu.mit.ohj2.Mjonot;
import static kanta.HetuTarkistus.rand;
import kanta.Tietue;

/** Terapeutti, huolehtii tunnusnumerosta
 * @author Lari Kettunen
 * @version 4.7.2017
 *
 */
public class Terapeutti implements Cloneable, Tietue {
    
    private int tunnusNro;
    private int asiakasNro;
    private String terapeutti;
    private String terapiamuoto;
    private String kesto;
    
    private static int seuraavaNro = 1;
    
    /** Alustetaan Terapeutti
     * 
     */
    public Terapeutti() {
        // ei viel�
    }
    
    /**
     * Alustetaan tietyn j�senen harrastus.  
     * @param asiakasNro j�senen viitenumero 
     */
    public Terapeutti(int asiakasNro) {
        this.asiakasNro = asiakasNro;
    }

    /**
     * @return harrastukse kenttien lukum��r�
     */
    @Override
    public int getKenttia() {
        return 5;
    }
    
    public int GetAsiakasNro() {
    	return asiakasNro;
    }
    
    /**
     * @return ensimm�inen k�ytt�j�n sy�tett�v�n kent�n indeksi
     */
    @Override
    public int ekaKentta() {
        return 2;
    }
    
    /**
     * @param k mink� kent�n kysymys halutaan
     * @return valitun kent�n kysymysteksti
     */
    @Override
    public String getKysymys(int k) {
        switch (k) {
            case 0:
                return "id";
            case 1:
                return "j�senId";
            case 2:
                return "terapeutti";
            case 3:
                return "terapiamuoto";
            case 4:
                return "kesto";
            default:
                return "???";
        }
    }


    /**
     * @param k Mink� kent�n sis�lt� halutaan
     * @return valitun kent�n sis�lt�
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   har.anna(0) === "2";   
     *   har.anna(1) === "10";   
     *   har.anna(2) === "Kalastus";   
     *   har.anna(3) === "1949";   
     *   har.anna(4) === "22";   
     *   
     * </pre>
     */
    @Override
    public String anna(int k) {
        switch (k) {
            case 0:
                return "" + tunnusNro;
            case 1:
                return "" + asiakasNro;
            case 2:
                return "" + terapeutti;
            case 3:
                return "" + terapiamuoto;
            case 4:
                return "" + kesto;
            default:
                return "???";
        }
    }
    
    /**
     * Asetetaan valitun kent�n sis�lt�.  Mik�li asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k mink� kent�n sis�lt� asetetaan
     * @param s asetettava sis�lt� merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.aseta(3,"kissa") === "Aloitusvuosi v��rin jono = \"kissa\"";
     *   har.aseta(3,"1940")  === null;
     *   har.aseta(4,"kissa") === "Viikkotunnit v��rin jono = \"kissa\"";
     *   har.aseta(4,"20")    === null;
     *   
     * </pre>
     */
    @Override
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
                setTunnusNro(Mjonot.erota(sb, '$', getTunnusNro()));
                return null;
            case 1:
                asiakasNro = Mjonot.erota(sb, '$', asiakasNro);
                return null;
            case 2:
                terapeutti = st;
                return null;
            case 3:
                terapiamuoto = st;
                /*
                try {
                    aloitusvuosi = Mjonot.erotaEx(sb, '�', aloitusvuosi);
                } catch (NumberFormatException ex) {
                    return "Aloitusvuosi v��rin " + ex.getMessage();
                } */
                return null;

            case 4:
                kesto = st;
                /*try {
                    tuntiaViikossa = Mjonot.erotaEx(sb, '�', tuntiaViikossa);
                } catch (NumberFormatException ex) {
                    return "Viikkotunnit v��rin " + ex.getMessage();
                } */
                return null;

            default:
                return "V��r� kent�n indeksi";
        }
    }


    /**
     * Tehd��n identtinen klooni j�senest�
     * @return Object kloonattu j�sen
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Harrastus kopio = har.clone();
     *   kopio.toString() === har.toString();
     *   har.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(har.toString()) === false;
     * </pre>
     */
    @Override
    public Terapeutti clone() throws CloneNotSupportedException { 
        return (Terapeutti)super.clone();
    }

    /**
     * Apumetodi, jolla saadaan t�ytetty� testiarvot Terapeutille.
     * Tiedot arvotaan, jotta kahdella terapeutilla ei olisi
     * samoja tietoja.
     * @param nro viite asiakkaaseen, jonka terapiamuodosta on kyse
     */
    public void vastaaGretaHuhtoinen(int nro) {
        String[] terapiamuodot = { "Musiikkiterapia", "TMT", "GIM-terapia", "Ryhm�terapia" };
        String[] kestot = {"45", "90", "120", "2x45"};
        asiakasNro = nro;
        terapeutti = "Greta Huhtoinen";
        terapiamuoto = terapiamuodot[rand(0,2)];
        kesto = kestot[rand(0,3)];        
    }

    /**
     * Tulostetaan terapian tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(terapeutti + " " + terapiamuoto + " " + kesto);
    }

    /**
     * Tulostetaan asiakkaan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Antaa Terapeutille seuraavan rekisterinumeron.
     * @return Terapeutin uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Terapeutti greta1 = new Terapeutti();
     *   greta1.getTunnusNro() === 0;
     *   greta1.rekisteroi();
     *   Terapeutti greta2 = new Terapeutti();
     *   greta2.rekisteroi();
     *   int n1 = greta1.getTunnusNro();
     *   int n2 = greta2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    /**
     * Palautetaan Terapeutin oma id
     * @return terapeutin id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    /** Palautetaan kenelle asiakkaalle Terapeutti kuuluu
     * @return asiakas ID
     */
    public int getAsiakasNro() {
        return asiakasNro;
    }
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa ett�
     * seuraava numero on aina suurempi kuin t�h�n menness� suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }

    /**
     * Palauttaa terapian tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return terapia tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Terapeutti terapeutti = new Terapeutti();
     *   terapeutti.parse("   1   |  2  |   Jorg Jonkainen  | Musiikkiterapia | 2x45 ");
     *   terapeutti.toString()    === "1|2|Jorg Jonkainen|Musiikkiterapia|2x45";
     * </pre>
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();
     }
    /**
     * Selvit�� terapian tiedot | erotellusta merkkijonosta.
     * Pit�� huolen ett� seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta terapian tiedot otetaan
     * @example
     * <pre name="test">
     *   Terapeutti terapeutti = new Terapeutti();
     *   terapeutti.parse("   1   |  2  |   Jorg Jonkainen | Musiikkiterapia | 2x45 ");
     *   terapeutti.getAsiakasNro() === 2;
     *   terapeutti.toString()    === "1|2|Jorg Jonkainen|Musiikkiterapia|2x45";
     *   terapeutti.rekisteroi();
     *   int n = terapeutti.getTunnusNro();
     *   terapeutti.parse(""+(n+20));
     *   terapeutti.rekisteroi();
     *   terapeutti.getTunnusNro() === n+20+1;
     *   terapeutti.toString()     === "" + (n+20+1) + "|2|Jorg Jonkainen|Musiikkiterapia|2x45";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
    /*    setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        asiakasNro = Mjonot.erota(sb, '|', asiakasNro);
        terapeutti = Mjonot.erota(sb, '|', terapeutti);
        terapiamuoto = Mjonot.erota(sb, '|', terapiamuoto);
        kesto = Mjonot.erota(sb, '|', kesto); */
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
    

    @Override
    public int hashCode() {
        return tunnusNro;
    }
    

    /** Testiohjelma terapeutille
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Terapeutti ter = new Terapeutti();
        ter.vastaaGretaHuhtoinen(2);
        ter.tulosta(System.out);
    }

}
