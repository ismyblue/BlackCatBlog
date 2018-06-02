package com.ismyblue.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ismyblue.field.path.ConfigPathField;

public class InitAppPathListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ConfigPathField.APPPATH_STRING = sce.getServletContext().getRealPath("");
		System.out.println(ConfigPathField.APPPATH_STRING);	
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
