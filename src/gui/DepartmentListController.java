package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {
	private DepartmentService service; //declaração do metodo departmentservicer
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumId;
	
	@FXML
	private TableColumn<Department, String> tableColumName;
	 
	
	@FXML
	private Button btNew;
	
	//variavel para carrehar os departamentos
	//@FXML
	private ObservableList<Department> obsList;
	
	
	//eventos do botão
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage= Utils. currentStage(event);
		Department obj =new Department();
		createDialogForm(obj,"/gui/DepartmentForm.fxml", parentStage);
	}
	
	//metodo set para ter dependencia do department service
	public void setDepartmentService(DepartmentService service) {
		this.service=service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//iniciar componente nas tabelas
		
		initializeNodes();
		
	}
	//padrao javafx para inciar o compartamento das colunas
	private void initializeNodes() {
		tableColumId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumName.setCellValueFactory(new PropertyValueFactory<>("name"));
		//completar a tableview, o tableview acompannha o tamanho da janela
		
		Stage stage= (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());

		
	}
	//metodo para carregar os departamentos e passar para obslist
	public void updateTableView() {
		if(service==null) {
			throw new IllegalStateException("Service was null");	
		}
		List<Department> list=service.findAll();
		obsList= FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}
	
	//metodo paa abrir um formulario de novo cadastro um novo departamneto
	private void createDialogForm (Department obj, String absoluteName , Stage parentStage) {
		try {
			FXMLLoader loader= new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane=loader.load();
			
			//referencia para o controlador
			DepartmentFormController controller =loader.getController();
			controller.setDepartment(obj);
			controller.setDepartmentService(new DepartmentService());
			controller.updateFormData();
			Stage dialogStage= new Stage();
			dialogStage.setTitle("Enter Department data");
			dialogStage.setScene(new Scene(pane)); ;//janela
			dialogStage.setResizable(false); //redimensionar
			dialogStage.initOwner(parentStage);//stage pai da janela
			dialogStage.initModality(Modality.WINDOW_MODAL);//deixar janela modal
			dialogStage.showAndWait();

			
					
		}
		catch (IOException e) {
			Alerts.showAlert("IO EXception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
