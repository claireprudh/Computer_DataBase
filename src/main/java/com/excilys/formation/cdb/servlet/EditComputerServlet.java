package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.ihm.Page;
import com.excilys.formation.cdb.mappers.CompanyMapper;
import com.excilys.formation.cdb.mappers.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;


@SuppressWarnings("serial")
@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {

	ComputerDTO computerdto;
	Computer computer;
	
	List<CompanyDTO> listCompanies = new ArrayList<CompanyDTO>();
	int id;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("id") != null) {
			try {
				
			id = Integer.valueOf(request.getParameter("id"));
			computer = ComputerService.getInstance().getDetails(id);
			computerdto = ComputerMapper.getInstance().map(computer);
			} catch (NumberFormatException nfe) {
				
			}
			
		}
		
		request.setAttribute("computer", computerdto);
		
		for (Company company : CompanyService.getInstance().getList()) {
			listCompanies.add(CompanyMapper.getInstance().map(company));
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
		
		ComputerService.getInstance().update(ComputerMapper.getInstance().map(computerdto));
		
		this.getServletContext().getRequestDispatcher("/dashboard?page=" + Page.getNoPage()).forward(request, response);
	}

	

}
