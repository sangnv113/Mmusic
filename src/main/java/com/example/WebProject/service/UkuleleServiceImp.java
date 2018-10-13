
package com.example.WebProject.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WebProject.entity.Ukulele;
import com.example.WebProject.repository.UkuleleRepository;

@Service
public class UkuleleServiceImp implements UkuleleService {
	
	@Autowired
    private UkuleleRepository ukuleleRepository;
	 @PersistenceContext
	  private EntityManager em;
	
	  @Override
	    public Iterable<Ukulele> findAll() {
	        return ukuleleRepository.findAll();
	    }

	    @Override
	    public List<Ukulele> search(String q) {
	        return ukuleleRepository.findByNameContaining(q);
	    }

	    @Override
	    public Ukulele findOne(int id) {
	        return ukuleleRepository.findOne(id);
	    }

	    @Override
	    public void save(Ukulele contact) {
	    	ukuleleRepository.save(contact);
	    }

	    @Override
	    public void delete(int id) {
	    	ukuleleRepository.delete(id);
	    }
	    @Override
	    public int count() {
	    	
	    	
	    	return (int)ukuleleRepository.count();
	     
	    }
}
