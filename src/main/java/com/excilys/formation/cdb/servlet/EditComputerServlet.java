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
import com.excilys.formation.cdb.mappers.CompanyMapper;
import com.excilys.formation.cdb.mappers.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;


@SuppressWarnings("serial")
@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		remplirAffichage(request);



		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ComputerDTO computer = remplirAffichage(request);
		
		

		ComputerService.getInstance().update(ComputerMapper.getInstance().map(computer));

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	public ComputerDTO remplirAffichage(HttpServletRequest request) {
		
		ComputerDTO computer = new ComputerDTO();

		List<CompanyDTO> listCompanies = new ArrayList<CompanyDTO>();
		
		if (request.getParameter("id") != null) {
			try {
			computer.setId(Integer.valueOf(request.getParameter("id")));
			} catch (NumberFormatException nfe) {
				computer.setId(0); //TODO Ceci est une rustine de fortune à changer impérativement
			}
		}
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
		if (request.getParameter("selected") != null) {
			computer.setId(Integer.valueOf(request.getParameter("selected")));
		}

		
		
		for (Company company : CompanyService.getInstance().getList()) {
			listCompanies.add(CompanyMapper.getInstance().map(company));
		}

		request.setAttribute("listCompanies", listCompanies);
		
		return computer;
	}

}
