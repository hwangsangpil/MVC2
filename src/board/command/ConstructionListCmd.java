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

public class ConstructionListCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
			HttpSession session=request.getSession();
		try {
			
			int pageno = Integer.parseInt(StringUtil.nchk(request.getParameter("pageno"), "1"));
			String[] checked=request.getParameterValues("check");
			String searchKeyword = URLDecoder.decode(StringUtil.nchk(request.getParameter("searchKeyword"),""),"UTF-8");
			
			ConstructionDAO dao = new ConstructionDAO();
			
			int totalcnt = dao.cntTotalMember(searchKeyword, checked);
			ArrayList<ConstructionDTO> list = dao.selectConstructionList(searchKeyword, pageno, totalcnt, checked);

			request.setAttribute("constructionList",list);

			session.setAttribute("checked", checked);
			session.setAttribute("searchKeyword", searchKeyword);
			session.setAttribute("pageno", String.valueOf(pageno));
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		


	}

}