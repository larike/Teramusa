package firma;

import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.PrintWriter; 
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.Collection; 
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import fi.jyu.mit.ohj2.WildChars;


/**
 * Firman asiakkaat, joka osaa mm. lis‰t‰ uuden asiakkaan
 *
 * @author Lari Kettunen
 * @version 1.0, 26.06.2017
 */
public class Asiakkaat implements Iterable<Asiakas> {
    
    private static int MAX_ASIAKKAITA = Integer.MAX_VALUE;
    private boolean muutettu = false;
    private Asiakas[]        alkiot = new Asiakas[5];
    private int              lkm = 0;
    private String           tiedostonPerusNimi = "nimet";
    private String           kokoNimi = "";
    
    
    /**
     * Oletusmuodostaja
     */
    public Asiakkaat() {
        // oma alustus riitt‰‰
    }
    
    /**
     * Lis‰‰ uuden j‰senen tietorakenteeseen.  Ottaa j‰senen omistukseensa.
     * @param asiakas lis‰t‰‰v‰n j‰senen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo t‰ynn‰
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Jasenet jasenet = new Jasenet();
     * Jasen aku1 = new Jasen(), aku2 = new Jasen();
     * jasenet.getLkm() === 0;
     * jasenet.lisaa(aku1); jasenet.getLkm() === 1;
     * jasenet.lisaa(aku2); jasenet.getLkm() === 2;
     * jasenet.lisaa(aku1); jasenet.getLkm() === 3;
     * Iterator<Jasen> it = jasenet.iterator();
     * it.next() === aku1;
     * it.next() === aku2;
     * it.next() === aku1;
     * jasenet.lisaa(aku1); jasenet.getLkm() === 4;
     * jasenet.lisaa(aku1); jasenet.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Asiakas asiakas) throws SailoException {
        if ( lkm >= MAX_ASIAKKAITA ) throw new SailoException("Liikaa alkioita"); 
        if ( lkm >= alkiot.length ) alkiot = Arrays.copyOf(alkiot, lkm*2); 
        alkiot[lkm] = asiakas;
        lkm++;
        muutettu = true;
    }
    
    /**
     * Korvaa j‰senen tietorakenteessa.  Ottaa j‰senen omistukseensa.
     * Etsit‰‰n samalla tunnusnumerolla oleva j‰sen.  Jos ei lˆydy,
     * niin lis‰t‰‰n uutena j‰senen‰.
     * @param asiakas lis‰t‰‰v‰n j‰senen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo t‰ynn‰
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Jasenet jasenet = new Jasenet();
     * Jasen aku1 = new Jasen(), aku2 = new Jasen();
     * aku1.rekisteroi(); aku2.rekisteroi();
     * jasenet.getLkm() === 0;
     * jasenet.korvaaTaiLisaa(aku1); jasenet.getLkm() === 1;
     * jasenet.korvaaTaiLisaa(aku2); jasenet.getLkm() === 2;
     * Jasen aku3 = aku1.clone();
     * aku3.aseta(3,"kkk");
     * Iterator<Jasen> it = jasenet.iterator();
     * it.next() == aku1 === true;
     * jasenet.korvaaTaiLisaa(aku3); jasenet.getLkm() === 2;
     * it = jasenet.iterator();
     * Jasen j0 = it.next();
     * j0 === aku3;
     * j0 == aku3 === true;
     * j0 == aku1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Asiakas asiakas) throws SailoException {
        int id = asiakas.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = asiakas;
                muutettu = true;
                return;
            }
        }
        lisaa(asiakas);
    }
    
    
    /**
     * Palauttaa viitteen i:teen j‰seneen.
     * @param i monennenko j‰senen viite halutaan
     * @return viite j‰seneen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    protected Asiakas anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    public String annaAsNimi(int aID) {
    	for (int i = 0; i < lkm; i++) {
    		if ( alkiot[i].ekaKentta() == aID)
    	 return alkiot[i].getNimi();
    } return "";
    }
    /** 
     * Poistaa j‰senen jolla on valittu tunnusnumero  
     * @param id poistettavan j‰senen tunnusnumero 
     * @return 1 jos poistettiin, 0 jos ei lˆydy 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Jasenet jasenet = new Jasenet(); 
     * Jasen aku1 = new Jasen(), aku2 = new Jasen(), aku3 = new Jasen(); 
     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi(); 
     * int id1 = aku1.getTunnusNro(); 
     * jasenet.lisaa(aku1); jasenet.lisaa(aku2); jasenet.lisaa(aku3); 
     * jasenet.poista(id1+1) === 1; 
     * jasenet.annaId(id1+1) === null; jasenet.getLkm() === 2; 
     * jasenet.poista(id1) === 1; jasenet.getLkm() === 1; 
     * jasenet.poista(id1+3) === 0; jasenet.getLkm() === 1; 
     * </pre> 
     *  
     */ 
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    } 


    /**
     * Lukee asiakkaat tiedostosta.
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen ep‰onnistuu
     @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * #import java.io.File; 
     *  
     *  Asiakkaat asiakkaat = new Asiakkaat(); 
     *  Asiakas matti1 = new Asiakas(), matti2 = new Asiakas(); 
     *  matti1.vastaaMattiMeikalainen(); 
     *  matti2.vastaaMattiMeikalainen(); 
     *  String hakemisto = "testifirmat"; 
     *  String tiedNimi = hakemisto+"/nimet"; 
     *  File ftied = new File(tiedNimi+".dat"); 
     *  File dir = new File(hakemisto); 
     *  dir.mkdir(); 
     *  ftied.delete(); 
     *  asiakkaat.lueTiedostosta(tiedNimi); #THROWS SailoException 
     *  asiakkaat.lisaa(matti1); 
     *  asiakkaat.lisaa(matti2); 
     *  asiakkaat.tallenna(); 
     *  asiakkaat = new Asiakkaat();            // Poistetaan vanhat luomalla uusi 
     *  asiakkaat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta. 
     *  Iterator<Asiakas> i = asiakkaat.iterator(); 
     *  i.next() === matti1; 
     *  i.next() === matti2; 
     *  i.hasNext() === false; 
     *  asiakkaat.lisaa(matti2); 
     *  asiakkaat.tallenna(); 
     *  ftied.delete() === true; 
     *  File fbak = new File(tiedNimi+".bak"); 
     *  fbak.delete() === true; 
     *  dir.delete() === true; 
     * </pre> 
     */  
     
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi (tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
        //    kokoNimi = fi.readLine();
         //   if ( kokoNimi == null ) throw new SailoException("Firman nimi puuttuu");
            String rivi;
           // if ( rivi == null) throw new SailoException("Maksimikoko puuttuu");
            // int maxKoko = Mjonot.erotaInt(rivi,10); ...
            
            while ( ( rivi = fi.readLine() ) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Asiakas asiakas = new Asiakas();
                asiakas.parse(rivi);
                lisaa(asiakas);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
        
    /** 
     * Luetaan aikaisemmin annetun nimisest‰ tiedostosta 
     * @throws SailoException jos tulee poikkeus 
     */ 
     public void lueTiedostosta() throws SailoException { 
         lueTiedostosta(getTiedostonPerusNimi()); 
     } 
     
    /**
     * Tallentaa asiakkaat tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     * Firma Teramusa
     * 20
     * ; kommenttirivi
     * 2|Matti Meik‰l‰inen|010285-123A|Kauppakatu 6|40600|Jyv‰skyl‰|0501234567|K-Suomen sh-piiri|F32.1|28.03.15-13.03.17
     * 3|Siiri Siev‰nen|010285-123A|Kauppakatu 6|40600|Jyv‰skyl‰|0501234567|K-Suomen sh-piiri|F32.1|28.03.15-13.03.17
     * </pre>
     * @throws SailoException jos talletus ep‰onnistuu
     * kommentti 3|Siiri Siev‰nen|020367-123B|Rantapolku 5a16|41200|Jyv‰skyl‰|0401234567|Yksityinen|F60.0|01.01.16-31.05.16
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        File fbak = new File(getBakNimi()); 
        File ftied = new File(getTiedostonNimi()); 
        fbak.delete(); // if .. System.err.println("Ei voi tuhota"); 
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimet‰"); 
        
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) { 
        //    fo.println(getKokoNimi()); 
           // fo.println(alkiot.length); 
            for (Asiakas asiakas : this) { 
                fo.println(asiakas.toString()); 
            } 
            //} catch ( IOException e ) { // ei heit‰ poikkeusta 
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage()); 
        } catch ( FileNotFoundException ex ) { 
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea"); 
        } catch ( IOException ex ) { 
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia"); 
        } 
        
        muutettu = false; 
        
    } 
        
        /**
        * Palauttaa firman koko nimen 
        * @return firman koko nimi merkkijononna 
        */
        public String getKokoNimi() { 
        return kokoNimi;   
        }
        
        /** 
         * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen 
         * @return tallennustiedoston nimi 
         */ 
        public String getTiedostonPerusNimi() { 
            return tiedostonPerusNimi; 
        } 
        
        
        /** 
         * Asettaa tiedoston perusnimen ilman tarkenninta 
         * @param nimi tallennustiedoston perusnimi 
         */ 
        public void setTiedostonPerusNimi(String nimi) { 
            tiedostonPerusNimi = nimi; 
        } 
        
        
        /** 
         * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen 
         * @return tallennustiedoston nimi 
         */ 
        public String getTiedostonNimi() { 
            return getTiedostonPerusNimi() + ".dat"; 
        } 
        
        
        /** 
         * Palauttaa varakopiotiedoston nimen 
         * @return varakopiotiedoston nimi 
         */ 
        public String getBakNimi() { 
            return tiedostonPerusNimi + ".bak"; 
        } 
        
    /**
     * Palauttaa firman asiakkaiden lukum‰‰r‰n
     * @return asiakkaiden lukum‰‰r‰
     */
    public int getLkm() {
        return lkm;
    } 
    
    /** 
     * Luokka asiakkaiden iteroimiseksi. 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * #PACKAGEIMPORT 
     * #import java.util.*; 
     *  
     * Asiakkaat asiakkaat = new Asiakkaat(); 
     * Asiakas matti1 = new Asiakas(), matti2 = new Asiakas(); 
     * matti1.rekisteroi(); matti2.rekisteroi(); 
     * 
     * asiakkaat.lisaa(matti1);  
     * asiakkaat.lisaa(matti2);  
     * asiakkaat.lisaa(matti1);  
     *  
     * StringBuffer ids = new StringBuffer(30); 
     * for (Asiakas asiakas: asiakkaat)   // Kokeillaan for-silmukan toimintaa 
     *   ids.append(" "+asiakas.getTunnusNro());            
     *  
     * String tulos = " " + matti1.getTunnusNro() + " " + matti2.getTunnusNro() + " " + matti1.getTunnusNro(); 
     *  
     * ids.toString() === tulos;  
     *  
     * ids = new StringBuffer(30); 
     * for (Iterator<Asiakas>  i=asiakkaat.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa 
     *   Asiakas asiakas = i.next(); 
     *   ids.append(" "+asiakas.getTunnusNro());            
     * } 
     *  
     * ids.toString() === tulos; 
     *  
     * Iterator<Asiakas>  i=asiakkaat.iterator(); 
     * i.next() == matti1  === true; 
     * i.next() == matti2  === true; 
     * i.next() == matti1  === true; 
     *  
     * i.next();  #THROWS NoSuchElementException 
     *   
     * </pre> 
     */ 
    public class AsiakkaatIterator implements Iterator<Asiakas> { 
        private int kohdalla = 0; 
    
    
        /** 
         * Onko olemassa viel‰ seuraavaa asiakasta 
         * @see java.util.Iterator#hasNext() 
         * @return true jos on viel‰ asiakkaita 
         */ 
        @Override 
        public boolean hasNext() { 
            return kohdalla < getLkm(); 
        } 
    
    
        /** 
         * Annetaan seuraava asiakas 
         * @return seuraava asiakas 
         * @throws NoSuchElementException jos seuraava alkiota ei en‰‰ ole 
         * @see java.util.Iterator#next() 
         */ 
        @Override 
        public Asiakas next() throws NoSuchElementException { 
            if ( !hasNext() ) throw new NoSuchElementException("Ei ole"); 
            return anna(kohdalla++); 
        } 
    
    
        /** 
         * Tuhoamista ei ole toteutettu 
         * @throws UnsupportedOperationException aina 
         * @see java.util.Iterator#remove() 
         */ 
        @Override 
        public void remove() throws UnsupportedOperationException { 
            throw new UnsupportedOperationException("Me ei poisteta"); 
        } 
    } 
    
    
    /** 
     * Palautetaan iteraattori j‰senist‰‰n. 
     * @return j‰sen iteraattori 
     */ 
    @Override 
    public Iterator<Asiakas> iterator() { 
        return new AsiakkaatIterator(); 
    } 
    
    
    /**  
     * Palauttaa "taulukossa" hakuehtoon vastaavien asiakkaiden viitteet  
     * @param hakuehto hakuehto  
     * @param k etsitt‰v‰n kent‰n indeksi   
     * @return tietorakenteen lˆytyneist‰ asiakkaista  
     * @example  
     * <pre name="test">  
     * #THROWS SailoException   
     *   Asiakkaat asiakkaat = new Asiakkaat();  
     *   Asiakas asiakas1 = new Asiakas(); asiakas1.parse("1|Matti Meik‰l‰inen|010285-123A|Kauppakatu 6|");  
     *   Asiakas asiakas2 = new Asiakas(); asiakas2.parse("2|Siiri Siev‰nen||020367-123B|");  
     *   Asiakas asiakas3 = new Asiakas(); asiakas3.parse("3|Maija Mehil‰inen|030475-123C||39550|Laukaa");  
     *   asiakkaat.lisaa(asiakas1); asiakkaat.lisaa(asiakas2); asiakkaat.lisaa(asiakas3);
     *   List<Asiakas> loytyneet;   
     *   loytyneet = (List<Asiakas>)asiakkaat.etsi("*s*",1);   
     *   loytyneet.size() === 2;   
     *   loytyneet.get(0) == asiakas3 === true;   
     *   loytyneet.get(1) == asiakas4 === true;   
     *      
     *   loytyneet = (List<Asiakas>)asiakkaat.etsi("*7-*",2);   
     *   loytyneet.size() === 2;   
     *   loytyneet.get(0) == asiakas3 === true;   
     *   loytyneet.get(1) == asiakas5 === true;  
     *      
     *   loytyneet = (List<Asiakas>)asiakkaat.etsi(null,-1);   
     *   loytyneet.size() === 5
     * </pre>  
     */  
    
    public Collection<Asiakas> etsi(String hakuehto, int k) {  
        String ehto = "*";
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto;
        int hk = k;
        if ( hk < 0 ) hk = 1;
        List<Asiakas> loytyneet = new ArrayList<Asiakas>();  
        for (Asiakas asiakas : this) {  
            if (WildChars.onkoSamat(asiakas.anna(hk), ehto)) loytyneet.add(asiakas);  
        }  
        Collections.sort(loytyneet, new Asiakas.Vertailija(k));
        return loytyneet;  
    } 
    
    /**  
     * Etsii j‰senen id:n perusteella  
     * @param id tunnusnumero, jonka mukaan etsit‰‰n  
     * @return j‰sen jolla etsitt‰v‰ id tai null  
     * <pre name="test">  
     * #THROWS SailoException   
     * Jasenet jasenet = new Jasenet();  
     * Jasen aku1 = new Jasen(), aku2 = new Jasen(), aku3 = new Jasen();  
     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi();  
     * int id1 = aku1.getTunnusNro();  
     * jasenet.lisaa(aku1); jasenet.lisaa(aku2); jasenet.lisaa(aku3);  
     * jasenet.annaId(id1  ) == aku1 === true;  
     * jasenet.annaId(id1+1) == aku2 === true;  
     * jasenet.annaId(id1+2) == aku3 === true;  
     * </pre>  
     */  
    public Asiakas annaId(int id) {  
        for (Asiakas asiakas : this) {  
            if (id == asiakas.getTunnusNro()) return asiakas;  
        }  
        return null;  
    }  
    
    
    /**  
     * Etsii j‰senen id:n perusteella  
     * @param id tunnusnumero, jonka mukaan etsit‰‰n  
     * @return lˆytyneen j‰senen indeksi tai -1 jos ei lˆydy  
     * <pre name="test">  
     * #THROWS SailoException   
     * Jasenet jasenet = new Jasenet();  
     * Jasen aku1 = new Jasen(), aku2 = new Jasen(), aku3 = new Jasen();  
     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi();  
     * int id1 = aku1.getTunnusNro();  
     * jasenet.lisaa(aku1); jasenet.lisaa(aku2); jasenet.lisaa(aku3);  
     * jasenet.etsiId(id1+1) === 1;  
     * jasenet.etsiId(id1+2) === 2;  
     * </pre>  
     */  
    public int etsiId(int id) {  
        for (int i = 0; i < lkm; i++)  
            if (id == alkiot[i].getTunnusNro()) return i;  
        return -1;  
    }
    
    /**
     * Testiohjelma asiakkaille
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Asiakkaat asiakkaat = new Asiakkaat();
        
        Asiakas matti1 = new Asiakas();
        Asiakas matti2 = new Asiakas();
        
        matti1.rekisteroi();
        matti1.vastaaMattiMeikalainen();
        matti2.rekisteroi();
        matti2.vastaaMattiMeikalainen();
        
        try {
            asiakkaat.lisaa(matti1);
            asiakkaat.lisaa(matti2);
       
          
        System.out.println("============= Asiakkaat testi =================");
            int i = 0;
            for (Asiakas asiakas : asiakkaat) {
                System.out.println("Asiakas nro: " + i);
                asiakas.tulosta(System.out);
            }
         /*   for (Asiakas asiakas: asiakkaat) {
                System.out.println("Asiakas nro: " + i++);
                asiakas.tulosta(System.out);
            }         */
   
    }  catch ( SailoException ex ) {
        System.out.println(ex.getMessage());
    }
}
}
