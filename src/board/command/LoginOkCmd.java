package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.AdminDAO;
import board.model.AdminDTO;
import util.HashUtil;

public class LoginOkCmd implements Cmd{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub

		HttpSession session=request.getSession();
		
		AdminDAO dao = new AdminDAO();
		AdminDTO dto = new AdminDTO();
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		try {
			dto = dao.loginAdmin(id, HashUtil.encryptPassword(id,password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String memseq = String.valueOf(dto.getSeqNo());
		System.out.println("LoginOkCmd memseq:   "+memseq);
		if( dto.getSeqNo() != 0) {
			session.setAttribute("LOGIN", "SUCCESS");
			session.setAttribute("MEM_SEQ", memseq);
			session.setAttribute("memberNum", memseq);
			session.setAttribute("ROLE", dto.getAdminRole());
			session.setMaxInactiveInterval(15);
		}
		
	}
	
}
