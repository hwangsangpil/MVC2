<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="util.StringUtil"%>
<%@page import="util.DateUtil"%>
<%
String role = (String)session.getAttribute("ROLE");
String constructionModResult = (String)request.getAttribute("constructionModResult");
String constructionDelResult = (String)request.getAttribute("constructionDelResult");
String constructionAddResult = (String)request.getAttribute("constructionAddResult");
String businessAddResult = (String)request.getAttribute("businessAddResult");
String businessModResult = (String)request.getAttribute("businessModResult");
String businessDelResult = (String)request.getAttribute("businessDelResult");
String adminAddResult = (String)request.getAttribute("adminAddResult");
String adminModResult = (String)request.getAttribute("adminModResult");
String adminDelResult = (String)request.getAttribute("adminDelResult");
%>
<script>
if('<%=constructionModResult%>' != "null"){
	if('<%=constructionModResult%>' === "0"){
		alert("수정실패");
		history.back();
	}else{
		alert("수정성공");
	}
}

if('<%=constructionDelResult%>' != "null"){
	if('<%=constructionDelResult%>' === "0"){
		alert("삭제실패");
		history.back();
	}else{
		alert("삭제성공");
	}
}

if('<%=constructionAddResult%>' != "null"){
	if('<%=constructionAddResult%>' === "0"){
		alert("공고 등록 실패");
	}else{
		alert("공고 등록 성공");
	}
}

if('<%=businessAddResult%>' != "null"){
	if('<%=businessAddResult%>' === "0"){
		alert("업제 등록을 실패했습니다.\n이미 해당공고에 참여한 업체가 있습니다.");
		history.back();
	}else{
		alert("업체 등록 성공");
	}
}

if('<%=businessModResult%>' != "null"){
	if('<%=businessModResult%>' === "0"){
		alert("업제 수정 실패");
		history.back();
	}else{
		alert("업체 수정 성공");
	}
}

if('<%=businessDelResult%>' != "null"){
	if('<%=businessDelResult%>' === "0"){
		alert("업제 삭제 실패");
		history.back();
	}else{
		alert("업체 삭제 성공");
	}
}

if('<%=adminAddResult%>' != "null"){
	if('<%=adminAddResult%>' === "0"){
		alert("관리자 등록에 실패하였습니다.");
		history.back();
	}else{
		alert("관리자 등록 성공");
	}
}

if('<%=adminModResult%>' != "null"){
	if('<%=adminModResult%>' === "0"){
		alert("관리자 수정 실패");
		history.back();
	}else{
		alert("관리자 수정 성공");
	}
}

