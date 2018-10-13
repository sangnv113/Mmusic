
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
import com.example.WebProject.dao.AccessoryDao;
import com.example.WebProject.dao.AccessoryFilterDao;
import com.example.WebProject.model.AccessoryFilter;
import com.example.WebProject.model.AccessoryInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ProducerService;


@Controller
public class FilterAccessoryController {
	@Autowired
	private AccessoryFilterDao accessoryFilterDao;
	
	@Autowired
	private AccessoryDao accessoryDao;

	@Autowired
	
	private ProducerService producerService;
	
	@Autowired
	private CategoryService categoryService;
	
	private void AddAtribute(Model model){
		model.addAttribute("filter", new AccessoryFilter(-1, -1, -1, -1));
		model.addAttribute("producers", accessoryFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterAccessory));
	}
	@GetMapping("/accessoryfilterCategory/{id}")
	public String adaccessoryacousticCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", accessoryFilterDao.filterCategory(id, accessoryDao.findAllAccessoryInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PHỤ KIỆN >LOẠI");
		return "/accessory";
	}
	@GetMapping("/accessoryfilterProducer/{id}")
	public String adaccessoryacousticProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", accessoryFilterDao.filterProducer(id, accessoryDao.findAllAccessoryInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PHỤ KIỆN >HÃNG");
		return "/accessory";
	}
	
	
	@GetMapping("/accessoryfilterPrice/{p1}")
	public String adaccessory1pricer(Model model, @PathVariable int p1) {
		
		
		
		
		
		model.addAttribute("infos",ListPrice(p1, accessoryDao.findAllAccessoryInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PHỤ KIỆN");
		return "/accessory";
	}
	@GetMapping("/accessoryfilter")
	public String filter(Model model, @Valid AccessoryFilter filter) {
		String s="PHỤ KIỆN";
		List<AccessoryInfo> list=accessoryDao.findAllAccessoryInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("infos", accessoryDao.findAllAccessoryInfo());
			model.addAttribute("tittle", "PHỤ KIỆN ");
			return "accessory";
		}
		if(filter.getCategory()>0){
			list=accessoryFilterDao.filterCategory(filter.getCategory(), list);
			if(categoryService.findOne(filter.getCategory()).getCategory()!=null)
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=accessoryFilterDao.filterProducer(filter.getProducer(), list);
			if(producerService.findOne(filter.getProducer()).getName()!=null)
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			
			list=ListPrice(filter.getPrice1(), accessoryDao.findAllAccessoryInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("infos", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/accessory";
	}
	List<AccessoryInfo> ListPrice(int p1, List<AccessoryInfo> listaccessoryInfo  ){
		List<AccessoryInfo> accessoryInfo=new ArrayList<AccessoryInfo>();
		switch(p1){
		case 1: accessoryInfo=accessoryFilterDao.filterPrice(1,100000, listaccessoryInfo);
		break;
		case 2: accessoryInfo=accessoryFilterDao.filterPrice(100000,300000, listaccessoryInfo);
		break;
		case 3: accessoryInfo=accessoryFilterDao.filterPrice(300000,500000, listaccessoryInfo);
		break;
		case 4: accessoryInfo=accessoryFilterDao.filterPrice(500000, listaccessoryInfo);
		break;
		}
		return accessoryInfo;
	}
	/*admin*/
	@GetMapping("/adaccessoryfilterCategory/{id}")
	public String FindCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", accessoryFilterDao.filterCategory(id, accessoryDao.findAllAccessoryInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PHỤ KIỆN >LOẠI");
		return "/admin/AccessoryAd";
	}
	@GetMapping("/adaccessoryfilterProducer/{id}")
	public String adaccessoryProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", accessoryFilterDao.filterProducer(id, accessoryDao.findAllAccessoryInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PHỤ KIỆN >HÃNG");
		return "/admin/AccessoryAd";
	}

	
	@GetMapping("/adaccessoryfilterPrice/{p1}")
	public String aadaccessory1pricer(Model model, @PathVariable int p1) {
		
		
		
	
		
		model.addAttribute("infos",ListPrice(p1, accessoryDao.findAllAccessoryInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PHỤ KIỆN");
		return "/admin/AccessoryAd";
	}
	@GetMapping("/adaccessoryfilter")
	public String afilter(Model model, @Valid AccessoryFilter filter) {
		String s="PHỤ KIỆN";
		List<AccessoryInfo> list=accessoryDao.findAllAccessoryInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("infos", accessoryDao.findAllAccessoryInfo());
		
			return "admin/AccessoryAd";
		}
		if(filter.getCategory()>0){
			list=accessoryFilterDao.filterCategory(filter.getCategory(), list);
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=accessoryFilterDao.filterProducer(filter.getProducer(), list);
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			list=ListPrice(filter.getPrice1(), accessoryDao.findAllAccessoryInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("infos", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/admin/AccessoryAd";
	}

}
