package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.ConstructionDAO;
import util.StringUtil;

public class ConstructionModOkCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		HttpSession session=request.getSession();
		
		String pageCast=(String)session.getAttribute("pageno");
		int pageno = Integer.parseInt(pageCast);
		String[] checked=(String[])session.getAttribute("checked");
		String searchKeyword = (String)session.getAttribute("searchKeyword");
		request.setAttribute("pageno", String.valueOf(pageno));
		request.setAttribute("checked", checked);
		request.setAttribute("searchKeyword", searchKeyword);
		
		String constWay = StringUtil.nchk(request.getParameter("constWay"),"");
		String constArea = StringUtil.nchk(request.getParameter("constArea"),"");
		String constPrice = StringUtil.nchk(request.getParameter("constPrice"),"");
		String constLower = StringUtil.nchk(request.getParameter("constLower"),"");
		String constOpening = StringUtil.nchk(request.getParameter("constOpening"),"");
		String constInstitution = StringUtil.nchk(request.getParameter("constInstitution"),"");
		String constPercent = StringUtil.nchk(request.getParameter("constPercent"),"");
		
		String con = (String)session.getAttribute("constNum");
		int constNum = 0;
		if(con!=null){
			constNum = Integer.parseInt(con);
		}
		ConstructionDAO dao = new ConstructionDAO();
		
		int result = 0;
		result = dao.constructionModOk(constNum, constWay, constArea, constPrice, constLower, constOpening, constInstitution, constPercent);
		request.setAttribute("constructionModResult", String.valueOf(result));
	}

}
