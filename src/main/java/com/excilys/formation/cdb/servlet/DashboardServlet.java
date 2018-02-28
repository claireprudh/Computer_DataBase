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

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int nbCompByPage = 30;
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

		


		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doHead(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		super.doHead(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int nbCompByPage = 30;
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

		if (request.getParameter("selection") != null) {

			String selection = request.getParameter("selection");
			System.out.println(selection);

		}
		
		if (request.getParameter("selection") != null) {

			List<String> selection = Arrays.asList(request.getParameter("selection").split(","));
			
			for (String s : selection) {
				ComputerService.getInstance().deleteComputer(Integer.valueOf(s));
			}
			
			

		}


		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

	}
}
