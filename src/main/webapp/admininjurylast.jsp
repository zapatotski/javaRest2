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
        		fOut.close();
        		oOut.close();
        	}
        	catch(Exception e){
        		throw new RuntimeException(e);
        	}
        }
    }
                 
    
    public void adminPart(String month,String date) throws MalformedURLException{

        String strUrl="https://www.sofascore.com/football//2017-"+month+"-"+date+"/json?_="+new Date(new Date().getYear(),Integer.valueOf(month)-1,Integer.valueOf(date),23,59,60).getTime()/10000;
        URL url = new URL(strUrl);
    	
        serialization(Parser.parse(url,month,date),f);
        Parser.setInjury(f);
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
adminPart(request.getParameter("month"),request.getParameter("date"));
%>