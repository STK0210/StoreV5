package com.lky.store.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class OutPut {
    public static void outputToClient(String result,HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html;charset=UTF-8");       	
        PrintWriter out = response.getWriter();     
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");    
        out.println("<HTML>");    
        out.println("    <HEAD><meta charset=\"UTF-8\"><TITLE>A Servlet</TITLE></HEAD>");    
        out.println("    <BODY>");
        
        out.println(result);
        
        out.println("    </BODY>");    
        out.println("</HTML>");    
        out.flush();    
        out.close();       
    }    
    
    public static void outputToClient(List<String> list,HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html;charset=UTF-8");       	
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");    
        out.println("<HTML>");    
        out.println("    <HEAD><meta charset=\"UTF-8\"><TITLE>A Servlet</TITLE></HEAD>");    
        out.println("    <BODY>");
        
        for(int i = 0; i < list.size(); i++)
        {
            out.println(list.get(i));
            System.out.println(list.get(i));
        }
        
        out.println("    </BODY>");    
        out.println("</HTML>");    
        out.flush();    
        out.close();       
    }
}
