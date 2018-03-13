package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mappers.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.pagination.Book;
import com.excilys.formation.cdb.pagination.Page;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.tag.PageTag;

@Controller
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8114638763563191433L;

	@Autowired
	private ComputerService computerService /*= ComputerService.getInstance()*/;
	
	private String searchValue = "";
	public static int noPage = 1;
	private Book book;
	private Page page;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		book = new Book(50, computerService.getSearchCount(searchValue), searchValue);
		page = book.getPage(noPage);
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

		request.setAttribute("searchValue", searchValue);
		
		//Nombre de résultats
		int count = computerService.getSearchCount(searchValue);
		request.setAttribute("count", count);
		
		//Nombre de pages
		book.setPageMax(count);
		request.setAttribute("maxPage", book.getPageMax());	
		
		//Récupération de la page
		page = book.getPage(noPage);
		
		computerService.fillPage(page);
		
		//Récupération de la liste à afficher
		for (Computer c : page.getListComputers()) {
			list.add(ComputerMapper.getInstance().map(c));
		}
		
		request.setAttribute("list", list);
		
		PageTag.setMax(book.getPageMax());
		PageTag.setCurrent(page.getNoPage());
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	    AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
	    autowireCapableBeanFactory.autowireBean(this);
	}
}
