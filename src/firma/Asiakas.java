package firma;

import java.io.*;
import java.util.Comparator;
import fi.jyu.mit.ohj2.Mjonot;
import kanta.HetuTarkistus;
import static kanta.HetuTarkistus.*;
import kanta.HetuKentta;
import kanta.IntKentta;
import kanta.JonoKentta;
import kanta.Kentta;
import kanta.SisaltaaTarkistaja;

import kanta.Tietue;
/**
 * Firman asiakas, osaa huolehtia omasta tunnusnumerosta
 * @author Lari Kettunen
 * @version 26.6.2017
 *
 */
public class Asiakas implements Cloneable, Tietue {
    /*
    private int     tunnusNro;
    private String  nimi         = "";
   // private String  sukunimi        = "";
    private String  htun            = "";
    private String  katuosoite      = "";
    private String  postinumero     = "";
    private String  kaupunki        = "";
    private String  puhelin         = "";
    private String  lähettäjä       = "";
    private String  diagnoosi       = "";
    private String  sopimuskausi    = "";
    
    private static int seuraavaNro  = 1;
    */
    private Kentta     kentat[]    = { // valitettavasti ei voi olla final
            // vaikka pitäisi, clone estää tämän :-(
            new IntKentta("id"),
            new JonoKentta("nimi"),
            new HetuKentta("htun", new HetuTarkistus()),
            new JonoKentta("katuosoite"),            
            new JonoKentta("postinumero",  new SisaltaaTarkistaja(SisaltaaTarkistaja.NUMEROT)),
            new JonoKentta("kaupunki"),
            new JonoKentta("puhelin"), // new RegExpTarkistaja("[- 0-9]*")),           
            new JonoKentta("lähettäjä"), 
            new JonoKentta("diagnoosi"),
            new JonoKentta("sopimuskausi")         
    };
    
    private static int seuraavaNro    = 1;
 
    
    /**
     * Luokka joka vertaa kahta jäsentä keskenään 
     */
    public static class Vertailija implements Comparator<Asiakas> {

        private final int kenttanro;


        /**
         * Alustetaan vertailija vertailemaan tietyn kentän perusteella
         * @param k vertailtavan kentän indeksi.
         */
        public Vertailija(int k) {
            this.kenttanro = k;
        }


        /**
         * Verrataana kahta jäsentä keskenään.
         * @param j1 1. verrattava jäsen
         * @param j2 2. verrattava jäsen
         * @return <0 jos j1 < j2, == 0 jos j1 == j2 ja muuten >0
         */
        @Override
        public int compare(Asiakas j1, Asiakas j2) {
            String s1 = j1.getAvain(kenttanro);
            String s2 = j2.getAvain(kenttanro);

            return s1.compareTo(s2);

        }

    }  
    
    
    /**
     * Palauttaa asiakkaan kenttien lukumäärän
     * @return kenttien lkm
     */
    @Override
    public int getKenttia() {
        return kentat.length;
    }
    
    /** 
    * Eka kenttä joka on mielekäs kysyttäväksi 
    * @return ekan kentän indeksi 
    */ 
   @Override
public int ekaKentta() { 
       return 1; 
   } 
   
   
   /** 
    * Alustetaan jäsenen merkkijono-attribuuti tyhjiksi jonoiksi 
    * ja tunnusnro = 0. 
    */ 
   public Asiakas() { 
       // Toistaiseksi ei tarvita mitään 
   } 
    
    /**
     * @return asiakkaan nimen 
     * @example
     * <pre name="test">
     *     Asiakas matti1 = new Asiakas();
     *     matti1.vastaaMattiMeikalainen();
     *     matti1.getNimi() =R= "Matti Meikäläinen .*";
     * </pre>
     
     */
    public String getNimi() {        
        return anna(1);
    }
    
