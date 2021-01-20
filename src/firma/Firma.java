package firma;
import java.io.File;
import java.util.List;
import java.util.Collection;

/**
 * Kerho-luokka, joka huolehtii jäsenistöstä.  Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" jäsenistöön.
 *
 * @author Vesa Lappalainen
 * @version 1.0, 09.02.2003
 * @version 1.1, 23.02.2003
 * @version 1.2, 07.01.2008 / testit
 * @version 1.3, 03.03.2013 / Harrastukset
 * 
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import kerho.SailoException;
 *  private Kerho kerho;
 *  private Jasen aku1;
 *  private Jasen aku2;
 *  private int jid1;
 *  private int jid2;
 *  private Harrastus pitsi21;
 *  private Harrastus pitsi11;
 *  private Harrastus pitsi22; 
 *  private Harrastus pitsi12; 
 *  private Harrastus pitsi23;
 *  
 *  @SuppressWarnings("javadoc")
 *  public void alustaKerho() {
 *    kerho = new Kerho();
 *    aku1 = new Jasen(); aku1.vastaaAkuAnkka(); aku1.rekisteroi();
 *    aku2 = new Jasen(); aku2.vastaaAkuAnkka(); aku2.rekisteroi();
 *    jid1 = aku1.getTunnusNro();
 *    jid2 = aku2.getTunnusNro();
 *    pitsi21 = new Harrastus(jid2); pitsi21.vastaaPitsinNyplays(jid2);
 *    pitsi11 = new Harrastus(jid1); pitsi11.vastaaPitsinNyplays(jid1);
 *    pitsi22 = new Harrastus(jid2); pitsi22.vastaaPitsinNyplays(jid2); 
 *    pitsi12 = new Harrastus(jid1); pitsi12.vastaaPitsinNyplays(jid1); 
 *    pitsi23 = new Harrastus(jid2); pitsi23.vastaaPitsinNyplays(jid2);
 *    try {
 *    kerho.lisaa(aku1);
 *    kerho.lisaa(aku2);
 *    kerho.lisaa(pitsi21);
 *    kerho.lisaa(pitsi11);
 *    kerho.lisaa(pitsi22);
 *    kerho.lisaa(pitsi12);
 *    kerho.lisaa(pitsi23);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
 */
public class Firma {

    private Asiakkaat asiakkaat = new Asiakkaat();
    private Terapeutit terapeutit = new Terapeutit();
    
        
    /**
     * Poistaa jäsenistöstä ja harrasteista jäsenen tiedot 
     * @param asiakas asiakas jokapoistetaan
     * @return montako jäsentä poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaKerho();
     *   kerho.etsi("*",0).size() === 2;
     *   kerho.annaHarrastukset(aku1).size() === 2;
     *   kerho.poista(aku1) === 1;
     *   kerho.etsi("*",0).size() === 1;
     *   kerho.annaHarrastukset(aku1).size() === 0;
     *   kerho.annaHarrastukset(aku2).size() === 3;
     * </pre>
     */
    public int poista(Asiakas asiakas) {
        if ( asiakas == null ) return 0;
        int ret = asiakkaat.poista(asiakas.getTunnusNro()); 
        terapeutit.poista(asiakas.getTunnusNro()); 
        return ret; 
    }
    
    /** 
     * Poistaa tämän harrastuksen 
     * @param terapeutti poistettava harrastus 
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaKerho();
     *   kerho.annaHarrastukset(aku1).size() === 2;
     *   kerho.poistaHarrastus(pitsi11);
     *   kerho.annaHarrastukset(aku1).size() === 1;
     */ 
    public void poistaTerapeutti(Terapeutti terapeutti) { 
        terapeutit.poista(terapeutti); 
    } 
    
    
    /**
     * Lisää firmaan uuden asiakkaan
     * @param asiakas lisättävä asiakas
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Firma firma = new Firma();
     * Asiakas matti1 = new Asiakas(), matti2 = new Asiakas();
     * firma.lisaa(matti1);
     * firma.lisaa(matti2);
     * firma.lisaa(matti1);
     * Collection<Asiakas> loytyneet = firma.etsi("",-1);
     * Iterator<Asiakas> it = loytyneet.iterator();
     * it.next() === matti1;
     * it.next() === matti2;
     * it.next() === matti1;
     * </pre>
     */
    public void lisaa(Asiakas asiakas) throws SailoException {
        asiakkaat.lisaa(asiakas);
    }
    
    /** 
     * Korvaa jäsenen tietorakenteessa.  Ottaa jäsenen omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva jäsen.  Jos ei löydy, 
     * niin lisätään uutena jäsenenä. 
     * @param asiakas lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaKerho();
     *  kerho.etsi("*",0).size() === 2;
     *  kerho.korvaaTaiLisaa(aku1);
     *  kerho.etsi("*",0).size() === 2;
     * </pre>
     */ 
    public void korvaaTaiLisaa(Asiakas asiakas) throws SailoException { 
        asiakkaat.korvaaTaiLisaa(asiakas); 
    } 
    
