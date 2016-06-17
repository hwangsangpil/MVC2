package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BusinessDAO;
import util.StringUtil;

public class BusinessAddOkCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		int constNum = Integer.parseInt(StringUtil.nchk(request.getParameter("constNum"),"0"));
		String busiName = StringUtil.nchk(request.getParameter("busiName"),"");
		String busiOpening = StringUtil.nchk(request.getParameter("bisiOpening"),"");
		String busiPercent = StringUtil.nchk(request.getParameter("busiPercent"),"");
		String busiPrice = StringUtil.nchk(request.getParameter("busiPrice"),"");
		String busiWay = StringUtil.nchk(request.getParameter("busiWay"),"");
		String busiArea = StringUtil.nchk(request.getParameter("busiArea"),"");
		
		BusinessDAO dao = new BusinessDAO();
		
		int result=0;
		result = dao.businessAddOk(constNum, busiName, busiOpening, busiPrice, busiPercent, busiWay, busiArea);
		request.setAttribute("businessAddResult",result);
	}

}
