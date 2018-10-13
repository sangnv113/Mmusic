package com.example.WebProject.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WebProject.entity.Guitar;
import com.example.WebProject.repository.GuitarRepository;

@Service
public class GuitarServiceImp implements GuitarService {
	
	@Autowired
    private GuitarRepository guitarRepository;
	 @PersistenceContext
	  private EntityManager em;
	
	  @Override
	    public Iterable<Guitar> findAll() {
	        return guitarRepository.findAll();
	    }

	    @Override
	    public List<Guitar> search(String q) {
	        return guitarRepository.findByNameContaining(q);
	    }

	    @Override
	    public Guitar findOne(int id) {
	        return guitarRepository.findOne(id);
	    }

	    @Override
	    public void save(Guitar contact) {
	    	guitarRepository.save(contact);
	    }

	    @Override
	    public void delete(int id) {
	    	guitarRepository.delete(id);
	    }
	    @Override
	    public int count() {
	    	
	    	
	    	return (int)guitarRepository.count();
	     
	    }
}
