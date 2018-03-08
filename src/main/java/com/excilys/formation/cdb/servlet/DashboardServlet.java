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
import com.excilys.formation.cdb.ihm.Page;
import com.excilys.formation.cdb.mappers.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.tag.PageTag;

@SuppressWarnings("serial")
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {


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
				ComputerService.getInstance().deleteComputer(Integer.valueOf(s));
			}			

		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

	}

	public void managePage(HttpServletRequest request) {


		request.setAttribute("count", ComputerService.getInstance().getList().size());

		if (request.getParameter("page") != null) {
			Page.setNoPage(Integer.valueOf(request.getParameter("page")));

		} else {
			Page.setNoPage(1);
		}

		request.setAttribute("page", Page.getNoPage());
		
		if (request.getParameter("nbbypage") != null) {
			Page.setNbComputer(Integer.valueOf(request.getParameter("nbbypage")));
		}
		
		request.setAttribute("maxPage", ComputerService.getInstance().getMaxPage(Page.getNbComputer()));
		
		List<ComputerDTO> list = new ArrayList<ComputerDTO>();

		if (request.getParameter("search") == null) {

			for (Computer c : new Page(Page.getNbComputer(), Page.getNoPage()).getListComputers()) {
				list.add(ComputerMapper.getInstance().map(c));
			}
			
		} else {
			
			for (Computer c : new Page(Page.getNbComputer(), Page.getNoPage(), request.getParameter("search")).getListComputers()) {
				list.add(ComputerMapper.getInstance().map(c));
			}
			
		}

		request.setAttribute("list", list);

		PageTag.setCurrent(Page.getNoPage());
	}


}
