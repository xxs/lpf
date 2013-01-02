package net.htjs.srvapp.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.io.*;
import java.util.*;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class FunctionLib {
    private static long UIDCounter=System.currentTimeMillis();
    public static synchronized String generateUID() {
            FunctionLib.UIDCounter++;
            return System.currentTimeMillis()+"JCS"+UIDCounter;
    }

    public static String replaceUID(String content,String oldWord) {
            String newWord=FunctionLib.generateUID();
            content=content.replaceAll(oldWord,newWord);
            return content;
    }

    public static String openFile(String filename) throws Exception{
            String results="";
            String line;
            File in_file=new File(filename);
            byte[] bFileContents=new byte[(int) in_file.length()];
            try{
                    FileInputStream fis=new FileInputStream(in_file);
                    int intResults=fis.read(bFileContents);
                    if(intResults!=in_file.length()){
                            throw new IOException("不能打开文件"+filename);
                    }
                    results=new String(bFileContents);
                    fis.close();
            }
            catch(FileNotFoundException e){
                    throw new Exception("找不到文件"+filename);
            }
            catch(IOException e){
                    throw new Exception("文件IO错误！");
            }
            catch(Exception e){
                    throw new Exception("出现未知错误！");
            }
            return results;
    }

    public static String getInnerXML(String rawXML,String tagname,String defaultValue){
            String tempString=new String(rawXML);
            int position=tempString.indexOf("<"+tagname);
            if(position>-1){
                    tempString=tempString.substring(position+tagname.length());
                    position=tempString.indexOf(">");
                    if(position>-1){
                            tempString=tempString.substring(position+1);
                            position=tempString.indexOf("</"+tagname);
                            if(position>-1){
                                    tempString=tempString.substring(0,position);
                            }
                            else{
                                    tempString=defaultValue;
                            }
                    }
                    else{
                            tempString=defaultValue;
                    }
            }
            else{
                    tempString=defaultValue;
            }
            return tempString;
    }

    public static String getCDATASection(String rawXML,String tagname,String defaultValue){
            try{
                    String innerXML=getInnerXML(rawXML,tagname,defaultValue);
                    String cDataStart="<![CDATA[";
                    String cDataEnd="]]>";
                    String innerXMLStart=innerXML.substring(0,cDataStart.length());
                    String innerXMLEnd=innerXML.substring(innerXML.length()-cDataEnd.length());
                    if((innerXMLStart.equals(cDataStart))&&(innerXMLEnd.equals(cDataEnd))){
                            return innerXML.substring(cDataStart.length(),innerXML.length()-cDataEnd.length()).trim();
                    }else{
                            return innerXML;
                    }
            }catch(Exception e){
                    return defaultValue;
            }
    }

    public static String skipComments(String content) throws Exception{
            int start;
            int end;
            start=content.indexOf("<!--");
            while(start>-1){
                    end=content.indexOf("-->",start);
                    if(end==-1){
                            throw new Exception("XML文件丢失了注释结束标记！");
                    }
                    String tempString=content.substring(start,end+3);
                    content=content.replaceAll(tempString,"");
                    start=content.indexOf("<!--");
            }
            return content;
    }

    public static String SQLEncode(String content) {
            content=content.replaceAll("\'","\'\'");
            return content;
    }

    public static String XMLEncode(String content) {
            if(content!=null)
            {
                    content=content.replaceAll("&","#amp;");
                    content=content.replaceAll("#amp;","&amp;");
                    content=content.replaceAll("<","&lt;");
                    content=content.replaceAll(">","&gt;");
                    content=content.replaceAll("\"","&quot;");
            }
            else
                    content="";
            return content;
    }

    public static String HTMLLineBreak(String content) {
            content=content.replaceAll("\r","<br>");
            return content;
    }

    public static int StringToInt(String content) {
            try{
                    return Integer.parseInt(content);
            }catch(Exception e){
                    return -1;
            }
    }

    public static String IntToString(int num) {
            try{
                    return Integer.toString(num);
            }catch(Exception e){
                    return "";
            }
    }

    //分拆字符串
    public static Vector split(String sy_str,String sy_split)
    {
        Vector lv_vector=new Vector();
        StringTokenizer tokens=null;
        tokens=new StringTokenizer(sy_str,sy_split);
        try
        {
        String s="";
            while(tokens.hasMoreTokens())
            {
            s=tokens.nextToken();
                lv_vector.addElement(s);
            }
        }
        catch(NoSuchElementException e)
        {
            return null;
        }
        return lv_vector;
    }
    //utf-8转换为ISO8859_1
    public static String utfToIso(String sy_str)
    {
    	try{
    		return new String(sy_str.getBytes("utf-8"),"ISO-8859-1"); 
    	}catch(Exception e){
    		return sy_str;
    	}
       
    }  
    
    //ISO8859_1转换为utf-8
    public static String isoToUtf(String sy_str)
    {
    	try{
    		return new String(sy_str.getBytes("ISO-8859-1"),"utf-8"); 
    	}catch(Exception e){
    		return sy_str;
    	}
       
    }  
    //GB2312转换为utf-8
    public static String gbToUtf(String sy_str)
    {
    	try{
    		return new String(sy_str.getBytes("GB2312"),"utf-8"); 
    	}catch(Exception e){
    		return sy_str;
    	}
       
    }   
 
    //utf-8转换为GB2312
    public static String utfToGb(String sy_str)
    {
    	try{
    		return new String(sy_str.getBytes("utf-8"),"GBK"); 
    	}catch(Exception e){
    		return sy_str;
    	}
       
    }    
    //GB2312转换为ISO8859_1
    public static String gbToIso(String sy_str)
    {
            try{
                    return new String(sy_str.getBytes("GBK"),"ISO-8859-1");
            }catch(Exception e){
                    return sy_str;
            }

    }

    //GB2312转换为ISO8859_1
    public static String isoToGb(String sy_str)
    {
            try{
                    return new String(sy_str.getBytes("ISO-8859-1"),"GBK");
            }catch(Exception e){
                    return sy_str;
            }

    }
    public static String[] isoToGb(String[] sy_str)
    {
            try{
              for(int i=0;i<sy_str.length;i++){
                sy_str[i]= new String(sy_str[i].getBytes("ISO-8859-1"), "GBK");
              }
              return sy_str;
            }catch(Exception e){
                    return sy_str;
            }

    }

    //如果不存在则新建目录
    public static boolean newFolder(String s){
                  String sPath = s;
                  sPath = sPath.toString();//中文转换
                  //创建一个File(文件）对象
                  File myFilePath = new File(sPath);
                  if(!myFilePath.exists())
                  {
                      try
                      {
                          myFilePath.mkdirs();
                          return true;
                      }catch(Exception e)
                      {
                          e.printStackTrace();
                          return false;
                      }
                  }
                  else return true;
    }

    /********************
            * 新建文件
            *******************/
    public static boolean newFile(String s1,String s2)
   {
         String  sFilePath = s1;
         sFilePath = sFilePath.toString();
         File myFilePath = new File(sFilePath);
               if(!myFilePath.exists())
               {
                   try
                   {
                       myFilePath.createNewFile();
                       FileWriter resultFile=new FileWriter(myFilePath);
                       PrintWriter myFile=new PrintWriter(resultFile);
                       String sContent = s2.toString();
                       myFile.println(sContent);
                       myFile.close();
                       resultFile.close();
                       return true;
                   }catch(Exception e)
                   {
                       e.printStackTrace();
                       return false;
                   }
               }
               else return false;
           }

           /********************
            * 删除文件
            *******************/
           public static boolean delFile(String s)
           {
             String  sFilePath = s;
               sFilePath = sFilePath.toString();
               File dFile = new File(sFilePath);
               if(dFile.exists())
               {
                   try
                   {
                       dFile.delete();
                       return true;
                   }catch(Exception e)
                   {
                       e.printStackTrace();
                       return false;
                   }
               }
               else
               {
                   System.out.print("文件：" + s + "不存在!");
                   return false;
               }
           }

           /********************
            * 复制文件
            *******************/
           public static boolean copyFile(String s1,String s2)
           {
               int bytesum=0;
               int byteread=0;
               //file:读到流中
               try
               {
                   InputStream inStream=new FileInputStream(s1);
                   FileOutputStream fs=new FileOutputStream(s2);
                   byte[]  buffer =new  byte[1444];
                   while ((byteread=inStream.read(buffer))!=-1)
                   {
                      System.out.println("--  "+byteread+" --");
                      bytesum+=byteread;
                      System.out.println(bytesum);
                      fs.write(buffer,0,byteread);
                   }
                   inStream.close();
                   fs.close();
                   return true;
               }catch(Exception e)
               {
                   return false;
                   }
           }

           /********************
            * 复制文件夹
            *******************/
           public static boolean copyFolder(String s1,String s2)
           {
           try{
               (new File(s2)).mkdirs();
               File[] file=(new File(s1)).listFiles();
               for(int i=0;i<file.length;i++)
               {
                   if(file[i].isFile()){
                       file[i].toString();
                       FileInputStream input=new FileInputStream(file[i]);
                       FileOutputStream output=new FileOutputStream(s2+"/"+(file[i].getName()).toString());
                       byte[] b=new byte[1024*5];
                       int len;
                       while((len=input.read(b))!=-1){
                           output.write(b,0,len);
                       }
                       output.flush();
                       output.close();
                       input.close();
                   }
               }
               return true;
            }catch(IOException e){
                e.printStackTrace();
                return false;
                }
           }

           /********************
            * 得到文本文件的内容
            *******************/
           public static String getFile(String s1)
           {
           try{
               StringBuffer sb = new StringBuffer();
               BufferedReader in = new BufferedReader(new FileReader(s1));
               while(in.readLine()!=null){
                   sb.append(in.readLine()+"\n\r");
               }
               return sb.toString();
            }catch(IOException e){
                e.printStackTrace();
                return null;
                }
           }
           /********************
                   * 替换文件内容,文件不存在则新建
                   *******************/
           public static boolean replaceFile(String s1,String s2)
          {
                String  sFilePath = s1;
                sFilePath = sFilePath.toString();
                File myFilePath = new File(sFilePath);
                      if(!myFilePath.exists())
                      {
                         return newFile(s1,s2);
                      }
                      else if(delFile(s1)){
                         return newFile(s1,s2);
                      }
                      else
                         return false;
                  }
           public static String escape (String src) {
               int i;
               char j;
               StringBuffer tmp = new StringBuffer();
               tmp.ensureCapacity(src.length()*6);
               for (i=0;i<src.length();i++) {
                   j = src.charAt(i);
                   if(Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
                       tmp.append(j);
                   else
                       if(j<256){
                           tmp.append("%");
                           if(j<16)
                               tmp.append("0");
                           tmp.append(Integer.toString(j,16));
                       } 
                       else{
                           tmp.append("%u");
                           tmp.append(Integer.toString(j,16));
                       }
               }
               return tmp.toString();
           }
           
           public static String unescape (String src) {
               StringBuffer tmp = new StringBuffer();
               tmp.ensureCapacity(src.length());
               int lastPos=0,pos=0;
               char ch;
               while (lastPos<src.length()) {
                   pos = src.indexOf("%",lastPos);
                   if(pos == lastPos) {
                       if(src.charAt(pos+1)=='u') {
                           ch = (char)Integer.parseInt(src.substring(pos+2,pos+6),16);
                           tmp.append(ch);
                           lastPos = pos+6;
                       }
                       else{
                           ch = (char)Integer.parseInt(src.substring(pos+1,pos+3),16);
                           tmp.append(ch);
                           lastPos = pos+3;
                       }
                   }
                   else{
                       if(pos == -1){
                           tmp.append(src.substring(lastPos));
                           lastPos=src.length();
                       }
                       else{
                           tmp.append(src.substring(lastPos,pos));
                           lastPos=pos;
                       }
                   }
               }
               return tmp.toString();
           }
}
