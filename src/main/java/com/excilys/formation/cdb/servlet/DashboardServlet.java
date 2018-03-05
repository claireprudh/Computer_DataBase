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
		int nbCompByPage = Page.nbComputer;
		int page;

		request.setAttribute("count", ComputerService.getInstance().getList().size());

		request.setAttribute("maxPage", ComputerService.getInstance().getMaxPage(nbCompByPage));

		if (request.getParameter("page") != null) {
			page = Integer.valueOf(request.getParameter("page"));
		} else {
			page = 1;
		}
		
		request.setAttribute("page", page);

		List<ComputerDTO> list = new ArrayList<ComputerDTO>();
		for (Computer c : new Page(nbCompByPage, page).getListComputers()) {
			list.add(ComputerMapper.getInstance().map(c));
		}

		request.setAttribute("list", list);

		PageTag.setCurrent(page);
	}
	
	
}
