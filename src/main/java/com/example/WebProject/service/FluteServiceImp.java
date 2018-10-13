
package com.example.WebProject.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WebProject.entity.Flute;
import com.example.WebProject.repository.FluteRepository;

@Service
public class FluteServiceImp implements FluteService {
	
	@Autowired
    private FluteRepository fluteRepository;
	 @PersistenceContext
	  private EntityManager em;
	
	  @Override
	    public Iterable<Flute> findAll() {
	        return fluteRepository.findAll();
	    }

	    @Override
	    public List<Flute> search(String q) {
	        return fluteRepository.findByNameContaining(q);
	    }

	    @Override
	    public Flute findOne(int id) {
	        return fluteRepository.findOne(id);
	    }

	    @Override
	    public void save(Flute contact) {
	    	fluteRepository.save(contact);
	    }

	    @Override
	    public void delete(int id) {
	    	fluteRepository.delete(id);
	    }
	    @Override
	    public int count() {
	    	
	    	
	    	return (int)fluteRepository.count();
	     
	    }
}
