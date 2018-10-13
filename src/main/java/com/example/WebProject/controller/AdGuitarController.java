
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
import com.example.WebProject.dao.GuitarFilterDao;
import com.example.WebProject.WebProjectApplication;
import com.example.WebProject.dao.GuitarDao;
import com.example.WebProject.entity.Guitar;
import com.example.WebProject.entity.Ma;
import com.example.WebProject.entity.Producer;
import com.example.WebProject.entity.Color;
import com.example.WebProject.model.GuitarInfo;
import com.example.WebProject.model.PianoFilter;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.GuitarService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;
import com.example.WebProject.validator.EditColorValidator;
import com.example.WebProject.validator.EditGuitarValidator;
import com.example.WebProject.validator.EditProducerValidator;


@Controller
public class AdGuitarController {
	@Autowired
	private GuitarService guitarService;
	@Autowired
	private GuitarDao guitarDao;
	@Autowired
	private GuitarFilterDao guitarFilterDao;
	@Autowired
	private ColorService colorService;
	@Autowired
	
	private ProducerService producerService;
	@Autowired
	
	private MaService maService;

	@Autowired
	private CategoryService categoryService;
	
	 @Autowired
	    private EditGuitarValidator editGuitarValidator;
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
	 
	        if (target.getClass() == GuitarInfo.class) {
	            dataBinder.setValidator(editGuitarValidator); 
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
			model.addAttribute("producers", guitarFilterDao.list5Producer());
			model.addAttribute("categories", categoryService.findByIdprContaining(WebProjectApplication.filterGuitar));
		}
	    private void AddCategoryColorProduct(Model model){
	    	model.addAttribute("categoris", categoryService.findByIdprContaining(WebProjectApplication.filterGuitar));
			model.addAttribute("colors", colorService.findAll());
			model.addAttribute("producers", producerService.findByIdprContaining(WebProjectApplication.filterGuitar));
			
		}
		
	/*view*/
	@GetMapping("/admin")
	public String admin() {
		
		return "/admin/Admin";
	}
	/*view guitar*/
	@GetMapping("/adguitar")
	public String adguitar(Model model) {
		AddAtribute(model);
		model.addAttribute("tittle", "GUITAR");
		model.addAttribute("guitars", guitarDao.findAllGuitarInfo());
		return "/admin/GuitarAd";
	}
	/*@GetMapping("/adguitar")
	public String index(Model model,HttpServletRequest request
			,RedirectAttributes redirect) {
		request.getSession().setAttribute("guitarlist", null);
		//request.getSession().setAttribute("list", null);
		
		if(model.asMap().get("success") != null)
			redirect.addFlashAttribute("success",model.asMap().get("success").toString());
		return "redirect:/adguitar/page/1";
	}*/

