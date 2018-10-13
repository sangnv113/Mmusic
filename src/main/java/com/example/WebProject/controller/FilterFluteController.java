
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
import com.example.WebProject.dao.FluteDao;
import com.example.WebProject.dao.FluteFilterDao;
import com.example.WebProject.model.FluteFilter;
import com.example.WebProject.model.FluteInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ProducerService;


@Controller
public class FilterFluteController {
	@Autowired
	private FluteFilterDao fluteFilterDao;
	
	@Autowired
	private FluteDao fluteDao;

	@Autowired
	
	private ProducerService producerService;
	
	@Autowired
	private CategoryService categoryService;
	
	private void AddAtribute(Model model){
		model.addAttribute("filter", new FluteFilter(-1, -1, -1, -1));
		model.addAttribute("producers", fluteFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterFlute));
	}
	@GetMapping("/flutefilterCategory/{id}")
	public String adfluteacousticCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", fluteFilterDao.filterCategory(id, fluteDao.findAllFluteInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "SÁO TRÚC >LOẠI");
		return "/flute";
	}
	@GetMapping("/flutefilterProducer/{id}")
	public String adfluteacousticProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", fluteFilterDao.filterProducer(id, fluteDao.findAllFluteInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "SÁO TRÚC >HÃNG");
		return "/flute";
	}
	
	
	@GetMapping("/flutefilterPrice/{p1}")
	public String adflute1pricer(Model model, @PathVariable int p1) {
		
		
		
		
		
		model.addAttribute("infos",ListPrice(p1, fluteDao.findAllFluteInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "SÁO TRÚC");
		return "/flute";
	}
	@GetMapping("/flutefilter")
	public String filter(Model model, @Valid FluteFilter filter) {
		String s="SÁO TRÚC";
		List<FluteInfo> list=fluteDao.findAllFluteInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("infos", fluteDao.findAllFluteInfo());
			model.addAttribute("tittle", "SÁO TRÚC ");
			return "flute";
		}
		if(filter.getCategory()>0){
			list=fluteFilterDao.filterCategory(filter.getCategory(), list);
			if(categoryService.findOne(filter.getCategory()).getCategory()!=null)
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=fluteFilterDao.filterProducer(filter.getProducer(), list);
			if(producerService.findOne(filter.getProducer()).getName()!=null)
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			
			list=ListPrice(filter.getPrice1(), fluteDao.findAllFluteInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("infos", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/flute";
	}
	List<FluteInfo> ListPrice(int p1, List<FluteInfo> listfluteInfo  ){
		List<FluteInfo> fluteInfo=new ArrayList<FluteInfo>();
		switch(p1){
		case 1: fluteInfo=fluteFilterDao.filterPrice(1,100000, listfluteInfo);
		break;
		case 2: fluteInfo=fluteFilterDao.filterPrice(100000,500000, listfluteInfo);
		break;
		case 3: fluteInfo=fluteFilterDao.filterPrice(500000,1500000, listfluteInfo);
		break;
		case 4: fluteInfo=fluteFilterDao.filterPrice(1500000, listfluteInfo);
		break;
		}
		return fluteInfo;
	}
	/*admin*/
	@GetMapping("/adflutefilterCategory/{id}")
	public String FindCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", fluteFilterDao.filterCategory(id, fluteDao.findAllFluteInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "SÁO TRÚC >LOẠI");
		return "/admin/FluteAd";
	}
	@GetMapping("/adflutefilterProducer/{id}")
	public String adfluteProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", fluteFilterDao.filterProducer(id, fluteDao.findAllFluteInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "SÁO TRÚC >HÃNG");
		return "/admin/FluteAd";
	}

	
	@GetMapping("/adflutefilterPrice/{p1}")
	public String aadflute1pricer(Model model, @PathVariable int p1) {
		
		
		
	
		
		model.addAttribute("infos",ListPrice(p1, fluteDao.findAllFluteInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "SÁO TRÚC");
		return "/admin/FluteAd";
	}
	@GetMapping("/adflutefilter")
	public String afilter(Model model, @Valid FluteFilter filter) {
		String s="SÁO TRÚC";
		List<FluteInfo> list=fluteDao.findAllFluteInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("infos", fluteDao.findAllFluteInfo());
		
			return "admin/FluteAd";
		}
		if(filter.getCategory()>0){
			list=fluteFilterDao.filterCategory(filter.getCategory(), list);
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=fluteFilterDao.filterProducer(filter.getProducer(), list);
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			list=ListPrice(filter.getPrice1(), fluteDao.findAllFluteInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("infos", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/admin/FluteAd";
	}

}
