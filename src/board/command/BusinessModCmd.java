package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BusinessDAO;
import board.model.BusinessDTO;
import util.StringUtil;

public class BusinessModCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int busiNum = Integer.parseInt(StringUtil.nchk(request.getParameter("busiNum"),"1"));
		
		BusinessDAO dao = new BusinessDAO();
		BusinessDTO dto = new BusinessDTO();
		
		dto = dao.businessMod(busiNum);
		request.setAttribute("businessMod", dto);
		request.setAttribute("busiNum", String.valueOf(busiNum));
		
	}

}
