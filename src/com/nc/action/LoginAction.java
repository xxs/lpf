// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2010-5-7 11:50:30
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   LoginAction.java

package com.nc.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.nc.bean.RoleBean;
import com.nc.bean.UserBean;
import com.nc.comm.NewAppUtil;
import com.nc.service.UserService;

// Referenced classes of package com.nc.action:
//            BaseAction

public class LoginAction extends BaseAction
    implements SessionAware
{

    public LoginAction()
    {
    }

    public void setSession(Map session)
    {
        this.session = session;
    }

    public Map getSession()
    {
        return session;
    }

    public String execute()
        throws Exception
    {
        if(session.get("com_newapp_web_user_Info") != null && StringUtils.isBlank(getActions()))
        {
            UserBean usersession = (UserBean)session.get("com_newapp_web_user_Info");
            userName = usersession.getUserName();
            passWord = usersession.getPassWord();
            UserBean user = userservice.findUserByUserName(userName);
            getSession().put("com_newapp_web_user_Info", user);
            return "success";
        }
        if(getActions() == null && getActions().length() <= 0){System.out.println("aa2");
            return "error";
        }else{
            return executeMethod(getActions());
        }
    }

    public String dologin()
        throws Exception
    {System.out.println(corpid);
        if(StringUtils.isNotBlank(passWord) && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(corpid))
        {
            UserBean user =null; 
			try {
				user = userservice.findUserByUserNameCorp(userName,corpid);
				//user = corpuserservice.findUserByUserNameCorp(userName,corpid);
				getSession().put("SKIN", user.getSkin());
				List roles = user.getRoles();
				StringBuffer srole = new StringBuffer();
				for(int i = 0; i < roles.size(); i++)
				    srole.append(((RoleBean)roles.get(i)).getRoleId());

				getSession().put("USERNAME", userName);
				getSession().put("NAME", user.getName());
				getSession().put("ROLES", srole.toString());
				getSession().put("CORPID", corpid);
			} catch (Exception e) {
			}
            if(user == null)
            {
                //addActionError(getText("error.login.user"));
            	this.addFieldError("userName", "用户名不存在");
                return "error";
            }
            String md5 = NewAppUtil.MD5(passWord);
            if(StringUtils.isEmpty(user.getPassWord()) || md5.equals(user.getPassWord()))
            {
                getSession().put("com_newapp_web_user_Info", user);
                return "success";
            } else
            {
                //addActionError(getText("error.login.password"));
                return "error";
            }
        } else
        {
            return "error";
        }
    }

    public String loginOut()
    {
    	System.out.println("用户退出");
        getSession().remove("com_newapp_web_user_Info");
        return "relogin";
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassWord()
    {
        return passWord;
    }

    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }

    public UserService getUserservice()
    {
        return userservice;
    }

    public void setUserservice(UserService userservice)
    {
        this.userservice = userservice;
    }
    
    private String userName;
    private String passWord;
    private UserService userservice;
    private String corpid;
    private Map session;
    private String name ;
	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}