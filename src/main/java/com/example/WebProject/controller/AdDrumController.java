
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
import com.example.WebProject.dao.DrumFilterDao;
import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.dao.DrumDao;
import com.example.WebProject.entity.Drum;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.entity.Color;
import com.example.WebProject.model.DrumFilter;
import com.example.WebProject.model.DrumInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.DrumService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;
import com.example.WebProject.validator.EditColorValidator;
import com.example.WebProject.validator.EditDrumValidator;
import com.example.WebProject.validator.EditProducerValidator;


@Controller
public class AdDrumController {
	@Autowired
	private DrumService drumService;
	@Autowired
	private DrumDao drumDao;
	@Autowired
	private DrumFilterDao drumFilterDao;
	@Autowired
	private ColorService colorService;
	@Autowired
	
	private ProducerService producerService;
	@Autowired
	
	private MaService maService;

	@Autowired
	private CategoryService categoryService;
	
	 @Autowired
	    private EditDrumValidator editDrumValidator;
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
	 
	        if (target.getClass() == DrumInfo.class) {
	            dataBinder.setValidator(editDrumValidator); 
	        }
	        if (target.getClass() == Producer.class) {
	            dataBinder.setValidator(editProducerValidator); 
	        }
	        if (target.getClass() == Color.class) {
	            dataBinder.setValidator(editColorValidator); 
	        }
	    }
	
	    private void AddAtribute(Model model){
			model.addAttribute("filter", new DrumFilter(-1, -1, -1, -1));
			model.addAttribute("producers", drumFilterDao.list5Producer());
			model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterDrum));
		}
	    private void AddCategoryColorProduct(Model model){
	    	model.addAttribute("categoris", categoryService.findByIdprContaining(WebProjectApplication.filterDrum));
			model.addAttribute("colors", colorService.findAll());
			model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterDrum));
			
		}
	

	/*view drum*/
	@GetMapping("/addrum")
	public String addrum(Model model) {
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR");
		model.addAttribute("infos", drumDao.findAllDrumInfo());
		return "/admin/DrumAd";
	}


	/*edit drum*/
	@GetMapping("/addrum/{id}/edit")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("filter",WebProjectApplication.filterDrum);
		model.addAttribute("contact", drumDao.findDrumInfoSave(id));
		AddCategoryColorProduct(model);
		/*model.addAttribute("filterDrum",WebProjectApplication.filterDrum );
		*/return "/admin/DrumEdit";
	}
	@PostMapping("/addrum/save")
	public String save ( Model model, @ModelAttribute("contact") @Validated DrumInfo contact, BindingResult result, 
			RedirectAttributes redirect ) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			return "/admin/DrumEdit";
		}
		/*if(contact.getFileData().isEmpty()){
			contact.setFileData(drumService.findOne(contact.getId()).getImage());
		}*/
		contact.setValid(true);
		drumDao.save(contact);
		//drumService.save(drum);
		redirect.addFlashAttribute("success", "Saved drum successfully!");
		return "redirect:/addrum";
	}
	@GetMapping("/addrum/create")
	public String create( Model model) {
		DrumInfo gt=new DrumInfo();
		
		
		gt.setId(maService.findOne(1).getDrum());
		gt.setRate(0);
		gt.setLuotdanhgia(0);
		model.addAttribute("filter",WebProjectApplication.filterDrum);
		model.addAttribute("contact",gt );
		AddCategoryColorProduct(model);
		return "/admin/DrumCreate";
	}
	@PostMapping("/addrum/savecreate")
	public String saveCreate(Model model, @ModelAttribute("contact") @Validated DrumInfo contact, BindingResult result, 
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			
			return "/admin/DrumCreate";
		}
		contact.setValid(true);
		drumDao.SaveCreate(contact);
		//drumService.save(drum);
		redirect.addFlashAttribute("success", "Saved drum successfully!");
		return "redirect:/addrum";
	}
	@GetMapping("/addrum/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		
		drumService.delete(id);
	
		redirect.addFlashAttribute("success", "Deleted drum successfully!");
		return "redirect:/addrum";
	}
	/*load image*/
	@RequestMapping(value = { "/productImagedrum" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("code") String code) throws IOException {
        Drum product = null;
        if (code != null) {
        	
            product = this.drumService.findOne(Integer.parseInt(code));
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
	
	/*@GetMapping("/adproducerDr")
	public String ProducerAd(Model model) {
		model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterDrum));
		
		model.addAttribute("filter",WebProjectApplication.filterDrum );
		return "/admin/ProducerAd";
	}*/
	}
