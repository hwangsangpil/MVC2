package board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.command.BusinessAddCmd;
import board.command.BusinessAddOkCmd;
import board.command.BusinessListCmd;
import board.command.BusinessListFirstCmd;
import board.command.Cmd;
import board.command.ConstructionAddOkCmd;
import board.command.ConstructionDelOkCmd;
import board.command.ConstructionListCmd;
import board.command.ConstructionListFirstCmd;
import board.command.ConstructionModCmd;
import board.command.ConstructionModListCmd;
import board.command.ConstructionModOkCmd;
import board.command.LogOutCmd;
import board.command.LoginOkCmd;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.bbs")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		request.setCharacterEncoding("UTF-8");
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmdURI = requestURI.substring(contextPath.length());
		
		Cmd cmd = null;
		String viewPage = null;
		
		//로그인 후 메인화면
		if(cmdURI.equals("/loginOk.bbs")){
			cmd = new LoginOkCmd();
			cmd.execute(request, response);
			viewPage = "/home/home.jsp";
		}
		
		//로그아웃
		if(cmdURI.equals("/logOut.bbs")){
			cmd = new LogOutCmd();
			cmd.execute(request, response);
			viewPage = "/index.jsp";
		}
		
		//메인화면
		if(cmdURI.equals("/home.bbs")){
			viewPage = "/home/home.jsp";
		}
		
		//공고조회 버튼누를시
		if(cmdURI.equals("/constructionListFirst.bbs")){
			cmd = new ConstructionListFirstCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionList.jsp";
		}
		
		//공고조회에서 검색어 체크박스 유지하고 다시 조회
		if(cmdURI.equals("/constructionList.bbs")){
			cmd = new ConstructionListCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionList.jsp";
		}
		
		//공고수정 페이지 이동
		if(cmdURI.equals("/constructionMod.bbs")){
			cmd = new ConstructionModCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionMod.jsp";
		}
		
		//공고수정 완료
		if(cmdURI.equals("/constructionModOk.bbs")){
			cmd = new ConstructionModOkCmd();
			cmd.execute(request,response);
			viewPage = "constructionList.bbs";
		}
		
		//공고수정 페이지에서 목록버튼 누를때
		if(cmdURI.equals("/constructionModList.bbs")){
			cmd = new ConstructionModListCmd();
			cmd.execute(request,response);
			viewPage = "constructionList.bbs";
		}
		
		//공고삭제
		if(cmdURI.equals("/constructionDelOk.bbs")){
			cmd = new ConstructionDelOkCmd();
			cmd.execute(request, response);
			viewPage = "constructionList.bbs";
		}
		
		//공고등록 페이지
		if(cmdURI.equals("/constructionAdd.bbs")){
			viewPage = "/construction/constructionAdd.jsp";
		}
		
		//공고등록 완료
		if(cmdURI.equals("/constructionAddOk.bbs")){
			cmd = new ConstructionAddOkCmd();
			cmd.execute(request, response);
			viewPage = "constructionListFirst.bbs";
		}
		
		//업체관리 메뉴 누를시
		if(cmdURI.equals("/businessListFirst.bbs")){
			cmd = new BusinessListFirstCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessList.jsp";
		}
		
		//업체관리 조회
		if(cmdURI.equals("/businessList.bbs")){
			cmd = new BusinessListCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessList.jsp";
		}
		
		//업체등록 페이지
		if(cmdURI.equals("/businessAdd.bbs")){
			cmd = new BusinessAddCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessAdd.jsp";
		}
		
		//업체등록 완료
		if(cmdURI.equals("/businessAddOk.bbs")){
			cmd = new BusinessAddOkCmd();
			cmd.execute(request, response);
			viewPage = "test.jsp";
		}
		
		
		RequestDispatcher dis = request.getRequestDispatcher(viewPage);
		dis.forward(request, response);
	}

}
