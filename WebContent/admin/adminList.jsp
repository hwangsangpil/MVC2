<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="board.model.AdminDAO"%>
<%@page import="board.model.AdminDTO"%>
<%@page import="util.StringUtil"%>
<%@page import="util.DateUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setCharacterEncoding("UTF-8");

String totalCast = (String)request.getAttribute("totalcnt");
int totalcnt = Integer.parseInt(totalCast);

String searchKeyword = (String)request.getAttribute("searchKeyword");
String[] checked=(String[])request.getAttribute("checked");
String pageCast = (String)request.getAttribute("pageno");
int pageno = Integer.parseInt(pageCast);
session.setAttribute("pageno", String.valueOf(pageno));
session.setAttribute("searchKeyword", searchKeyword);
session.setAttribute("checked", checked);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>관리자 관리</title>
<%@ include file="../include/inc_header.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#searchKeyword").focus();
});

function down(){
	location = "AdminExportToExcel.bbs?title=adminList.xls";
}

	function fnc_view(no, pageno){
		location.href = "adminView.jsp?no=" + no + "&pageno=" + pageno
	}
	
	function pageLink(arg) {
		document.frm.pageno.value = arg;
		document.frm.submit();
	}
	
	function fnc_search(){
		var searchKeyword = document.getElementById("searchKeyword").value;
		
		/* if( searchKeyword.length == 0 ) {
			alert("검색어를 입력해주세요.");
			document.getElementById("searchKeyword").focus();
			return;
		} */
		 
		document.frm.submit();
	}
	
	function fnc_add(){
		if('<%=role%>'==="일반관리자"){
			alert("<%=role%>는 권한이 없습니다.");
			return;
		}else{    //확인
			location = "adminAdd.bbs?";
		}
	}
	
	function adminDel(seqNo){
		if('<%=role%>'==="일반관리자"){
			alert("<%=role%>는 권한이 없습니다.");
			return;
		}else if (confirm("정말 삭제하시겠습니까?")){    //확인
			location = "AdminDelOk.bbs?seqNo="+seqNo;
		}else{
			return;
		}
	}
	
	function adminMod(seqNo){
		if('<%=role%>'==="일반관리자"){
			alert("<%=role%>는 권한이 없습니다.");
			return;
		}else if (confirm("정말 수정하시겠습니까?")){    //확인
			location = "adminMod.bbs?seqNo="+seqNo;
		}else{
			return;
		}
	}
