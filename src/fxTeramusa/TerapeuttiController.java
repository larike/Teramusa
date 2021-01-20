package fxTeramusa;
import static fxTeramusa.TietueDialogController.getFieldId;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import firma.Terapeutti;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import firma.Asiakas;
import firma.Firma;
import firma.SailoException;
import firma.Terapeutti;
/**
 * 
 * @author Kena
 * @version 6.6.2017
 *
 */
public class TerapeuttiController implements ModalControllerInterface<Firma> {
    
	
    @FXML
    private ListChooser<String> chooserTerapeutit;

    @FXML
    private StringGrid<Terapeutti> GridTeraNakyma;

    @FXML
    void handlePoistu() {
        Dialogs.showMessageDialog("Ei osata");
    }   
    
    
      
    
    @Override
    public Firma getResult() {
        return null;
    } 

    
    @Override
    public void setDefault(Firma oletus) {
        if ( oletus == null ) return;
        this.firma = oletus; 
        haeTera();
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
    	String otsikot[] = {"Asiakas", "Terapiamuoto", "Min/vko"};                   
        GridTeraNakyma.initTable(otsikot);
        
        GridTeraNakyma.setPlaceholder(new Label("Ei vielä terapioita"));
        GridTeraNakyma.setOnCellString( (g, terapeutti,  defValue, r, c) -> naytaTiedot(terapeutti,  c) );
    }
    
   
    //===================================================
    
   // private ListChooser<Terapeutti> lista = chooserTerapeutit;
    private Terapeutti terapeuttiKohdalla;
    private Firma firma;
    private String firmannimi = "Teramusa";
            
    
    private Asiakas asiakasKohdalla;
    private Asiakas apuasiakas = new Asiakas();
    private Terapeutti aputerapeutti = new Terapeutti();
    private TextField edits[];
    private int kentta = 0;
    
    /**
     
     * 
     */
    protected void haeTera() {
      
        
        List<String> nimet = firma.annaKaikkiNimet();
                System.out.println("nimet");
        int i = 0;
       
        for( i = 1; i < nimet.size(); i++)
        if ( nimet.get(i).equals(nimet.get(i-1) )) {
            nimet.remove(i); i--;
        }
       
        
       for (String nimi: nimet)
    	   chooserTerapeutit.add(nimi, nimi);
       chooserTerapeutit.addSelectionListener(e -> naytaTera());
             
       
        
        }
    
    
    /**
     
     */
    protected void naytaTera() {
    	GridTeraNakyma.clear();
        String nimiKohdalla = chooserTerapeutit.getSelectedObject();
        Collection<Terapeutti> terapeutti = firma.annaKaikkiTerat(nimiKohdalla);              
      
        GridTeraNakyma.add(terapeutti);
        
    }
    
    private String naytaTiedot(Terapeutti terapeutti, int c) {
    	
		switch(c) {
		case (0):
			int aID = terapeutti.GetAsiakasNro(); // terapeutti.anna(1);
			String AsiakkaanNimi = firma.annaAsNimi(aID);
			return AsiakkaanNimi;
		case (1):
			return terapeutti.anna(3);
		case (2):
			return terapeutti.anna(4);
			default: return "moi";
		}	
	}


	public static void nakyma(Firma nakyma) {
       // TerapeuttiController teraCtrl = (TerapeuttiController)
        ModalController.showModeless(TerapeuttiController.class.getResource("TerapeuttiView.fxml"),
        "Terapeuttinäkymä", nakyma);
        
    }
}


