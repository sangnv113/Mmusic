
package com.example.WebProject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.model.DrumInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.DrumService;
import com.example.WebProject.service.ProducerService;
@Transactional
@Repository
public class DrumFilterDao {
	 @Autowired
	    private DrumService drumService;
	  @Autowired
	    private CategoryService categoryService;
	 
		@Autowired
		
		private ProducerService producerService;
	
		
	//FILLTER
		//find with category
public List<DrumInfo> filterCategory(int category, List<DrumInfo> listdrum) {
    	
        try {
        	List<DrumInfo> listdrum2=new ArrayList<DrumInfo>();
        	
        	for(int i=0; i<listdrum.size(); i++){
        		if(listdrum.get(i).getCategory().equals(categoryService.findOne(category).getCategory())){
        			listdrum2.add(listdrum.get(i));
        		}
        	}
            return listdrum2;
        	} catch (NoResultException e) {
            return null;
        }
    }
public List<DrumInfo> filterProducer(int producer, List<DrumInfo> listdrum) {
	
    try {
    	List<DrumInfo> listdrum2=new ArrayList<DrumInfo>();
    	
    	for(int i=0; i<listdrum.size(); i++){
    		if(listdrum.get(i).getProducer().equals(producerService.findOne(producer).getName())){
    			listdrum2.add(listdrum.get(i));
    		}
    	}
        return listdrum2;
    	} catch (NoResultException e) {
        return null;
    }
}

//fill <=5 producer
	public List<Producer> list5Producer() {
	
	  try {
        	List<Producer> listproducer=new ArrayList<Producer>();
        	List<Producer> producer=(List<Producer>) producerService.findByIdprContaining(WebProjectApplication.filterDrum);   
        
        	
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
	
	public List<DrumInfo> filterPrice(int price1,int price2, List<DrumInfo> listdrum) {
		
	    try {
	    	List<DrumInfo> listdrum2=new ArrayList<DrumInfo>();
	    	int n;
	    	for(int i=0; i<listdrum.size(); i++){
	    		n=Integer.parseInt(drumService.findOne(listdrum.get(i).getId()).getGiasaugiam());
	    		if(n>=price1&&n<=price2){
	    			listdrum2.add(listdrum.get(i));
	    		}
	    	}
	        return listdrum2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	public List<DrumInfo> filterPrice(int price1, List<DrumInfo> listdrum) {
		
	    try {
	    	List<DrumInfo> listdrum2=new ArrayList<DrumInfo>();
	    	int n;
	    	for(int i=0; i<listdrum.size(); i++){
	    		n=Integer.parseInt(drumService.findOne(listdrum.get(i).getId()).getGiasaugiam());
	    		if(n>=price1){
	    			listdrum2.add(listdrum.get(i));
	    		}
	    	}
	        return listdrum2;
	    	} catch (NoResultException e) {
	        return null;
	    }
	}
	
}
