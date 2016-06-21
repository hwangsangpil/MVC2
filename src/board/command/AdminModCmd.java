package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.AdminDAO;
import board.model.AdminDTO;

public class AdminModCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		int seqNo = Integer.parseInt(request.getParameter("seqNo"));
		
		AdminDAO dao = new AdminDAO();
		AdminDTO dto = new AdminDTO();
		
		dto = dao.adminMod(seqNo);
		request.setAttribute("adminMod", dto);
		session.setAttribute("seqNo", String.valueOf(seqNo));
	}

}
