package kanta;

/**
 * Kenttä hetuja varten
 * 
 * @author vesal
 * 
 * @example
 * <pre name="testJonoKentta">
 *  HetuKentta kentta = new HetuKentta("hetu",new HetuTarkistus());
 *  kentta.toString()           === "";
 *  kentta.aseta("kissa")       === "Hetu liian lyhyt"
 *  kentta.toString()           === ""
 *  kentta.aseta("010203-111L") === null
 *  kentta.getAvain()           === "1030201-111L"
 *  kentta.getKysymys()         === "hetu"
 *  
 * </pre>
 *
 */
public class HetuKentta extends JonoKentta {

    /**
     * 
     * @param kysymys kentästä esitettävä kysymys
     */
    public HetuKentta(String kysymys) { super(kysymys); }

    /**
     * 
     * @param kysymys kentästä esitettävä kysymys
     * @param tarkistaja olio joka tarkastaa kentän
     */
    public HetuKentta(String kysymys,Tarkistaja tarkistaja) {
        super(kysymys,tarkistaja);
    }


    /** 
     * @return avain
     * @see kanta.PerusKentta#getAvain()
     * @example
     * <pre name="test">
     *  HetuKentta kentta = new HetuKentta("hetu"); 
     *  kentta.aseta("010203+111L") === null // 1800
     *  kentta.getAvain()           === "0030201+111L"
     *  kentta.aseta("010203-111L") === null // 1900
     *  kentta.getAvain()           === "1030201-111L"
     *  kentta.aseta("010203A111L") === null // 2000
     *  kentta.getAvain()           === "2030201A111L"
     * </pre>
     */
    @Override
    public String getAvain() {
        StringBuffer sb = new StringBuffer(toString());
        if ( sb.length() < 6 ) return sb.toString(); // TODO mitä palautetaan
        char c = sb.charAt(4);
        sb.setCharAt(4,sb.charAt(0));
        sb.setCharAt(0,c);
        c = sb.charAt(5);
        sb.setCharAt(5,sb.charAt(1));
        sb.setCharAt(1,c);
        sb.insert(0, '0'); //  - jonon alkuun ja  + < - < A
        if ( sb.length() < 8 ) return sb.toString(); // jos ei erotinta, mennään ilman sitä
        if ( sb.charAt(7) == '-')  sb.setCharAt(0,'1');
        if ( sb.charAt(7) == 'A')  sb.setCharAt(0,'2');
        return sb.toString();  
    }
}
