package com.example.WebProject.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.dao.AccessoryDao;
import com.example.WebProject.dao.AccessoryFilterDao;
import com.example.WebProject.dao.DrumDao;
import com.example.WebProject.dao.DrumFilterDao;
import com.example.WebProject.dao.FluteDao;
import com.example.WebProject.dao.FluteFilterDao;
import com.example.WebProject.dao.GuitarDao;
import com.example.WebProject.dao.GuitarFilterDao;
import com.example.WebProject.dao.PianoDao;
import com.example.WebProject.dao.PianoFilterDao;
import com.example.WebProject.dao.UkuleleDao;
import com.example.WebProject.dao.UkuleleFilterDao;
import com.example.WebProject.model.PianoFilter;
import com.example.WebProject.service.AccessoryService;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.DrumService;
import com.example.WebProject.service.FluteService;
import com.example.WebProject.service.GuitarService;
import com.example.WebProject.service.PianoService;
import com.example.WebProject.service.UkuleleService;


@Controller
public class AdViewController {

	@Autowired
	private GuitarFilterDao guitarFilterDao;
	@Autowired
	private GuitarService guitarService;
	@Autowired
	private GuitarDao guitarDao;
	//
	@Autowired
	private PianoFilterDao pianoFilterDao;
	@Autowired
	private PianoService pianoService;
	@Autowired
	private PianoDao pianoDao;
	//
	@Autowired
	private UkuleleFilterDao ukuleleFilterDao;
	@Autowired
	private UkuleleService ukuleleService;
	@Autowired
	private UkuleleDao ukuleleDao;
	//
	@Autowired
	private FluteFilterDao fluteFilterDao;
	@Autowired
	private FluteService fluteService;
	@Autowired
	private FluteDao fluteDao;
	//
	@Autowired
	private DrumFilterDao drumFilterDao;
	@Autowired
	private DrumService drumService;
	@Autowired
	private DrumDao drumDao;
	//
	@Autowired
	private AccessoryFilterDao accessoryFilterDao;
	@Autowired
	private AccessoryService accessoryService;
	@Autowired
	private AccessoryDao accessoryDao;

	@Autowired
	private CategoryService categoryService;

	private void PrAddModel(Model model) {
		model.addAttribute("filter", new PianoFilter(-1, -1, -1, -1));

	}

	
	@GetMapping("/_")
	public String index(Model model) {
		
		model.addAttribute("guitars", guitarDao.listGuitarInfo());
		model.addAttribute("pianos", pianoDao.listPianoInfo());
		model.addAttribute("drums", drumDao.listDrumInfo());
		model.addAttribute("ukuleles", ukuleleDao.listUkuleleInfo());
		
		return "/admin/_home";
	}
	
	@GetMapping("/_guitar")
	public String bbb(Model model) {
		model.addAttribute("tittle", "GUITAR");

		model.addAttribute("guitars", guitarDao.findAllGuitarInfo());
		PrAddModel(model);
		model.addAttribute("producers", guitarFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterGuitar));

		return "/admin/_guitar";
	}
	
	@GetMapping("/_ukulele")
	public String Ukulele(Model model) {
		model.addAttribute("tittle", "UKULELE");
		PrAddModel(model);
		model.addAttribute("producers", ukuleleFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterUkulele));

		model.addAttribute("infos", ukuleleDao.findAllUkuleleInfo());
	
		return "/admin/_ukulele";
	}
	@GetMapping("/_piano")
	public String Piano(Model model) {
		model.addAttribute("tittle", "PIANO");
		PrAddModel(model);
		model.addAttribute("producers", pianoFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterPiano));

		model.addAttribute("infos", pianoDao.findAllPianoInfo());
		
		return "/admin/_piano";
	}
	@GetMapping("/_drum")
	public String Drum(Model model) {
		model.addAttribute("tittle", "TRỐNG");
		PrAddModel(model);
		model.addAttribute("producers", drumFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterDrum));

		model.addAttribute("infos", drumDao.findAllDrumInfo());
		
		return "/admin/_drum";
	}
	@GetMapping("/_flute")
	public String uku(Model model) {
		model.addAttribute("tittle", "SÁO TRÚC");
		PrAddModel(model);
		model.addAttribute("producers", fluteFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterFlute));

		model.addAttribute("infos", fluteDao.findAllFluteInfo());
		
		return "/admin/_flute";
	}	
	//
	@GetMapping("/_sparepart")
	public String sparepart(Model model) {
		return "/admin/_flute";
	}	
	@GetMapping("/_product_guitar/{id}")
	public String Show1item(@PathVariable int id, Model model) {
	
		model.addAttribute("guitarinfo", guitarDao.findGuitarInfo(id));
		return "/admin/_productguitar";
	}
	
	
	
}
