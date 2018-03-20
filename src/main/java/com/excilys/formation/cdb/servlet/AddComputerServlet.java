package com.excilys.formation.cdb.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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

@Controller
public class AddComputerServlet {

	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@GetMapping("addComputer")
	public String getAddPage(ModelMap model, @RequestParam Map<String, String> params) {
		
		remplirAffichage(model, params);

		return "addComputer";
	}

	
	@PostMapping("addComputer")
	public String addComputer(ModelMap model, @RequestParam Map<String, String> params) {
		Computer computer = computerMapper.map(remplirAffichage(model, params));

		computerService.createNew(computer);

		return "addComputer";

	}

	public ComputerDTO remplirAffichage(ModelMap model, @RequestParam Map<String, String> params) {

		ComputerDTO computerDTO = new ComputerDTO();

		List<CompanyDTO> listCompanies = new ArrayList<CompanyDTO>();

		String name = params.getOrDefault("name", "");
			computerDTO.setName(name);
		 
		String introduced = params.getOrDefault("introduced", "");
		computerDTO.setIntroduced(introduced);
		
		String discontinued = params.getOrDefault("discontinued", "");
		computerDTO.setIntroduced(discontinued);
		
		int companyId = Integer.valueOf(params.getOrDefault("companyId", "0"));
		computerDTO.setCompanyId(companyId);
		
		for (Company company : companyService.getList()) {
			listCompanies.add(companyMapper.map(company));                                                                                                                                                
		}
		listCompanies.add(new CompanyDTO());

		model.addAttribute("listCompanies", listCompanies);

		return computerDTO;
	}
	
	
}
