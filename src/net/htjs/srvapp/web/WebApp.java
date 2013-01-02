package net.htjs.srvapp.web;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import net.htjs.srvapp.util.FunctionLib;

import java.util.Vector;
public class WebApp {
  private String sql;
  private Vector sqls;
  private String sqlSplit;
  private String webAppContent;
  private String path;
  private String template;
  private int pagesize;
  private String sqlType;
  private String successMsg;
  private String failMsg;
  private String url;
  private String target;
  private String multiValue;
  private String secType;
  private String clear_session;
  private Vector clear_sessions;
  private String djhm;
  private String db;
  private String escape;
  private String code;

  public WebApp(String qryContent, String path)
  {
      this.path = path;
      setWebAppContent(qryContent);
  }

  public WebApp(String qryContent)
  {
      path = "c:";
      setWebAppContent(qryContent);
  }

  public String getSql()
  {
      return this.sql;
  }
  public Vector getSqls()
  {
      return this.sqls;
  }

  public String getSqlSplit()
  {
      return this.sqlSplit;
  }

  public String getTemplate()
 {
    return this.template;
 }

  public int getPagesize(){
    return this.pagesize;
  }

  public String getSqlType()
  {
      return this.sqlType;
  }

  public String getSuccessMsg()
  {
      return this.successMsg;
  }

  public String getFailMsg()
  {
      return this.failMsg;
  }

  public String getUrl()
  {
      return this.url;
  }

  public String getTarget()
  {
      return this.target;
  }

  public String getMultiValue()
  {
      return this.multiValue;
  }

  public String getSecType()
  {
      return this.secType;
  }
  public void setTemplate(String templ)
  {
    this.template=templ;
  }
  public String getClear_session()
  {
      return this.clear_session;
  }
  public Vector getClear_sessions()
  {
      return this.clear_sessions;
  }
  public String getDjhm()
  {
      return this.djhm;
  }
  public String getDb()
  {
      return this.db;
  }
  public String getEscape()
  {
      return this.escape;
  }  
  public String getCode()
  {
      return this.code;
  }    
  public void setWebAppContent(String qryContent)
  {
      this.webAppContent = qryContent;
      try{
        FunctionLib.skipComments(FunctionLib.getInnerXML(qryContent, "web:page",""));
        sql = FunctionLib.getInnerXML(qryContent, "web:sql", "");
        sqlSplit = FunctionLib.getInnerXML(qryContent, "web:sql_split", "##");
        sqls = FunctionLib.split(sql, sqlSplit);
        sqlType = FunctionLib.getInnerXML(qryContent, "web:sql_type", "select");
        secType = FunctionLib.getInnerXML(qryContent, "web:secType", "");
        successMsg = FunctionLib.getInnerXML(qryContent, "web:successMsg", "操作成功！");
        failMsg = FunctionLib.getInnerXML(qryContent, "web:failMsg", "操作失败！");
        url = FunctionLib.getInnerXML(qryContent, "web:url", "");
        target = FunctionLib.getInnerXML(qryContent, "web:target", "parent");
        pagesize=FunctionLib.StringToInt(FunctionLib.getInnerXML(qryContent, "web:pagesize", "0"));
        multiValue =","+ FunctionLib.getInnerXML(qryContent, "web:multiValue", "")+",";
        template = FunctionLib.getInnerXML(qryContent, "web:template","");
        clear_session = FunctionLib.getInnerXML(qryContent, "web:clear", "");
        clear_sessions = FunctionLib.split(clear_session, ",");
        djhm = FunctionLib.getInnerXML(qryContent, "web:keyid", "");
        db = FunctionLib.getInnerXML(qryContent, "web:db", "Srv");
        escape = FunctionLib.getInnerXML(qryContent, "web:escape", "");
        code = FunctionLib.getInnerXML(qryContent, "web:code", "");

        if (template == "") {
          template = FunctionLib.getInnerXML(qryContent, "web:template_file","");
          //templateDoc = FunctionLib.openFile(path + "\\template\\" + templateDoc);
        }
      }catch(Exception e){
        e.printStackTrace();
      }

  }

}
