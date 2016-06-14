package board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.command.Cmd;
import board.command.ConstructionListCmd;
import board.command.ConstructionModCmd;
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
		
		if(cmdURI.equals("/loginOk.bbs")){
			cmd = new LoginOkCmd();
			cmd.execute(request, response);
			viewPage = "/home/home.jsp";
			//viewPage="/test.jsp";
		}
		
		if(cmdURI.equals("/logOut.bbs")){
			cmd = new LogOutCmd();
			cmd.execute(request, response);
			viewPage = "/index.jsp";
		}
		
		if(cmdURI.equals("/constructionList.bbs")){
			cmd = new ConstructionListCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionList.jsp";
			//viewPage="/test.jsp";
		}
		
		if(cmdURI.equals("/constructionMod.bbs")){
			cmd = new ConstructionModCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionMod.jsp";
		}
		
		if(cmdURI.equals("/constructionModOk.bbs")){
			viewPage = "/construction/constructionList.jsp";
		}
		
		RequestDispatcher dis = request.getRequestDispatcher(viewPage);
		dis.forward(request, response);
	}

}
