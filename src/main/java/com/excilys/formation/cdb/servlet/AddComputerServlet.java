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
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6742178476293935906L;
	
	@Autowired
	private ComputerService computerService/* = ComputerService.getInstance()*/;
	
	@Autowired
	private CompanyService companyService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	    AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
	    autowireCapableBeanFactory.autowireBean(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		remplirAffichage(request);

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);

	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		Computer computer = ComputerMapper.getInstance().map(remplirAffichage(request));

		computerService.createNew(computer);

		response.sendRedirect("dashboard?page=" + DashboardServlet.noPage);

	}

	public ComputerDTO remplirAffichage(HttpServletRequest request) {

		ComputerDTO computer = new ComputerDTO();

		List<CompanyDTO> listCompanies = new ArrayList<CompanyDTO>();

		if (request.getParameter("name") != null) {
			computer.setName(request.getParameter("name"));

		} 
		if (request.getParameter("introduced") != null) {
			computer.setIntroduced(request.getParameter("introduced"));
		}
		if (request.getParameter("discontinued") != null) {
			computer.setDiscontinued(request.getParameter("discontinued"));
		}
		if (request.getParameter("companyId") != null) {

			computer.setCompanyId(Integer.valueOf(request.getParameter("companyId")));
		}



		for (Company company : companyService.getList()) {
			listCompanies.add(CompanyMapper.getInstance().map(company));
		}
		listCompanies.add(new CompanyDTO());

		request.setAttribute("listCompanies", listCompanies);

		return computer;
	}
	
	
}
