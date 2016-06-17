package board.command;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BusinessDAO;
import board.model.BusinessDTO;
import util.StringUtil;

public class BusinessListFirstCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		int pageno = Integer.parseInt(StringUtil.nchk(request.getParameter("pageno"),"1"));
		String[] checked = request.getParameterValues("check");
		try {
			String searchKeyword = URLDecoder.decode(StringUtil.nchk(request.getParameter("searchKeyword"),""),"UTF-8");
			BusinessDAO dao = new BusinessDAO();
			int totalcnt = dao.businessListTotalCnt(searchKeyword, checked);
			ArrayList<BusinessDTO> list = dao.businessList(searchKeyword, pageno, totalcnt, checked);
		
			request.setAttribute("searchKeyword", searchKeyword);
			request.setAttribute("pageno", String.valueOf(pageno));
			request.setAttribute("checked", checked);
			request.setAttribute("totalcnt", String.valueOf(totalcnt));
			request.setAttribute("businessList", list);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
