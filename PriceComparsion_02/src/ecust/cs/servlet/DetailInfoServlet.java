package ecust.cs.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import ecust.cs.dao.DetailInfoDao;
import ecust.cs.handle.GetBookDetailInfo;
import ecust.cs.model.AllInfo;
import ecust.cs.model.DetailInfo;

public class DetailInfoServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String picUrl = req.getParameter("picUrl");
		
		req.getSession().setAttribute("picUrl", picUrl);
		
		resp.getWriter().print("encryption");
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
}
