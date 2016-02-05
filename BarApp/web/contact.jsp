<%-- 
    Document   : contact
    Author     : mary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Page Value -- contact=5
    session.setAttribute("page", 5);
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
            <div id="contact" class="wrapper clearfix">
                <div class="main">
                    <h1>Contact Us</h1>
                    <form action="index12314.html" method="post">
                        <ul>
                            <li>
                                <label>Enter your full name here.</label>
                                <input type="text" value="Full Name" name="fullname" id="fullname" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">
                            </li>
                            <li>
                                <label>Enter your email address here.</label>
                                <input type="text" value="Email Address" name="email" id="email" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">
                            </li>
                            <li>
                                <label>Enter the Subject message here.</label>
                                <input type="text" value="Subject" name="subject" id="subject" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">
                            </li>
                            <li>
                                <label class="msg">Enter your Message here.</label>
                                <textarea name="message" id="message" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">Message</textarea>

                                <input type="submit" value="Send Now" class="btn3">
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
