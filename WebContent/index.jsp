<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>Login</title>
    <%@ include file="/include/inc_header.jsp"%>
<%
if(session!=null){
	session.invalidate();
}
%>     
	<script>
	
		$(document).ready(function() {
			$("#id").focus();
		});
		
	 
		function signInForm() {
			
			if (Validator.isEmpty("#id", "아이디를 입력해주세요.")) { return; }
			if (Validator.isEmpty("#password", "패스워드를 입력해주세요.")) { return; }
			
			loginForm.submit();
		}
		
		function signUpForm() {
			
			location.href = "admin/adminRegist.jsp";
		}
		
		function hitEnterKey(e){
		  if(e.keyCode == 13){
			  signInForm();
		  }else{
			  e.keyCode == 0;
		 	  return;
		  }
		} 
	
	</script>
</head>
<body style="background: url('images/bg/bg.png') 100% 100% fixed;">
    <div class="page-form">
        <div class="panel panel-blue">
            <div class="panel-body pan">
                <form action="loginOk.bbs" id="loginForm" method="post" class="form-horizontal">
				<input type="hidden" name="returnUrl" value="L2FkbWluL21haW4vbWFpbi5kbw"/>
				<br/><br/>
                <div class="form-body pal" style="margin-top: -135px;">
                    <div class="col-md-12 text-center">
                        <h1 style="font-size: 30px;">
                            INFO SYSTEM</h1>
                        <br/>
                    </div>
                    <div class="form-group">
                        <div class="col-md-3">
                            <img src="images/avatar/profile-pic.png" class="img-responsive" style="margin-top: -30px;" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputName" class="col-md-3 control-label">
                            UserId:<br>(guest)</label>
                        <div class="col-md-9">
                            <div class="input-icon right">
                                <i class="fa fa-user"></i>
                                <input type="text" id="id" name="id" placeholder="" class="form-control" tabindex="1" onKeypress="hitEnterKey(event)"/></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword" class="col-md-3 control-label">
                            Password:<br>(guest)</label>
                        <div class="col-md-9">
                            <div class="input-icon right">
                                <i class="fa fa-lock"></i>
                                <input type="password" id="password" name="password" placeholder="" class="form-control" tabindex="2" onKeypress="hitEnterKey(event)"/></div>
                        </div>
                    </div>
                    <div class="form-group mbn">
                        <div class="col-lg-12" align="right">
                            <div class="form-group mbn">
                                <div class="col-lg-3">
                                    &nbsp;
                                </div>
                                <div class="col-lg-9">
                                    <button type="button" class="btn btn-default" onclick="signUpForm();" tabindex="4">
                                        Sign up</button>
                                    <button type="button" class="btn btn-default" onclick="signInForm();" tabindex="3">
                                        Sign In</button>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
