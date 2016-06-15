package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.ConstructionDAO;
import board.model.ConstructionDTO;
import util.StringUtil;

public class ConstructionModListCmd implements Cmd{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		HttpSession session=request.getSession();
		
		String[] checked=(String[])session.getAttribute("checked");
		String pageCast=(String)session.getAttribute("pageno");
		int pageno = Integer.parseInt(pageCast);
		String searchKeyword = (String)session.getAttribute("searchKeyword");
		request.setAttribute("checked", checked);
		request.setAttribute("searchKeyword", searchKeyword);
		request.setAttribute("pageno", String.valueOf(pageno));
	}

}
