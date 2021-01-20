package firma;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/** Firman terapeutit, osaa lisätä uuden terapian.
 * @author Lari Kettunen
 * @version 4.7.2017
 *
 */
public class Terapeutit implements Iterable<Terapeutti> {
    
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";
    
    /**
     * taulukko terapeuteista
     */
    private final List<Terapeutti> alkiot = new ArrayList<Terapeutti>();

    /**
     * Terapeuttien alustaminen
     */
    public Terapeutit() {
        // ei vielä
    }
    
    /** Lisätään terapeutti tietorakenteeseen.
     * @param ter lisättävä terapeutti
     */
    public void lisaa(Terapeutti ter) {
        alkiot.add(ter);
        muutettu = true;
    }
    
    /**
     * Poistaa valitun harrastuksen
     * @param terapeutti poistettava terapeutti
     * @return tosi jos löytyi poistettava tietue 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Harrastukset harrasteet = new Harrastukset();
     *  Harrastus pitsi21 = new Harrastus(); pitsi21.vastaaPitsinNyplays(2);
     *  Harrastus pitsi11 = new Harrastus(); pitsi11.vastaaPitsinNyplays(1);
     *  Harrastus pitsi22 = new Harrastus(); pitsi22.vastaaPitsinNyplays(2); 
     *  Harrastus pitsi12 = new Harrastus(); pitsi12.vastaaPitsinNyplays(1); 
     *  Harrastus pitsi23 = new Harrastus(); pitsi23.vastaaPitsinNyplays(2); 
     *  harrasteet.lisaa(pitsi21);
     *  harrasteet.lisaa(pitsi11);
     *  harrasteet.lisaa(pitsi22);
     *  harrasteet.lisaa(pitsi12);
     *  harrasteet.poista(pitsi23) === false ; harrasteet.getLkm() === 4;
     *  harrasteet.poista(pitsi11) === true;   harrasteet.getLkm() === 3;
     *  List<Harrastus> h = harrasteet.annaHarrastukset(1);
     *  h.size() === 1; 
     *  h.get(0) === pitsi12;
     * </pre>
     */
    public boolean poista(Terapeutti terapeutti) {
        boolean ret = alkiot.remove(terapeutti);
        if (ret) muutettu = true;
        return ret;
    }

    
    
