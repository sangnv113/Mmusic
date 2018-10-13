
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
import com.example.WebProject.dao.UkuleleFilterDao;
import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.dao.UkuleleDao;
import com.example.WebProject.entity.Ukulele;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.entity.Color;
import com.example.WebProject.model.UkuleleFilter;
import com.example.WebProject.model.UkuleleInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.UkuleleService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;
import com.example.WebProject.validator.EditColorValidator;
import com.example.WebProject.validator.EditUkuleleValidator;
import com.example.WebProject.validator.EditProducerValidator;


@Controller
public class AdUkuleleController {
	@Autowired
	private UkuleleService ukuleleService;
	@Autowired
	private UkuleleDao ukuleleDao;
	@Autowired
	private UkuleleFilterDao ukuleleFilterDao;
	@Autowired
	private ColorService colorService;
	@Autowired
	
	private ProducerService producerService;
	@Autowired
	
	private MaService maService;

	@Autowired
	private CategoryService categoryService;
	
	 @Autowired
	    private EditUkuleleValidator editUkuleleValidator;
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
	 
	        if (target.getClass() == UkuleleInfo.class) {
	            dataBinder.setValidator(editUkuleleValidator); 
	        }
	        if (target.getClass() == Producer.class) {
	            dataBinder.setValidator(editProducerValidator); 
	        }
	        if (target.getClass() == Color.class) {
	            dataBinder.setValidator(editColorValidator); 
	        }
	    }
	
	    private void AddAtribute(Model model){
			model.addAttribute("filter", new UkuleleFilter(-1, -1, -1, -1));
			model.addAttribute("producers", ukuleleFilterDao.list5Producer());
			model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterUkulele));
		}
	    private void AddCategoryColorProduct(Model model){
	    	model.addAttribute("categoris", categoryService.findByIdprContaining(WebProjectApplication.filterUkulele));
			model.addAttribute("colors", colorService.findAll());
			model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterUkulele));
			
		}
	

	/*view ukulele*/
	@GetMapping("/adukulele")
	public String adukulele(Model model) {
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR");
		model.addAttribute("infos", ukuleleDao.findAllUkuleleInfo());
		return "/admin/UkuleleAd";
	}


	/*edit ukulele*/
	@GetMapping("/adukulele/{id}/edit")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("filter",WebProjectApplication.filterUkulele);
		model.addAttribute("contact", ukuleleDao.findUkuleleInfoSave(id));
		AddCategoryColorProduct(model);
		/*model.addAttribute("filterUkulele",WebProjectApplication.filterUkulele );
		*/return "/admin/UkuleleEdit";
	}
	@PostMapping("/adukulele/save")
	public String save ( Model model, @ModelAttribute("contact") @Validated UkuleleInfo contact, BindingResult result, 
			RedirectAttributes redirect ) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			return "/admin/UkuleleEdit";
		}
		/*if(contact.getFileData().isEmpty()){
			contact.setFileData(ukuleleService.findOne(contact.getId()).getImage());
		}*/
		contact.setValid(true);
		ukuleleDao.save(contact);
		System.out.println(contact.getCategory());
		//ukuleleService.save(ukulele);
		redirect.addFlashAttribute("success", "Saved ukulele successfully!");
		return "redirect:/adukulele";
	}
	@GetMapping("/adukulele/create")
	public String create( Model model) {
		UkuleleInfo gt=new UkuleleInfo();
		
		
		gt.setId(maService.findOne(1).getUkulele());
		gt.setRate(0);
		gt.setLuotdanhgia(0);
		model.addAttribute("filter",WebProjectApplication.filterUkulele);
		model.addAttribute("contact",gt );
		AddCategoryColorProduct(model);
		return "/admin/UkuleleCreate";
	}
	@PostMapping("/adukulele/savecreate")
	public String saveCreate(Model model, @ModelAttribute("contact") @Validated UkuleleInfo contact, BindingResult result, 
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			
			return "/admin/UkuleleCreate";
		}
		contact.setValid(true);
		ukuleleDao.SaveCreate(contact);
		//ukuleleService.save(ukulele);
		redirect.addFlashAttribute("success", "Saved ukulele successfully!");
		return "redirect:/adukulele";
	}
	@GetMapping("/adukulele/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		
		ukuleleService.delete(id);
	
		redirect.addFlashAttribute("success", "Deleted ukulele successfully!");
		return "redirect:/adukulele";
	}
	/*load image*/
	@RequestMapping(value = { "/productImageukulele" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("code") String code) throws IOException {
        Ukulele product = null;
        if (code != null) {
        	
            product = this.ukuleleService.findOne(Integer.parseInt(code));
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
	
/*	@GetMapping("/adproducerUk")
	public String ProducerAd(Model model) {
		model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterUkulele));
		
		model.addAttribute("filter",WebProjectApplication.filterUkulele );
		return "/admin/ProducerAd";
	}*/
	}
