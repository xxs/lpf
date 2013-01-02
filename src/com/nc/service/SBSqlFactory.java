package com.nc.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class SBSqlFactory {
	
	private static Map datasource1 = new HashMap();
	
	static {

		datasource1.put("datasource1", " temp_report_forecast t  " );
		
	
		
	}
	
   public static String Union(String[] field ,Map<String,String> queryparam){

       int index1 = indexOf(field, "allmonth");
	   int index2 = indexOf(field, "fectmonth");

	   
       StringBuffer buffer = new StringBuffer();
       String area2 = null;
	   	if(queryparam.get("orgtype").equals("06")){
		   area2 =	queryparam.get("AREADESC");
			
		}else{
			area2 = queryparam.get("ORGDESC");
			
		}
	   if(index1!=-1){
		    
			String startdata = (String)queryparam.get("startdata");
			String stryear = startdata.substring(0, 4);
			int year = (Integer.parseInt(stryear)-1);
			String month = startdata.substring(5,7);
			String pmonth = "M"+String.valueOf(year)+month;
			///////
	
			
		   buffer.append(" Select ");
		   int count = field.length;
		    buffer.append(" typedesc,itemtypeDesc,itemStandard,itemName,area1name,orgname" +
		    		",0 as ingood, ingood as allmonth, 0 as fectmonth, 0 as Taskmny");
			buffer.append(" from ");
			buffer.append(datasource1.get("datasource1"));
			buffer.append(" where t.orgtype = '"+queryparam.get("orgtype")+"' and " );
			buffer.append(" t.periodno = '"+pmonth+"'  and " );
			buffer.append(" t.area1name = decode(nvl('"+queryparam.get("bigarea")+"',''),'',t.area1name,'"+queryparam.get("bigarea")+"') and " );
			
			buffer.append(" t.TYPEDESC = decode(nvl('"+queryparam.get("bigclass")+"',''),'',t.TYPEDESC,'"+queryparam.get("bigclass")+"') and " );
			buffer.append(" t.ITEMTYPEDESC = decode(nvl('"+queryparam.get("class")+"',''),'',t.ITEMTYPEDESC,'"+queryparam.get("class")+"') and " );
			if(StringUtils.isNotEmpty(queryparam.get("name"))){
				buffer.append(" t.ITEMNAME like  '%"+queryparam.get("name")+"%' ");
				buffer.append(" and   ");
			}
			if(StringUtils.isNotEmpty(queryparam.get("produceId"))){
				buffer.append(" t.ITEMCODE like  '%"+queryparam.get("produceId")+"%' ");
				buffer.append(" and   ");
			}
			
			buffer.append(" t.orgname = decode(nvl('"+area2+"',''),'',t.orgname,'"+area2+"')   ");
			
			
	

			buffer.append(" union all ");
			
	   }
	


       if(index2!=-1) {

			String startdate = queryparam.get("startdata");
			String stryear = startdate.substring(0, 4);
			int year = (Integer.parseInt(stryear)-1);
			String enddtae = queryparam.get("enddata");
			String endstryear = enddtae.substring(0, 4);
			int endyear = (Integer.parseInt(endstryear)-1);
		
			String startyear =String.valueOf(year)+startdate.substring(4,startdate.length());
			String strendyear = String.valueOf(endyear)+enddtae.substring(4,enddtae.length());
			///////
			
			
			///
			
    	   buffer.append(" Select ");
		   int count = field.length;
		   buffer.append(" typedesc,itemtypeDesc,itemStandard,itemName,area1name,orgname" +
   		    ",0 as ingood, 0 as allmonth,ingood as fectmonth, 0 as Taskmny");
			buffer.append(" from ");
			buffer.append(datasource1.get("datasource1"));
			buffer.append(" where t.orgtype = '"+queryparam.get("orgtype")+"' and " );
			buffer.append("  t.indate>='"+startyear+"' and t.indate<='"+strendyear+"' and " );
			buffer.append(" t.area1name = decode(nvl('"+queryparam.get("bigarea")+"',''),'',t.area1name,'"+queryparam.get("bigarea")+"') and " );
			buffer.append(" t.TYPEDESC = decode(nvl('"+queryparam.get("bigclass")+"',''),'',t.TYPEDESC,'"+queryparam.get("bigclass")+"') and " );
			buffer.append(" t.ITEMTYPEDESC = decode(nvl('"+queryparam.get("class")+"',''),'',t.ITEMTYPEDESC,'"+queryparam.get("class")+"') and " );
			if(StringUtils.isNotEmpty(queryparam.get("name"))){
				buffer.append(" t.ITEMNAME like  '%"+queryparam.get("name")+"%' ");
				buffer.append(" and   ");
			}
			if(StringUtils.isNotEmpty(queryparam.get("produceId"))){
				buffer.append(" t.ITEMCODE like  '%"+queryparam.get("produceId")+"%' ");
				buffer.append(" and   ");
			}
			buffer.append(" t.orgname = decode(nvl('"+area2+"',''),'',t.orgname,'"+area2+"')  ");
			

			buffer.append(" union all ");
	
	   }
       buffer.append(" Select ");
     
       buffer.append(" typedesc,itemtypeDesc,itemStandard,itemName,area1name,orgname" +
	    ",ingood, 0 as allmonth,0 as fectmonth,  Taskmny");
	
		buffer.append(" from ");
		buffer.append(datasource1.get("datasource1"));
		buffer.append(" where t.orgtype = '"+queryparam.get("orgtype")+"' and " );
		buffer.append("  t.indate>='"+queryparam.get("startdata")+"' and t.indate<='"+queryparam.get("enddata")+"' and " );
		buffer.append(" t.area1name = decode(nvl('"+queryparam.get("bigarea")+"',''),'',t.area1name,'"+queryparam.get("bigarea")+"') and " );
		buffer.append(" t.TYPEDESC = decode(nvl('"+queryparam.get("bigclass")+"',''),'',t.TYPEDESC,'"+queryparam.get("bigclass")+"') and " );
		buffer.append(" t.ITEMTYPEDESC = decode(nvl('"+queryparam.get("class")+"',''),'',t.ITEMTYPEDESC,'"+queryparam.get("class")+"') and " );
		if(StringUtils.isNotEmpty(queryparam.get("name"))){
			buffer.append(" t.ITEMNAME like  '%"+queryparam.get("name")+"%' ");
			buffer.append(" and   ");
		}
		if(StringUtils.isNotEmpty(queryparam.get("produceId"))){
			buffer.append(" t.ITEMCODE like  '%"+queryparam.get("produceId")+"%' ");
			buffer.append(" and   ");
		}
		buffer.append(" t.orgname = decode(nvl('"+area2+"',''),'',t.orgname,'"+area2+"')  ");
	

	   return buffer.toString();
	   
   }
   
   public static int indexOf(String[] field,String str){
	   int index = -1;
	   for(int i=0;i<field.length;i++){
		  if(field[i].indexOf(str)!=-1) {
			  return i;
		  }
	   }
	   return index;
   }
	
	public static String  MakeSql(String[] field,Map<String,String> queryparam,String strgroup ){
		StringBuffer buffer = new StringBuffer(" select B.* from ("
        	+" select A.*,ROWNUM ROWNO,last_value(ROWNUM) over() as total"
        	+" from ( Select  ");
		  int count = field.length;
		  for(int i=0;i<count;i++){
				buffer.append(field[i]);
		
			if((i+1)!=count){
				buffer.append(",");
			}
		}
		buffer.append(" from ( ");
		buffer.append(Union(field,queryparam));
		buffer.append(") ");
		buffer.append(" group by ");
		buffer.append(strgroup);
		buffer.append(" order by ");
		buffer.append(strgroup);
		buffer.append(" ) A ) B where B.ROWNO between ? and ? ");
		return buffer.toString();
		
		
	}
	
	public  static void main(String[] arg){
		String[] array = {"sum(fectmonth) fectmonth","sum(allmonth) allmonth","itemtypeDesc","itemStandard","itemName"};
        Map<String,String> param = new HashMap<String,String>();
        param.put("orgtype", "06");
        param.put("startdata", "2008-10-01");
        param.put("enddata", "2008-10-30");
        param.put("bigarea", "");
        param.put("area1name", "");
        param.put("orgname", "");
    
		System.out.println(SBSqlFactory.MakeSql(array,param,"itemtypeDesc,itemStandard,itemName"));
	}
   

	


}
