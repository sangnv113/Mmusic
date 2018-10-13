
package com.example.WebProject.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.dao.UkuleleDao;
import com.example.WebProject.dao.UkuleleFilterDao;
import com.example.WebProject.model.UkuleleFilter;
import com.example.WebProject.model.UkuleleInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ProducerService;


@Controller
public class FilterUkuleleController {
	@Autowired
	private UkuleleFilterDao ukuleleFilterDao;
	
	@Autowired
	private UkuleleDao ukuleleDao;

	@Autowired
	
	private ProducerService producerService;
	
	@Autowired
	private CategoryService categoryService;
	
	private void AddAtribute(Model model){
		model.addAttribute("filter", new UkuleleFilter(-1, -1, -1, -1));
		model.addAttribute("producers", ukuleleFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterUkulele));
	}
	@GetMapping("/ukulelefilterCategory/{id}")
	public String adukuleleacousticCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", ukuleleFilterDao.filterCategory(id, ukuleleDao.findAllUkuleleInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "UKULELE >LOẠI");
		return "/ukulele";
	}
	@GetMapping("/ukulelefilterProducer/{id}")
	public String adukuleleacousticProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", ukuleleFilterDao.filterProducer(id, ukuleleDao.findAllUkuleleInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "UKULELE >HÃNG");
		return "/ukulele";
	}
	
	
	@GetMapping("/ukulelefilterPrice/{p1}")
	public String adukulele1pricer(Model model, @PathVariable int p1) {
		
		
		
		
		
		model.addAttribute("infos",ListPrice(p1, ukuleleDao.findAllUkuleleInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "UKULELE");
		return "/ukulele";
	}
	@GetMapping("/ukulelefilter")
	public String filter(Model model, @Valid UkuleleFilter filter) {
		String s="UKULELE";
		List<UkuleleInfo> list=ukuleleDao.findAllUkuleleInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("infos", ukuleleDao.findAllUkuleleInfo());
			model.addAttribute("tittle", "UKULELE ");
			return "ukulele";
		}
		if(filter.getCategory()>0){
			list=ukuleleFilterDao.filterCategory(filter.getCategory(), list);
			if(categoryService.findOne(filter.getCategory()).getCategory()!=null)
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=ukuleleFilterDao.filterProducer(filter.getProducer(), list);
			if(producerService.findOne(filter.getProducer()).getName()!=null)
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			
			list=ListPrice(filter.getPrice1(), ukuleleDao.findAllUkuleleInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("infos", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/ukulele";
	}
	List<UkuleleInfo> ListPrice(int p1, List<UkuleleInfo> listukuleleInfo  ){
		List<UkuleleInfo> ukuleleInfo=new ArrayList<UkuleleInfo>();
		switch(p1){
		case 1: ukuleleInfo=ukuleleFilterDao.filterPrice(1,500000, listukuleleInfo);
		break;
		case 2: ukuleleInfo=ukuleleFilterDao.filterPrice(500000,1500000, listukuleleInfo);
		break;
		case 3: ukuleleInfo=ukuleleFilterDao.filterPrice(1500000,3000000, listukuleleInfo);
		break;
		case 4: ukuleleInfo=ukuleleFilterDao.filterPrice(3000000, listukuleleInfo);
		break;
		}
		return ukuleleInfo;
	}
	/*admin*/
	@GetMapping("/adukulelefilterCategory/{id}")
	public String FindCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", ukuleleFilterDao.filterCategory(id, ukuleleDao.findAllUkuleleInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "UKULELE >LOẠI");
		return "/admin/UkuleleAd";
	}
	@GetMapping("/adukulelefilterProducer/{id}")
	public String adukuleleProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", ukuleleFilterDao.filterProducer(id, ukuleleDao.findAllUkuleleInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "UKULELE >HÃNG");
		return "/admin/UkuleleAd";
	}

	
	@GetMapping("/adukulelefilterPrice/{p1}")
	public String aadukulele1pricer(Model model, @PathVariable int p1) {
		
		
		
	
		
		model.addAttribute("infos",ListPrice(p1, ukuleleDao.findAllUkuleleInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "UKULELE");
		return "/admin/UkuleleAd";
	}
	@GetMapping("/adukulelefilter")
	public String afilter(Model model, @Valid UkuleleFilter filter) {
		String s="UKULELE";
		List<UkuleleInfo> list=ukuleleDao.findAllUkuleleInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("infos", ukuleleDao.findAllUkuleleInfo());
		
			return "admin/UkuleleAd";
		}
		if(filter.getCategory()>0){
			list=ukuleleFilterDao.filterCategory(filter.getCategory(), list);
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=ukuleleFilterDao.filterProducer(filter.getProducer(), list);
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			list=ListPrice(filter.getPrice1(), ukuleleDao.findAllUkuleleInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("infos", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/admin/UkuleleAd";
	}

}