	/*edit guitar*/
	@GetMapping("/adguitar/{id}/edit")
	public String edit(@PathVariable int id, Model model) {
		
		model.addAttribute("contact", guitarDao.findGuitarInfoSave(id));
		AddCategoryColorProduct(model);
		model.addAttribute("filter",WebProjectApplication.filterGuitar);
		return "/admin/GuitarEdit";
	}
	@PostMapping("/adguitar/save")
	public String save ( Model model, @ModelAttribute("contact") @Validated GuitarInfo contact, BindingResult result, 
			RedirectAttributes redirect ) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			return "/admin/GuitarEdit";
		}
	
		contact.setValid(true);
		guitarDao.save(contact);
		//guitarService.save(guitar);
		redirect.addFlashAttribute("success", "Saved guitar successfully!");
		return "redirect:/adguitar";
	}
	@GetMapping("/adguitar/create")
	public String create( Model model) {
		GuitarInfo gt=new GuitarInfo();
		
		
		gt.setId(maService.findOne(1).getGuitar());
		gt.setRate(0);
		gt.setLuotdanhgia(0);
		
		model.addAttribute("contact",gt );
		AddCategoryColorProduct(model);
		model.addAttribute("filter",WebProjectApplication.filterGuitar);
		return "/admin/GuitarCreate";
	}
	@PostMapping("/adguitar/savecreate")
	public String saveCreate(Model model, @ModelAttribute("contact") @Validated GuitarInfo contact, BindingResult result, 
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			contact.setValid(false);
			AddCategoryColorProduct(model);
			
			return "/admin/GuitarCreate";
		}
		contact.setValid(true);
		guitarDao.SaveCreate(contact);
		//guitarService.save(guitar);
		redirect.addFlashAttribute("success", "Saved guitar successfully!");
		return "redirect:/adguitar";
	}
	@GetMapping("/adguitar/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		
		guitarService.delete(id);
	
		redirect.addFlashAttribute("success", "Deleted guitar successfully!");
		return "redirect:/adguitar";
	}
	/*load image*/
	@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("code") String code) throws IOException {
        Guitar product = null;
        if (code != null) {
        	
            product = this.guitarService.findOne(Integer.parseInt(code));
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
	/*@GetMapping("/adguitar/{id}/page/{pageNumber}")

	public String showEmployeePage(HttpServletRequest request, 
			@PathVariable int pageNumber, @PathVariable int id, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("guitarlist");
		int pagesize = 3;
		List<GuitarInfo> list=new ArrayList<GuitarInfo>();
		switch(id){
		case 1:
			list =(List<GuitarInfo>) guitarDao.findAllGuitarInfo();
			break;
		case 2:
			list =(List<GuitarInfo>) guitarFilterDao.filterCategory(id, guitarDao.findAllGuitarInfo());
			break;
		case 3:
			list =(List<GuitarInfo>) guitarFilterDao.filterProducer(id, guitarDao.findAllGuitarInfo());
			break;
		case 4:
			list =(List<GuitarInfo>)ListPrice(p1, guitarDao.findAllGuitarInfo());
			break;
		case 5:
			list =(List<GuitarInfo>)
			break;
		}
		//List<GuitarInfo> list =(List<GuitarInfo>) guitarDao.findAllGuitarInfo();
		System.out.println(list.size());
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("guitarlist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/adguitar/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		//model.addAttribute("employees", pages);
		model.addAttribute("filter", new GuitarFilter(-1, -1, -1, -1));
		model.addAttribute("producers", guitarFilterDao.list5Producer());
		model.addAttribute("categories", categoryService.findAll());
		
		model.addAttribute("tittle", "GUITAR");
		model.addAttribute("guitars", pages);
		return "/admin/GuitarAd";
	}
	*/
	
	
	
	
	
	
	/*producer*/
	@GetMapping("/adproducerGt/{idpr}")
	public String ProducerAd(Model model, @PathVariable int idpr) {
		/*model.addAttribute("producers", producerService.findByIdprContaining(idpr));
		
		model.addAttribute("filter",idpr );*/
		PrAddModel(model, idpr);
		return "/admin/ProducerAd";
	}
	@GetMapping("/adproducer/{id}/edit")
	public String editProducer(@PathVariable int id,  Model model) {
		
		Producer pr=producerService.findOne(id);
		
		model.addAttribute("contact",pr);
		return "/admin/ProducerEdit";
	}
	@GetMapping("/adproducer/{fil}/create")
	public String createProducer( Model model, @PathVariable int fil) {
		Producer p=new Producer();
		p.setPid(maService.findOne(1).getProducer());
		p.setIdpr(fil);
		model.addAttribute("contact",p );
		
		
		return "/admin/ProducerEdit";
	}
	@GetMapping("/adproducer/{id}/delete/{idpr}")
	public String Producer( Model model, @PathVariable int id,@PathVariable int idpr, RedirectAttributes redirect) {
		
		producerService.delete(id);
		PrAddModel(model, idpr);
		model.addAttribute("success", "Deleted producer successfully!");
		return "/admin/ProducerAd";
	}
	@PostMapping("/adproducer/save")
	public String saveProducer(Model model, @ModelAttribute("contact") @Validated Producer contact, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			
			return "/admin/ProducerEdit";
		}
		if(producerService.findOne(contact.getPid())==null){
			
			Ma ma=maService.findOne(1);
	  		Ma vma=new Ma(1,ma.getGuitar(), ma.getPiano(), ma.getUkulele(),
	  				ma.getDrum(), ma.getFlute(), ma.getAccessory(), ma.getProducer()+1,
	  				ma.getColor() );
	  		maService.save(vma);
		}
		
		producerService.save(contact);
		PrAddModel(model, contact.getIdpr());
	/*	model.addAttribute("producers", producerService.findByIdprContaining(contact.getIdpr()));
		model.addAttribute("filter",contact.getIdpr() );*/
		model.addAttribute("success", "Saved producer successfully!");
		return "/admin/ProducerAd";
		
	}
private void PrAddModel(Model model, int idpr){
	model.addAttribute("producers", producerService.findByIdprContaining(idpr));
	model.addAttribute("filter",idpr);
}
	/*color*/
	@GetMapping("/adcolor")
	public String ColorAd(Model model) {
		model.addAttribute("colors", colorService.findAll());
		return "/admin/ColorAd";
	}
	@GetMapping("/adcolor/{id}/edit")
	public String editColor(@PathVariable int id, Model model) {
		
		
		model.addAttribute("contact", colorService.findOne(id));
		return "/admin/ColorEdit";
	}
	@GetMapping("/adcolor/create")
	public String createColor( Model model) {
		Color p=new Color();
		p.setId(maService.findOne(1).getColor());
		
		model.addAttribute("contact",p );
		
		
		return "/admin/ColorEdit";
	}
	@GetMapping("/adcolor/{id}/delete")
	public String Color(@PathVariable int id, RedirectAttributes redirect) {
		
		colorService.delete(id);
	
		redirect.addFlashAttribute("success", "Deleted color successfully!");
		return "redirect:/adcolor";
	}
	@PostMapping("/adcolor/save")
	public String saveColor(@ModelAttribute("contact") @Validated Color contact, BindingResult result, RedirectAttributes redirect) {
		
		if (result.hasErrors()) {
			return "/admin/ColorEdit";
		}
		if(colorService.findOne(contact.getId())==null){
			Ma ma=maService.findOne(1);
	  		Ma vma=new Ma(1,ma.getGuitar(), ma.getPiano(), ma.getUkulele(),
	  				ma.getDrum(), ma.getFlute(), ma.getAccessory(), ma.getProducer(),
	  				ma.getColor() +1);
	  		maService.save(vma);
		}
		  
		colorService.save(contact);
		redirect.addFlashAttribute("success", "Saved color successfully!");
		return "redirect:/adcolor";
	}
}
