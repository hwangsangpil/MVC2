package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.ConstructionDAO;
import util.StringUtil;

public class ConstructionAddOkCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String constName = StringUtil.nchk(request.getParameter("constName"),"");
		String constWay = StringUtil.nchk(request.getParameter("constWay"),"");
		String constArea = StringUtil.nchk(request.getParameter("constArea"),"");
		String constPrice = StringUtil.nchk(request.getParameter("constPrice"),"");
		String constLower = StringUtil.nchk(request.getParameter("constLower"),"");
		String constOpening = StringUtil.nchk(request.getParameter("constOpening"),"");
		String constInstitution = StringUtil.nchk(request.getParameter("constInstitution"),"");
		String constPercent = StringUtil.nchk(request.getParameter("constPercent"),"");
		ConstructionDAO dao = new ConstructionDAO();
		System.out.println("constName: "+ constName);
		System.out.println("constWay= "+ constWay);
		System.out.println("constArea= "+ constArea);
		System.out.println("constPrice= "+ constPrice);
		System.out.println("constLower= "+ constLower);
		System.out.println("constOpening= "+constOpening );
		System.out.println("constInstitution= "+ constInstitution);
		System.out.println("constPercent= "+constPercent );
		
		int result = 0;
		result = dao.constructionAddOk(constName, constWay, constArea, constPrice, constLower, constOpening, constInstitution, constPercent);
		
		request.setAttribute("constructionAddResult", String.valueOf(result));
	}

}