</script>
</head>
<body>
	<div style="min-width: 300px">
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
						<div class="page-title">관리자 관리</div>
					</div>
					<ol class="breadcrumb page-breadcrumb pull-right">
						<li><i class="fa fa-home"></i>&nbsp;<a href="home.bbs">Home</a>&nbsp;&nbsp;<i
							class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>
						<li class="active"><a href="#">설정</a>&nbsp;&nbsp;<i
							class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>
						<li class="active">관리자 관리</li>
					</ol>
					<div class="clearfix"></div>
				</div>
				<!--END TITLE & BREADCRUMB PAGE-->
				<!--BEGIN CONTENT-->
				<div class="page-content">
					<form name="frm" action="adminList.bbs" method="post">
						<input type="hidden" name="pageno" value="<%=pageno%>">
						<div id="tab-general">
							<div class="row mbl">
								<div class="col-lg-12">
									<div class="row">
										<div class="col-lg-12">
											<div class="panel panel-yellow">
												<div class="panel-heading">관리자 목록</div>
												<div class="mbl"></div>
												<div class="col-lg-8">&nbsp;</div>
												<div class="col-lg-4">
													<div class="input-group">
													<span class="input-group-addon">
													<i class="fa fa-search"></i></span>
													<input type="text" id="searchKeyword" name="searchKeyword" placeholder="이름/아이디/이메일/폰번호" class="form-control" value="<%=searchKeyword%>" tabindex="1"/>
													<span class="input-group-btn"><button type="button" class="btn btn-default" onclick="javascript:fnc_search()" tabindex="2">검색</button></span></div>
												</div>
												<div class="col-lg-12">&nbsp;</div>
												<div class="col-lg-12">&nbsp;</div>
												<div class="panel-body" style="overflow:auto;">
													<table class="table table-hover">
														<thead>
															<tr>
																<th style="text-align:center; width: 50px;">NO</th>
																<th style="text-align:center; width: 200px;">관리자 이름<input type="checkbox" tabindex="3" id="check" name="check" value="0" 
																<%if(checked!=null){for(int i=0;i<checked.length;i++){if(checked[i].equals("0")){ %>checked<%}}}%> /></th>
																<th style="text-align:center; width: 150px;">아이디<input type="checkbox" tabindex="4" id="check" name="check" value="1" 
																<%if(checked!=null){for(int i=0;i<checked.length;i++){if(checked[i].equals("1")){ %>checked<%}}}%> /></th>
																<th style="text-align:center; width: 200px;">이메일<input type="checkbox" tabindex="5" id="check" name="check" value="2" 
																<%if(checked!=null){for(int i=0;i<checked.length;i++){if(checked[i].equals("2")){ %>checked<%}}}%> /></th>
																<th style="text-align:center; width: 200px;">폰번호<input type="checkbox" tabindex="6" id="check" name="check" value="3" 
																<%if(checked!=null){for(int i=0;i<checked.length;i++){if(checked[i].equals("3")){ %>checked<%}}}%> /></th>
																<th style="text-align:center; width: 200px;">권한<input type="checkbox" tabindex="7" id="check" name="check" value="4" 
																<%if(checked!=null){for(int i=0;i<checked.length;i++){if(checked[i].equals("4")){ %>checked<%}}}%> /></th>
																<th style="text-align:center; width: 100px;">생성일</th>
																<th style="text-align:center; width: 100px;">수정일</th>
																<th style="text-align:center; ">수정</th>
																<th style="text-align:center; ">삭제</th>
															</tr>
														</thead>
														<tbody>
															<%
															if (totalcnt > 0) {
																	%>
																	<c:forEach items="${adminList}" var="dto">
																<tr style="cursor: pointer;" onclick="javascript:fnc_view('${dto.getSeqNo()}','${pageno}')">
																	<td style="text-align:center;">${dto.getSeqNo()}</td>
																	<td style="text-align:center;">${dto.getAdminName()}</td>
																	<td style="text-align:center;">${dto.getAdminId()}</td>
																	<td style="text-align:center;">${dto.getAdminEmail()}</td>
																	<td style="text-align:center;">${dto.getAdminPhone()}</td>
																	<td style="text-align:center;">${dto.getAdminRole()}</td>
																	<td style="text-align:center;">${dto.getCrtDate()}</td>
																	<td style="text-align:center;">${dto.getUdtDate()}</td>
																	<td onclick="event.cancelBubble = true;"><button type="button" tabindex="10" class="btn btn-primary" onclick="adminMod(${dto.getSeqNo()})">수정</button></td>
																	<td onclick="event.cancelBubble = true;"><button type="button" tabindex="11" class="btn btn-primary" onclick="adminDel(${dto.getSeqNo()})">삭제</button></td>
																</tr>
																</c:forEach>
																<%
															} else {
																out.println("<tr><td align='center' colspan='10'>조회 결과가 없습니다.</td></tr>");
															}
															%>
														</tbody>
													</table>
												</div>
												<jsp:include page="../include/inc_paging.jsp">
													<jsp:param name="totalRecord" value="<%=totalcnt%>"/>
													<jsp:param name="pageNo" value="<%=pageno%>"/>
													<jsp:param name="rowCount" value="10"/> 
													<jsp:param name="pageGroup" value="10"/>
												</jsp:include>
												<div class="text-right pal">
												<button type="button" class="btn btn-primary" onclick="fnc_add()" tabindex="8">관리자 추가</button>
												<button type="button" class="btn btn-primary" onclick="down()" tabindex="9">엑셀 다운로드</button>
												</div>
											</div>
										</div>
									</div>
	
	
								</div>
	
							</div>
						</div>
					</form>
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
