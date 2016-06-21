package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BusinessDAO;

public class BusinessViewDelOkCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		int pageno = Integer.parseInt((String)session.getAttribute("pageno"));
		String[] checked = (String[])session.getAttribute("checked");
		String searchKeyword = (String)session.getAttribute("searchKeyword");
		
		int busiNum = Integer.parseInt((String)session.getAttribute("busiNum"));
		
		BusinessDAO dao = new BusinessDAO();
		
		int result = 0;
		result = dao.businessDelOk(busiNum);
		request.setAttribute("businessDelResult", String.valueOf(result));
	}

}
