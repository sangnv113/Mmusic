
package com.example.WebProject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.model.UkuleleInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.UkuleleService;
import com.example.WebProject.service.ProducerService;
@Transactional
@Repository
public class UkuleleFilterDao {
	 @Autowired
	    private UkuleleService ukuleleService;
	  @Autowired
	    private CategoryService categoryService;
	 
		@Autowired
		
		private ProducerService producerService;
	
		
	//FILLTER
		//find with category
public List<UkuleleInfo> filterCategory(int category, List<UkuleleInfo> listukulele) {
    	
        try {
        	List<UkuleleInfo> listukulele2=new ArrayList<UkuleleInfo>();
        	
        	for(int i=0; i<listukulele.size(); i++){
        		if(listukulele.get(i).getCategory().equals(categoryService.findOne(category).getCategory())){
        			listukulele2.add(listukulele.get(i));
        		}
        	}
            return listukulele2;
        	} catch (NoResultException e) {
            return null;
        }
    }
public List<UkuleleInfo> filterProducer(int producer, List<UkuleleInfo> listukulele) {
	
    try {
    	List<UkuleleInfo> listukulele2=new ArrayList<UkuleleInfo>();
    	
    	for(int i=0; i<listukulele.size(); i++){
    		if(listukulele.get(i).getProducer().equals(producerService.findOne(producer).getName())){
    			listukulele2.add(listukulele.get(i));
    		}
    	}
        return listukulele2;
    	} catch (NoResultException e) {
        return null;
    }
}

//fill <=5 producer
	public List<Producer> list5Producer() {
	
	  try {
        	List<Producer> listproducer=new ArrayList<Producer>();
        	List<Producer> producer=(List<Producer>) producerService.findByIdprContaining(WebProjectApplication.filterUkulele);   
        
        	
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
	
	public List<UkuleleInfo> filterPrice(int price1,int price2, List<UkuleleInfo> listukulele) {
		
	    try {
	    	List<UkuleleInfo> listukulele2=new ArrayList<UkuleleInfo>();
	    	int n;
	    	for(int i=0; i<listukulele.size(); i++){
	    		n=Integer.parseInt(ukuleleService.findOne(listukulele.get(i).getId()).getGiasaugiam());
	    		if(n>=price1&&n<=price2){
	    			listukulele2.add(listukulele.get(i));
	    		}
	    	}
	        return listukulele2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	public List<UkuleleInfo> filterPrice(int price1, List<UkuleleInfo> listukulele) {
		
	    try {
	    	List<UkuleleInfo> listukulele2=new ArrayList<UkuleleInfo>();
	    	int n;
	    	for(int i=0; i<listukulele.size(); i++){
	    		n=Integer.parseInt(ukuleleService.findOne(listukulele.get(i).getId()).getGiasaugiam());
	    		if(n>=price1){
	    			listukulele2.add(listukulele.get(i));
	    		}
	    	}
	        return listukulele2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	
}
