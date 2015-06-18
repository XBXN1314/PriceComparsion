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

public class LoadIndexBookServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		List<DetailInfo> detailInfoList = DetailInfoDao.getNumDetailInfo(3);
		
		JSONArray jsonarray = JSONArray.fromObject(detailInfoList);  
		
		out.print(jsonarray);
	}

}