  //  public int getAid() {
   // 	return IntKentta;
   // }
    
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    @Override
    public String anna(int k) {
        try {
            return kentat[k].toString();
        } catch (Exception ex) {
            return "";
        }
    }
    /**
     * Antaa k:n kentän sisällön avain-merkkijonona
     * jonka perusteella voi lajitella
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     *
     * @example
     * <pre name="test">
     *   Jasen aku = new Jasen();
     *   aku.parse("   1  |  Ankka Aku   | 030201-111C");
     *   aku.getAvain(0) === "         1";
     *   aku.getAvain(1) === "ANKKA AKU";
     *   aku.getAvain(2) === "1010203-111C";
     *   aku.getAvain(20) === "";
     * </pre>
     */
    public String getAvain(int k) {
        try {
            return kentat[k].getAvain();
        } catch (Exception ex) {
            return "";
        }
    }
   
   
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.aseta(1,"Ankka Aku") === null;
     *   jasen.aseta(2,"kissa") =R= "Hetu liian lyhyt"
     *   jasen.aseta(2,"030201-1111") === "Tarkistusmerkin kuuluisi olla C"; 
     *   jasen.aseta(2,"030201-111C") === null; 
     *   jasen.aseta(9,"kissa") === "Ei kokonaisluku (kissa)";
     *   jasen.aseta(9,"1940") === null;
     * </pre>
     */
    @Override
    public String aseta(int k, String jono) {
        try {
            String virhe = kentat[k].aseta(jono.trim());
            if ( virhe == null && k == 0 ) setTunnusNro(getTunnusNro());
            return virhe;
        } catch (Exception ex) {
            return "Virhe: " + ex.getMessage();
        }
    }
   
   
   /** 
    * Palauttaa k:tta jäsenen kenttää vastaavan kysymyksen 
    * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen) 
    * @return k:netta kenttää vastaava kysymys 
    */ 
   @Override
public String getKysymys(int k) { 
       try {
           return kentat[k].getKysymys();
       } catch (Exception ex) {
           return "Tylsimys";
       }
   }
    
   
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot asiakkaalle
     * @param apuhetu hetu asiakkaalle
     */
    public void vastaaMattiMeikalainen(String apuhetu) {
        aseta(1,"Matti Meikäläinen " + kanta.HetuTarkistus.rand(1000, 9999));
        aseta(2,apuhetu);                                            
        aseta(3,"Kauppakatu 7");                                      
        aseta(4,"33111");                                            
        aseta(5,"Jyväskylä");                                       
        aseta(6,"0501234567");                                          
        aseta(7,"K-Suomen sh-piiri");                                                 
        aseta(8,"F32.1");                                                 
        aseta(9,"28.3.15-13.3.17");                                             
                                       
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot asiakkaalle.
     * Henkilötunnus arvotaan, jotta kahdella jäsenellä ei olisi
     * samoja tietoja.
     */
    public void vastaaMattiMeikalainen() {
        String apuhetu = arvoHetu();
        vastaaMattiMeikalainen(apuhetu);
    }
    
    /**
     * Tulostetaan Asiakkaan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        int pisin = 0;
        for (Kentta kentta : kentat)
            if (kentta.getKysymys().length() > pisin)
                pisin = kentta.getKysymys().length();
        
        for (Kentta kentta : kentat)
            out.println(Mjonot.fmt(kentta.getKysymys(),  -pisin -1) +
                    ": " + kentta.toString());
   /*    out.println(String.format("%03d", tunnusNro, 3) + " " + nimi + " " + htun);
        out.println(" Osoite: " + katuosoite + ", " + postinumero + ", " + kaupunki);
        out.println(" Puhelinnumero: " + puhelin);
        out.print(" Lähettävä taho: " + lähettäjä +",");
        out.println("  Diagnoosi: " + diagnoosi);
        out.println(" Terapiakausi: " + sopimuskausi);        */
    }
    

    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Antaa asiakkaalle seuraavan reskisterinumeron
     * @return asiakkaan uusi tunnusNro
     * @example
     * <pre name="test">
     *   Asiakas matti1 = new Asiakas();
     *   matti1.getTunnusNro() === 0;
     *   matti1.rekisteroi();
     *   Asiakas matti2 = new Asiakas();
     *   matti2.rekisteroi();
     *   int n1 = matti1.getTunnusNro();
     *   int n2 = matti2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
      /*  if (tunnusNro != 0) return tunnusNro;
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro; */
        return setTunnusNro(seuraavaNro);
    }
    
    /**
     * Palauttaa asiakkaan tunnusnumeron.
     * @return asiakkaan tunnusnumero
     */
    public int getTunnusNro() {
        return ((IntKentta)(kentat[0])).getValue();
    }
    
    /** 
     * Asettaa tunnusnumeron ja samalla varmistaa että 
     * seuraava numero on aina suurempi kuin tähän mennessä suurin. 
     * @param nr asetettava tunnusnumero 
     * @return asetettu tunnusnumero
     */ 
    private int setTunnusNro(int nr) { 
        IntKentta k = ((IntKentta)(kentat[0]));
        k.setValue(nr);
        if (nr >= seuraavaNro) seuraavaNro = nr + 1;
        return k.getValue();
    }

    /** 
     * Palauttaa asiakkaan tiedot merkkijonona jonka voi tallentaa tiedostoon. 
     * @return asiakas tolppaeroteltuna merkkijonona  
     * @example 
     * <pre name="test"> 
     *   Asiakas asiakas = new Asiakas(); 
     *   asiakas.parse("   3  |  Matti Meikäläinen | 010285-123A"); 
     *   asiakas.toString().startsWith("3|Matti Meikäläinen|010285-123A|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu | 
     * </pre>   
     */ 
    @Override 
    public String toString() { 
        StringBuffer sb = new StringBuffer("");
        String delim = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(delim);
            sb.append(anna(k));
            delim = "|";
            }
        return sb.toString();
    } 


    /** 
     * Selvitää asiakkaan tiedot | erotellusta merkkijonosta 
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro. 
     * @param rivi josta asiakkaan tiedot otetaan 
     *  
     * @example 
     * <pre name="test"> 
     *   Asiakas asiakas = new Asiakas(); 
     *   asiakas.parse("   3  |  Matti Meikäläinen  | 010285-123A"); 
     *   asiakas.getTunnusNro() === 3; 
     *   asiakas.toString().startsWith("3|Matti Meikäläinen|010285-123A|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu | 
     * 
     *   asiakas.rekisteroi(); 
     *   int n = asiakas.getTunnusNro(); 
     *   asiakas.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero 
     *   asiakas.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi 
     *   asiakas.getTunnusNro() === n+20+1; 
     *      
     * </pre> 
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++) {
            aseta(k, Mjonot.erota(sb, '|'));
        }
    } 
    
    /**
     * Tehdään identtinen klooni jäsenestä
     * @return Object kloonattu jäsen
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   3  |  Ankka Aku   | 123");
     *   Jasen kopio = jasen.clone();
     *   kopio.toString() === jasen.toString();
     *   jasen.parse("   4  |  Ankka Tupu   | 123");
     *   kopio.toString().equals(jasen.toString()) === false;
     * </pre>
     */
    @Override
    public Asiakas clone() throws CloneNotSupportedException {
        Asiakas uusi;
        uusi = (Asiakas) super.clone();
        uusi.kentat = kentat.clone();

        for (int k = 0; k < getKenttia(); k++)
            uusi.kentat[k] = kentat[k].clone();
        return uusi;
    }
    
    
    /** 
    * Tutkii onko jäsenen tiedot samat kuin parametrina tuodun jäsenen tiedot 
    * @param asiakas asiakas johon verrataan 
    * @return true jos kaikki tiedot samat, false muuten 
    * @example 
    * <pre name="test"> 
    *   Jasen jasen1 = new Jasen(); 
    *   jasen1.parse("   3  |  Ankka Aku   | 030201-111C"); 
    *   Jasen jasen2 = new Jasen(); 
    *   jasen2.parse("   3  |  Ankka Aku   | 030201-111C"); 
    *   Jasen jasen3 = new Jasen(); 
    *   jasen3.parse("   3  |  Ankka Aku   | 030201-115H"); 
    *    
    *   jasen1.equals(jasen2) === true; 
    *   jasen2.equals(jasen1) === true; 
    *   jasen1.equals(jasen3) === false; 
    *   jasen3.equals(jasen2) === false; 
    * </pre> 
    */ 
   public boolean equals(Asiakas asiakas) { 
       if ( asiakas == null ) return false; 
       for (int k = 0; k < getKenttia(); k++) 
           if ( !anna(k).equals(asiakas.anna(k)) ) return false; 
       return true; 
   } 
    
    
    @Override 
         public boolean equals(Object asiakas) { 
             if ( asiakas instanceof Asiakas ) return equals((Asiakas)asiakas); 
             return false;
         } 
      
      
         @Override 
         public int hashCode() { 
             return getTunnusNro(); 
         } 
         
             
    /**
     * Testausohjelma asiakas-luokalle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Asiakas matti1 = new Asiakas();
        Asiakas matti2 = new Asiakas();
        matti1.rekisteroi();
        matti2.rekisteroi();
        matti1.tulosta(System.out);
        matti1.vastaaMattiMeikalainen();
        matti1.tulosta(System.out);
        
        matti2.vastaaMattiMeikalainen();
        matti2.tulosta(System.out);
        
        matti2.vastaaMattiMeikalainen();
        matti2.tulosta(System.out);
    }

      
}
