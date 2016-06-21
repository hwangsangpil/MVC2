package board.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.AdminDAO;
import board.model.AdminDTO;
import util.StringUtil;

public class AdminListFirstCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		int pageno = Integer.parseInt(StringUtil.nchk(request.getParameter("pageno"),"1"));
		String[] checked = request.getParameterValues("check");
		String searchKeyword =StringUtil.nchk(request.getParameter("searchKeyword"), "");
		
		AdminDAO dao = new AdminDAO();
		int totalcnt = dao.AdminListTotalCnt(searchKeyword, checked);
		ArrayList<AdminDTO> list = dao.AdminList(searchKeyword, pageno, totalcnt, checked);
		
		request.setAttribute("pageno", String.valueOf(pageno));
		request.setAttribute("checked", checked);
		request.setAttribute("searchKeyword", searchKeyword);
		
		request.setAttribute("adminList", list);
		request.setAttribute("totalcnt", String.valueOf(totalcnt));
	}

}
