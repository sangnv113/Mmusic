package com.example.WebProject.dao;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.WebProject.entity.Guitar;
import com.example.WebProject.entity.Ma;
import com.example.WebProject.model.GuitarInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.GuitarService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;
@Transactional
@Repository
public class GuitarDao {
	  @Autowired
	    private GuitarService guitarService;
	  @Autowired
	    private CategoryService categoryService;
	  @Autowired
		private ColorService colorService;
		@Autowired
		
		private ProducerService producerService;
		
		@Autowired
		
		private MaService maService;
		//xu ly in tien
	    public GuitarInfo findGuitarInfo(int id) {
	    	
	        try {
	        	Guitar gt=guitarService.findOne(id);
	            if(gt==null){
	            	return null;
	            }
	         
	            GuitarInfo gtinfo= new GuitarInfo(gt.getId(), gt.getName(),  
	            		gt.getCategoryid().getCategory(), gt.getProducerid().getName(),
	            		gt.getColorid().getName(), (int)gt.getRate(), gt.getSoluong(),
	            		intien(gt.getGia()), gt.getSoluot(),gt.getGiamgia(), intien( gt.getGiasaugiam()), gt.getVisits());
	            if(gt.getDatepr()!=null){
	      	    	   gtinfo.setDatepr(gt.getDatepr().toString());
	      	       }
	           
	            return gtinfo;
	        } catch (NoResultException e) {
	            return null;
	        }
	    }
	    //khong xu ly in tien
	    public GuitarInfo findGuitarInfoSave(int id) {
	    	
	        try {
	        	Guitar gt=guitarService.findOne(id);
	            if(gt==null){
	            	return null;
	            }
	            GuitarInfo gtinfo= new GuitarInfo(gt.getId(), gt.getName(),  
	            		gt.getCategoryid().getCategory(), gt.getProducerid().getName(), gt.getColorid().getName(), 
	            		(int)gt.getRate(), gt.getSoluong(),gt.getGia(), gt.getSoluot(),
	            		gt.getGiamgia(),gt.getGiasaugiam());
	            if(gt.getDatepr()!=null){
	      	    	   gtinfo.setDatepr(gt.getDatepr().toString());
	      	       }
	            
	            return gtinfo;
	        } catch (NoResultException e) {
	            return null;
	        }
	    }
	    //find all
  		public List<GuitarInfo> findAllGuitarInfo() {
	    	
	        try {
	        	List<GuitarInfo> listguitar=new ArrayList<GuitarInfo>();
	        	List<Guitar> guitar=(List<Guitar>) guitarService.findAll();
	        	
	        	Guitar gt=new Guitar();
	        	GuitarInfo gtinfo;
	        	
	        	for(int i=0; i<guitar.size(); i++){
	        		 gt=guitar.get(i);
	        		 gtinfo= new GuitarInfo(gt.getId(), gt.getName(),  
	 	            		gt.getCategoryid().getCategory(), gt.getProducerid().getName(), gt.getColorid().getName(),
	 	            		(int)gt.getRate(), gt.getSoluong(),intien(gt.getGia()), gt.getSoluot(),
	 	            		gt.getGiamgia(), intien( gt.getGiasaugiam()));
	      	       if(gt.getDatepr()!=null){
	      	    	   gtinfo.setDatepr(gt.getDatepr().toString());
	      	       }
	      	   
	        		listguitar.add(gtinfo);
	        		
	        			}
	        	
	        	
	            return listguitar;
	        	} catch (NoResultException e) {
	            return null;
	        }
	    }
  	
//find for index.html
  		public List<GuitarInfo> listGuitarInfo() {
	    	
  		  try {
	        	List<GuitarInfo> listguitar=new ArrayList<GuitarInfo>();
	        	List<Guitar> guitar=(List<Guitar>) guitarService.findAll();
	        	
	        	Guitar gt=new Guitar();
	        	GuitarInfo gtinfo;
	        	
	        	for(int i=guitar.size()-1; i>=guitar.size()-4; i--){
	        		
	        		
	        			 if(i<0){
	        			 return listguitar;
	        		 }
	        			 gt=guitar.get(i);
	        			 if(gt==null){
		        			 return listguitar;
		        		 }
	        		 gtinfo= new GuitarInfo(gt.getId(), gt.getName(),  
	 	            		gt.getCategoryid().getCategory(), gt.getProducerid().getName(), gt.getColorid().getName(),
	 	            		(int)gt.getRate(), gt.getSoluong(),intien(gt.getGia()), gt.getSoluot(),
	 	            		gt.getGiamgia(), intien( gt.getGiasaugiam()));
	      	       if(gt.getDatepr()!=null){
	      	    	   gtinfo.setDatepr(gt.getDatepr().toString());
	      	       }
	      	   
	        		listguitar.add(gtinfo);
	        		
	        			}
	        	
	        	
	            return listguitar;
	        } catch (NoResultException e) {
	            return null;
	        }
	    }
  		
  	
  		public void save(GuitarInfo guitarInfo) {
  			//create object Guitar from guitarinfo
  			final byte[] guitarImage=guitarService.findOne(guitarInfo.getId()).getImage();
	  Guitar gt=new Guitar(guitarInfo.getId(),guitarInfo.getName(),  categoryService.findByCategoryContaining(guitarInfo.getCategory()).get(0),
			  producerService.findByNameContaining(guitarInfo.getProducer()).get(0),colorService.findByNameContaining(guitarInfo.getColor()).get(0)
			  , guitarInfo.getSoluong(), guitarInfo.getGia(),guitarInfo.getGiamgia(),
			  GiaSauGiam(Integer.parseInt(guitarInfo.getGia()),guitarInfo.getGiamgia()));
	  //set date
	  if(guitarInfo.getDatepr().toString().trim().equals("")==false){
			
		
			String date = guitarInfo.getDatepr().toString();
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date startDate;
			try {
				startDate = sdf.parse(date);
				java.sql.Date sqlDate = new java.sql.Date(startDate.getTime());	
				gt.setDatepr(sqlDate);
				/*System.out.println("ok");*/
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else
			gt.setDatepr(null);
	  //set image
	  
	  
	  if (guitarInfo.getFileData().isEmpty()==false) {
          byte[] image = null;
          try {
              image = guitarInfo.getFileData().getBytes();
          } catch (IOException e) {
          }
          if (image != null && image.length > 0) {
        	  gt.setImage(image);
          }
      }else
    	  gt.setImage(guitarImage);
	
	
	  
	
	  guitarService.save(gt);
	  
  }
  		public void SaveCreate(GuitarInfo guitarInfo) {
  			Ma ma=maService.findOne(1);
  			//int entity Guitar from guitarInfo-non gianiemyet, rate, status
  			Guitar gt=new Guitar(ma.getGuitar(),guitarInfo.getName(),  categoryService.findByCategoryContaining(guitarInfo.getCategory()).get(0),
  				  producerService.findByNameContaining(guitarInfo.getProducer()).get(0),colorService.findByNameContaining(guitarInfo.getColor()).get(0),0, 0
  				, guitarInfo.getSoluong(), guitarInfo.getGia(),guitarInfo.getGiamgia(),
				  GiaSauGiam(Integer.parseInt(guitarInfo.getGia()),guitarInfo.getGiamgia()), 0);
  			 //set date
  		  if(guitarInfo.getDatepr().toString().trim().equals("")==false){
  				
  			
  				String date = guitarInfo.getDatepr().toString();
  				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  				java.util.Date startDate;
  				try {
  					startDate = sdf.parse(date);
  					java.sql.Date sqlDate = new java.sql.Date(startDate.getTime());	
  					gt.setDatepr(sqlDate);
  					/*System.out.println("ok");*/
  				
  				} catch (ParseException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
  				
  			}else
  				gt.setDatepr(null);
  			 //set image
  		  if (guitarInfo.getFileData() != null) {
  	          byte[] image = null;
  	          try {
  	              image = guitarInfo.getFileData().getBytes();
  	          } catch (IOException e) {
  	          }
  	          if (image != null && image.length > 0) {
  	        	  gt.setImage(image);
  	          }
  	      }
  		
  			guitarService.save(gt);
  		
  		  
  	
  		
  		Ma vma=new Ma(1,ma.getGuitar()+1, ma.getPiano(), ma.getUkulele(),
  				ma.getDrum(), ma.getFlute(), ma.getAccessory(), ma.getProducer(),
  				ma.getColor() );
  		maService.save(vma);
  		
  	  }
  static public String GiaSauGiam(int gia, int giam){
	  return String.valueOf(gia-(gia*giam)/100);
	 
	  
  }
  
  
 
  
  static public String intien(String sotien)
  {
	  sotien=sotien.trim();
      int n = sotien.length() - 1;
      int s = 0;
      String tienphu="";
      while (n >= 0)
      {
          if (s == 3)
          {
              s = 0;
              tienphu = tienphu + ",";
          }
          tienphu = tienphu + sotien.charAt(n);
          s++;
          n--;

      }
      sotien = "";
      for (int i = tienphu.length() - 1; i >= 0; i--)
      {
          sotien = sotien + tienphu.charAt(i);
      }
      if (sotien.charAt(0) == '-' && sotien.charAt(1) == ',')
      {
    	  String st = sotien;
          sotien = "-";
          for (int i = 2; i <= st.length() - 1; i++)
          {
              sotien = sotien + st.charAt(i);
          }
      }
      return sotien+" VND";
  }
  
  
}
