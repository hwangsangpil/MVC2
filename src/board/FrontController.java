package board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.command.AdminAddCmd;
import board.command.AdminAddOkCmd;
import board.command.AdminDelOkCmd;
import board.command.AdminListCmd;
import board.command.AdminListFirstCmd;
import board.command.AdminModCmd;
import board.command.AdminModListCmd;
import board.command.AdminModOkCmd;
import board.command.BusinessAddCmd;
import board.command.BusinessAddOkCmd;
import board.command.BusinessDelOkCmd;
import board.command.BusinessListCmd;
import board.command.BusinessListFirstCmd;
import board.command.BusinessModCmd;
import board.command.BusinessModListCmd;
import board.command.BusinessModOkCmd;
import board.command.BusinessViewCmd;
import board.command.BusinessViewDelOkCmd;
import board.command.BusinessViewFirstCmd;
import board.command.BusinessViewModCmd;
import board.command.BusinessViewModListCmd;
import board.command.BusinessViewModOkCmd;
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
		
		//�����ȸ ��ư������
		if(cmdURI.equals("/constructionListFirst.bbs")){
			cmd = new ConstructionListFirstCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionList.jsp";
		}
		
		//�����ȸ���� �˻��� üũ�ڽ� �����ϰ� �ٽ� ��ȸ
		if(cmdURI.equals("/constructionList.bbs")){
			cmd = new ConstructionListCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionList.jsp";
		}
		
		//������ ������ �̵�
		if(cmdURI.equals("/constructionMod.bbs")){
			cmd = new ConstructionModCmd();
			cmd.execute(request, response);
			viewPage = "/construction/constructionMod.jsp";
		}
		
		//������ �Ϸ�
		if(cmdURI.equals("/constructionModOk.bbs")){
			cmd = new ConstructionModOkCmd();
			cmd.execute(request,response);
			viewPage = "constructionList.bbs";
		}
		
		//������ ���������� ��Ϲ�ư ������
		if(cmdURI.equals("/constructionModList.bbs")){
			cmd = new ConstructionModListCmd();
			cmd.execute(request,response);
			viewPage = "constructionList.bbs";
		}
		
		//������
		if(cmdURI.equals("/constructionDelOk.bbs")){
			cmd = new ConstructionDelOkCmd();
			cmd.execute(request, response);
			viewPage = "constructionList.bbs";
		}
		
		//��� �󼼺���
		if(cmdURI.equals("/businessViewFirst.bbs")){
			cmd = new BusinessViewFirstCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessView.jsp";
		}
		
		//��� �󼼺���
		if(cmdURI.equals("/businessView.bbs")){
			cmd = new BusinessViewCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessView.jsp";
		}
		
		//��� �󼼺��� ���������� �̵�
		if(cmdURI.equals("/businessViewMod.bbs")){
			cmd = new BusinessViewModCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessMod.jsp";
		}

		//��� �󼼺��� �����Ϸ�
		if(cmdURI.equals("/businessViewModOk.bbs")){
			cmd = new BusinessViewModOkCmd();
			cmd.execute(request, response);
			viewPage = "businessView.bbs";
		}
		//��� �󼼺��� ����
		if(cmdURI.equals("/businessViewModList.bbs")){
			cmd = new BusinessViewModListCmd();
			cmd.execute(request, response);
			viewPage = "businessView.bbs";
		}
		
		//공고상세 보기 삭제
		if(cmdURI.equals("/businessViewDelOk.bbs")){
			cmd = new BusinessViewDelOkCmd();
			cmd.execute(request, response);
			viewPage ="businessView.bbs";
		}
		
		//����� ������
		if(cmdURI.equals("/constructionAdd.bbs")){
			viewPage = "/construction/constructionAdd.jsp";
		}
		
		//����� �Ϸ�
		if(cmdURI.equals("/constructionAddOk.bbs")){
			cmd = new ConstructionAddOkCmd();
			cmd.execute(request, response);
			viewPage = "constructionListFirst.bbs";
		}
		
		//��ü�� �޴� ������
		if(cmdURI.equals("/businessListFirst.bbs")){
			cmd = new BusinessListFirstCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessList.jsp";
		}
		
		//��ü�� ��ȸ
		if(cmdURI.equals("/businessList.bbs")){
			cmd = new BusinessListCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessList.jsp";
		}
		
		//��ü�� ���� ������ �̵�
		if(cmdURI.equals("/businessMod.bbs")){
			cmd = new BusinessModCmd();
			cmd.execute(request, response);
			viewPage = "/business/businessMod.jsp";
		}
		
		//��ü�� �����Ϸ�
		if(cmdURI.equals("/businessModOk.bbs")){
			cmd = new BusinessModOkCmd();
			cmd.execute(request, response);
			viewPage = "businessList.bbs";
		}
		
		//��ü�� ����ȭ�鿡�� ����̵�
		if(cmdURI.equals("/businessModList.bbs")){
			cmd = new BusinessModListCmd();
			cmd.execute(request, response);
			viewPage = "businessList.bbs";
		}
		
		//��ü�� �����Ϸ�
		if(cmdURI.equals("/businessDelOk.bbs")){
			cmd = new BusinessDelOkCmd();
			cmd.execute(request, response);
			viewPage = "businessList.bbs";
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
			try{
				cmd.execute(request, response);
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			viewPage = "businessList.bbs";
		}
		
		//관리자 관리 버튼 누를시 
		if(cmdURI.equals("/adminListFirst.bbs")){
			cmd = new AdminListFirstCmd();
			cmd.execute(request, response);
			viewPage = "/admin/adminList.jsp";
		}
		
		//관리자 리스트
		if(cmdURI.equals("/adminList.bbs")){
			cmd = new AdminListCmd();
			cmd.execute(request, response);
			viewPage = "/admin/adminList.jsp";
		}
		
		//관리자 추가
		if(cmdURI.equals("/adminAdd.bbs")){
			viewPage = "/admin/adminAdd.jsp";
		}
		
		//관리자 추가 완료
		if(cmdURI.equals("/adminAddOk.bbs")){
			cmd = new AdminAddOkCmd();
			cmd.execute(request, response);
			viewPage = "adminList.bbs";
		}
		
		//관리자 수정
		if(cmdURI.equals("/adminMod.bbs")){
			cmd = new AdminModCmd();
			cmd.execute(request, response);
			viewPage = "/admin/adminMod.jsp";
		}
		
		//관리자 수정 완료
		if(cmdURI.equals("/adminModOk.bbs")){
			cmd = new AdminModOkCmd();
			cmd.execute(request, response);
			viewPage = "adminList.bbs";
		}
		
		//관리자 수정 목록
		if(cmdURI.equals("/adminModList.bbs")){
			cmd = new AdminModListCmd();
			cmd.execute(request, response);
			viewPage = "adminList.bbs";
		}
		
		//관리자 삭제
		if(cmdURI.equals("/adminDelOk.bbs")){
			cmd = new AdminDelOkCmd();
			cmd.execute(request, response);
			viewPage = "adminList.bbs";
		}
		
		
		RequestDispatcher dis = request.getRequestDispatcher(viewPage);
		dis.forward(request, response);
	}

}
