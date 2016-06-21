package board.command;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.AdminDAO;
import util.StringUtil;

public class AdminAddOkCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
			HttpSession session = request.getSession();
			
			int pageno = Integer.parseInt((String)session.getAttribute("pageno"));
			String[] checked = (String[])session.getAttribute("checked");
			String searchKeyword = (String)session.getAttribute("searchKeyword");
			request.setAttribute("pageno", String.valueOf(pageno));
			request.setAttribute("checked", checked);
			request.setAttribute("searchKeyword", searchKeyword);
			
			try {
				String adminName = URLDecoder.decode(StringUtil.nchk(request.getParameter("adminName"), ""),"UTF-8");
				String adminId = URLDecoder.decode(StringUtil.nchk(request.getParameter("adminId"), ""),"UTF-8");
				String adminPw = StringUtil.nchk(request.getParameter("adminPw"), "");
				String adminPhone = URLDecoder.decode(StringUtil.nchk(request.getParameter("adminPhone"), ""),"UTF-8");
				String adminEmail = URLDecoder.decode(StringUtil.nchk(request.getParameter("adminEmail"), ""),"UTF-8");
				String adminRole = URLDecoder.decode(StringUtil.nchk(request.getParameter("adminRole"), "-1"),"UTF-8");
				
				AdminDAO dao = new AdminDAO();
				
				int result = dao.adminAddOk(adminId, adminPw, adminName, adminPhone, adminEmail, adminRole);
				request.setAttribute("adminAddResult", String.valueOf(result));
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
