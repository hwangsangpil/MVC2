package board.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BusinessDAO;
import board.model.BusinessDTO;
import util.StringUtil;

public class BusinessViewFirstCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		
		int pageno = 1;
		String[] checked = null;
		String searchKeyword = "";
		
		int constNum = Integer.parseInt(StringUtil.nchk(request.getParameter("constNum"),"1"));
		
		BusinessDAO dao = new BusinessDAO();
		int totalcnt = dao.businessViewTotalCnt(searchKeyword, constNum, checked);
		ArrayList<BusinessDTO> list = dao.businessView(constNum, pageno, searchKeyword, totalcnt, checked);
		
		request.setAttribute("pageno", String.valueOf(pageno));
		request.setAttribute("checked", checked);
		request.setAttribute("searchKeyword", searchKeyword);
		
		request.setAttribute("totalcnt", String.valueOf(totalcnt));
		request.setAttribute("businessView", list);
		request.setAttribute("constNum", String.valueOf(constNum));
	
	}

}
