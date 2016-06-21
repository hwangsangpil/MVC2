package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BusinessViewModListCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		int pageno = Integer.parseInt((String)session.getAttribute("pageno"));
		String[] checked = (String[])session.getAttribute("checked");
		String searchKeyword = (String)session.getAttribute("searchKeyword");
		request.setAttribute("pageno", String.valueOf(pageno));
		request.setAttribute("checked", checked);
		request.setAttribute("searchKeyword", searchKeyword);
		
		
	}

}
