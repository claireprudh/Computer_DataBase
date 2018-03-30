package com.excilys.formation.cdb.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mappers.CompanyMapper;
import com.excilys.formation.cdb.mappers.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.pagination.Book;
import com.excilys.formation.cdb.pagination.Page;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.tag.PageTag;
import com.excilys.formation.cdb.validator.ComputerDTOValidator;

@Controller
public class WebController {

	private ComputerService computerService;
	
	private ComputerMapper computerMapper;

	private final CompanyService companyService;
	
	private final CompanyMapper companyMapper;
	
	private ComputerDTO computerdto;

	private final ComputerDTOValidator computerValidator;

	List<CompanyDTO> listCompanies = new ArrayList<>();
	int id;
	
	private String searchValue = "";
	private static int noPage = 1;
	private int nbByPage = 50;
	private Book book;
	private Page page;

	public WebController(ComputerService computerService, 
			CompanyService companyService, 
			ComputerMapper computerMapper, 
			CompanyMapper companyMapper,
			ComputerDTOValidator computerValidator) {

		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
		this.computerValidator = computerValidator;

	}
	
	@GetMapping("dashboard")
	public String getDashboardPage(ModelMap model, @RequestParam Map<String, String> params, Locale locale) {
		book = new Book(50, computerService.getSearchCount(searchValue), searchValue);
		page = book.getPage(noPage);
		managePage(model, params);
		return "dashboard";
	}

	@PostMapping("dashboard")
	public String deleteComputer(ModelMap model, @RequestParam Map<String, String> params, Locale locale) {

		managePage(model, params);
		
		if (params.get("selection") != null) {

			List<String> selection = Arrays.asList(params.get("selection").split(","));

			for (String s : selection) {
				computerService.deleteComputer(Integer.parseInt(s));
			}		
		}

		return "dashboard";

	}

	public void managePage(ModelMap model, Map<String, String> params) {

		String nbbypage = "nbbypage";
		
		//Gestion no de page
		noPage = Integer.parseInt(params.getOrDefault("page", "1"));
		model.addAttribute("page", noPage);

		//Gestion nb de computers par page 
		if (params.get(nbbypage) != "" && params.get(nbbypage) != null) {
			nbByPage = Integer.parseInt(params.get(nbbypage));
		}
		book.setNbComputer(nbByPage);
		model.addAttribute(nbbypage, nbByPage);

		//Gestion contenu de la page
		List<ComputerDTO> list = new ArrayList<>();

		//Valeur de recherche
		searchValue = params.getOrDefault("searchValue", "");
		book.setContenu(searchValue);
		model.addAttribute("searchValue", searchValue);

		//Nombre de résultats
		int count = computerService.getSearchCount(searchValue);
		model.addAttribute("count", count);

		//Nombre de pages
		book.setPageMax(count);
		model.addAttribute("maxPage", book.getPageMax());	

		//Récupération de la page
		page = book.getPage(noPage);

		computerService.fillPage(page);
		
		//Récupération de la liste à afficher
		for (Computer c : page.getListComputers()) {
			list.add(computerMapper.map(c));
		}

		model.addAttribute("list", list);

		PageTag.setMax(book.getPageMax());
		PageTag.setCurrent(page.getNoPage());
	}

	
	@GetMapping("editComputer")
	public String getEditPage(ModelMap model, @RequestParam Map<String, String> params) {
		Computer computer;


		id = Integer.valueOf(params.getOrDefault("id", "0"));
		computer = computerService.getDetails(id);
		computerdto = computerMapper.map(computer);


		model.addAttribute("computer", computerdto);

		fillCompanyList(model);
		
		return "editComputer";
	}
	
	@PostMapping("editComputer")
	public String editComputer(ModelMap model, @RequestParam Map<String, String> params) {

		computerdto.setName(params.get("name"));
		computerdto.setIntroduced(params.get("introduced"));
		computerdto.setDiscontinued(params.get("discontinued"));
		computerdto.setCompanyId(Integer.valueOf(params.get("companyId")));

		computerService.update(computerMapper.map(computerdto));

		return "redirect:dashboard";
	}
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(computerValidator);
	}

	@GetMapping("addComputer")
	public String getAddPage(ModelMap model, @RequestParam Map<String, String> params) {

		fillCompanyList(model);

		return "addComputer";
	}


	@PostMapping("addComputer")
	public String addComputer(@ModelAttribute("addForm") @Validated(ComputerDTO.class) ComputerDTO computerDTO, BindingResult bindingResult, ModelMap model, @RequestParam Map<String, String> params) {

		Computer computer = computerMapper.map(computerDTO);

		if (!bindingResult.hasErrors()) {
			computerService.createNew(computer);
			return "redirect:dashboard";

		} else {
			return "addComputer";
		}


	}

	public void fillCompanyList(ModelMap model) {

		List<CompanyDTO> listCompanies = new ArrayList<>();

		for (Company company : companyService.getList()) {
			listCompanies.add(companyMapper.map(company));                                                                                                                                                
		}
		listCompanies.add(new CompanyDTO());

		model.addAttribute("listCompanies", listCompanies);

	}



}
