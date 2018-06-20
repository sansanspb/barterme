package ru.iqdevelop.barterme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.iqdevelop.barterme.entities.CategoryEntity;
import ru.iqdevelop.barterme.entities.CompanyEntity;
import ru.iqdevelop.barterme.services.CategoriesService;
import ru.iqdevelop.barterme.services.CompanyService;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

	@RequestMapping(value = "/notification", method = RequestMethod.GET)
	public ModelAndView printHello() {
		ModelAndView model = new ModelAndView();
		model.setViewName("notification");
		return model;
	}


	@Autowired
	CategoriesService categoriesService;

	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		List<CategoryEntity> allParents = categoriesService.getParents();

		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		model.addObject("parents", allParents);
		return model;
	}

	@RequestMapping(value = "/welcome**", method = RequestMethod.GET)
	public ModelAndView defaultPage() {

	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This is default page!");
	  model.setViewName("hello");
	  return model;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This page is for ROLE_ADMIN only!");
	  model.setViewName("admin");
	  return model;

	}

	@RequestMapping(value = "/cabinet", method = RequestMethod.GET)
	public ModelAndView cabinetPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("cabinet");
		return model;
	}

	@PreAuthorize("hasRole('ROLE_CONFIRMED_USER')")
	@RequestMapping(value = "/requests", method = RequestMethod.GET)
	public ModelAndView requests() {
		ModelAndView model = new ModelAndView();
		model.setViewName("requests");
		return model;
	}

	@RequestMapping(value = "/listing", method = RequestMethod.GET)
	public ModelAndView listing(@RequestParam(name = "id") Long id) {
		List<CategoryEntity> childs = categoriesService.getSubcategs(id);
		List<CategoryEntity> allParents = categoriesService.getParents();
		CategoryEntity categ = categoriesService.getById(id);

		ModelAndView model = new ModelAndView();
		model.setViewName("listing");
		model.addObject("subcategories", childs);
		model.addObject("parents", allParents);
		model.addObject("currentParentCateg", categ);
		return model;
	}

	@RequestMapping(value = "/blog-page", method = RequestMethod.GET)
	public ModelAndView blogPage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("blog-page");
		return model;
	}

	@PreAuthorize("hasRole('ROLE_CONFIRMED_USER')")
	@RequestMapping(value = "/insideCateg", method = RequestMethod.GET)
	public ModelAndView insideCateg(@RequestParam(name = "parentId") Long parentId, @RequestParam(name = "subId") Long subId) {
		List<CategoryEntity> childs = categoriesService.getSubcategs(parentId);
		List<CategoryEntity> allParents = categoriesService.getParents();
		CategoryEntity currentParentCateg = categoriesService.getById(parentId);
		CategoryEntity currentCateg = categoriesService.getById(subId);

		ModelAndView model = new ModelAndView();
		model.setViewName("inside-categ");
		model.addObject("subcategories", childs);
		model.addObject("parents", allParents);
		model.addObject("currentParentCateg", currentParentCateg);
		model.addObject("currentCateg", currentCateg);
		return model;
	}

	@PreAuthorize("hasRole('ROLE_CONFIRMED_USER')")
	@RequestMapping(value = "/cabinet-edit", method = RequestMethod.GET)
	public ModelAndView cabinetEditPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("cabinet-edit");
		return model;
	}

	@PreAuthorize("hasRole('ROLE_CONFIRMED_USER')")
	@RequestMapping(value = "/card", method = RequestMethod.GET)
	public ModelAndView listing(@RequestParam(name = "parentId") Long parentId, @RequestParam(name = "subId") Long subId, @RequestParam(name = "companyId") Long companyId) {
		List<CategoryEntity> allParents = categoriesService.getParents();
		CategoryEntity currentParentCateg = categoriesService.getById(parentId);
		CategoryEntity currentCateg = categoriesService.getById(subId);
		CompanyEntity company = companyService.getById(companyId);

		ModelAndView model = new ModelAndView();
		model.setViewName("card");
		model.addObject("parents", allParents);
		model.addObject("currentParentCateg", currentParentCateg);
		model.addObject("currentCateg", currentCateg);
		model.addObject("company", company);
		return model;
	}

	@RequestMapping(value = "auth/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

	  ModelAndView model = new ModelAndView();
	  if (error != null) {
		model.addObject("error", "Не верная пара логин\\пароль.");
	  }

	  if (logout != null) {
		model.addObject("msg", "Вы вышли из личного кабинета.");
	  }
	  model.setViewName("auth/customLogin");

	  return model;

	}
	
	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accessDenied() {

	  ModelAndView model = new ModelAndView();
		
	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();	
		model.addObject("username", userDetail.getUsername());
	  }
		
	  model.setViewName("403");
	  return model;
	}

}
