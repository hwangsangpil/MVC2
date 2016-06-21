package board.command;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.AdminDAO;
import util.HashUtil;
import util.StringUtil;

public class AdminModOkCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
			
		HttpSession session = request.getSession();
		
		String pageCast = (String)session.getAttribute("pageno");
		int pageno = Integer.parseInt(pageCast);
		String[] checked = (String[])session.getAttribute("checked");
		String searchKeyword = (String)session.getAttribute("searchKeyword");
		request.setAttribute("pageno", String.valueOf(pageno));
		request.setAttribute("checked", checked);
		request.setAttribute("searchKeyword", searchKeyword);
		
		try {
			int seqNo = Integer.parseInt(StringUtil.nchk(session.getAttribute("seqNo"), "1"));
			String adminName = URLDecoder.decode(StringUtil.nchk(request.getParameter("adminName"), ""),"UTF-8");
			String adminId = URLDecoder.decode(StringUtil.nchk(request.getParameter("adminId"), ""),"UTF-8");
			String adminPw = StringUtil.nchk(request.getParameter("adminPw"), "");
			String adminEmail = URLDecoder.decode(StringUtil.nchk(request.getParameter("adminEmail"), ""),"UTF-8");
			String adminPhone = URLDecoder.decode(StringUtil.nchk(request.getParameter("adminPhone"), ""),"UTF-8");
			String adminRole = URLDecoder.decode(StringUtil.nchk(request.getParameter("adminRole"), "-1"),"UTF-8");
		
			try {
			AdminDAO dao = new board.model.AdminDAO();
			int result = 0;
				result = dao.adminModOk(adminId, HashUtil.encryptPassword(adminId,adminPw), adminName, adminPhone, adminEmail, adminRole, seqNo);
				request.setAttribute("adminModresult", String.valueOf(result));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
