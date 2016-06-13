package board.command;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.ConstructionDAO;
import board.model.ConstructionDTO;
import util.StringUtil;

public class ConstructionListCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int pageno = Integer.parseInt(StringUtil.nchk(request.getParameter("pageno"), "1"));
		String[] checked=request.getParameterValues("check");
		try {
			String searchKeyword = URLDecoder.decode(StringUtil.nchk(request.getParameter("searchKeyword"),""),"UTF-8");
			ConstructionDAO dao = new ConstructionDAO();
			int totalcnt = dao.cntTotalMember(searchKeyword, checked);
			ArrayList<ConstructionDTO> list = dao.selectConstructionList(searchKeyword, pageno, totalcnt, checked);
			request.setAttribute("constructionList",list);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		


	}

}
