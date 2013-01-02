package net.htjs.srvapp.web;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htjs.srvapp.util.FunctionLib;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.htjs.web.WebEngine;

public class ToNcEngine {
	Logger log = Logger.getLogger(WebEngine.class);
  Map inMap=new HashMap();
  //BaseBean bdao=new BaseBean("Srv");
  BaseBean bdao=null;
  int pagesize=15;
  public ToNcEngine() {
  }

  public ToNcEngine(HttpServletRequest request,HttpServletResponse response,WebApp app,VelocityContext ctx,ServletConfig config) {
	this.bdao=new BaseBean(app.getDb());
    this.inMap=new HashMap();
    this.insertFormVariables(request,inMap,ctx,app.getMultiValue());
    this.insertSessionVariables(request,inMap,ctx);
    this.insertCgiVariables(request,inMap,ctx);

    java.util.Date date = new java.util.Date();
    ctx.put("CGI_YEAR",FunctionLib.IntToString((date.getYear()+1900)));
    ctx.put("CGI_MONTH",FunctionLib.IntToString((date.getMonth()+1)));
    ctx.put("CGI_DAY",FunctionLib.IntToString(date.getDate()));
    String formvalue = request.getParameter("pageSize");
    if(formvalue!=null && !formvalue.equals("")){
      this.pagesize=FunctionLib.StringToInt(formvalue);
    }else{
      this.pagesize=app.getPagesize();
    }
    ctx.put("form_pageSize",FunctionLib.IntToString(pagesize));
    formvalue = request.getParameter("page");
    if(formvalue==null){
      inMap.put("START","1");
      inMap.put("END",FunctionLib.IntToString(this.pagesize));
    }else{
      inMap.put("START",FunctionLib.IntToString(this.pagesize*FunctionLib.StringToInt(formvalue)-this.pagesize+1));
      log.debug("START"+FunctionLib.IntToString(pagesize*FunctionLib.StringToInt(formvalue)-pagesize+1));
      inMap.put("END",FunctionLib.IntToString(this.pagesize*FunctionLib.StringToInt(formvalue)));
      log.debug("END"+FunctionLib.IntToString(pagesize*FunctionLib.StringToInt(formvalue)));
    }
    formvalue = request.getParameter("start");
    if(formvalue!=null && request.getParameter("start").equals("0"))
    {
        inMap.put("STARTS","1");
        inMap.put("ENDS",FunctionLib.IntToString(this.pagesize));       
    }else{
        inMap.put("STARTS",FunctionLib.IntToString(FunctionLib.StringToInt(formvalue)+1));
        inMap.put("ENDS",FunctionLib.IntToString(this.pagesize+FunctionLib.StringToInt(formvalue)));
      }    
    //限定查询的sql的数据范围,依靠岗位代码/sqlid来定位
    if(!app.getDjhm().equals("")){
      //String hm=DjhmUtil.getMaxDjhm(app.getDjhm());
      inMap.put("DJLX",app.getDjhm());
      String hm1="";
      Object rtn=this.bdao.getObject("selectMAX_DJHM",this.inMap);
      if (rtn == null) {
        hm1= "";
      }     else {
        hm1= rtn.toString();

      }

      //log.debug("CGI_KEYID:"+hm);
      log.debug("CGI_KEY:"+hm1);
      inMap.put("CGI_KEYID",hm1);
    }
  }

