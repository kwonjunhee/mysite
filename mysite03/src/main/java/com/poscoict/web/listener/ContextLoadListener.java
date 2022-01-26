package com.poscoict.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext sc = sce.getServletContext();
    	//sc.setAttribute(null, sc); 어플리케이션 스코프에 저장
    	String contextConfigLocation = sc.getInitParameter("contextConfigLocation");
    	System.out.println("Application(mysite02) Starts..:" + contextConfigLocation);
    }
	
    public void contextDestroyed(ServletContextEvent sce)  { 
    	
    }

}
