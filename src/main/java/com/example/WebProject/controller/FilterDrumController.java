
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
import com.example.WebProject.dao.DrumDao;
import com.example.WebProject.dao.DrumFilterDao;
import com.example.WebProject.model.DrumFilter;
import com.example.WebProject.model.DrumInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ProducerService;


@Controller
public class FilterDrumController {
	@Autowired
	private DrumFilterDao drumFilterDao;
	
	@Autowired
	private DrumDao drumDao;

	@Autowired
	
	private ProducerService producerService;
	
	@Autowired
	private CategoryService categoryService;
	
	private void AddAtribute(Model model){
		model.addAttribute("filter", new DrumFilter(-1, -1, -1, -1));
		model.addAttribute("producers", drumFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterDrum));
	}
	@GetMapping("/drumfilterCategory/{id}")
	public String addrumacousticCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", drumFilterDao.filterCategory(id, drumDao.findAllDrumInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "TRỐNG >LOẠI");
		return "/drum";
	}
	@GetMapping("/drumfilterProducer/{id}")
	public String addrumacousticProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", drumFilterDao.filterProducer(id, drumDao.findAllDrumInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "TRỐNG >HÃNG");
		return "/drum";
	}
	
	
	@GetMapping("/drumfilterPrice/{p1}")
	public String addrum1pricer(Model model, @PathVariable int p1) {
		
		
		
		
		
		model.addAttribute("infos",ListPrice(p1, drumDao.findAllDrumInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "TRỐNG");
		return "/drum";
	}
	@GetMapping("/drumfilter")
	public String filter(Model model, @Valid DrumFilter filter) {
		String s="TRỐNG";
		List<DrumInfo> list=drumDao.findAllDrumInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("infos", drumDao.findAllDrumInfo());
			model.addAttribute("tittle", "TRỐNG ");
			return "drum";
		}
		if(filter.getCategory()>0){
			list=drumFilterDao.filterCategory(filter.getCategory(), list);
			if(categoryService.findOne(filter.getCategory()).getCategory()!=null)
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=drumFilterDao.filterProducer(filter.getProducer(), list);
			if(producerService.findOne(filter.getProducer()).getName()!=null)
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			
			list=ListPrice(filter.getPrice1(), drumDao.findAllDrumInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("infos", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/drum";
	}
	List<DrumInfo> ListPrice(int p1, List<DrumInfo> listdrumInfo  ){
		List<DrumInfo> drumInfo=new ArrayList<DrumInfo>();
		switch(p1){
		case 1: drumInfo=drumFilterDao.filterPrice(1,5000000, listdrumInfo);
		break;
		case 2: drumInfo=drumFilterDao.filterPrice(5000000,15000000, listdrumInfo);
		break;
		case 3: drumInfo=drumFilterDao.filterPrice(15000000,30000000, listdrumInfo);
		break;
		case 4: drumInfo=drumFilterDao.filterPrice(30000000, listdrumInfo);
		break;
		}
		return drumInfo;
	}
	/*admin*/
	@GetMapping("/addrumfilterCategory/{id}")
	public String FindCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", drumFilterDao.filterCategory(id, drumDao.findAllDrumInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "TRỐNG >LOẠI");
		return "/admin/DrumAd";
	}
	@GetMapping("/addrumfilterProducer/{id}")
	public String addrumProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", drumFilterDao.filterProducer(id, drumDao.findAllDrumInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "TRỐNG >HÃNG");
		return "/admin/DrumAd";
	}

	
	@GetMapping("/addrumfilterPrice/{p1}")
	public String aaddrum1pricer(Model model, @PathVariable int p1) {
		
		
		
	
		
		model.addAttribute("infos",ListPrice(p1, drumDao.findAllDrumInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "TRỐNG");
		return "/admin/DrumAd";
	}
	@GetMapping("/addrumfilter")
	public String afilter(Model model, @Valid DrumFilter filter) {
		String s="TRỐNG";
		List<DrumInfo> list=drumDao.findAllDrumInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("infos", drumDao.findAllDrumInfo());
		
			return "admin/DrumAd";
		}
		if(filter.getCategory()>0){
			list=drumFilterDao.filterCategory(filter.getCategory(), list);
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=drumFilterDao.filterProducer(filter.getProducer(), list);
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			list=ListPrice(filter.getPrice1(), drumDao.findAllDrumInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("infos", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/admin/DrumAd";
	}

}
