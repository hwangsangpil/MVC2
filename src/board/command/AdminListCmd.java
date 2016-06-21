package board.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.AdminDAO;
import board.model.AdminDTO;
import util.StringUtil;

public class AdminListCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		int pageno = Integer.parseInt(StringUtil.nchk(request.getParameter("pageno"), (String)request.getAttribute("pageno"), "1"));
		String[] checked = request.getParameterValues("check");
		if(checked == null){
			checked = (String[])request.getAttribute("checked");
		}
		String searchKeyword =StringUtil.nchk(request.getParameter("searchKeyword"), (String)request.getAttribute("searchKeyword") , "");
		
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
