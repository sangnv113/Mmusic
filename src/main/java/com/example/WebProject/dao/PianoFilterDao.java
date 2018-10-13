
package com.example.WebProject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.model.PianoInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.PianoService;
import com.example.WebProject.service.ProducerService;
@Transactional
@Repository
public class PianoFilterDao {
	 @Autowired
	    private PianoService pianoService;
	  @Autowired
	    private CategoryService categoryService;
	 
		@Autowired
		
		private ProducerService producerService;
	
		
	//FILLTER
		//find with category
public List<PianoInfo> filterCategory(int category, List<PianoInfo> listpiano) {
    	
        try {
        	List<PianoInfo> listpiano2=new ArrayList<PianoInfo>();
        	
        	for(int i=0; i<listpiano.size(); i++){
        		if(listpiano.get(i).getCategory().equals(categoryService.findOne(category).getCategory())){
        			listpiano2.add(listpiano.get(i));
        		}
        	}
            return listpiano2;
        	} catch (NoResultException e) {
            return null;
        }
    }
public List<PianoInfo> filterProducer(int producer, List<PianoInfo> listpiano) {
	
    try {
    	List<PianoInfo> listpiano2=new ArrayList<PianoInfo>();
    	
    	for(int i=0; i<listpiano.size(); i++){
    		if(listpiano.get(i).getProducer().equals(producerService.findOne(producer).getName())){
    			listpiano2.add(listpiano.get(i));
    		}
    	}
        return listpiano2;
    	} catch (NoResultException e) {
        return null;
    }
}

//fill <=5 producer
	public List<Producer> list5Producer() {
	
	  try {
        	List<Producer> listproducer=new ArrayList<Producer>();
        	List<Producer> producer=(List<Producer>) producerService.findByIdprContaining(WebProjectApplication.filterPiano);   
        
        	
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
	
	public List<PianoInfo> filterPrice(int price1,int price2, List<PianoInfo> listpiano) {
		
	    try {
	    	List<PianoInfo> listpiano2=new ArrayList<PianoInfo>();
	    	int n;
	    	for(int i=0; i<listpiano.size(); i++){
	    		n=Integer.parseInt(pianoService.findOne(listpiano.get(i).getId()).getGiasaugiam());
	    		if(n>=price1&&n<=price2){
	    			listpiano2.add(listpiano.get(i));
	    		}
	    	}
	        return listpiano2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	public List<PianoInfo> filterPrice(int price1, List<PianoInfo> listpiano) {
		
	    try {
	    	List<PianoInfo> listpiano2=new ArrayList<PianoInfo>();
	    	int n;
	    	for(int i=0; i<listpiano.size(); i++){
	    		n=Integer.parseInt(pianoService.findOne(listpiano.get(i).getId()).getGiasaugiam());
	    		if(n>=price1){
	    			listpiano2.add(listpiano.get(i));
	    		}
	    	}
	        return listpiano2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	
}
