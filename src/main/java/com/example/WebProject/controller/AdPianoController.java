
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
import com.example.WebProject.dao.PianoFilterDao;
import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.dao.PianoDao;
import com.example.WebProject.entity.Piano;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.entity.Color;
import com.example.WebProject.model.PianoFilter;
import com.example.WebProject.model.PianoInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.PianoService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;
import com.example.WebProject.validator.EditColorValidator;
import com.example.WebProject.validator.EditPianoValidator;
import com.example.WebProject.validator.EditProducerValidator;


@Controller
public class AdPianoController {
	@Autowired
	private PianoService pianoService;
	@Autowired
	private PianoDao pianoDao;
	@Autowired
	private PianoFilterDao pianoFilterDao;
	@Autowired
	private ColorService colorService;
	@Autowired
	
	private ProducerService producerService;
	@Autowired
	
	private MaService maService;

	@Autowired
	private CategoryService categoryService;
	
	 @Autowired
	    private EditPianoValidator editPianoValidator;
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
	 
	        if (target.getClass() == PianoInfo.class) {
	            dataBinder.setValidator(editPianoValidator); 
	        }
	        if (target.getClass() == Producer.class) {
	            dataBinder.setValidator(editProducerValidator); 
	        }
	        if (target.getClass() == Color.class) {
	            dataBinder.setValidator(editColorValidator); 
	        }
	    }
	
	    private void AddAtribute(Model model){
			model.addAttribute("filter", new PianoFilter(-1, -1, -1, -1));
			model.addAttribute("producers", pianoFilterDao.list5Producer());
			model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterPiano));
		}
	    private void AddCategoryColorProduct(Model model){
	    	model.addAttribute("categoris", categoryService.findByIdprContaining(WebProjectApplication.filterPiano));
			model.addAttribute("colors", colorService.findAll());
			model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterPiano));
			
		}
	

	/*view piano*/
	@GetMapping("/adpiano")
	public String adpiano(Model model) {
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR");
		model.addAttribute("infos", pianoDao.findAllPianoInfo());
		return "/admin/PianoAd";
	}


	/*edit piano*/
	@GetMapping("/adpiano/{id}/edit")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("filter",WebProjectApplication.filterPiano);
		model.addAttribute("contact", pianoDao.findPianoInfoSave(id));
		AddCategoryColorProduct(model);
		/*model.addAttribute("filterPiano",WebProjectApplication.filterPiano );
		*/return "/admin/PianoEdit";
	}
	@PostMapping("/adpiano/save")
	public String save ( Model model, @ModelAttribute("contact") @Validated PianoInfo contact, BindingResult result, 
			RedirectAttributes redirect ) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			return "/admin/PianoEdit";
		}
		/*if(contact.getFileData().isEmpty()){
			contact.setFileData(pianoService.findOne(contact.getId()).getImage());
		}*/
		contact.setValid(true);
		pianoDao.save(contact);
		//pianoService.save(piano);
		redirect.addFlashAttribute("success", "Saved piano successfully!");
		return "redirect:/adpiano";
	}
	@GetMapping("/adpiano/create")
	public String create( Model model) {
		PianoInfo gt=new PianoInfo();
		
		
		gt.setId(maService.findOne(1).getPiano());
		gt.setRate(0);
		gt.setLuotdanhgia(0);
		model.addAttribute("filter",WebProjectApplication.filterPiano);
		model.addAttribute("contact",gt );
		AddCategoryColorProduct(model);
		return "/admin/PianoCreate";
	}
	@PostMapping("/adpiano/savecreate")
	public String saveCreate(Model model, @ModelAttribute("contact") @Validated PianoInfo contact, BindingResult result, 
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			
			return "/admin/PianoCreate";
		}
		contact.setValid(true);
		pianoDao.SaveCreate(contact);
		//pianoService.save(piano);
		redirect.addFlashAttribute("success", "Saved piano successfully!");
		return "redirect:/adpiano";
	}
	@GetMapping("/adpiano/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		
		pianoService.delete(id);
	
		redirect.addFlashAttribute("success", "Deleted piano successfully!");
		return "redirect:/adpiano";
	}
	/*load image*/
	@RequestMapping(value = { "/productImagepiano" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("code") String code) throws IOException {
        Piano product = null;
        if (code != null) {
        	
            product = this.pianoService.findOne(Integer.parseInt(code));
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
	
	@GetMapping("/adproducerPa")
	public String ProducerAd(Model model) {
		model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterPiano));
		
		model.addAttribute("filter",WebProjectApplication.filterPiano );
		return "/admin/ProducerAd";
	}
	}
