package ecust.cs.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import ecust.cs.dao.BookInfoDao;
import ecust.cs.dao.DetailInfoDao;
import ecust.cs.model.AllInfo;
import ecust.cs.model.DetailInfo;

public class GetBookInfoServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String searchContent = req.getParameter("search").trim();
		String searchCategory = req.getParameter("category");

		req.getSession().setAttribute("searchContetnt", searchContent);
		req.getSession().setAttribute("searchCategory", searchCategory);
		
		req.getRequestDispatcher("searchPage.jsp").forward(req, resp);

	}
	
}
