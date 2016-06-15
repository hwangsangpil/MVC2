package board.command;

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
		
		int constNum = Integer.parseInt(StringUtil.nchk(request.getParameter("constNum"), "1"));
		
		dto = dao.selectConstructionInfo(constNum);
		request.setAttribute("selectConstructionInfo", dto);
		session.setAttribute("constNum", String.valueOf(constNum));
	}

}
