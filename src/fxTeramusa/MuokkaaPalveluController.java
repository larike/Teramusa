package fxTeramusa;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import firma.Asiakas;
import firma.Terapeutti;



/**
 * 
 * @author Kena
 * @version 6.6.2017
 *
 */
public class MuokkaaPalveluController implements ModalControllerInterface<Terapeutti>, Initializable {
    
    @FXML private ScrollPane panelTerapeutti;
    @FXML private GridPane gridTerapeutti;
    @FXML private Label labelVirhe;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
    
    @FXML
    void handleTallenna() {
        if ( terapeuttiKohdalla != null && terapeuttiKohdalla.anna(2).trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhj‰");
            return;
        }
        ModalController.closeStage(labelVirhe);
    } 
    @FXML
    void HandleCancel() {
        terapeuttiKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    

    
 // ========================================================    
    private Terapeutti terapeuttiKohdalla;
    private static Terapeutti aputerapeutti = new Terapeutti(); // J‰sen jolta voidaan kysell‰ tietoja.
    private TextField[] edits;
    private int kentta = 0;
    

    /**
     * Luodaan GridPaneen j‰senen tiedot
     * @param gridTerapeutti mihin tiedot luodaan
     * @return luodut tekstikent‰t
     */
    public static TextField[] luoKentat(GridPane gridTerapeutti) {
        gridTerapeutti.getChildren().clear();
        TextField[] edits = new TextField[aputerapeutti.getKenttia()];
        
        for (int i=0, k = aputerapeutti.ekaKentta(); k < aputerapeutti.getKenttia(); k++, i++) {
            Label label = new Label(aputerapeutti.getKysymys(k));
            gridTerapeutti.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridTerapeutti.add(edit, 1, i);
        }
        return edits;
    } 
    

    /**
     * Tyhjent‰‰n tekstikent‰t 
     * @param edits tyhjennett‰v‰t kent‰t
     */
    private static void tyhjenna(TextField[] edits){ 
        for (TextField edit: edits) 
            if ( edit != null ) edit.setText(""); 
    }

    /**
     * Palautetaan komponentin id:st‰ saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mik‰ arvo jos id ei ole kunnollinen
     * @return komponentin id lukuna 
     */
    public static int getFieldId(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    } 
    
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikentt‰, johon voidaan tulostaa j‰senten tiedot.
     */
    protected void alusta() {
        edits = luoKentat(gridTerapeutti);
        for (TextField edit : edits)
        if (edit != null) 
            edit.setOnKeyReleased( e -> kasitteleMuutosTerapeuttiin((TextField)(e.getSource())));
        panelTerapeutti.setFitToHeight(true); 
    }
    
    
    @Override
    public void setDefault(Terapeutti oletus) {
        terapeuttiKohdalla = oletus;
        naytaTerapeutti(edits, terapeuttiKohdalla);
    }  
    
    @Override
    public Terapeutti getResult() {
        return terapeuttiKohdalla;
    }
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    } 
    
    
    /**
     * Mit‰ tehd‰‰n kun dialogi on n‰ytetty
     */
    @Override
    public void handleShown() {
        kentta = Math.max(aputerapeutti.ekaKentta(), Math.min(kentta, aputerapeutti.getKenttia()-1));
        edits[kentta].requestFocus();
    
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
     * K‰sitell‰‰n j‰seneen tullut muutos
     * @param edit muuttunut kentt‰
     */
    protected void kasitteleMuutosTerapeuttiin(TextField edit) {
        if (terapeuttiKohdalla == null) return;
        int k = getFieldId(edit,aputerapeutti.ekaKentta());
        String s = edit.getText();
        String virhe = null;        
        virhe = terapeuttiKohdalla.aseta(k,s);        
       
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        } 
    }
     
    
    /**
     * N‰ytet‰‰n j‰senen tiedot TextField komponentteihin
     * @param edits taulukko TextFieldeist‰ johon n‰ytet‰‰n
     * @param terapeutti n‰ytett‰v‰ asiakkaan
     */
    public static void naytaTerapeutti(TextField[] edits, Terapeutti terapeutti) {
        if (terapeutti == null) return;
        for (int k = terapeutti.ekaKentta(); k < terapeutti.getKenttia(); k++) {
            edits[k].setText(terapeutti.anna(k));
        } 
       
    }
    
    
    /**
     * Luodaan j‰senen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mit‰ dataan n‰ytet‰‰n oletuksena
     * @param kentta mik‰ kentt‰ saa fokuksen kun n‰ytet‰‰n
     * @return null jos painetaan Cancel, muuten t‰ytetty tietue
     */
    public static Terapeutti kysyTerapeutti(Stage modalityStage, Terapeutti oletus, int kentta){ 
        return ModalController.<Terapeutti, MuokkaaPalveluController>showModal(
                    MuokkaaPalveluController.class.getResource("MuokkaaPalveluView.fxml"),
                    "Firma",
                    modalityStage, oletus,
                    ctrl -> ctrl.setKentta(kentta) 
                );
    }
    
}
