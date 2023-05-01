package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	//chamada dA DEPARTMENT LIST
	@FXML
	public void onMenuItemDepartmentAction() {
		//função para inicilizar o novo comando contido no departmentlist
		loadView("/gui/DepartmentList.fxml",(DepartmentListController controller)-> { 
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();});

	}
	@FXML
	public void onMenuItemAboutAction() {
		//essa janela nao possui açãoa alguuma
		loadView("/gui/About.fxml", (x->{}));
	}
	
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

	//funcçao  para abir uma outra tela
	private synchronized <T >void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader= new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox=loader.load();
			//instancia da cena
			Scene mainScene=Main.getMainScene();
			VBox mainVBox=(VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//exibir os filhos da Vbox
			Node mainMenu= mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			//adiciona a lista
			mainVBox.getChildren().addAll(newVBox.getChildren());
			//ativação ou iniacilação, passam qualquer metodo como argumneto de controller, seja departmentservice ou about
			T controller=loader.getController();
			initializingAction.accept(controller);
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading", e.getMessage(), AlertType.ERROR);
		}
		
		}
	
	
	//chamada dA DEPARTMENT LIST
	
}
