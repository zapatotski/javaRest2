<%@ page import="live.Game" %>
<%@ page import="live.Player" %>
<%@ page import="injury.Parser" %>


<%!  
    public void refresh(String url){
       Parser.refreshShedule(url);
    } 
%>

<%
refresh(request.getServerName());
%>