package com.newapp.report.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.nc.comm.NewAppUtil;
import com.nc.dao.ibatis.IDAO;
import com.opensymphony.xwork2.ActionSupport;

public class TimeValueCharAction extends ActionSupport {

	private IDAO baseDao;

	public IDAO getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IDAO baseDao) {
		this.baseDao = baseDao;
	}

	private List datavalue;

	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Map param = NewAppUtil.convertReportParam(request);
		datavalue = baseDao.getDataByObject((String)param.get("querysql"), param);
		return (String)param.get("forward");
	}

	

	public List getDatavalue() {
		return datavalue;
	}

	public void setDatavalue(List datavalue) {
		this.datavalue = datavalue;
	}

}
