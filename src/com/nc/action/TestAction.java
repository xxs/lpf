package com.nc.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nc.dao.ibatis.TestDao;
import com.nc.service.DWRBasicService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class TestAction extends ActionSupport { 
	
    private DWRBasicService service;
	  @Override
	public String execute() throws Exception {
		  service.excesimpleProcedure("test");
          return null;
	  }
	public  DWRBasicService getService() {
		return service;
	}
	public  void setService(DWRBasicService service) {
		this.service = service;
	}
	


	


}
