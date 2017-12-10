<%@ page import="live.Game" %>
<%@ page import="live.Player" %>
<%@ page import="injury.Parser" %>


<%!  
    public void refresh(){
       Parser.createFilesForInjury();
    } 
%>

<%
refresh();
%>