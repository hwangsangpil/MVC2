package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BusinessDAO;
import util.StringUtil;

public class BusinessViewModOkCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
			
		HttpSession session = request.getSession();
			
		String page = (String)session.getAttribute("pageno");
		int	pageno = Integer.parseInt(page);
		String[] checked = (String[])session.getAttribute("checked");
		String searchKeyword = (String)session.getAttribute("searchKeyword");
		session.setAttribute("pageno", String.valueOf(pageno));
		session.setAttribute("checked", checked);
		session.setAttribute("searchKeyword", searchKeyword);
		
		int busiNum = Integer.parseInt(StringUtil.nchk(session.getAttribute("busiNum"),"0"));
		String busiOpening = StringUtil.nchk(request.getParameter("bisiOpening"),"");
		String busiPercent = StringUtil.nchk(request.getParameter("busiPercent"),"");
		String busiPrice = StringUtil.nchk(request.getParameter("busiPrice"),"");
		String busiWay = StringUtil.nchk(request.getParameter("busiWay"),"");
		String busiArea = StringUtil.nchk(request.getParameter("busiArea"),"");
		
		BusinessDAO dao = new BusinessDAO();
		int result = 0;
		result = dao.BusinessViewModOk(busiNum, busiOpening, busiPercent, busiPrice, busiWay, busiArea);
		
		request.setAttribute("businessModResult", String.valueOf(result));
		
	}

}