  //将表单变量送入map
 public void insertFormVariables(HttpServletRequest request,Map inMap,VelocityContext ctx,String multivalue)
{
    Enumeration e= request.getParameterNames();
    String formvalue = "";
    String keyname ="";
    String[] formvalues=null;
    Map rs[]=null;
    int rsize=0;
    while(e.hasMoreElements()){
      keyname =(String) e.nextElement();
      if(multivalue.indexOf(","+keyname+",")>-1){
        formvalues = request.getParameterValues(keyname);
        if(rsize==0){
          rsize=formvalues.length;
          rs=new HashMap[rsize];
          for(int i=0;i<rs.length;i++){
            rs[i]=new HashMap();
          }

        }
        if (!(formvalues == null)){
          log.debug("forms_"+keyname);
          log.debug(formvalues.toString());
          //System.out.println("forms_"+keyname+":"+formvalues.toString());
          //System.out.println("forms_"+keyname+":"+FunctionLib.isoToGb(formvalues.toString()));
          log.debug("size"+rsize);
          inMap.put("forms_" + keyname, formvalues);
          ctx.put("forms_" + keyname, formvalues);
          for(int i=0;i<rs.length;i++){
            log.debug(formvalues[i]);
            //System.out.println("form_"+keyname+":"+formvalues[i].toString());
            rs[i].put(keyname,formvalues[i]);
          }
        }

      }else{
        formvalue = request.getParameter(keyname);
        if (formvalue == null)
          formvalue = "";
          //log.debug("form_"+keyname);
          //log.debug(formvalue);
          //System.out.println("form_"+keyname+":"+formvalue.toString());
          //System.out.println("form_"+keyname+"gb:"+FunctionLib.isoToGb(formvalue.toString()));
          //System.out.println("form_"+keyname+"iso:"+FunctionLib.gbToIso(formvalue.toString()));
         // System.out.println("form_"+keyname+"utf:"+FunctionLib.gbToUtf(formvalue.toString()));
         // System.out.println("form_"+keyname+"utf2:"+FunctionLib.utfToGb(formvalue.toString()));
         // System.out.println("form_"+keyname+"utf2:"+FunctionLib.isoToUtf(formvalue.toString()));
        inMap.put("form_" + keyname,formvalue);
        if(keyname.equals("qxxkdm")) inMap.put(keyname,formvalue);
        //ctx.put("form_" + keyname, FunctionLib.gbToIso(formvalue));
        ctx.put("form_" + keyname,formvalue);
      }
    }
    if (!(rs == null)){
      inMap.put("rs", rs);
      ctx.put("rsform", rs);
    }
}

  //将session变量送入map
  public void insertSessionVariables(HttpServletRequest request,Map inMap,VelocityContext ctx)
  {
      Enumeration e = request.getSession().getAttributeNames();
      String sessvalue = "";
      while(e.hasMoreElements())
      {
          String keyname = (String)e.nextElement();
            try {
              sessvalue = request.getSession().getAttribute(keyname).toString();
              if (sessvalue == null)
                sessvalue = "";
            }
            catch (Exception ex) {
              sessvalue = "";
            }
            inMap.put("session_" + keyname, sessvalue);
            if(keyname.equals("USERID")) inMap.put("LOG_USERID", sessvalue);
            if(keyname.equals("CZRY_DM")) inMap.put("LOG_CZRY_DM", sessvalue);
            if(keyname.equals("ZZJG_DM")) inMap.put("LOG_ZZJG_DM", sessvalue);
            ctx.put("session_" + keyname, FunctionLib.gbToIso(sessvalue));
            log.debug("session_"+keyname);
            log.debug(sessvalue);
            log.debug(inMap);
            log.debug(FunctionLib.isoToGb(sessvalue));
      }
  }
  //将CGI变量送入map
  public void insertCgiVariables(HttpServletRequest request,Map inMap,VelocityContext ctx)
  {
         ctx.put("CGI_REMOTE_ADDR", request.getRemoteAddr()==null ? "":request.getRemoteAddr());
         ctx.put("CGI_QUERY_STRING", request.getQueryString()==null ? "":request.getQueryString());
         ctx.put("CGI_REMOTE_HOST", request.getRemoteHost()==null ? "":request.getRemoteHost());
         java.util.Date dt=new java.util.Date();
         ctx.put("CGI_DATE_YEAR", FunctionLib.IntToString(dt.getYear()+1900));
         ctx.put("CGI_DATE_MONTH", FunctionLib.IntToString(dt.getMonth()+1));
         ctx.put("CGI_DATE_DATE", FunctionLib.IntToString(dt.getDate()));
  }
  //拼接数据权限条件，送入map 
  //处理数据，为velocity模板返回数据
  public List getData(String sqlname) throws IOException, Exception
  {
    //return this.bdao.getList(sqlname,this.inMap);
    inMap.put("sql_cond", "1=1");
    //return CommonUtil.toISO(this.bdao.getList(sqlname,this.inMap));
    return this.bdao.getList(sqlname,this.inMap);
  }

  public List getData(String sqlname,String escape) throws IOException, Exception
  {
    if(escape.equals("")) return getData(sqlname);
    inMap.put("sql_cond", "1=1");
    //return CommonUtil.escape(this.bdao.getList(sqlname,this.inMap));
    return this.bdao.getList(sqlname,this.inMap);
  }  
}
