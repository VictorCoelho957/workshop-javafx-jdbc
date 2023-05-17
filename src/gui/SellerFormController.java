package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exception.ValidationException;
import model.services.SellerService; 

//classe para contolorar as ações da classe department form design

public class SellerFormController implements Initializable{
	private List<DataChangeListener>dataChangeListeners = new ArrayList<>();
	
	private Seller entity;
	
	private SellerService service;
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity==null) {
			throw new IllegalStateException("Entity was null");
		}
		
		if (service==null) {
			throw new IllegalStateException("Entity was null");	
		}
		try {
		//pegar os dados das caixinhas dos formularios e instanciar
			entity=getFormData();
			service.saveOrUpdate(entity);
			//notificação de salvamento
			notifyDataChangeListeners();
			Utils.currentStage(event).close(); //fechar a janela
		} 
		 catch(ValidationException e) {
			 setErrorMessages(e.getErros());
		 }
		catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Seller getFormData() {
		Seller obj= new Seller();
		ValidationException exception= new ValidationException("Validation error ");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		if(txtName.getText()==null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Fild can't be empty");
		}
		obj.setName(txtName.getText());
		
		if(exception.getErros().size()>0) {
			throw exception;
		}
		return obj;
	}
	
	//metodo para receber a lista do chanlist
	
	public void subscribeDataChangeListerner(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close(); //fechar a janela

	}
	//metodo set do entity
	public void setSeller(Seller entity) {
		this.entity=entity;
	}
	
	//metodo set do service
	public void setSellerService(SellerService service) {
		this.service=service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
   
	//metodo para popular o quesionario
	public void updateFormData() {
		if (entity ==null ) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());

		
	}
	//metodo de mensagem de erro
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if(fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}
}
