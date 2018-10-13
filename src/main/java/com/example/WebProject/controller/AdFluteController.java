
package com.example.WebProject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.WebProject.dao.FluteFilterDao;
import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.dao.FluteDao;
import com.example.WebProject.entity.Flute;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.entity.Color;
import com.example.WebProject.model.FluteFilter;
import com.example.WebProject.model.FluteInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.FluteService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;
import com.example.WebProject.validator.EditColorValidator;
import com.example.WebProject.validator.EditFluteValidator;
import com.example.WebProject.validator.EditProducerValidator;


@Controller
public class AdFluteController {
	@Autowired
	private FluteService fluteService;
	@Autowired
	private FluteDao fluteDao;
	@Autowired
	private FluteFilterDao fluteFilterDao;
	@Autowired
	private ColorService colorService;
	@Autowired
	
	private ProducerService producerService;
	@Autowired
	
	private MaService maService;

	@Autowired
	private CategoryService categoryService;
	
	 @Autowired
	    private EditFluteValidator editFluteValidator;
	 @Autowired
	    private EditProducerValidator editProducerValidator;
	 @Autowired
	    private EditColorValidator editColorValidator;
	 
	    @InitBinder
	    public void myInitBinder(WebDataBinder dataBinder) {
	        Object target = dataBinder.getTarget();
	        if (target == null) {
	            return;
	        }
	        System.out.println("Target=" + target);
	 
	        if (target.getClass() == FluteInfo.class) {
	            dataBinder.setValidator(editFluteValidator); 
	        }
	        if (target.getClass() == Producer.class) {
	            dataBinder.setValidator(editProducerValidator); 
	        }
	        if (target.getClass() == Color.class) {
	            dataBinder.setValidator(editColorValidator); 
	        }
	    }
	
	    private void AddAtribute(Model model){
			model.addAttribute("filter", new FluteFilter(-1, -1, -1, -1));
			model.addAttribute("producers", fluteFilterDao.list5Producer());
			model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterFlute));
		}
	    private void AddCategoryColorProduct(Model model){
	    	model.addAttribute("categoris", categoryService.findByIdprContaining(WebProjectApplication.filterFlute));
			model.addAttribute("colors", colorService.findAll());
			model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterFlute));
			
		}
	

	/*view flute*/
	@GetMapping("/adflute")
	public String adflute(Model model) {
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR");
		model.addAttribute("infos", fluteDao.findAllFluteInfo());
		return "/admin/FluteAd";
	}


	/*edit flute*/
	@GetMapping("/adflute/{id}/edit")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("filter",WebProjectApplication.filterFlute);
		model.addAttribute("contact", fluteDao.findFluteInfoSave(id));
		AddCategoryColorProduct(model);
		/*model.addAttribute("filterFlute",WebProjectApplication.filterFlute );
		*/return "/admin/FluteEdit";
	}
	@PostMapping("/adflute/save")
	public String save ( Model model, @ModelAttribute("contact") @Validated FluteInfo contact, BindingResult result, 
			RedirectAttributes redirect ) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			return "/admin/FluteEdit";
		}
		/*if(contact.getFileData().isEmpty()){
			contact.setFileData(fluteService.findOne(contact.getId()).getImage());
		}*/
		contact.setValid(true);
		fluteDao.save(contact);
		//fluteService.save(flute);
		redirect.addFlashAttribute("success", "Saved flute successfully!");
		return "redirect:/adflute";
	}
	@GetMapping("/adflute/create")
	public String create( Model model) {
		FluteInfo gt=new FluteInfo();
		
		
		gt.setId(maService.findOne(1).getFlute());
		gt.setRate(0);
		gt.setLuotdanhgia(0);
		model.addAttribute("filter",WebProjectApplication.filterFlute);
		model.addAttribute("contact",gt );
		AddCategoryColorProduct(model);
		return "/admin/FluteCreate";
	}
	@PostMapping("/adflute/savecreate")
	public String saveCreate(Model model, @ModelAttribute("contact") @Validated FluteInfo contact, BindingResult result, 
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			
			return "/admin/FluteCreate";
		}
		contact.setValid(true);
		fluteDao.SaveCreate(contact);
		//fluteService.save(flute);
		redirect.addFlashAttribute("success", "Saved flute successfully!");
		return "redirect:/adflute";
	}
	@GetMapping("/adflute/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		
		fluteService.delete(id);
	
		redirect.addFlashAttribute("success", "Deleted flute successfully!");
		return "redirect:/adflute";
	}
	/*load image*/
	@RequestMapping(value = { "/productImageflute" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("code") String code) throws IOException {
        Flute product = null;
        if (code != null) {
        	
            product = this.fluteService.findOne(Integer.parseInt(code));
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
	
	/*@GetMapping("/adproducerFl")
	public String ProducerAd(Model model) {
		model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterFlute));
		
		model.addAttribute("filter",WebProjectApplication.filterFlute );
		return "/admin/ProducerAd";
	}*/
	}
