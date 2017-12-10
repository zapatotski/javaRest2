<%@ page import="java.io.File" %>
<%@ page import="java.io.*" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.ObjectInputStream" %>
<%@ page import="java.io.ObjectOutputStream" %>
<%@ page import="java.io.Serializable" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.MalformedURLException" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.*" %>
<%@ page import="live.Game" %>
<%@ page import="live.Player" %>
<%@ page import="injury.Parser" %>


<%! 
File f=new File("matchiinjury.xml");

    
    public void serialization(Map<String,List<Game>> m, File f){
        FileOutputStream fOut=null;
        ObjectOutputStream oOut=null;
        try{
            fOut=new FileOutputStream(f);
            oOut=new ObjectOutputStream(fOut);
            oOut.writeObject(m);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        finally{
        	try{
        		oOut.close();
        	}
        	catch(Exception e){
        		throw new RuntimeException(e);
        	}
        }
    }
                 
    
    public void adminPart() throws MalformedURLException{
    	String month="";
    	String date="";
    	int m=new Date().getMonth()+1;
    	int d=new Date().getDate();
    	if(m<10){
    		month="0"+m;
    	}
    	else{
    		month=""+m;
    	}
    	if(d<10){
    		date="0"+d;
    	}
    	else{
    		date=""+d;
    	}
        
        String strUrl="https://www.sofascore.com/football//2017-"+month+"-"+date+"/json?_="+new Date().getTime()/10000;
        URL url = new URL(strUrl);
    	
        serialization(Parser.parse(url),f);
        
    	//логирование последнего обновления данных
    	FileWriter fOut=null;
    	try{
            fOut=new FileWriter(new File("obnovinjury.txt"), false);
            fOut.write(String.valueOf(new Date().getTime()));
            fOut.flush();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }    	
    } 
%>

<%
adminPart();
%>