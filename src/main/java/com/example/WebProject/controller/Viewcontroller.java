
package com.example.WebProject.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.example.WebProject.entity.Accessory;
import com.example.WebProject.entity.Customer;
import com.example.WebProject.entity.Drum;
import com.example.WebProject.entity.Flute;
import com.example.WebProject.entity.Guitar;
import com.example.WebProject.entity.Piano;
import com.example.WebProject.entity.Ukulele;
import com.example.WebProject.model.CartLineInfo;
import com.example.WebProject.model.FluteInfo;
import com.example.WebProject.model.GuitarInfo;
import com.example.WebProject.model.PianoFilter;
import com.example.WebProject.service.AccessoryService;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.CustomerService;
import com.example.WebProject.service.DrumService;
import com.example.WebProject.service.FluteService;
import com.example.WebProject.service.GuitarService;
import com.example.WebProject.service.PianoService;
import com.example.WebProject.service.UkuleleService;

@Controller
public class Viewcontroller {
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
//
	@Autowired
	private CustomerService customerService;

	
	private void AddProductNumber(Model model){
		model.addAttribute("prnumber", WebProjectApplication.productNumber);
		
	}
	
	@GetMapping("/")
	public String index(Model model) {
		AddProductNumber(model);
		model.addAttribute("guitars", guitarDao.listGuitarInfo());
		model.addAttribute("pianos", pianoDao.listPianoInfo());
		model.addAttribute("drums", drumDao.listDrumInfo());
		model.addAttribute("ukuleles", ukuleleDao.listUkuleleInfo());
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {

		return "login";
	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "/403";
	}

	// addmodel
	private void PrAddModel(Model model) {
		model.addAttribute("filter", new PianoFilter(-1, -1, -1, -1));

	}

	/* guitar */
	@GetMapping("/guitar")
	public String bbb(Model model) {
		model.addAttribute("tittle", "GUITAR");

		model.addAttribute("guitars", guitarDao.findAllGuitarInfo());
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", guitarFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterGuitar));

		return "guitar";
	}

	@GetMapping("/product_guitar/{id}")
	public String Show1item(@PathVariable int id, Model model) {
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", guitarFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterGuitar));
		Guitar gti = guitarService.findOne(id);
		gti.setVisits(gti.getVisits() + 1);
		guitarService.save(gti);
		model.addAttribute("info", guitarDao.findGuitarInfo(id));
		
		model.addAttribute("contact", guitarDao.listGuitarInfo());
		return "guitarproduct";
	}

	/* piano */
	@GetMapping("/piano")
	public String piano(Model model) {
		model.addAttribute("tittle", "PIANO");
		PrAddModel(model);
		AddProductNumber(model);
		AddProductNumber(model);
		model.addAttribute("producers", pianoFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterPiano));

		model.addAttribute("infos", pianoDao.findAllPianoInfo());
		return "piano";
	}

	@GetMapping("/product_piano/{id}")
	public String Show1itempia(@PathVariable int id, Model model) {
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", pianoFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterPiano));
		Piano gti = pianoService.findOne(id);
		gti.setVisits(gti.getVisits() + 1);
		pianoService.save(gti);
		model.addAttribute("info", pianoDao.findPianoInfo(id));
		model.addAttribute("infos", pianoDao.listPianoInfo());
		return "pianoproduct";
	}

	/* ukulele */
	@GetMapping("/ukulele")
	public String ukulele(Model model) {
		model.addAttribute("tittle", "UKULELE");
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", ukuleleFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterUkulele));

		model.addAttribute("infos", ukuleleDao.findAllUkuleleInfo());
		return "ukulele";
	}

	@GetMapping("/product_ukulele/{id}")
	public String Show1itemu(@PathVariable int id, Model model) {
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", ukuleleFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterUkulele));
		Ukulele gti = ukuleleService.findOne(id);
		gti.setVisits(gti.getVisits() + 1);
		ukuleleService.save(gti);
		model.addAttribute("info", ukuleleDao.findUkuleleInfo(id));
		model.addAttribute("infos", ukuleleDao.listUkuleleInfo());
		return "ukuleleproduct";
	}


	/* FLUTE */
	@GetMapping("/flute")
	public String flute(Model model) {
		model.addAttribute("tittle", "SÁO TRÚC");
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", fluteFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterFlute));

		model.addAttribute("infos", fluteDao.findAllFluteInfo());
		return "flute";
	}

	@GetMapping("/product_flute/{id}")
	public String Show1itempi(@PathVariable int id, Model model) {
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", fluteFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterFlute));
		Flute gti = fluteService.findOne(id);
		gti.setVisits(gti.getVisits() + 1);
		fluteService.save(gti);
		model.addAttribute("info", fluteDao.findFluteInfo(id));
		model.addAttribute("infos", fluteDao.listFluteInfo());
		return "fluteproduct";
	}

	/* drum */
	@GetMapping("/drum")
	public String drum(Model model) {
		model.addAttribute("tittle", "TRỐNG");
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", drumFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterDrum));

		model.addAttribute("infos", drumDao.findAllDrumInfo());
		return "drum";
	}

	@GetMapping("/product_drum/{id}")
	public String Show1itempid(@PathVariable int id, Model model) {
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", drumFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterDrum));
		Drum gti = drumService.findOne(id);
		gti.setVisits(gti.getVisits() + 1);
		drumService.save(gti);
		model.addAttribute("info", drumDao.findDrumInfo(id));
		model.addAttribute("infos", drumDao.listDrumInfo());
		return "drumproduct";
	}

	/* accessory */
	@GetMapping("/accessory")
	public String accessory(Model model) {
		model.addAttribute("tittle", "PHỤ KIỆN");
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", accessoryFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterAccessory));

		model.addAttribute("infos", accessoryDao.findAllAccessoryInfo());
		return "accessory";
	}

	@GetMapping("/product_accessory/{id}")
	public String Show1itempiac(@PathVariable int id, Model model) {
		PrAddModel(model);
		AddProductNumber(model);
		model.addAttribute("producers", accessoryFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterAccessory));
		Accessory gti = accessoryService.findOne(id);
		gti.setVisits(gti.getVisits() + 1);
		accessoryService.save(gti);
		model.addAttribute("info", accessoryDao.findAccessoryInfo(id));
		model.addAttribute("infos", accessoryDao.listAccessoryInfo());
		return "accessoryproduct";
	}

//cart
@PostMapping("/shoppingcart")
public String cart(Model model, @Valid CartLineInfo infoe) {
	AddProductNumber(model);
	model.addAttribute("info", null);
	WebProjectApplication.productNumber+=infoe.getSoluong();
	WebProjectApplication.listCartLine.add(infoe);
	model.addAttribute("infos", WebProjectApplication.listCartLine);
	return "shoppingcart";
}
@GetMapping("/infoCustomer")
public String customer(Model model) {
	model.addAttribute("contact", new Customer());
	return "infocustomer";
}
@PostMapping("/success")
public String success(Model model, @Valid Customer contact) {

	customerService.save(contact);
	
	return "success";
}


}
