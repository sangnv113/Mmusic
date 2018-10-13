
package com.example.WebProject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.model.AccessoryInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.AccessoryService;
import com.example.WebProject.service.ProducerService;
@Transactional
@Repository
public class AccessoryFilterDao {
	 @Autowired
	    private AccessoryService accessoryService;
	  @Autowired
	    private CategoryService categoryService;
	 
		@Autowired
		
		private ProducerService producerService;
	
		
	//FILLTER
		//find with category
public List<AccessoryInfo> filterCategory(int category, List<AccessoryInfo> listaccessory) {
    	
        try {
        	List<AccessoryInfo> listaccessory2=new ArrayList<AccessoryInfo>();
        	
        	for(int i=0; i<listaccessory.size(); i++){
        		if(listaccessory.get(i).getCategory().equals(categoryService.findOne(category).getCategory())){
        			listaccessory2.add(listaccessory.get(i));
        		}
        	}
            return listaccessory2;
        	} catch (NoResultException e) {
            return null;
        }
    }
public List<AccessoryInfo> filterProducer(int producer, List<AccessoryInfo> listaccessory) {
	
    try {
    	List<AccessoryInfo> listaccessory2=new ArrayList<AccessoryInfo>();
    	
    	for(int i=0; i<listaccessory.size(); i++){
    		if(listaccessory.get(i).getProducer().equals(producerService.findOne(producer).getName())){
    			listaccessory2.add(listaccessory.get(i));
    		}
    	}
        return listaccessory2;
    	} catch (NoResultException e) {
        return null;
    }
}

//fill <=5 producer
	public List<Producer> list5Producer() {
	
	  try {
        	List<Producer> listproducer=new ArrayList<Producer>();
        	List<Producer> producer=(List<Producer>) producerService.findByIdprContaining(WebProjectApplication.filterAccessory);   
        
        	
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
	
	public List<AccessoryInfo> filterPrice(int price1,int price2, List<AccessoryInfo> listaccessory) {
		
	    try {
	    	List<AccessoryInfo> listaccessory2=new ArrayList<AccessoryInfo>();
	    	int n;
	    	for(int i=0; i<listaccessory.size(); i++){
	    		n=Integer.parseInt(accessoryService.findOne(listaccessory.get(i).getId()).getGiasaugiam());
	    		if(n>=price1&&n<=price2){
	    			listaccessory2.add(listaccessory.get(i));
	    		}
	    	}
	        return listaccessory2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	public List<AccessoryInfo> filterPrice(int price1, List<AccessoryInfo> listaccessory) {
		
	    try {
	    	List<AccessoryInfo> listaccessory2=new ArrayList<AccessoryInfo>();
	    	int n;
	    	for(int i=0; i<listaccessory.size(); i++){
	    		n=Integer.parseInt(accessoryService.findOne(listaccessory.get(i).getId()).getGiasaugiam());
	    		if(n>=price1){
	    			listaccessory2.add(listaccessory.get(i));
	    		}
	    	}
	        return listaccessory2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	
}
