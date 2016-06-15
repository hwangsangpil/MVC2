package board.command;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.ConstructionDAO;
import board.model.ConstructionDTO;
import util.StringUtil;

public class ConstructionListFirstCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		HttpSession session=request.getSession();
		
		int pageno = Integer.parseInt(StringUtil.nchk(request.getParameter("pageno"), "1"));
		String[] checked=request.getParameterValues("check");
		
		try {
			String searchKeyword = URLDecoder.decode(StringUtil.nchk(request.getParameter("searchKeyword"),""),"UTF-8");
			ConstructionDAO dao = new ConstructionDAO();
			int totalcnt = dao.constructionListTotalCnt(searchKeyword, checked);
			ArrayList<ConstructionDTO> list = dao.constructionList(searchKeyword, pageno, totalcnt, checked);
			
			request.setAttribute("constructionList",list);
			request.setAttribute("totalcnt", String.valueOf(totalcnt));
			request.setAttribute("checked", checked);
			request.setAttribute("searchKeyword", searchKeyword);
			request.setAttribute("pageno", String.valueOf(pageno));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
