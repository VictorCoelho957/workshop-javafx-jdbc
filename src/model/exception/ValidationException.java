package model.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private Map<String, String> errors= new HashMap<>();
	private static final long serialVersionUID = 1L;
	public ValidationException(String msg) {
		
	}
	
	//metodo get do error
	public Map<String, String> getErros(){
		return errors;
	}
	//adcionar um erro
	public void addError(String fildName, String errorMessage) {
		errors.put(fildName, errorMessage);
	}
}
