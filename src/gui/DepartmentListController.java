package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	//metodo set para ter dependencia do department service
	public void setDepartmentService(DepartmentService service) {
		this.service=service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//inixiar componente nas tabelas
		
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
	

}
