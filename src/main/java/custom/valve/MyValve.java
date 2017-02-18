package custom.valve;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;



public class MyValve extends ValveBase {

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
//    	        System.out.println("a VALVE");
//    	        response.getWriter().write("<html><body>a7a<pre>");

//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        Enumeration<String> headerNames = httpRequest.getHeaderNames();

    	System.out.println("A7A START ");
    	

    	
//        request.getRequestURI();
        System.out.println("URI "+request.getRequestURI());
        String[] types = {".png",".jpg",".gif",".jpeg"};

//    	if(!request.getRequestURI().toString().equals("/")){
        String uri=request.getRequestURI().toString();
        
        
        if(uri.contains(".")&&
            Arrays.asList(types).contains(
            		uri.substring(uri.lastIndexOf('.'), uri.length()))){
        	System.out.println("A7A no match exit ");

            getNext().invoke(request, response);

    	}else{
    	
String script="<script type='text/javascript' src=";
script+="\"http://localhost:92/hiwi/thrift.js\"></script>";
script+="<script type='text/javascript' src=";
script+="\"http://localhost:92/hiwi/Types_types.js\"></script>";
script+="<script type='text/javascript' src=";
script+="\"http://localhost:92/hiwi/TAny2Pmp.js\"></script>";
script+="<script type='text/javascript' src=";
script+="\"http://localhost:92/hiwi/myscript.js\"></script>";


//response.get
//response.getWriter().write("<html><head>"+script+"<head>");
//response.getWriter().append(script);
//        System.out.println("BEFORE filter");
        PrintWriter out = response.getWriter();
//        		request.
        
    	
        CharResponseWrapper responseWrapper = new CharResponseWrapper(
                (Response) response);

        String servletResponse = new String(responseWrapper.toString());
//
        
        out.write(servletResponse + script); // Here you can change the response

        //
//
//        System.out.println("AFTER filter, original response: "
//                + servletResponse);

//    	System.out.println("A7A END "+servletResponse.toString());
        getNext().invoke(request, response);

    	
    }
    }

}
