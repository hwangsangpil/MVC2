<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="board.model.AdminDAO"%>
<%@page import="board.model.AdminDTO"%>
<%@page import="util.StringUtil"%>
<%@page import="util.DateUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%
request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>관리자 상세관리</title>
<%@ include file="../include/inc_header.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#adminName").focus();
});

function checkForm() {
	var adminName = document.getElementById("adminName").value;
	var adminId = document.getElementById("adminId").value;
	var adminPw = document.getElementById("adminPw").value;
	var adminConfirmPw = document.getElementById("adminConfirmPw").value;
	var adminEmail = document.getElementById("adminEmail").value;
	var adminPhone = document.getElementById("adminPhone").value;
	var adminRole = document.getElementById("adminRole");
	var selectedAdminRole = adminRole.options[adminRole.selectedIndex].value;
	
	if( adminName.length == 0 ) {
		alert("이름을 입력해주세요.");
		document.getElementById("adminName").focus();
		return;
	}
	
	if( adminId.length == 0 ) {
		alert("아이디를 입력해주세요.");
		document.getElementById("adminId").focus();
		return;
	}
	
	 if( adminPw.length == 0 ) {
		alert("비밀번호를 입력해주세요.");
		document.getElementById("adminPw").focus();
		return;
	}
	
	if( adminConfirmPw.length == 0 ) {
		alert("비밀번호 확인을 입력해주세요.");
		document.getElementById("adminConfirmPw").focus();
		return;
	}
	
	if( adminEmail.length == 0 ) {
		alert("이메일을 입력해주세요.");
		document.getElementById("adminEmail").focus();
		return;
	}
	
	if( adminPhone.length == 0 ) {
		alert("폰번호를 입력해주세요.");
		document.getElementById("adminPhone").focus();
		return;
	}
	
	if( adminPw != adminConfirmPw) {
		alert("비밀번호 확인을 정확히 입력해주세요.");
		document.getElementById("adminConfirmPw").focus();
		return;
	}
	
	if( selectedAdminRole == -1){
		alert("메뉴 권한을 선택해주세요.");
		return;
	}
	
	registForm.submit();
}


function fnc_list(){
	location = "adminModList.bbs";
}

function hitEnterKey(e){
	  if(e.keyCode == 13){
		  checkForm();
	  }else{
		  e.keyCode == 0;
	 	  return;
	  }
} 

function notModConName(){
	alert("관리자 아이디는 수정할 수 없습니다");
	$("#adminId").focus();
}

function changeView(a){
	if(a==4){
		document.all.branchGubun.style.display="block";
	}else{
		document.all.branchGubun.style.display="none";
	}
}

