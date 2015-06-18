package ecust.cs.servlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import ecust.cs.dao.DetailInfoDao;
import ecust.cs.model.DetailInfo;

public class GetDetailInfoServlet extends HttpServlet {
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
		
		String searchContent = (String) req.getSession().getAttribute("searchContetnt");
		String searchCategory = (String) req.getSession().getAttribute("searchCategory");
		
		List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>();
		
		if(searchContent != ""){
			detailInfoList = DetailInfoDao.getDetailInfoBySearchAfterSegment(searchContent);
			
			if(searchCategory != null || searchCategory != ""){
				detailInfoList = DetailInfoDao.getSpecialCategoryBook(detailInfoList, searchCategory);
			}
		}
		


		JSONArray jsonarray = JSONArray.fromObject(detailInfoList);  
		
		resp.getWriter().print(jsonarray);

	}
}
