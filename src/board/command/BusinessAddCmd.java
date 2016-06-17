package board.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.ConstructionDAO;
import board.model.ConstructionDTO;

public class BusinessAddCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		ConstructionDAO dao = new ConstructionDAO();
		ArrayList<ConstructionDTO> list = dao.businessAdd();
		
		request.setAttribute("businessAdd",list);
	}

}