$(document).ready(function() {
	gfnc_Ajax({
	    type: "post",
	    url: "/branch/branch_list_selectbox_ajax.jsp",
	    data: {
	    	
	    },
	    dataType: "html",
	    success: function(data){
	    	//alert(data);
	    },
	    error: function(err) {
	    	//alert(err.responseText);
	    }
	});
});
</script>
</head>
<body>
	<div>
		<!--BEGIN TOPBAR-->
		<%@ include file="../include/inc_top.jsp"%>
		<!--END TOPBAR-->
		<div id="wrapper">
			<!--BEGIN SIDEBAR MENU-->
			<jsp:include page="../include/inc_left_menu.jsp">
			<jsp:param value="setting" name="menuId"/>
			</jsp:include>
			<!--END SIDEBAR MENU-->
			<div id="page-wrapper">
				<!--BEGIN TITLE & BREADCRUMB PAGE-->
				<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
					<div class="page-header pull-left">
						<div class="page-title">관리자 상세관리</div>
					</div>
					<ol class="breadcrumb page-breadcrumb pull-right">
						<li><i class="fa fa-home"></i>&nbsp;<a href="home.bbs">Home</a>&nbsp;&nbsp;<i
							class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>
						<li class="active"><a href="#">설정</a>&nbsp;&nbsp;<i
							class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>
						<li class="active">관리자 상세관리</li>
					</ol>
					<div class="clearfix"></div>
				</div>
				<!--END TITLE & BREADCRUMB PAGE-->
				<!--BEGIN CONTENT-->
				<div class="page-content">
					<div id="tab-general">
						<div class="row mbl">
							<!-- 등록폼 -->
							<div class="col-lg-8">
								<div class="panel panel-orange">
		                            <div class="panel-heading">관리자 수정/삭제</div>
		                            <div class="panel-body pan">
		                                <form id="registForm" action = "adminModOk.bbs" method="post">
		                                <div class="form-body pal">
		                                    <div class="form-group">
		                                        <div class="input-icon right">
		                                            <i class="fa fa-user"></i>
		                                            <input id="adminName" name="adminName" type="text" placeholder="이름" class="form-control" value="${adminMod.getAdminName()}" onKeypress="hitEnterKey(event)" tabindex="1"
		                                            onMouseOver="javascript: this.value='이름';" onmouseout="javascript: this.value='${adminMod.getAdminName()}';" onclick="javascript: this.value='${adminMod.getAdminName()}';"/></div>
		                                    </div>
		                                    <div class="form-group">
		                                        <div class="input-icon right">
		                                            <i class="fa fa-envelope"></i>
		                                            <input id="adminId" name="adminId" type="text" placeholder="아이디" class="form-control" value="${adminMod.getAdminId()}" onKeypress="hitEnterKey(event)" tabindex="2"
		                                            onMouseOver="javascript: this.value='아이디';" onmouseout="javascript: this.value='${adminMod.getAdminId()}';" onclick="notModConName();" readonly/></div>
		                                    </div>
		                                    <div class="form-group">
		                                        <div class="input-icon right">
		                                            <i class="fa fa-lock"></i>
		                                            <input id="adminPw" name="adminPw" type="password" placeholder="비밀번호" class="form-control" onKeypress="hitEnterKey(event)" tabindex="3"/></div>
		                                    </div>
		                                    <div class="form-group">
		                                        <div class="input-icon right">
		                                            <i class="fa fa-lock"></i>
		                                            <input id="adminConfirmPw" name="adminConfirmPw" type="password" placeholder="비밀번호 확인" class="form-control" onKeypress="hitEnterKey(event)" tabindex="4"/></div>
		                                    </div>
		                                    <div class="form-group">
		                                        <div class="input-icon right">
		                                            <i class="fa fa-envelope"></i>
		                                            <input id="adminEmail" name="adminEmail" type="text" placeholder="이메일" class="form-control" value="${adminMod.getAdminEmail()}" onKeypress="hitEnterKey(event)" tabindex="5"
		                                            onMouseOver="javascript: this.value='이메일';" onmouseout="javascript: this.value='${adminMod.getAdminEmail()}';" onclick="javascript: this.value='${adminMod.getAdminEmail()}';"/></div>
		                                    </div>
		                                    <div class="form-group">
		                                        <div class="input-icon right">
		                                            <i class="fa fa-envelope"></i>
		                                            <input id="adminPhone" name="adminPhone" type="text" placeholder="핸드폰 번호" class="form-control" value="${adminMod.getAdminPhone()}" onKeypress="hitEnterKey(event)" tabindex="6"
		                                            onMouseOver="javascript: this.value='핸드폰 번호';" onmouseout="javascript: this.value='${adminMod.getAdminPhone()}';" onclick="javascript: this.value='${adminMod.getAdminPhone()}';"/></div>
		                                    </div>
		                                    <div class="form-group">
		                                        <select id="adminRole" name="adminRole" class="form-control" onChange="javascript:changeView(this.value)" onKeypress="hitEnterKey(event)" tabindex="7">
		                                            <option value="-1" <%if("-1".equals(role)){%>selected<%}%>>메뉴 권한</option>
		                                            <option value="전체관리자" <%if("전체관리자".equals(role)){%>selected<%}%>>전체 관리자</option>
		                                            <option value="일반관리자" <%if("일반관리자".equals(role)){%>selected<%}%>>일반 관리자</option>
		                                        </select>
		                                    </div>
		                                   
		                                </div>
		                                <div class="form-actions text-right pal">
		                                    <button type="button" onclick="checkForm();" class="btn btn-primary" tabindex="8">수정</button>&nbsp;
		                                    <button type="button" onclick="fnc_list();" class="btn btn-primary" tabindex="9">목록</button>
		                                </div>
		                                 <div>
                                                        <h5>&nbsp;&nbsp;&nbsp;전체 관리자 : 모든 메뉴 및 관리자 관리</h5>
                                                        <h5>&nbsp;&nbsp;&nbsp;일반 관리자 : 데이터 수정, 삭제, 복구 권한 미부여</h5>
                                                </div>
		                                
		                                </form>
		                            </div>
		                        </div>
	                        </div>
						<!-- 여기까지 등록 폼 -->	
						</div>
					</div>
				</div>
				<!--END CONTENT-->
				<!--BEGIN FOOTER-->
				<%@ include file="../include/inc_footer.jsp"%>
				<!--END FOOTER-->
			</div>
			<!--END PAGE WRAPPER-->
		</div>
	</div>
</body>
</html>
