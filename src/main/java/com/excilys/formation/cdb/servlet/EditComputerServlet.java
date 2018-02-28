package com.excilys.formation.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.ComputerDTO;


@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ComputerDTO computer = new ComputerDTO();


		computer.setId(Integer.valueOf(request.getParameter("id")));
		computer.setName(request.getParameter("name"));
		if (request.getParameter("introduced") != null) {
			computer.setIntroduced(request.getParameter("introduced"));
		}
		if (request.getParameter("discontinued") != null) {
			computer.setDiscontinued(request.getParameter("discontinued"));
		}
		if (request.getParameter("companyid") != null) {
			computer.setCompanyId(Integer.valueOf(request.getParameter("companyid")));
		}

		request.setAttribute("computer", computer);



		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Posting");
		ComputerDTO computer = new ComputerDTO();


		if (request.getParameter("id") != null) {
			computer.setId(Integer.valueOf(request.getParameter("id")));
		}
		if (request.getParameter("id") != null) {
			computer.setName(request.getParameter("name"));
			
		}
		if (request.getParameter("introduced") != null) {
			computer.setIntroduced(request.getParameter("introduced"));
			
		}
		if (request.getParameter("discontinued") != null) {
			computer.setDiscontinued(request.getParameter("discontinued"));
		}
		if (request.getParameter("companyid") != null) {
			computer.setCompanyId(Integer.valueOf(request.getParameter("companyid")));
		}
		
		request.setAttribute("computer", computer);

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

}
