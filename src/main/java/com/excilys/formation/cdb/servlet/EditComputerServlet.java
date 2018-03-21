package com.excilys.formation.cdb.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class EditComputerServlet {

private ComputerService computerService;
	
	private final CompanyService companyService;
	
	private final ComputerMapper computerMapper;
	
	private final CompanyMapper companyMapper;
	
	private ComputerDTO computerdto;

	List<CompanyDTO> listCompanies = new ArrayList<>();
	int id;

	public EditComputerServlet(ComputerService computerService, 
			CompanyService companyService, 
			ComputerMapper computerMapper, 
			CompanyMapper companyMapper) {
		
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
		
	}
	
	@GetMapping("editComputer")
	public String getEditPage(ModelMap model, @RequestParam Map<String, String> params) {
		Computer computer;


		id = Integer.valueOf(params.getOrDefault("id", "0"));
		computer = computerService.getDetails(id);
		computerdto = computerMapper.map(computer);


		model.addAttribute("computer", computerdto);

		for (Company company : companyService.getList()) {
			listCompanies.add(companyMapper.map(company));
		}
		listCompanies.add(new CompanyDTO());

		model.addAttribute("listCompanies", listCompanies);
		return "editComputer";
	}
	
	@PostMapping("editComputer")
	public String editComputer(ModelMap model, @RequestParam Map<String, String> params) {

		computerdto.setName(params.get("name"));
		computerdto.setIntroduced(params.get("introduced"));
		computerdto.setDiscontinued(params.get("discontinued"));
		computerdto.setCompanyId(Integer.valueOf(params.get("companyId")));

		computerService.update(computerMapper.map(computerdto));

		return "editComputer";
	}





}
