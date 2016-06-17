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
		
		//�α��� �� ����ȭ��
		if(cmdURI.equals("/loginOk.bbs")){
			cmd = new LoginOkCmd();
			cmd.execute(request, response);
			viewPage = "/home/home.jsp";
		}
		
		//�α׾ƿ�
		if(cmdURI.equals("/logOut.bbs")){
			cmd = new LogOutCmd();
			cmd.execute(request, response);
			viewPage = "/index.jsp";
		}
		
		//����ȭ��
		if(cmdURI.equals("/home.bbs")){
			viewPage = "/home/home.jsp";
		}
		
		//������ȸ ��ư������
		if(cmdURI.equals("/constructionListFirst.bbs")){
			cmd = new ConstructionListFirstCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionList.jsp";
		}
		
		//������ȸ���� �˻��� üũ�ڽ� �����ϰ� �ٽ� ��ȸ
		if(cmdURI.equals("/constructionList.bbs")){
			cmd = new ConstructionListCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionList.jsp";
		}
		
		//������� ������ �̵�
		if(cmdURI.equals("/constructionMod.bbs")){
			cmd = new ConstructionModCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionMod.jsp";
		}
		
		//������� �Ϸ�
		if(cmdURI.equals("/constructionModOk.bbs")){
			cmd = new ConstructionModOkCmd();
			cmd.execute(request,response);
			viewPage = "constructionList.bbs";
		}
		
		//������� ���������� ��Ϲ�ư ������
		if(cmdURI.equals("/constructionModList.bbs")){
			cmd = new ConstructionModListCmd();
			cmd.execute(request,response);
			viewPage = "constructionList.bbs";
		}
		
		//�������
		if(cmdURI.equals("/constructionDelOk.bbs")){
			cmd = new ConstructionDelOkCmd();
			cmd.execute(request, response);
			viewPage = "constructionList.bbs";
		}
		
		//������ ������
		if(cmdURI.equals("/constructionAdd.bbs")){
			viewPage = "/construction/constructionAdd.jsp";
		}
		
		//������ �Ϸ�
		if(cmdURI.equals("/constructionAddOk.bbs")){
			cmd = new ConstructionAddOkCmd();
			cmd.execute(request, response);
			viewPage = "constructionListFirst.bbs";
		}
		
		//��ü���� �޴� ������
		if(cmdURI.equals("/businessListFirst.bbs")){
			cmd = new BusinessListFirstCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessList.jsp";
		}
		
		//��ü���� ��ȸ
		if(cmdURI.equals("/businessList.bbs")){
			cmd = new BusinessListCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessList.jsp";
		}
		
		//��ü��� ������
		if(cmdURI.equals("/businessAdd.bbs")){
			cmd = new BusinessAddCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessAdd.jsp";
		}
		
		//��ü��� �Ϸ�
		if(cmdURI.equals("/businessAddOk.bbs")){
			cmd = new BusinessAddOkCmd();
			cmd.execute(request, response);
			viewPage = "test.jsp";
		}
		
		
		RequestDispatcher dis = request.getRequestDispatcher(viewPage);
		dis.forward(request, response);
	}

}
