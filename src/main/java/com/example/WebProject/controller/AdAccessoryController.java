
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
import com.example.WebProject.dao.AccessoryFilterDao;
import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.dao.AccessoryDao;
import com.example.WebProject.entity.Accessory;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.entity.Color;
import com.example.WebProject.model.AccessoryFilter;
import com.example.WebProject.model.AccessoryInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.AccessoryService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;
import com.example.WebProject.validator.EditColorValidator;
import com.example.WebProject.validator.EditAccessoryValidator;
import com.example.WebProject.validator.EditProducerValidator;


@Controller
public class AdAccessoryController {
	@Autowired
	private AccessoryService accessoryService;
	@Autowired
	private AccessoryDao accessoryDao;
	@Autowired
	private AccessoryFilterDao accessoryFilterDao;
	@Autowired
	private ColorService colorService;
	@Autowired
	
	private ProducerService producerService;
	@Autowired
	
	private MaService maService;

	@Autowired
	private CategoryService categoryService;
	
	 @Autowired
	    private EditAccessoryValidator editAccessoryValidator;
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
	 
	        if (target.getClass() == AccessoryInfo.class) {
	            dataBinder.setValidator(editAccessoryValidator); 
	        }
	        if (target.getClass() == Producer.class) {
	            dataBinder.setValidator(editProducerValidator); 
	        }
	        if (target.getClass() == Color.class) {
	            dataBinder.setValidator(editColorValidator); 
	        }
	    }
	
	    private void AddAtribute(Model model){
			model.addAttribute("filter", new AccessoryFilter(-1, -1, -1, -1));
			model.addAttribute("producers", accessoryFilterDao.list5Producer());
			model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterAccessory));
		}
	    private void AddCategoryColorProduct(Model model){
	    	model.addAttribute("categoris", categoryService.findByIdprContaining(WebProjectApplication.filterAccessory));
			model.addAttribute("colors", colorService.findAll());
			model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterAccessory));
			
		}
	

	/*view accessory*/
	@GetMapping("/adaccessory")
	public String adaccessory(Model model) {
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR");
		model.addAttribute("infos", accessoryDao.findAllAccessoryInfo());
		return "/admin/AccessoryAd";
	}


	/*edit accessory*/
	@GetMapping("/adaccessory/{id}/edit")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("filter",WebProjectApplication.filterAccessory);
		model.addAttribute("contact", accessoryDao.findAccessoryInfoSave(id));
		AddCategoryColorProduct(model);
		/*model.addAttribute("filterAccessory",WebProjectApplication.filterAccessory );
		*/return "/admin/AccessoryEdit";
	}
	@PostMapping("/adaccessory/save")
	public String save ( Model model, @ModelAttribute("contact") @Validated AccessoryInfo contact, BindingResult result, 
			RedirectAttributes redirect ) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			return "/admin/AccessoryEdit";
		}
		/*if(contact.getFileData().isEmpty()){
			contact.setFileData(accessoryService.findOne(contact.getId()).getImage());
		}*/
		contact.setValid(true);
		accessoryDao.save(contact);
		//accessoryService.save(accessory);
		redirect.addFlashAttribute("success", "Saved accessory successfully!");
		return "redirect:/adaccessory";
	}
	@GetMapping("/adaccessory/create")
	public String create( Model model) {
		AccessoryInfo gt=new AccessoryInfo();
		
		
		gt.setId(maService.findOne(1).getAccessory());
		gt.setRate(0);
		gt.setLuotdanhgia(0);
		model.addAttribute("filter",WebProjectApplication.filterAccessory);
		model.addAttribute("contact",gt );
		AddCategoryColorProduct(model);
		return "/admin/AccessoryCreate";
	}
	@PostMapping("/adaccessory/savecreate")
	public String saveCreate(Model model, @ModelAttribute("contact") @Validated AccessoryInfo contact, BindingResult result, 
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			
			return "/admin/AccessoryCreate";
		}
		contact.setValid(true);
		accessoryDao.SaveCreate(contact);
		//accessoryService.save(accessory);
		redirect.addFlashAttribute("success", "Saved accessory successfully!");
		return "redirect:/adaccessory";
	}
	@GetMapping("/adaccessory/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		
		accessoryService.delete(id);
	
		redirect.addFlashAttribute("success", "Deleted accessory successfully!");
		return "redirect:/adaccessory";
	}
	/*load image*/
	@RequestMapping(value = { "/productImageaccessory" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("code") String code) throws IOException {
        Accessory product = null;
        if (code != null) {
        	
            product = this.accessoryService.findOne(Integer.parseInt(code));
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
	
	/*@GetMapping("/adproducerPa")
	public String ProducerAd(Model model) {
		model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterAccessory));
		
		model.addAttribute("filter",WebProjectApplication.filterAccessory );
		return "/admin/ProducerAd";
	}*/
	}