    /**
     * Poistaa kaikki tietyn tietyn jäsenen harrastukset
     * @param tunnusNro viite siihen, mihin liittyvät tietueet poistetaan
     * @return montako poistettiin 
     * @example
     * <pre name="test">
     *  Harrastukset harrasteet = new Harrastukset();
     *  Harrastus pitsi21 = new Harrastus(); pitsi21.vastaaPitsinNyplays(2);
     *  Harrastus pitsi11 = new Harrastus(); pitsi11.vastaaPitsinNyplays(1);
     *  Harrastus pitsi22 = new Harrastus(); pitsi22.vastaaPitsinNyplays(2); 
     *  Harrastus pitsi12 = new Harrastus(); pitsi12.vastaaPitsinNyplays(1); 
     *  Harrastus pitsi23 = new Harrastus(); pitsi23.vastaaPitsinNyplays(2); 
     *  harrasteet.lisaa(pitsi21);
     *  harrasteet.lisaa(pitsi11);
     *  harrasteet.lisaa(pitsi22);
     *  harrasteet.lisaa(pitsi12);
     *  harrasteet.lisaa(pitsi23);
     *  harrasteet.poista(2) === 3;  harrasteet.getLkm() === 2;
     *  harrasteet.poista(3) === 0;  harrasteet.getLkm() === 2;
     *  List<Harrastus> h = harrasteet.annaHarrastukset(2);
     *  h.size() === 0; 
     *  h = harrasteet.annaHarrastukset(1);
     *  h.get(0) === pitsi11;
     *  h.get(1) === pitsi12;
     * </pre>
     */
    public int poista(int tunnusNro) {
        int n = 0;
        for (Iterator<Terapeutti> it = alkiot.iterator(); it.hasNext();) {
            Terapeutti ter = it.next();
            if ( ter.getAsiakasNro() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    
    
    /**
     * Lukee terapiat tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Terapeutit terat = new Terapeutit();
     *  Terapeutti greta21 = new Terapeutti(); greta21.vastaaGretaHuhtoinen(2);
     *  Terapeutti greta11 = new Terapeutti(); greta11.vastaaGretaHuhtoinen(1);
     *  Terapeutti greta22 = new Terapeutti(); greta22.vastaaGretaHuhtoinen(2); 
     *  Terapeutti greta12 = new Terapeutti(); greta12.vastaaGretaHuhtoinen(1); 
     *  Terapeutti greta23 = new Terapeutti(); greta23.vastaaGretaHuhtoinen(2); 
     *  String tiedNimi = "testifirmat";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  terat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  terat.lisaa(greta21);
     *  terat.lisaa(greta11);
     *  terat.lisaa(greta22);
     *  terat.lisaa(greta12);
     *  terat.lisaa(greta23);
     *  terat.tallenna();
     *  terat = new Terapeutit();
     *  terat.lueTiedostosta(tiedNimi);
     *  Iterator<Terapeutti> i = terat.iterator();
     *  i.next().toString() === greta21.toString();
     *  i.next().toString() === greta11.toString();
     *  i.next().toString() === greta22.toString();
     *  i.next().toString() === greta12.toString();
     *  i.next().toString() === greta23.toString();
     *  i.hasNext() === false;
     *  terat.lisaa(greta23);
     *  terat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
            
            String rivi;
            while( (rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';') continue;
                Terapeutti ter = new Terapeutti();
                ter.parse(rivi);
                lisaa(ter);
            }
            muutettu = false;
            
         } catch (FileNotFoundException e ) {
            throw new SailoException("Tiedosto " +getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
}

        /**
         * Luetaan aikaisemmin annetun nimisestä tiedostosta
         * @throws SailoException jos tulee poikkeus
         */
        public void lueTiedostosta() throws SailoException {
            lueTiedostosta(getTiedostonPerusNimi());
        }

        /**
         * Tallentaa terapiat tiedostoon.
         * @throws SailoException jos talletus epäonnistuu
         */
        public void tallenna() throws SailoException {
            if ( !muutettu ) return;

            File fbak = new File(getBakNimi());
            File ftied = new File(getTiedostonNimi());
            fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
            ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

            try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
                for (Terapeutti ter : this) {
                    fo.println(ter.toString());
                }
            } catch ( FileNotFoundException ex ) {
                throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
            } catch ( IOException ex ) {
                throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
            }

            muutettu = false;
        }


    /**
     * Palauttaa kerhon harrastusten lukumäärän
     * @return harrastusten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }

    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }


    /**
     * Iteraattori kaikkien terapeuttien läpikäymiseen
     * @return terapeutti-iteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Terapeutit terat = new Terapeutit();
     *  Terapeutti greta21 = new Terapeutti(2); terat.lisaa(greta21);
     *  Terapeutti greta11 = new Terapeutti(1); terat.lisaa(greta11);
     *  Terapeutti greta22 = new Terapeutti(2); terat.lisaa(greta22);
     *  Terapeutti greta12 = new Terapeutti(1); terat.lisaa(greta12);
     *  Terapeutti greta23 = new Terapeutti(2); terat.lisaa(greta23);
     * 
     *  Iterator<Terapeutti> i2=terat.iterator();
     *  i2.next() === greta21;
     *  i2.next() === greta11;
     *  i2.next() === greta22;
     *  i2.next() === greta12;
     *  i2.next() === greta23;
     *  i2.next() === greta12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int anro[] = {2,1,2,1,2};
     *  
     *  for ( Terapeutti ter:terat ) { 
     *    ter.getAsiakasNro() === anro[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Terapeutti> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kaikki asiakas terapeutit
     * @param tunnusnro asiakkaan tunnusnumero jolle terapeutteja haetaan
     * @return tietorakenne jossa viiteet löydettyihin terapeutteihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Terapeutit terat = new Terapeutit();
     *  Terapeutti greta21 = new Terapeutti(2); terat.lisaa(greta21);
     *  Terapeutti greta11 = new Terapeutti(1); terat.lisaa(greta11);
     *  Terapeutti greta22 = new Terapeutti(2); terat.lisaa(greta22);
     *  Terapeutti greta12 = new Terapeutti(1); terat.lisaa(greta12);
     *  Terapeutti greta23 = new Terapeutti(2); terat.lisaa(greta23);
     *  Terapeutti greta51 = new Terapeutti(5); terat.lisaa(greta51);
     *  
     *  List<Terapeutti> loytyneet;
     *  loytyneet = terat.annaTerapeutit(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = terat.annaTerapeutit(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == greta11 === true;
     *  loytyneet.get(1) == greta12 === true;
     *  loytyneet = terat.annaTerapeutit(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == greta51 === true;
     * </pre> 
     */
    
    public List<Terapeutti> annaTerapeutit(int tunnusnro) {
        List<Terapeutti> loydetyt = new ArrayList<Terapeutti>();
        for (Terapeutti ter : alkiot)
            if (ter.getAsiakasNro() == tunnusnro) loydetyt.add(ter);
        return loydetyt;
    }
    
    public Collection<Terapeutti> annaKaikkiTerat(String nimiKohdalla) {
        Collection<Terapeutti> kaikki = new ArrayList<Terapeutti>();
             
        for (Terapeutti ter : alkiot) {
            if ( ter.anna(2).equals(nimiKohdalla) ) kaikki.add(ter); 
        }
            return kaikki;        
    }
    
    
    public List<String> annaKaikkiNimet() {
        List<String> nimet = new ArrayList<String>();    
       
        for(Terapeutti terat: alkiot) {
            nimet.add(terat.anna(2));            
        }            
        return nimet;
    }
    
    /**   
     * Laitetaan muutos, jolloin pakotetaan tallentamaan.     
     */   
    public void setMuutos() {   
        muutettu = true;   
    }  
    
    /** Testiohjelma terapeuteille
     * @param args ei käytös
     */
    public static void main(String[] args) {
        Terapeutit terat = new Terapeutit();
        Terapeutti greta1 = new Terapeutti();
        greta1.vastaaGretaHuhtoinen(2);
        Terapeutti greta2 = new Terapeutti();
        greta2.vastaaGretaHuhtoinen(1);
        Terapeutti greta3 = new Terapeutti();
        greta3.vastaaGretaHuhtoinen(2);
        Terapeutti greta4 = new Terapeutti();
        greta4.vastaaGretaHuhtoinen(2);
        
        terat.lisaa(greta1);
        terat.lisaa(greta2);
        terat.lisaa(greta3);
        terat.lisaa(greta4);
        
        
        System.out.println("======================= Terapeutit testi ============");

        List<Terapeutti> terapeutit2 = terat.annaTerapeutit(2);
        
        for (Terapeutti ter : terapeutit2) {
            System.out.println(ter.getAsiakasNro() + " ");
            ter.tulosta(System.out);
        }
    }

}
