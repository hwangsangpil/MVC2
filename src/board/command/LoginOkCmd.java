package board.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.AdminDAO;
import board.model.AdminDTO;
import util.CookieBox;
import util.HashUtil;

public class LoginOkCmd implements Cmd{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		
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
		System.out.println("memseq:   "+memseq);
		if( dto.getSeqNo() != 0) {
					try {
						response.addCookie(CookieBox.createCookie("LOGIN", "SUCCESS", "/", -1));
						response.addCookie(CookieBox.createCookie("ID", id, "/", -1));
						response.addCookie(CookieBox.createCookie("MEM_SEQ", memseq, "/", -1));
						response.addCookie(CookieBox.createCookie("ROLE", dto.getAdminRole(), "/", -1));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		
		request.setAttribute("memberNum", memseq);
		//dao.closeConn();
	}
	
}
