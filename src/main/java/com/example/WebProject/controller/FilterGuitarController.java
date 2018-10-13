
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
import com.example.WebProject.dao.GuitarDao;
import com.example.WebProject.dao.GuitarFilterDao;
import com.example.WebProject.model.GuitarFilter;
import com.example.WebProject.model.GuitarInfo;
import com.example.WebProject.model.PianoFilter;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ProducerService;


@Controller
public class FilterGuitarController {
	@Autowired
	private GuitarFilterDao guitarFilterDao;
	
	@Autowired
	private GuitarDao guitarDao;

	@Autowired
	
	private ProducerService producerService;
	
	@Autowired
	private CategoryService categoryService;
	
	private void AddAtribute(Model model){
		model.addAttribute("filter", new PianoFilter(-1, -1, -1, -1));
		model.addAttribute("producers", guitarFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterGuitar));
	}
	
	@GetMapping("/guitarfilterCategory/{id}")
	public String adguitaracousticCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("guitars", guitarFilterDao.filterCategory(id, guitarDao.findAllGuitarInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR >LOẠI");
		return "/guitar";
	}
	@GetMapping("/guitarfilterProducer/{id}")
	public String adguitaracousticProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("guitars", guitarFilterDao.filterProducer(id, guitarDao.findAllGuitarInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR >HÃNG");
		return "/guitar";
	}
	
	
	@GetMapping("/guitarfilterPrice/{p1}")
	public String adguitar1pricer(Model model, @PathVariable int p1) {
		
		
		
		
		
		model.addAttribute("guitars",ListPrice(p1, guitarDao.findAllGuitarInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR");
		return "/guitar";
	}
	@GetMapping("/guitarfilter")
	public String filter(Model model, @Valid GuitarFilter filter) {
		String s="GUITAR";
		List<GuitarInfo> list=guitarDao.findAllGuitarInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("guitars", guitarDao.findAllGuitarInfo());
			model.addAttribute("tittle", "GUITAR ");
			return "guitar";
		}
		if(filter.getCategory()>0){
			list=guitarFilterDao.filterCategory(filter.getCategory(), list);
			if(categoryService.findOne(filter.getCategory()).getCategory()!=null)
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=guitarFilterDao.filterProducer(filter.getProducer(), list);
			if(producerService.findOne(filter.getProducer()).getName()!=null)
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			
			list=ListPrice(filter.getPrice1(), guitarDao.findAllGuitarInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("guitars", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/guitar";
	}
	List<GuitarInfo> ListPrice(int p1, List<GuitarInfo> listguitarInfo  ){
		List<GuitarInfo> guitarInfo=new ArrayList<GuitarInfo>();
		switch(p1){
		case 1: guitarInfo=guitarFilterDao.filterPrice(1,1500000, listguitarInfo);
		break;
		case 2: guitarInfo=guitarFilterDao.filterPrice(1500000,3000000, listguitarInfo);
		break;
		case 3: guitarInfo=guitarFilterDao.filterPrice(3000000,5000000, listguitarInfo);
		break;
		case 4: guitarInfo=guitarFilterDao.filterPrice(5000000, listguitarInfo);
		break;
		}
		return guitarInfo;
	}
	
	/*admin*/
	@GetMapping("/adguitarfilterCategory/{id}")
	public String FindCategory(Model model, @PathVariable int id) {
	
		model.addAttribute("guitars", guitarFilterDao.filterCategory(id, guitarDao.findAllGuitarInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR >LOẠI");
		return "/admin/GuitarAd";
	}
	@GetMapping("/adguitarfilterProducer/{id}")
	public String adguitarProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("guitars", guitarFilterDao.filterProducer(id, guitarDao.findAllGuitarInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR >HÃNG");
		return "/admin/GuitarAd";
	}

	
	@GetMapping("/adguitarfilterPrice/{p1}")
	public String aadguitar1pricer(Model model, @PathVariable int p1) {
		
		
		
	
		
		model.addAttribute("guitars",ListPrice(p1, guitarDao.findAllGuitarInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR");
		return "/admin/GuitarAd";
	}
	@GetMapping("/adguitarfilter")
	public String afilter(Model model, @Valid GuitarFilter filter) {
		String s="GUITAR";
		List<GuitarInfo> list=guitarDao.findAllGuitarInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("guitars", guitarDao.findAllGuitarInfo());
			model.addAttribute("tittle", "GUITAR ");
			return "admin/GuitarAd";
		}
		if(filter.getCategory()>0){
			list=guitarFilterDao.filterCategory(filter.getCategory(), list);
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=guitarFilterDao.filterProducer(filter.getProducer(), list);
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			list=ListPrice(filter.getPrice1(), guitarDao.findAllGuitarInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("guitars", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/admin/GuitarAd";
	}
		
}
