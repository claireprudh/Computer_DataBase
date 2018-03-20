package com.excilys.formation.cdb.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mappers.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.pagination.Book;
import com.excilys.formation.cdb.pagination.Page;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.tag.PageTag;

@Controller
public class DashboardServlet {

	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerMapper computerMapper;

	private String searchValue = "";
	public static int noPage = 1;
	private Book book;
	private Page page;

	@GetMapping("dashboard")
	public String getDashboardPage(ModelMap model, @RequestParam Map<String, String> params) {
				
		book = new Book(50, computerService.getSearchCount(searchValue), searchValue);
		page = book.getPage(noPage);
		managePage(model, params);
		return "dashboard";
	}

	@PostMapping("dashboard")
	public String deleteComputer(ModelMap model, @RequestParam Map<String, String> params) {

		managePage(model, params);
		
		if (params.get("selection") != null) {

			List<String> selection = Arrays.asList(params.get("selection").split(","));

			for (String s : selection) {
				computerService.deleteComputer(Integer.valueOf(s));
			}		
		}

		return "dashboard";

	}

	public void managePage(ModelMap model, Map<String, String> params) {

		//Gestion no de page
		noPage = Integer.valueOf(params.getOrDefault("page", "1"));
		model.addAttribute("page", noPage);

		//Gestion nb de computers par page 
		int nbByPage = Integer.valueOf(params.getOrDefault("nbbypage", "50"));
		book.setNbComputer(Integer.valueOf(nbByPage));
		

		//Gestion contenu de la page
		List<ComputerDTO> list = new ArrayList<ComputerDTO>();

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


}
