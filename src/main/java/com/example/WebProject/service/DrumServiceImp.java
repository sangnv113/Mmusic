
package com.example.WebProject.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WebProject.entity.Drum;
import com.example.WebProject.repository.DrumRepository;

@Service
public class DrumServiceImp implements DrumService {
	
	@Autowired
    private DrumRepository drumRepository;
	 @PersistenceContext
	  private EntityManager em;
	
	  @Override
	    public Iterable<Drum> findAll() {
	        return drumRepository.findAll();
	    }

	    @Override
	    public List<Drum> search(String q) {
	        return drumRepository.findByNameContaining(q);
	    }

	    @Override
	    public Drum findOne(int id) {
	        return drumRepository.findOne(id);
	    }

	    @Override
	    public void save(Drum contact) {
	    	drumRepository.save(contact);
	    }

	    @Override
	    public void delete(int id) {
	    	drumRepository.delete(id);
	    }
	    @Override
	    public int count() {
	    	
	    	
	    	return (int)drumRepository.count();
	     
	    }
}
