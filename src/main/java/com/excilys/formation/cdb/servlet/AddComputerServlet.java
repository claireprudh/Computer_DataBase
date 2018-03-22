package com.excilys.formation.cdb.servlet;

import java.util.ArrayList;
import java.util.List;
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
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.validator.ComputerDTOValidator;

@Controller
public class AddComputerServlet {

	private final ComputerService computerService;

	private final CompanyService companyService;

	private final ComputerMapper computerMapper;

	private final CompanyMapper companyMapper;

	private final ComputerDTOValidator computerValidator;

	public AddComputerServlet(ComputerService computerService, 
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

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(computerValidator);
	}

	@GetMapping("addComputer")
	public String getAddPage(ModelMap model, @RequestParam Map<String, String> params) {

		remplirAffichage(model);

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

	public void remplirAffichage(ModelMap model) {

		List<CompanyDTO> listCompanies = new ArrayList<>();

		for (Company company : companyService.getList()) {
			listCompanies.add(companyMapper.map(company));                                                                                                                                                
		}
		listCompanies.add(new CompanyDTO());

		model.addAttribute("listCompanies", listCompanies);

	}


}
