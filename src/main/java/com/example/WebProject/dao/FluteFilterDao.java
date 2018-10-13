
package com.example.WebProject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.model.FluteInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.FluteService;
import com.example.WebProject.service.ProducerService;
@Transactional
@Repository
public class FluteFilterDao {
	 @Autowired
	    private FluteService fluteService;
	  @Autowired
	    private CategoryService categoryService;
	 
		@Autowired
		
		private ProducerService producerService;
	
		
	//FILLTER
		//find with category
public List<FluteInfo> filterCategory(int category, List<FluteInfo> listflute) {
    	
        try {
        	List<FluteInfo> listflute2=new ArrayList<FluteInfo>();
        	
        	for(int i=0; i<listflute.size(); i++){
        		if(listflute.get(i).getCategory().equals(categoryService.findOne(category).getCategory())){
        			listflute2.add(listflute.get(i));
        		}
        	}
            return listflute2;
        	} catch (NoResultException e) {
            return null;
        }
    }
public List<FluteInfo> filterProducer(int producer, List<FluteInfo> listflute) {
	
    try {
    	List<FluteInfo> listflute2=new ArrayList<FluteInfo>();
    	
    	for(int i=0; i<listflute.size(); i++){
    		if(listflute.get(i).getProducer().equals(producerService.findOne(producer).getName())){
    			listflute2.add(listflute.get(i));
    		}
    	}
        return listflute2;
    	} catch (NoResultException e) {
        return null;
    }
}

//fill <=5 producer
	public List<Producer> list5Producer() {
		 
	  try {
        	List<Producer> listproducer=new ArrayList<Producer>();
        	List<Producer> producer=(List<Producer>) producerService.findByIdprContaining(WebProjectApplication.filterFlute);   
        	
        	
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
	
	public List<FluteInfo> filterPrice(int price1,int price2, List<FluteInfo> listflute) {
		
	    try {
	    	List<FluteInfo> listflute2=new ArrayList<FluteInfo>();
	    	int n;
	    	for(int i=0; i<listflute.size(); i++){
	    		n=Integer.parseInt(fluteService.findOne(listflute.get(i).getId()).getGiasaugiam());
	    		if(n>=price1&&n<=price2){
	    			listflute2.add(listflute.get(i));
	    		}
	    	}
	        return listflute2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	public List<FluteInfo> filterPrice(int price1, List<FluteInfo> listflute) {
		
	    try {
	    	List<FluteInfo> listflute2=new ArrayList<FluteInfo>();
	    	int n;
	    	for(int i=0; i<listflute.size(); i++){
	    		n=Integer.parseInt(fluteService.findOne(listflute.get(i).getId()).getGiasaugiam());
	    		if(n>=price1){
	    			listflute2.add(listflute.get(i));
	    		}
	    	}
	        return listflute2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	
}