if('<%=adminDelResult%>' != "null"){
	if('<%=adminDelResult%>' === "0"){
		alert("관리자 삭제 실패");
		history.back();
	}else{
		alert("관리자 삭제 성공");
	}
}
</script>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="/images/icons/favicon.ico">
<link rel="apple-touch-icon" href="/images/icons/favicon.png">
<link rel="apple-touch-icon" sizes="72x72" href="/images/icons/favicon-72x72.png">
<link rel="apple-touch-icon" sizes="114x114" href="/images/icons/favicon-114x114.png">
<!--Loading bootstrap css-->
<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,400,300,700">
<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,700,300">
<link type="text/css" rel="stylesheet" href="/styles/jquery-ui-1.10.4.custom.min.css">
<link type="text/css" rel="stylesheet" href="/styles/font-awesome.min.css">
<link type="text/css" rel="stylesheet" href="/styles/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="/styles/animate.css">
<link type="text/css" rel="stylesheet" href="/styles/all.css">
<link type="text/css" rel="stylesheet" href="/styles/main.css">
<link type="text/css" rel="stylesheet" href="/styles/style-responsive.css">
<link type="text/css" rel="stylesheet" href="/styles/zabuto_calendar.min.css">
<link type="text/css" rel="stylesheet" href="/styles/pace.css">
<link type="text/css" rel="stylesheet" href="/styles/jquery.news-ticker.css">
<script src="/script/jquery-1.10.2.min.js"></script>
<script src="/script/jquery-migrate-1.2.1.min.js"></script>
<script src="/script/jquery-ui.js"></script>
<script src="/script/bootstrap.min.js"></script>
<script src="/script/bootstrap-hover-dropdown.js"></script>
<script src="/script/html5shiv.js"></script>
<script src="/script/respond.min.js"></script>
<script src="/script/jquery.metisMenu.js"></script>
<script src="/script/jquery.slimscroll.js"></script>
<script src="/script/jquery.cookie.js"></script>
<script src="/script/icheck.min.js"></script>
<script src="/script/custom.min.js"></script>
<script src="/script/jquery.news-ticker.js"></script>
<script src="/script/jquery.menu.js"></script>
<script src="/script/jquery.formchecker.js"></script>
<script src="/script/pace.min.js"></script>
<script src="/script/holder.js"></script>
<script src="/script/responsive-tabs.js"></script>
<script src="/script/jquery.flot.js"></script>
<script src="/script/jquery.flot.categories.js"></script>
<script src="/script/jquery.flot.pie.js"></script>
<script src="/script/jquery.flot.tooltip.js"></script>
<script src="/script/jquery.flot.resize.js"></script>
<script src="/script/jquery.flot.fillbetween.js"></script>
<script src="/script/jquery.flot.stack.js"></script>
<script src="/script/jquery.flot.spline.js"></script>
<script src="/script/zabuto_calendar.min.js"></script>
<!-- <script src="script/index.js"></script>  -->
<!--LOADING SCRIPTS FOR CHARTS-->
<!-- 
<script src="/script/highcharts.js"></script>
<script src="/script/data.js"></script>
<script src="/script/drilldown.js"></script>
<script src="/script/exporting.js"></script>
<script src="/script/highcharts-more.js"></script>
<script src="/script/charts-highchart-pie.js"></script>
<script src="/script/charts-highchart-more.js"></script>
-->
<!--CORE JAVASCRIPT-->
<script src="/script/main.js"></script>
<!-- skhero.kang 2015-06-16 펼침 메뉴 처리 코드 최적화 -->
<script type="text/javascript">
function func_menu_ext(menuId, subMenuCnt){
	if(document.getElementById(menuId + "_sub1").style.display == "none"){
		for ( var cnt = 1; cnt <= subMenuCnt; cnt++) {
			document.getElementById(menuId + "_sub" + cnt).style.display = "block";
		}
	}else{
		for ( var cnt = 1; cnt <= subMenuCnt; cnt++) {
			document.getElementById(menuId + "_sub" + cnt).style.display = "none";
		}
	}
}
var G_TIMER;
// setTimeout(location.reload(), 10000);
function pagestartbt(){
	alert("자동 리플레쉬가 켜졌습니다.");
	$.cookie('refresh', 'on');
	pagestart();
}

function pagestart() {
	if ($.cookie('refresh') == 'off') {
		return;
	}
	G_TIMER = setTimeout("pagereload()", 10000);
}

function pagereload() {
	location.reload();
}
 
function stop(){
	alert("자동 리플레쉬가 꺼졌습니다.");
	//window.stop();
	clearTimeout(G_TIMER);
}
 
function stopbt(){
	alert("자동 리플레쉬가 꺼졌습니다.");
	$.cookie('refresh', 'off');
	//window.stop();
	clearTimeout(G_TIMER);
}
  
/**
 * ajax 를 호출하기 위한 함수
 * @param $.ajax()를 호출하기 위한 인자값
 * @returns
 */
function gfnc_Ajax( options ) {
	var defaults = {
    	type: 'POST',
    	url: '',
    	data: {},
    	async: true,
    	cache: true,
    	headers: {}
    };
	var settings = $.extend({}, defaults, options);
	return $.ajax(settings);
}

</script>
