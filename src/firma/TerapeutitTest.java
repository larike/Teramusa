package firma;
// Generated by ComTest BEGIN
import java.io.File;
import firma.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2017.07.19 16:47:50 // Generated by ComTest
 *
 */
public class TerapeutitTest {



  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta48 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta48() throws SailoException {    // Terapeutit: 48
    Terapeutit terat = new Terapeutit(); 
    Terapeutti greta21 = new Terapeutti(); greta21.vastaaGretaHuhtoinen(2); 
    Terapeutti greta11 = new Terapeutti(); greta11.vastaaGretaHuhtoinen(1); 
    Terapeutti greta22 = new Terapeutti(); greta22.vastaaGretaHuhtoinen(2); 
    Terapeutti greta12 = new Terapeutti(); greta12.vastaaGretaHuhtoinen(1); 
    Terapeutti greta23 = new Terapeutti(); greta23.vastaaGretaHuhtoinen(2); 
    String tiedNimi = "testifirmat"; 
    File ftied = new File(tiedNimi+".dat"); 
    ftied.delete(); 
    try {
    terat.lueTiedostosta(tiedNimi); 
    fail("Terapeutit: 60 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    terat.lisaa(greta21); 
    terat.lisaa(greta11); 
    terat.lisaa(greta22); 
    terat.lisaa(greta12); 
    terat.lisaa(greta23); 
    terat.tallenna(); 
    terat = new Terapeutit(); 
    terat.lueTiedostosta(tiedNimi); 
    Iterator<Terapeutti> i = terat.iterator(); 
    assertEquals("From: Terapeutit line: 70", greta21.toString(), i.next().toString()); 
    assertEquals("From: Terapeutit line: 71", greta11.toString(), i.next().toString()); 
    assertEquals("From: Terapeutit line: 72", greta22.toString(), i.next().toString()); 
    assertEquals("From: Terapeutit line: 73", greta12.toString(), i.next().toString()); 
    assertEquals("From: Terapeutit line: 74", greta23.toString(), i.next().toString()); 
    assertEquals("From: Terapeutit line: 75", false, i.hasNext()); 
    terat.lisaa(greta23); 
    terat.tallenna(); 
    assertEquals("From: Terapeutit line: 78", true, ftied.delete()); 
    File fbak = new File(tiedNimi+".bak"); 
    assertEquals("From: Terapeutit line: 80", true, fbak.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testIterator187 */
  @Test
  public void testIterator187() {    // Terapeutit: 187
    Terapeutit terat = new Terapeutit(); 
    Terapeutti greta21 = new Terapeutti(2); terat.lisaa(greta21); 
    Terapeutti greta11 = new Terapeutti(1); terat.lisaa(greta11); 
    Terapeutti greta22 = new Terapeutti(2); terat.lisaa(greta22); 
    Terapeutti greta12 = new Terapeutti(1); terat.lisaa(greta12); 
    Terapeutti greta23 = new Terapeutti(2); terat.lisaa(greta23); 
    Iterator<Terapeutti> i2=terat.iterator(); 
    assertEquals("From: Terapeutit line: 199", greta21, i2.next()); 
    assertEquals("From: Terapeutit line: 200", greta11, i2.next()); 
    assertEquals("From: Terapeutit line: 201", greta22, i2.next()); 
    assertEquals("From: Terapeutit line: 202", greta12, i2.next()); 
    assertEquals("From: Terapeutit line: 203", greta23, i2.next()); 
    try {
    assertEquals("From: Terapeutit line: 204", greta12, i2.next()); 
    fail("Terapeutit: 204 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
    int n = 0; 
    int anro[] = { 2,1,2,1,2} ; 
    for ( Terapeutti ter:terat ) {
    assertEquals("From: Terapeutit line: 210", anro[n], ter.getAsiakasNro()); n++; 
    }
    assertEquals("From: Terapeutit line: 213", 5, n); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaTerapeutit228 */
  @Test
  public void testAnnaTerapeutit228() {    // Terapeutit: 228
    Terapeutit terat = new Terapeutit(); 
    Terapeutti greta21 = new Terapeutti(2); terat.lisaa(greta21); 
    Terapeutti greta11 = new Terapeutti(1); terat.lisaa(greta11); 
    Terapeutti greta22 = new Terapeutti(2); terat.lisaa(greta22); 
    Terapeutti greta12 = new Terapeutti(1); terat.lisaa(greta12); 
    Terapeutti greta23 = new Terapeutti(2); terat.lisaa(greta23); 
    Terapeutti greta51 = new Terapeutti(5); terat.lisaa(greta51); 
    List<Terapeutti> loytyneet; 
    loytyneet = terat.annaTerapeutit(3); 
    assertEquals("From: Terapeutit line: 241", 0, loytyneet.size()); 
    loytyneet = terat.annaTerapeutit(1); 
    assertEquals("From: Terapeutit line: 243", 2, loytyneet.size()); 
    assertEquals("From: Terapeutit line: 244", true, loytyneet.get(0) == greta11); 
    assertEquals("From: Terapeutit line: 245", true, loytyneet.get(1) == greta12); 
    loytyneet = terat.annaTerapeutit(5); 
    assertEquals("From: Terapeutit line: 247", 1, loytyneet.size()); 
    assertEquals("From: Terapeutit line: 248", true, loytyneet.get(0) == greta51); 
  } // Generated by ComTest END
}