package board.command;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.ConstructionDAO;
import board.model.ConstructionDTO;
import util.StringUtil;

public class ConstructionModCmd implements Cmd{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		ConstructionDAO dao = new ConstructionDAO();
		ConstructionDTO dto = new ConstructionDTO();
		
		int constNum = (int)request.getAttribute("constNum");
		int pageno = Integer.parseInt(StringUtil.nchk(request.getParameter("pageno"), "1"));
		String[] checked = request.getParameterValues("check");
		try {
			String searchKeyword = URLDecoder.decode(StringUtil.nchk(request.getParameter("searchKeyword"),""),"UTF-8");
			session.setAttribute("searchKeyword", searchKeyword);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		dto = dao.selectConstructionInfo(constNum);
		
		request.setAttribute("selectConstructionInfo", dto);
		
		session.setAttribute("checked", checked);
		session.setAttribute("pageno", String.valueOf(pageno));
	}

}
