package fxTeramusa;

import static fxTeramusa.TietueDialogController.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Collection;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import firma.Asiakas;
import firma.Firma;
import firma.SailoException;
import firma.Terapeutit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;

import java.awt.Desktop;
import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableView; 

import firma.Terapeutti;
import firma.Asiakas;
import firma.Firma;
import firma.SailoException;
/**
 * 
 * @author Kena
 * @version 6.6.2017
 *
 */ 
public class TeramusaGUIController implements Initializable {
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelAsiakas;
    @FXML private GridPane gridAsiakas;
    @FXML private ListChooser<Asiakas> chooserAsiakkaat;
    @FXML private StringGrid<Terapeutti> tableTerapeutit;
    @FXML private ListChooser<Terapeutti> chooserTerapeutit;
    
    
    
        
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }
    
    @FXML private void handleHakuehto() {
     /*   String hakukentta = cbKentat.getSelectionModel().getSelectedItem();
        String ehto = hakuehto.getText(); 
        if ( ehto.isEmpty() )
            naytaVirhe(null);
        else
            naytaVirhe("Ei osata viel‰ hakea " + hakukentta + ": " + ehto);
    } */
        if ( asiakasKohdalla != null )
            hae(asiakasKohdalla.getTunnusNro());
    }
    
    @FXML
    void handleTallenna() {
            tallenna();
    }
    
    @FXML
    void handleAvaaTiedosto() {
        avaa();
    }
    
    @FXML
    private void handleTulosta() {
      //  Dialogs.showMessageDialog("Ei osata");
        TulostusController tulostusCtrl = TulostusController.tulosta(null);
        tulostaValitut(tulostusCtrl.getTextArea());
        }
    
    @FXML
    void handleAvaaTerapeuttinakyma() {
        
        //naytaTeraNakyma();
      //  ModalController.showModal(TeramusaGUIController.class.getResource("TerapeuttiView.fxml"), "Terapeutti", null, "");
        TerapeuttiController.nakyma(firma);
      //  teraCtrl.haeTera();
       // naytaTeraNakyma(teraCtrl);
       // ListChooser<Terapeutti> lista = (teraCtrl.haeListChooser());
        //haeTera(lista);
        //StringGrid<Terapeutti> gridi = (teraCtrl.haeStringGrid());
        
        
        
    }
    
    @FXML
    void handleLopeta() {
            tallenna();
            Platform.exit(); 
    }
    @FXML
    void handleUusiAsiakas() {
        uusiAsiakas();
      //  ModalController.showModal(TeramusaGUIController.class.getResource("MuokkaaAsiakasView.fxml"), "Asiakas", null, "");
    
    }
    
    @FXML
    void handleMuokkaaAsiakas() {
        //ModalController.showModal(TeramusaGUIController.class.getResource("MuokkaaAsiakasView.fxml"), "Asiakas", null, "");
        muokkaa(kentta);
    }
    
    @FXML
    void handlePoistaAsiakas() {
        poistaAsiakas();
    }
        
    @FXML
    void handleLisaaPalvelu() {
        uusiTerapeutti();
        //ModalController.showModal(TeramusaGUIController.class.getResource("MuokkaaPalveluView.fxml"), "Palvelu", null, "");
    }
        
    @FXML
    void handleMuokkaaPalvelua() {
     muokkaaTerapeuttia();       
   // ModalController.showModal(TeramusaGUIController.class.getResource("MuokkaaPalveluView.fxml"), "Palvelu", null, "");
    }       

    @FXML
    void handlePoistaPalvelu() {
        poistaTerapeutti();
    }   
        
        
    
        
     @FXML
     void handleApua() {
     avustus();
        }
        
        
        @FXML
        void handleTietoja() {
            ModalController.showModal(TeramusaGUIController.class.getResource("AboutView.fxml"), "Teramusa", null, "");
        }
    
        //==============================================================                
            //t‰st‰ eteenp‰in aliohjelmia
        private String firmannimi = "Teramusa";
        private Firma firma;        
        private Terapeutti terapeuttiKohdalla;
        private Asiakas asiakasKohdalla;
        private Asiakas apuasiakas = new Asiakas();
        private Terapeutti aputerapeutti = new Terapeutti();
        private TextField edits[];
        private int kentta = 0;
        
        /**
         * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
         * yksi iso tekstikentt‰, johon voidaan tulostaa j‰senten tiedot.
         * Alustetaan myˆs j‰senlistan kuuntelija 
         */
        protected void alusta() {
                  
             panelAsiakas.setFitToHeight(true);
            
            chooserAsiakkaat.clear();
            chooserAsiakkaat.addSelectionListener(e -> naytaAsiakas());
            
            
            
            
         /* alustetaan hrrustustaulukon otsikot 
            int eka = aputerapeutti.ekaKentta(); 
            int lkm = aputerapeutti.getKenttia(); 
            String[] headings = new String[lkm-eka]; 
            for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = aputerapeutti.getKysymys(k); 
            tableTerapeutit.initTable(headings); 
            tableTerapeutit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
            tableTerapeutit.setEditable(false); 
            tableTerapeutit.setPlaceholder(new Label("Ei viel‰ harrastuksia")); 
             
            // T‰m‰ on viel‰ huono, ei automaattisesti muutu jos kentti‰ muutetaan. 
            tableTerapeutit.setColumnSortOrderNumber(1); 
            tableTerapeutit.setColumnSortOrderNumber(2); 
            tableTerapeutit.setColumnWidth(1, 60); 
            tableTerapeutit.setColumnWidth(2, 60); 
            tableTerapeutit.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaTerapeuttia(); } ); 
            tableTerapeutit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaTerapeuttia();}); 
        } */
            cbKentat.clear();
            for (int k = apuasiakas.ekaKentta(); k < apuasiakas.getKenttia(); k++)  
                cbKentat.add(apuasiakas.getKysymys(k), null);
            cbKentat.setSelectedIndex(0);
            edits = TietueDialogController.luoKentat(gridAsiakas, apuasiakas);
            for (TextField edit: edits) 
                if (edit != null ) {
                edit.setEditable(false);
                edit.setOnMouseClicked(e -> { if ( e.getClickCount() >1 ) muokkaa(getFieldId(e.getSource(),0)); });
                edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,0));
                edit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaa(kentta);}); 
                }
            
            //stringgridin alustus
            String otsikot[] = new String[aputerapeutti.getKenttia()-aputerapeutti.ekaKentta()];
            for (int i=0, k=aputerapeutti.ekaKentta(); k < aputerapeutti.getKenttia(); i++, k++) 
                otsikot[i] = aputerapeutti.getKysymys(k);
            tableTerapeutit.initTable(otsikot);
            
            tableTerapeutit.setPlaceholder(new Label("Ei viel‰ terapioita"));
            tableTerapeutit.setOnCellString( (g, terapeutti, defValue, r, c) -> terapeutti.anna(c+terapeutti.ekaKentta()) );
            
            tableTerapeutit.setOnGridLiveEdit((g, terapeutti, defValue, r, c, edit) -> {
                String virhe = terapeutti.aseta(c+terapeutti.ekaKentta(), defValue);
                if ( virhe == null ) {
                    edit.setStyle(null);
                    firma.setTerapeuttiMuutos();
                    Dialogs.setToolTipText(edit,"");
                } else {
                    edit.setStyle("-fx-background-color: red");
                    Dialogs.setToolTipText(edit,virhe);
                }
                return defValue;
            });
            
        } 
        
        private void setTitle(String title) {
            ModalController.getStage(hakuehto).setTitle(title);
        } 
        
        private void muokkaa(int k) {
            
            if (asiakasKohdalla == null) return;
             try {
                 Asiakas asiakas;
                 asiakas = TietueDialogController.kysyTietue(null, asiakasKohdalla.clone(), k);
                 if (asiakas == null) return;
                 firma.korvaaTaiLisaa(asiakas);
                 hae(asiakas.getTunnusNro());
             } catch (CloneNotSupportedException e) {
                 //            
         }  catch (SailoException e) {
             Dialogs.showMessageDialog(e.getMessage());
         }
     } 
        
        
        private void naytaVirhe(String virhe) {
            if ( virhe == null || virhe.isEmpty() ) {
                labelVirhe.setText("");
                labelVirhe.getStyleClass().removeAll("virhe");
                return;
            }
            labelVirhe.setText(virhe);
            labelVirhe.getStyleClass().add("virhe");
        } 
        
       
        
        /**
         * Alustaa firman lukemalla sen valitun nimisest‰ tiedostosta
         * @param nimi tiedosto josta firman tiedot luetaan
         * @return null jos onnistuu, muuten virhe tekstin‰
         */
        protected String lueTiedosto(String nimi) {
            firmannimi = nimi;
            setTitle("Firma - " + firmannimi);
            try {
                firma.lueTiedostosta(nimi);
                hae(0);
                return null;
            } catch (SailoException e) {
                hae(0);
                String virhe = e.getMessage();
                if (virhe != null) Dialogs.showMessageDialog(virhe);
                return virhe;
            }
        } 
        
        /**
         * Kysyt‰‰n tiedoston nimi ja luetaan se
         * @return true jos onnistui, false jo ei
         */
        public boolean avaa() {
              String uusinimi = FirmanNimiController.kysyNimi(null, firmannimi);
                     if (uusinimi == null) return false;
                     lueTiedosto(uusinimi);
                     return true; 
                 }
        
            /**
             * Tietojen tallennus
             * @return null jos onnistuu, muuten virhe tekstin‰
             */
            private String tallenna() {
                try {
                    firma.tallenna();
                    return null;
                } catch (SailoException ex) {
                    Dialogs.showMessageDialog("Talletuksessa ongelmia! " + ex.getMessage());
                    return ex.getMessage();
                }
            }
            /**
             * Tarkistetaan onko tallennettu
             * @return true jos saa sulkea sovelluksen, false jos ei
             */
            public boolean voikoSulkea() {
                tallenna();
                return true;                 
            }    
        
        /**
         * N‰ytt‰‰ listasta valitun j‰senen tiedot, tilap‰isesti yhteen isoon edit-kentt‰‰n
         */
        protected void naytaAsiakas() {
            asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
            TietueDialogController.naytaTietue(edits, asiakasKohdalla);
            naytaTerapeutit(asiakasKohdalla);
            gridAsiakas.setVisible(asiakasKohdalla != null);
            naytaVirhe(null);
              /*  areaAsiakas.clear();
            editNimi.setText(asiakasKohdalla.getNimi());
            editHetu.setText(asiakasKohdalla.getHetu());
            editKatu.setText(asiakasKohdalla.getKatu());
            editPosti.setText(asiakasKohdalla.getPosti());

            
            /*
            areaAsiakas.setText("");
            try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaAsiakas)) {
                tulosta(os, asiakasKohdalla); 
            } */
        }
        
        
    //    protected void naytaTeraNakyma() {
            //terapeuttiKohdalla = chooserTerapeutit.getSelectedObject();
      //      chooserTerapeutit.add("testi", aputerapeutti);
      //  }
        
        
           // gridi.setOnGridLiveEdit((g, terapeutti, defValue, r, c, edit) -> {
               /* String virhe = terapeutti.aseta(c+terapeutti.ekaKentta(), defValue);
                if ( virhe == null ) {
                    edit.setStyle(null);
                    firma.setTerapeuttiMuutos();
                    Dialogs.setToolTipText(edit,"");
                } else {
                    edit.setStyle("-fx-background-color: red");
                    Dialogs.setToolTipText(edit,virhe);
                }
                return defValue;
            }); */ 
        
     
        
        
        /**
         * Hakee asiakkaiden tiedot listaan
         * @param anro asiakkaan numero, joka aktivoidaan haun j‰lkeen
         */
        protected void hae(int anro) {
            int k = cbKentat.getSelectedIndex() + apuasiakas.ekaKentta();
            String ehto = hakuehto.getText();
                if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
                naytaVirhe(null);
            
            chooserAsiakkaat.clear();

            int index = 0;
            Collection<Asiakas> asiakkaat;
            try {
                asiakkaat = firma.etsi(ehto, k);
                int i = 0;
                for ( Asiakas asiakas: asiakkaat) {
                    if (asiakas.getTunnusNro() == anro) index = i;
                    chooserAsiakkaat.add(asiakas.getNimi(), asiakas);
                    i++;
                }
            } catch (SailoException ex) {
                Dialogs.showMessageDialog("Asiakkaan hakemisessa ongelmia! " + ex.getMessage());
            }
            chooserAsiakkaat.setSelectedIndex(index); // t‰st‰ tulee muutosviesti joka n‰ytt‰‰ j‰senen
        }
        
      
        /**
         * Luo uuden asiakkaan jota aletaan editoimaan 
         */
        protected void uusiAsiakas() {            
            try {
            Asiakas uusi = new Asiakas();           
            uusi = TietueDialogController.kysyTietue(null, uusi, 1);
            if ( uusi == null ) return;
                uusi.rekisteroi();
                firma.lisaa(uusi);
                hae(uusi.getTunnusNro());
            } catch (SailoException e) {
                Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
                return;
            }         
        }
        
        /**
         * Tekee uuden Terapeutin tyhj‰n‰ editointia varten
         */
     /* uus vai vanha?  public void uusiTerapeutti() {
            if (asiakasKohdalla == null) return;
            Terapeutti ter = new Terapeutti(asiakasKohdalla.getTunnusNro());
            ter.rekisteroi();            
            try {
                firma.lisaa(ter);
            } catch (Exception e) {
                Dialogs.showMessageDialog("Ongelmia lis‰‰misess‰! " + e.getMessage());
            }            
            hae(asiakasKohdalla.getTunnusNro());
        }  */   
        /**
        * Tekee uuden tyhj‰n harrastuksen editointia varten 
         */ 
        private void uusiTerapeutti() { 
            if ( asiakasKohdalla == null ) return; 
            try { 
                Terapeutti uusi = new Terapeutti(asiakasKohdalla.getTunnusNro()); 
                uusi = TietueDialogController.kysyTietue(null, uusi, 0); 
                if ( uusi == null ) return; 
                uusi.rekisteroi(); 
                firma.lisaa(uusi); 
                naytaTerapeutit(asiakasKohdalla);  
                tableTerapeutit.selectRow(1000);  // j‰rjestet‰‰n viimeinen rivi valituksi 
            } catch (Exception e) { 
                Dialogs.showMessageDialog("Lis‰‰minen ep‰onnistui: " + e.getMessage()); 
            } 
        } 
         
         
        private void muokkaaTerapeuttia() { 
            int r = tableTerapeutit.getRowNr(); 
            if ( r < 0 ) return; // klikattu ehk‰ otsikkorivi‰ 
            Terapeutti ter = tableTerapeutit.getObject(); 
            if ( ter == null ) return; 
            int k = tableTerapeutit.getColumnNr()+ter.ekaKentta(); 
            try { 
                ter = TietueDialogController.kysyTietue(null, ter.clone(), k); 
                if ( ter == null ) return; 
                firma.lisaa(ter); // korvaa tai lis‰‰?
                naytaTerapeutit(asiakasKohdalla);  
                tableTerapeutit.selectRow(r);  // j‰rjestet‰‰n sama rivi takaisin valituksi 
            } catch (CloneNotSupportedException  e) { /* clone on tehty */   
            } catch (Exception e) { 
                Dialogs.showMessageDialog("Ongelmia lis‰‰misess‰: " + e.getMessage()); 
            } 
        }
        
        /** 
         * N‰ytet‰‰n harrastukset taulukkoon.  Tyhjennet‰‰n ensin taulukko ja sitten 
         * lis‰t‰‰n siihen kaikki harrastukset 
         * @param jasen j‰sen, jonka harrastukset n‰ytet‰‰n 
         */ 
        private void naytaTerapeutit(Asiakas asiakas) { 
            tableTerapeutit.clear(); 
            if ( asiakas == null ) return; 
             
            try { 
                List<Terapeutti> terapiat = firma.annaTerapeutit(asiakas); 
                if ( terapiat.size() == 0 ) return; 
                for (Terapeutti ter: terapiat) 
                    naytaTerapeutti(ter); 
            } catch (Exception e) { 
                // naytaVirhe(e.getMessage()); 
            }  
        } 
        
        private void naytaTerapeutti(Terapeutti ter) {
                int kenttia = ter.getKenttia();
                String[] rivi = new String[kenttia-ter.ekaKentta()];
                for (int i=0, k=ter.ekaKentta(); k < kenttia; i++, k++)
                    rivi[i] = ter.anna(k);
                tableTerapeutit.add(ter,rivi);
            }


        
      
        
       /*    private void naytaTeraNakyma (Asiakas asiakas) {
        gridi.clear(); 
        if ( asiakas == null ) return; 
         
        try { 
            List<Terapeutti> terapiat = firma.annaTerapeutit(asiakas); 
            if ( terapiat.size() == 0 ) return; 
            for (Terapeutti ter: terapiat) 
                naytaTerapeutti(ter); 
        } catch (Exception e) { 
            // naytaVirhe(e.getMessage()); 
        }  
    } 
    
    private void naytaTerapeutti(Terapeutti ter) {
            int kenttia = ter.getKenttia();
            String[] rivi = new String[kenttia-ter.ekaKentta()];
            for (int i=0, k=ter.ekaKentta(); k < kenttia; i++, k++)
                rivi[i] = ter.anna(k);
            tableTerapeutit.add(ter,rivi);
        } */
        
        
        /**
         * 
         * @param firma jota k‰sitell‰‰n t‰ss‰ k‰yttˆliittym‰ss‰
         */
        public void setFirma(Firma firma) {
            this.firma = firma;
            naytaAsiakas();                
        }
        
        /**
         * Poistetaan harrastustaulukosta valitulla kohdalla oleva harrastus. 
         */
        private void poistaTerapeutti() {
            // Dialogs.showMessageDialog("Ei osata viel‰ poistaa harrastusta");
            Terapeutti terapeutti = tableTerapeutit.getObject();
            if ( terapeutti == null ) return;
            int rivi = tableTerapeutit.getRowNr();
            firma.poistaTerapeutti(terapeutti);
            naytaTerapeutit(asiakasKohdalla);
            tableTerapeutit.selectRow(rivi);
        }

        
        /*
         * Poistetaan listalta valittu j‰sen
         */
        private void poistaAsiakas() {
            if ( asiakasKohdalla == null ) return;
            if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko asiakas: " + asiakasKohdalla.getNimi(), "Kyll‰", "Ei") )
                return;
            firma.poista(asiakasKohdalla);
            int index = chooserAsiakkaat.getSelectedIndex();
            hae(0);
            chooserAsiakkaat.setSelectedIndex(index);
        }
        
            /**
             * N‰ytet‰‰n ohjelman suunnitelma erillisess‰ selaimessa.
             */
             
            private void avustus() {
                Desktop desktop = Desktop.getDesktop();
                try {
                    URI uri = new URI("https://www.mit.jyu.fi/demowww/ohj2/ht17/lamikett/trunk/");
                    desktop.browse(uri);
                } catch (URISyntaxException e) {
                    return;
                } catch (IOException e) {
                    return;
                } 
            }
                
            
            
            /** Tulostaa Asiakkaan  tiedot
             * @param os tietovirta johon tulostetaan
             * @param asiakas tulostettava asiakas
             */
            public void tulosta(PrintStream os, final Asiakas asiakas) {
                os.println("------------------------------------");
                asiakas.tulosta(os);
                os.println("------------------------------------");
                try {
                List<Terapeutti> terapeutit = firma.annaTerapeutit(asiakas);
                for (Terapeutti ter: terapeutit)
                    ter.tulosta(os);
            } catch (Exception ex) {
                Dialogs.showMessageDialog("Terapioiden hakemisessa ongelmia! " + ex.getMessage());
            }
      }
            
            /** Tulostaa listassa olevat asiakkaat tekstialueeseen
             * @param text alue johon tulostetaan
             */
            public void tulostaValitut(TextArea text) {
                try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
                    os.println("Valitut asiakkaat");
                  //  Collection<Asiakas> asiakkaat = firma.etsi("", -1);
                    for (Asiakas asiakas: chooserAsiakkaat.getObjects()) {
                        tulosta(os, asiakas);
                        os.println("\n\n"); 
                    }
                }                 
            }
}

   