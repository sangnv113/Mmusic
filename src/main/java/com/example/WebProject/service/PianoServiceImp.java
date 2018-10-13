
package com.example.WebProject.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WebProject.entity.Piano;
import com.example.WebProject.repository.PianoRepository;

@Service
public class PianoServiceImp implements PianoService {
	
	@Autowired
    private PianoRepository pianoRepository;
	 @PersistenceContext
	  private EntityManager em;
	
	  @Override
	    public Iterable<Piano> findAll() {
	        return pianoRepository.findAll();
	    }

	    @Override
	    public List<Piano> search(String q) {
	        return pianoRepository.findByNameContaining(q);
	    }

	    @Override
	    public Piano findOne(int id) {
	        return pianoRepository.findOne(id);
	    }

	    @Override
	    public void save(Piano contact) {
	    	pianoRepository.save(contact);
	    }

	    @Override
	    public void delete(int id) {
	    	pianoRepository.delete(id);
	    }
	    @Override
	    public int count() {
	    	
	    	
	    	return (int)pianoRepository.count();
	     
	    }
}
