package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.ConstructionDAO;

public class ConstructionDelOkCmd implements Cmd{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse resopnse){
		
		HttpSession session = request.getSession();
		
		String searchKeyword = (String)session.getAttribute("searchKeyword");
		String page = (String)session.getAttribute("pageno");
		int pageno=0;
		if(page!=null){
			pageno = Integer.parseInt(page);
		}
		String[] checked = (String[])session.getAttribute("checked");
		request.setAttribute("searchKeyword", searchKeyword);
		request.setAttribute("pageno", String.valueOf(pageno));
		request.setAttribute("checked", checked);
		
		int constNum = Integer.parseInt(request.getParameter("constNum"));
		
		ConstructionDAO dao = new ConstructionDAO();
		
		int result = 0;
		result = dao.ConstructionDelOk(constNum);
		
		request.setAttribute("constructionDelResult", String.valueOf(result));
	}
}
