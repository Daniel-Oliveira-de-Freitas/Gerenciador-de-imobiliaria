package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("ImoveisTela.fxml"));
			Scene scene = new Scene(root);
			primaryStage.getIcons().add(new Image("file:///C:/Users/danie/eclipse-workspace/SistemaImobiliario/imagens/shutterstock_1082369060.jpg"));
			primaryStage.setScene(scene);
			primaryStage.setTitle("SISTEMA IMOBILIARIO GP3");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
