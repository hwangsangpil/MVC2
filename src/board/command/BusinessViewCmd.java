package board.command;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BusinessDAO;
import board.model.BusinessDTO;
import util.StringUtil;

public class BusinessViewCmd implements Cmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
				
				int pageno = Integer.parseInt(StringUtil.nchk(request.getParameter("pageno"), request.getAttribute("pageno"), "1"));
				String[] checked = request.getParameterValues("check");
				if(checked==null){
					checked = (String[])request.getAttribute("checked");
				}
				String searchKeyword;
				try {
					searchKeyword = URLDecoder.decode(StringUtil.nchk(request.getParameter("searchKeyword"), request.getAttribute("searchKeyword"), ""), "UTF-8");
					
					int constNum = Integer.parseInt(StringUtil.nchk(request.getParameter("constNum"), request.getAttribute("constNum"), "1"));
					
					BusinessDAO dao = new BusinessDAO();
					int totalcnt = dao.businessViewTotalCnt(searchKeyword, constNum, checked);
					ArrayList<BusinessDTO> list = dao.businessView(constNum, pageno, searchKeyword, totalcnt, checked);
					
					request.setAttribute("pageno", String.valueOf(pageno));
					request.setAttribute("checked", checked);
					request.setAttribute("searchKeyword", searchKeyword);
					request.setAttribute("totalcnt", String.valueOf(totalcnt));
					request.setAttribute("businessView", list);
					request.setAttribute("constNum", String.valueOf(constNum));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

}
