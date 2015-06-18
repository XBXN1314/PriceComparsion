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
import ecust.cs.model.DetailInfo;

public class GetBookDetailInfoServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String picUrl = (String) req.getSession().getAttribute("picUrl");
		
		List<DetailInfo> detailInfoList = DetailInfoDao.getBookDetailInfoByURL(picUrl);
		
		JSONArray jsonarray = JSONArray.fromObject(detailInfoList);  

		PrintWriter out = resp.getWriter();
		
		out.print(jsonarray);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
}
