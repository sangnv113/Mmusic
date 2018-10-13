
package com.example.WebProject.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WebProject.entity.Accessory;
import com.example.WebProject.repository.AccessoryRepository;

@Service
public class AccessoryServiceImp implements AccessoryService {
	
	@Autowired
    private AccessoryRepository accessoryRepository;
	 @PersistenceContext
	  private EntityManager em;
	
	  @Override
	    public Iterable<Accessory> findAll() {
	        return accessoryRepository.findAll();
	    }

	    @Override
	    public List<Accessory> search(String q) {
	        return accessoryRepository.findByNameContaining(q);
	    }

	    @Override
	    public Accessory findOne(int id) {
	        return accessoryRepository.findOne(id);
	    }

	    @Override
	    public void save(Accessory contact) {
	    	accessoryRepository.save(contact);
	    }

	    @Override
	    public void delete(int id) {
	    	accessoryRepository.delete(id);
	    }
	    @Override
	    public int count() {
	    	
	    	
	    	return (int)accessoryRepository.count();
	     
	    }
}
