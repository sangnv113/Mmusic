
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
import com.example.WebProject.dao.PianoDao;
import com.example.WebProject.dao.PianoFilterDao;
import com.example.WebProject.model.PianoFilter;
import com.example.WebProject.model.PianoInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ProducerService;


@Controller
public class FilterPianoController {
	@Autowired
	private PianoFilterDao pianoFilterDao;
	
	@Autowired
	private PianoDao pianoDao;

	@Autowired
	
	private ProducerService producerService;
	
	@Autowired
	private CategoryService categoryService;
	
	private void AddAtribute(Model model){
		model.addAttribute("filter", new PianoFilter(-1, -1, -1, -1));
		model.addAttribute("producers", pianoFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterPiano));
	}
	@GetMapping("/pianofilterCategory/{id}")
	public String adpianoacousticCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", pianoFilterDao.filterCategory(id, pianoDao.findAllPianoInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PIANO >LOẠI");
		return "/piano";
	}
	@GetMapping("/pianofilterProducer/{id}")
	public String adpianoacousticProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", pianoFilterDao.filterProducer(id, pianoDao.findAllPianoInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PIANO >HÃNG");
		return "/piano";
	}
	
	
	@GetMapping("/pianofilterPrice/{p1}")
	public String adpiano1pricer(Model model, @PathVariable int p1) {
		
		
		
		
		
		model.addAttribute("infos",ListPrice(p1, pianoDao.findAllPianoInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PIANO");
		return "/piano";
	}
	@GetMapping("/pianofilter")
	public String filter(Model model, @Valid PianoFilter filter) {
		String s="PIANO";
		List<PianoInfo> list=pianoDao.findAllPianoInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("infos", pianoDao.findAllPianoInfo());
			model.addAttribute("tittle", "PIANO ");
			return "piano";
		}
		if(filter.getCategory()>0){
			list=pianoFilterDao.filterCategory(filter.getCategory(), list);
			if(categoryService.findOne(filter.getCategory()).getCategory()!=null)
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=pianoFilterDao.filterProducer(filter.getProducer(), list);
			if(producerService.findOne(filter.getProducer()).getName()!=null)
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			
			list=ListPrice(filter.getPrice1(), pianoDao.findAllPianoInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("infos", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/piano";
	}
	List<PianoInfo> ListPrice(int p1, List<PianoInfo> listpianoInfo  ){
		List<PianoInfo> pianoInfo=new ArrayList<PianoInfo>();
		switch(p1){
		case 1: pianoInfo=pianoFilterDao.filterPrice(1,5000000, listpianoInfo);
		break;
		case 2: pianoInfo=pianoFilterDao.filterPrice(5000000,15000000, listpianoInfo);
		break;
		case 3: pianoInfo=pianoFilterDao.filterPrice(15000000,30000000, listpianoInfo);
		break;
		case 4: pianoInfo=pianoFilterDao.filterPrice(30000000, listpianoInfo);
		break;
		}
		return pianoInfo;
	}
	/*admin*/
	@GetMapping("/adpianofilterCategory/{id}")
	public String FindCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", pianoFilterDao.filterCategory(id, pianoDao.findAllPianoInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PIANO >LOẠI");
		return "/admin/PianoAd";
	}
	@GetMapping("/adpianofilterProducer/{id}")
	public String adpianoProducer(Model model, @PathVariable int id) {
		
		model.addAttribute("infos", pianoFilterDao.filterProducer(id, pianoDao.findAllPianoInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PIANO >HÃNG");
		return "/admin/PianoAd";
	}

	
	@GetMapping("/adpianofilterPrice/{p1}")
	public String aadpiano1pricer(Model model, @PathVariable int p1) {
		
		
		
	
		
		model.addAttribute("infos",ListPrice(p1, pianoDao.findAllPianoInfo()));
		AddAtribute(model);
		model.addAttribute("tittle", "PIANO");
		return "/admin/PianoAd";
	}
	@GetMapping("/adpianofilter")
	public String afilter(Model model, @Valid PianoFilter filter) {
		String s="PIANO";
		List<PianoInfo> list=pianoDao.findAllPianoInfo();
		if(filter==null)
		{
			AddAtribute(model);
			model.addAttribute("infos", pianoDao.findAllPianoInfo());
		
			return "admin/PianoAd";
		}
		if(filter.getCategory()>0){
			list=pianoFilterDao.filterCategory(filter.getCategory(), list);
			s=s+" > "+categoryService.findOne(filter.getCategory()).getCategory();
		}
		
		if(filter.getProducer()>0){
			list=pianoFilterDao.filterProducer(filter.getProducer(), list);
			s=s+" > "+producerService.findOne(filter.getProducer()).getName();
		}
		if(filter.getPrice1()>0&&filter.getPrice1()<=4){
			list=ListPrice(filter.getPrice1(), pianoDao.findAllPianoInfo());
			s=s+" > Giá";
		}
		
		model.addAttribute("infos", list);
		AddAtribute(model);
		model.addAttribute("tittle", s);
		return "/admin/PianoAd";
	}

}
