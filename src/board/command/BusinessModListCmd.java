package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BusinessDAO;
import util.StringUtil;

public class BusinessModListCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
			HttpSession session = request.getSession();
			
			String pageCast = (String)session.getAttribute("pageno");
			int pageno = Integer.parseInt(pageCast);
			String[] checked = (String[])session.getAttribute("checked");
			String searchKeyword = (String)session.getAttribute("searchKeyword");
			request.setAttribute("pageno", String.valueOf(pageno));
			request.setAttribute("checked", checked);
			request.setAttribute("searchKeyword", searchKeyword);
			
	}

}
