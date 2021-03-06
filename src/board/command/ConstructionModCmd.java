package board.command;

import java.sql.SQLException;

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
		
		int constNum = Integer.parseInt(request.getParameter("constNum"));
		
		dto = dao.constructionMod(constNum);
		request.setAttribute("constructionMod", dto);
		session.setAttribute("constNum", String.valueOf(constNum));
	}
}
