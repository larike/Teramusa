package fxTeramusa;
	
import firma.Firma;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;

import javafx.fxml.FXMLLoader;

/**
 * 
 * @author Lari Kettunen
 * @version 6.6.2017
 *
 */
public class TeramusaMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("TeramusaGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final TeramusaGUIController firmaCtrl = (TeramusaGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Teramusa.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Firma");
            
            
          //  firmaCtrl.avaa();
            
            // Platform.setImplicitExit(false); // tätä ei kai saa laittaa

            primaryStage.setOnCloseRequest((event) -> {
                    if ( !firmaCtrl.voikoSulkea() ) event.consume();
                });
            
            Firma firma = new Firma();
            firmaCtrl.setFirma(firma);
            
            primaryStage.show();
            
            Application.Parameters params = getParameters();
                if ( params.getRaw().size() > 0 )
                firmaCtrl.lueTiedosto(params.getRaw().get(0));
            else
                if ( !firmaCtrl.avaa() ) Platform.exit();   
		/*	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("TeramusaGUIView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("Teramusa.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show(); */
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
