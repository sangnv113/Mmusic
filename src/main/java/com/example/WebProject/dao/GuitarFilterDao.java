package com.example.WebProject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.model.GuitarInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.GuitarService;
import com.example.WebProject.service.ProducerService;
@Transactional
@Repository
public class GuitarFilterDao {
	 @Autowired
	    private GuitarService guitarService;
	  @Autowired
	    private CategoryService categoryService;
	 
		@Autowired
		
		private ProducerService producerService;
	
		
	//FILLTER
		//find with category
public List<GuitarInfo> filterCategory(int category, List<GuitarInfo> listguitar) {
    	
        try {
        	List<GuitarInfo> listguitar2=new ArrayList<GuitarInfo>();
        	
        	for(int i=0; i<listguitar.size(); i++){
        		if(listguitar.get(i).getCategory().equals(categoryService.findOne(category).getCategory())){
        			listguitar2.add(listguitar.get(i));
        		}
        	}
            return listguitar2;
        	} catch (NoResultException e) {
            return null;
        }
    }
public List<GuitarInfo> filterProducer(int producer, List<GuitarInfo> listguitar) {
	
    try {
    	List<GuitarInfo> listguitar2=new ArrayList<GuitarInfo>();
    	
    	for(int i=0; i<listguitar.size(); i++){
    		if(listguitar.get(i).getProducer().equals(producerService.findOne(producer).getName())){
    			listguitar2.add(listguitar.get(i));
    		}
    	}
        return listguitar2;
    	} catch (NoResultException e) {
        return null;
    }
}

//fill <=5 producer
	public List<Producer> list5Producer() {
	
	  try {
        	List<Producer> listproducer=new ArrayList<Producer>();
        	List<Producer> producer=(List<Producer>) producerService.findByIdprContaining(WebProjectApplication.filterGuitar);
       
        	
        	for(int i=0; i<5; i++){
        		if(i>=producer.size()){
        			 return listproducer;
        		 }
        		
        		listproducer.add(producer.get(i));
        		
        			}
            return listproducer;
        } catch (NoResultException e) {
            return null;
        }
    }
	
	public List<GuitarInfo> filterPrice(int price1,int price2, List<GuitarInfo> listguitar) {
		
	    try {
	    	List<GuitarInfo> listguitar2=new ArrayList<GuitarInfo>();
	    	int n;
	    	for(int i=0; i<listguitar.size(); i++){
	    		n=Integer.parseInt(guitarService.findOne(listguitar.get(i).getId()).getGiasaugiam());
	    		if(n>=price1&&n<=price2){
	    			listguitar2.add(listguitar.get(i));
	    		}
	    	}
	        return listguitar2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	public List<GuitarInfo> filterPrice(int price1, List<GuitarInfo> listguitar) {
		
	    try {
	    	List<GuitarInfo> listguitar2=new ArrayList<GuitarInfo>();
	    	int n;
	    	for(int i=0; i<listguitar.size(); i++){
	    		n=Integer.parseInt(guitarService.findOne(listguitar.get(i).getId()).getGiasaugiam());
	    		if(n>=price1){
	    			listguitar2.add(listguitar.get(i));
	    		}
	    	}
	        return listguitar2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	
}
