package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	//referencia para a cena
	private static Scene mainScene;
	@Override
	public void start(Stage primaryStage) {
		try { //instancia do objeto
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = loader.load();
			//ajustar a janela
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);

			
			
			 mainScene = new Scene(scrollPane);//cena principal
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Sample JavaFX application"); //titulo
			primaryStage.show(); //palco da cena
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//metodo para o mainscene ficar visivel nas outras classes
	public static Scene getMainScene() {
		return mainScene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