    /** Lisätään firmaan uusi terapeutti
     * @param ter lisättävä terapeutti
     */
    public void lisaa(Terapeutti ter) {
        terapeutit.lisaa(ter);
    }
    
    /**  
     * Palauttaa "taulukossa" hakuehtoon vastaavien asiakkaiden viitteet  
     * @param hakuehto hakuehto   
     * @param k etsittävän kentän indeksi   
     * @return tietorakenteen löytyneistä asiakkaista  
     * @throws SailoException Jos jotakin menee väärin 
     */  
    public Collection<Asiakas> etsi(String hakuehto, int k) throws SailoException {  
        return asiakkaat.etsi(hakuehto, k);  
    }  
     
    /**
     * Haetaan kaikki asiakas terapeutit
     * @param asiakas asiakas jolle terapeutteja haetaan
     * @return tietorakenne jossa viiteet löydettyihin terapeutteihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Firma firma = new Firma();
     *  Asiakas matti1 = new Asiakas(), matti2 = new Asiakas(), matti3 = new Asiakas();
     *  matti1.rekisteroi(); matti2.rekisteroi(); matti3.rekisteroi();
     *  int id1 = matti1.getTunnusNro();
     *  int id2 = matti2.getTunnusNro();
     *  Terapeutti greta11 = new Terapeutti(id1); firma.lisaa(greta11);
     *  Terapeutti greta12 = new Terapeutti(id1); firma.lisaa(greta12);
     *  Terapeutti greta21 = new Terapeutti(id2); firma.lisaa(greta21);
     *  Terapeutti greta22 = new Terapeutti(id2); firma.lisaa(greta22);
     *  Terapeutti greta23 = new Terapeutti(id2); firma.lisaa(greta23);
     *  
     *  List<Terapeutti> loytyneet;
     *  loytyneet = firma.annaTerapeutit(matti3);
     *  loytyneet.size() === 0; 
     *  loytyneet = firma.annaTerapeutit(matti1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == greta11 === true;
     *  loytyneet.get(1) == greta12 === true;
     *  loytyneet = firma.annaTerapeutit(matti2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == greta21 === true;
     * </pre> 
     */
    public List<Terapeutti> annaTerapeutit(Asiakas asiakas) {
        return terapeutit.annaTerapeutit(asiakas.getTunnusNro());
    } 
    
    /**
     * @return kaikki
     */
    public Collection<Terapeutti> annaKaikkiTerat(String nimiKohdalla) {
        return terapeutit.annaKaikkiTerat(nimiKohdalla);
    }
    /**
     * @return kaikki
     */
    public List<String> annaKaikkiNimet() {
        return terapeutit.annaKaikkiNimet();
    }
    
    public String annaAsNimi(int aID) {
    	return asiakkaat.annaAsNimi(aID);
    }
    /**   
     * Laitetaan harrastukset muuttuneeksi, niin pakotetaan tallentamaan.   
     */   
    public void setTerapeuttiMuutos() {   
        terapeutit.setMuutos();   
    }  
    
     /** 
     * Asettaa tiedostojen perusnimet 
     * @param nimi uusi nimi 
     */ 
    public void setTiedosto(String nimi) { 
        File dir = new File(nimi); 
        dir.mkdirs(); 
        String hakemistonNimi = ""; 
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/"; 
        asiakkaat.setTiedostonPerusNimi(hakemistonNimi + "nimet"); 
        terapeutit.setTiedostonPerusNimi(hakemistonNimi + "terapiat"); 
    }
    
