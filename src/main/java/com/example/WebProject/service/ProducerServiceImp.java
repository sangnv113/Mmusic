package com.example.WebProject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WebProject.entity.Producer;
import com.example.WebProject.repository.ProducerRepository;

@Service
public class ProducerServiceImp implements ProducerService {
	
	@Autowired
    private ProducerRepository producerRepository;

	
	  @Override
	    public Iterable<Producer> findAll() {
	        return producerRepository.findAll();
	    }

	    @Override
	    public List<Producer> search(String q) {
	        return producerRepository.findByNameContaining(q);
	    }

	    @Override
	    public Producer findOne(int id) {
	        return producerRepository.findOne(id);
	    }

	    @Override
	    public void save(Producer contact) {
	    	producerRepository.save(contact);
	    }

	    @Override
	    public void delete(int id) {
	    	producerRepository.delete(id);
	    }
	    @Override
	    public List<Producer> findByNameContaining(String q) {
	    	return producerRepository.findByNameContaining(q);
	    }
	    @Override
	    public List<Producer> findByIdprContaining(int q) {
	    	List<Producer> fa=(ArrayList<Producer>) producerRepository.findAll();
	    	List<Producer>result=new ArrayList<Producer>();
	    	for(int i=0; i<fa.size(); i++){
	    		if(fa.get(i).getIdpr()==q){
	    			result.add(fa.get(i));
	    		}
	    	}
	    	return result;
	    }
}
