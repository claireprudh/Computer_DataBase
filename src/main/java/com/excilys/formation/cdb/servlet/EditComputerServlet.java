package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
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

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mappers.CompanyMapper;
import com.excilys.formation.cdb.mappers.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;

@Controller
@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 625646021789794121L;
	
	@Autowired
	private ComputerService computerService /* = ComputerService.getInstance()*/;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private CompanyMapper companyMapper;
	private ComputerDTO computerdto;
	private Computer computer;
	
	List<CompanyDTO> listCompanies = new ArrayList<CompanyDTO>();
	int id;
	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	    AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
	    autowireCapableBeanFactory.autowireBean(this);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("id") != null) {
			try {
				
			id = Integer.valueOf(request.getParameter("id"));
			computer = computerService.getDetails(id);
			computerdto = computerMapper.map(computer);
			} catch (NumberFormatException nfe) {
				
			}
			
		}
		
		request.setAttribute("computer", computerdto);
		
		for (Company company : companyService.getList()) {
			listCompanies.add(companyMapper.map(company));
		}
		listCompanies.add(new CompanyDTO());

		request.setAttribute("listCompanies", listCompanies);



		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		computerdto.setName(request.getParameter("name"));
		computerdto.setIntroduced(request.getParameter("introduced"));
		computerdto.setDiscontinued(request.getParameter("discontinued"));
		computerdto.setCompanyId(Integer.valueOf(request.getParameter("companyId")));
		
		computerService.update(computerMapper.map(computerdto));
		
		response.sendRedirect("dashboard?page=" + DashboardServlet.noPage);
	}

	

	

}
