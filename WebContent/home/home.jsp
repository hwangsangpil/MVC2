<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>INFO SYSTEM</title>
<%
if("0".equals(session.getAttribute("MEM_SEQ"))){
%>
<script>
		alert("아이디와 비밀번호를 확인후 다시 로그인해주세요.");
		history.back();
</script>
<%}%>
<%@ include file="../include/inc_header.jsp"%>
</head>
<body>
	<div>
		<!--BEGIN TOPBAR-->
		<%@ include file="../include/inc_top.jsp"%>
		<!--END TOPBAR-->
		<div id="wrapper">
			<!--BEGIN SIDEBAR MENU-->
			<%@ include file="../include/inc_left_menu.jsp"%>
			<!--END SIDEBAR MENU-->
			<div id="page-wrapper">

				<!--BEGIN CONTENT-->
				<div class="page-content">
						<div id="tab-general">
							<div class="row mbl">
								<div class="col-lg-12">
									<div class="row">
										<div class="col-lg-12">
											<div style="text-align: center; height: 100%; vertical-align: middle; font-size: 60px;">
												<br/><br/>
												<br/>
												<div style="font-size: 40px;">좌측 메뉴를 선택해 주세요.</div>
											</div>
										</div>	
									</div>
								</div>
							</div>
						</div>
				</div>
			</div>
		</div>
				<!--END CONTENT-->
	</div>
			<!--END PAGE WRAPPER-->
</body>
</html>
