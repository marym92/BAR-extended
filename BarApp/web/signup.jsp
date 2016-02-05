<%-- 
    Document   : signup
    Author     : mary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Page Value -- signup=3
    session.setAttribute("page", 3);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    
    <script type="text/javascript" src="imports/import_header.js"></script>
    
    <script>
        function check_empty_fields(id) {
            var x = document.getElementById(id);
            
            if (x.value.match(/^\s*$/)) {
                x.style.borderColor = '#B20000';
                x.style.borderWidth = "2px";
                x.style.borderStyle = "solid";
            } else {
                x.style.borderColor= '';
                x.style.borderWidth = ""; 
            }
            if(x.value=='') {
                x.value=x.defaultValue;
            }
        }
    </script>
    
    <body>
        
        <jsp:include page="imports/import_menu.jsp"/>
	
        <div id="contents">
            <script type="text/javascript" src="imports/import_title.js"></script>
        </div>
        <div id="footer">
            <div id="login" class="wrapper clearfix">
                <div class="main">
                    <h1>Sign Up</h1>
                    
                    <form action="index12314.html" method="post">
                        <ul>
                            <li>
                                <label>Enter your username.</label>
                                <input type="text" value="Username" name="username" id="username" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}" pattern=".{4,}" required title="Username must be up to 4 characters">
                            </li>
                            <li>
                                <label>Enter your password.</label>
                                <input type="password" value="Password" name="password" id="password" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}" pattern=".{6,}" required title="Username must be up to 6 characters">
                            </li>
                            <li>
                                <label>Re-enter your password.</label>
                                <input type="password" value="Password" name="repassword" id="repassword" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}" pattern=".{6,}" required title="Username must be up to 6 characters">
                            </li>
                            <li>
                                <label>Enter your email.</label>
                                <input type="email" value="Email" name="email" id="email" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">
                            </li>
                            <li>
                                <label>Enter your date of birth.</label>
                                <input type="email" value="Email" name="email" id="email" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">
                            </li>
                            <li>
                                <label>Prove you are a human.</label>
                                <input type="email" value="Email" name="email" id="email" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">
                            </li>
                            <li>
                                <a href="login.jsp">Having already an account? Login!</a>
                            </li>
                            <li>
                                <input type="submit" value="Sign Up" class="btn3">
                            </li>
                        </ul>
                    </form>
                </div>
            </div>
	</div>
	
	<div id="footer">
            <script type="text/javascript" src="imports/import_footer.js"></script>
	</div>
    </body>
</html>
