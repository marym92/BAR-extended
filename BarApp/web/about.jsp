<%-- 
    Document   : about
    Author     : mary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Page Value -- about=2
    session.setAttribute("page", 2);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    
    <script type="text/javascript" src="imports/import_header.js"></script>
    
    <body>
        
        <jsp:include page="imports/import_menu.jsp"/>
	
        <div id="contents">
            <div class="wrapper clearfix">
                <div class="main">
                    <h1>About Us</h1>
                    <div style="overflow-y: auto; max-height: 500px">
                        <p><i>Source: <a href="https://sophron.github.io/BAR/" target="_blank">https://sophron.github.io/BAR/</a><br/>Written by: George Chatzisofroniou</i></p>
                        <p>
                            <b>What is Broadcast Anonymous Routing?</b> 
                            Broadcast Anonymous Routing (BAR) is a system that provides strong anonymity for both the sender and the receiver of a message. In addition 
                            to that, it makes it difficult for other parties to distinguish if BAR users are actually communicating or not! These anonymity properties 
                            are not possible through other anonymous channels based only on traditional onion routing. However, communication through the BAR requires 
                            from the users more bandwidth than traditional anonymous communication systems.
                        </p>
                        <p>
                            <b>Can you explain broadcast anonymity in a simple manner?</b> 
                            Imagine Alice and Bob sitting in a crowded bar. Apart from Alice and Bob there are people standing between them and having their drinks. The 
                            bartender is willing to serve everyone around. Alice wants to communicate anonymously with Bob. She doesn't want anybody in the bar to know 
                            that she communicated with Bob and of course to hide the contents of her message. She can't just go there and talk to him because everyone 
                            will notice.
                            <br/>
                            What can Alice do?
                        </p>
                        <p>
                            <b>What can she do?</b> 
                            Alice writes a message for every customer in the bar and puts each message to a different envelop. Only Bob's message is real; all the other 
                            are "dummy" messages. She then hands the envelopes to the bartender along with specific directions.
                        </p>
                        <p>
                            <b>What happens next?</b> 
                            Everybody will read a dummy message from Alice, except from Bob, the actual receiver.<br/>
                            Now people will be able to see that Alice communicated with someone in the bar, but they cannot deduce that Bob was the actual receiver. Everyone 
                            got a message from Alice! Even the bartender himself cannot tell who the receiver is. Users can find the actual receiver, unless all of them cooperate 
                            to find this. That's it, Bob is an anonymous receiver!
                        </p>
                        <p>
                            <b>OK, I can see that Bob is anonymous, but I don't think that Alice really is.</b> 
                            BAR utilizes a couple of improvements in the basic version, to make Alice (the sender) anonymous. In the actual BAR protocol, Alice uses a random path of 
                            users, in order to handoff the message to the user for broadcasting. All the users have random keys in order to hide the routing path of Alice to the bartender 
                            (imagine something like Tor, with public keys dynamically updated for each user during his next login).
                        </p>
                        <p>
                            <b>You said that others cannot even distinguish if Alice (and Bob) are actually communicating or not.</b> 
                            This is an advanced anonymity property known as sender-receiver anonymity. BAR achieves this because all BAR users are programmed to continuously handoff messages 
                            of constant length and size and at constant time slots to the bartender (the broadcast server). If some users do not want to communicate at a giver slot, they will 
                            simply handoff to the bartender fake messages of the proper length. In this way, no one can tell if users of the system are actually communicating or not!
                        </p>
                    </div>
                </div>
            </div>
	</div>
	
	<div id="footer">
            <script type="text/javascript" src="imports/import_footer.js"></script>
	</div>
    </body>
</html>
