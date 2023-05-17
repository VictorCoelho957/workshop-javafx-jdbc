package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerService {
	private SellerDao dao = DaoFactory.createSellerDao();
	public List<Seller> findAll(){
		return dao.findAll();
		
		
		
	}
	
	//salvar ou  atualizar objetos do banco de dados
	public void saveOrUpdate(Seller obj) {
		if(obj.getId()==null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
//metodo de remoção
	public void remove(Seller obj) {
		dao.deleteById(obj.getId());
	}
}