    /**
     * Lukee firman tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa 
     * @throws SailoException jos lukeminen epäonnistuu 
     *  
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * #import java.io.*; 
     * #import java.util.*; 
     *  
     *  Firma firma = new Firma(); 
     *   
     *  Asiakas matti1 = new Asiakas(); matti1.vastaaMattiMeikalainen(); matti1.rekisteroi(); 
     *  Asiakas matti2 = new Asiakas(); matti2.vastaaMattiMeikalainen(); matti2.rekisteroi(); 
     *  Terapeutti greta21 = new Terapeutti(); greta21.vastaaGretaHuhtoinen(matti2.getTunnusNro());
     *  Terapeutti greta11 = new Terapeutti(); greta11.vastaaGretaHuhtoinen(matti1.getTunnusNro());
     *  Terapeutti greta22 = new Terapeutti(); greta22.vastaaGretaHuhtoinen(matti2.getTunnusNro()); 
     *  Terapeutti greta12 = new Terapeutti(); greta12.vastaaGretaHuhtoinen(matti1.getTunnusNro()); 
     *  Terapeutti greta23 = new Terapeutti(); greta23.vastaaGretaHuhtoinen(matti2.getTunnusNro());    
     *  String hakemisto = "testiTeramusa"; 
     *  File dir = new File(hakemisto); 
     *  File ftied  = new File(hakemisto+"/nimet.dat"); 
     *  File fhtied = new File(hakemisto+"/terapiat.dat");
     *  dir.mkdir();   
     *  ftied.delete(); 
     *  fhtied.delete();
     *  firma.lueTiedostosta(hakemisto); #THROWS SailoException 
     *  firma.lisaa(matti1); 
     *  firma.lisaa(matti2); 
     *  
     *  firma.lisaa(greta21);
     *  firma.lisaa(greta11);
     *  firma.lisaa(greta22);
     *  firma.lisaa(greta12);
     *  firma.lisaa(greta23);
     *  firma.tallenna(); 
     *  firma = new Firma(); 
     *  firma.lueTiedostosta(hakemisto); 
     *  Collection<Asiakas> kaikki = firma.etsi("",-1);  
     *  Iterator<Asiakas> it = kaikki.iterator(); 
     *  it.next() === matti1; 
     *  it.next() === matti2; 
     *  it.hasNext() === false; 
     *  List<Terapeutti> loytyneet = firma.annaTerapeutit(matti1);
     *  Iterator<Terapeutti> ih = loytyneet.iterator();
     *  ih.next() === greta11;
     *  ih.next() === greta12;
     *  ih.hasNext() === false;
     *  loytyneet = firma.annaTerapeutit(matti2);
     *  ih = loytyneet.iterator();
     *  ih.next() === greta21;
     *  ih.next() === greta22;
     *  ih.next() === greta23;
     *  ih.hasNext() === false;
     *  firma.lisaa(matti2); 
     *  firma.lisaa(greta23);
     *  firma.tallenna(); 
     *  ftied.delete()  === true; 
     *  fhtied.delete() === true;
     *  File fbak = new File(hakemisto+"/nimet.bak"); 
     *  File fhbak = new File(hakemisto+"/terapiat.bak"); 
     *  fbak.delete() === true; 
     *  fhbak.delete() === true; 
     *  dir.delete() === true; 
     * </pre> 
     */ 
    public void lueTiedostosta(String nimi) throws SailoException {
        asiakkaat = new Asiakkaat(); // helpoin tyhjentää näin
        terapeutit = new Terapeutit();
        
        setTiedosto(nimi);
        asiakkaat.lueTiedostosta();
        terapeutit.lueTiedostosta();
    }
    
    
    /**
     * Tallentaa firman tiedot tiedostoon
     * Vaikka asiakkaiden tallentaminen epäonnistuisi, yritetään tallettaa
     * terapeutteja ennen poikkeusta
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {     
       String virhe = "";
       try {
           asiakkaat.tallenna();
       } catch ( SailoException ex ) {
           virhe = ex.getMessage();
       }
       
       try {
           terapeutit.tallenna();
       } catch ( SailoException ex ) {
           virhe += ex.getMessage();
       }
       if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
    /**
     * Testiohjelma firmasta
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Firma firma = new Firma();      
                
        try {
            // firma.lueTiedostosta("teramusa");
            Asiakas matti1 = new Asiakas();
            Asiakas matti2 = new Asiakas();
            
            matti1.rekisteroi();
            matti1.vastaaMattiMeikalainen();
            matti2.rekisteroi();
            matti2.vastaaMattiMeikalainen();
            
            firma.lisaa(matti1);
            firma.lisaa(matti2);  
            
            int id1 = matti1.getTunnusNro();
            int id2 = matti2.getTunnusNro();
            
            Terapeutti greta11 = new Terapeutti(id1);
            greta11.vastaaGretaHuhtoinen(id1);
            firma.lisaa(greta11);
            
            Terapeutti greta12 = new Terapeutti(id1);
            greta12.vastaaGretaHuhtoinen(id1);
            firma.lisaa(greta12);
            
            Terapeutti greta21 = new Terapeutti(id2);
            greta21.vastaaGretaHuhtoinen(id2);
            firma.lisaa(greta21);
            
            Terapeutti greta22 = new Terapeutti(id2);
            greta22.vastaaGretaHuhtoinen(id2);
            firma.lisaa(greta22);
            
            Terapeutti greta23 = new Terapeutti(id2);
            greta23.vastaaGretaHuhtoinen(id2);
            firma.lisaa(greta23);
            
            System.out.println("===================== Firman testi ============");
            
            Collection<Asiakas> asiakkaat = firma.etsi("", -1);
            int i = 0;
            for (Asiakas asiakas: asiakkaat) {
                System.out.println("Asikas paikassa: " + i);
                asiakas.tulosta(System.out);
                List<Terapeutti> loytyneet = firma.annaTerapeutit(asiakas);
                for (Terapeutti terapeutti: loytyneet)
                    terapeutti.tulosta(System.out);
                i++;
            } 
    
        } catch (SailoException e) {
            System.err.println("Vikaa: " + e.getMessage());
        }                
    }           
}
