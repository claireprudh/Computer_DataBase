package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mappers.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.pagination.Book;
import com.excilys.formation.cdb.pagination.Page;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.tag.PageTag;

@SuppressWarnings("serial")
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	private ComputerService computerService = ComputerService.getInstance();
	private String searchValue = "";
	public static int noPage = 1;
	private Book book = new Book(50, computerService.getSearchCount(searchValue), searchValue);
	private Page page = book.getPage(noPage);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		managePage(request);

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		managePage(request);

		if (request.getParameter("selection") != null) {

			List<String> selection = Arrays.asList(request.getParameter("selection").split(","));

			for (String s : selection) {
				computerService.deleteComputer(Integer.valueOf(s));
			}			
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

	}

	public void managePage(HttpServletRequest request) {


		//Gestion no de page
		if (request.getParameter("page") != null) {
			noPage = Integer.valueOf(request.getParameter("page"));
		} else {
			noPage = 1;
		}

		request.setAttribute("page", noPage);
		
		//Gestion nb de computers par page 
		if (request.getParameter("nbbypage") != null) {
			book.setNbComputer(Integer.valueOf(request.getParameter("nbbypage")));
		}
		
		//Gestion contenu de la page
		List<ComputerDTO> list = new ArrayList<ComputerDTO>();

		//Valeur de recherche
		if (request.getParameter("search") != null) {

			searchValue = request.getParameter("search");
			
			book.setContenu(searchValue);
		}

		System.out.println(searchValue);
		request.setAttribute("searchValue", searchValue);
		
		//Nombre de résultats
		int count = computerService.getSearchCount(searchValue);
		request.setAttribute("count", count);
		
		//Nombre de pages
		book.setPageMax(count);
		request.setAttribute("maxPage", book.getPageMax());	
		
		System.out.println("contenu = " + book.getContenu());
		 //Récupération de la page
		page = book.getPage(noPage);
		
		//Récupération de la liste à afficher
		for (Computer c : page.getListComputers()) {
			list.add(ComputerMapper.getInstance().map(c));
		}
		
		request.setAttribute("list", list);
		
		PageTag.setMax(book.getPageMax());
		PageTag.setCurrent(page.getNoPage());
	}


}
