package net.htjs.srvapp.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.htjs.util.CommonUtil;




/**
 * @author zcy
 * 
 * 属性设置基类
 * 
 */
public abstract class BaseAttribute{
	protected Map attribute;
	/**
	 * 
	 */
	public BaseAttribute(){
		attribute = new HashMap();
	}
	/**
	 * 通过关键字取键值
	 * 
	 * @param key
	 * @return　键值
	 */
	public Object getAttribute(Object key){
		if(attribute.containsKey(key)){
			return attribute.get(key);
		}
		return null;
	}
	/**
	 * @return　返回所有属性
	 */
	public Map getAttributes(){
		return attribute;
	}
	/**
	 * @param key
	 * @param value
	 * 通过关键字和值设置属性
	 */
	public void setAttribute(Object key,Object value){
		if(key.toString().endsWith("_A")){
			if(value instanceof String){
				attribute.put(key, value.toString().split(","));
				return ;
			}
		}
		attribute.put(key, value);
	}
	/**
	 * @param map
	 * 将ｍａｐ键和键值设置到当前属性中
	 */
	public void setAttributes(Map map){
		attribute.putAll(map);
	}
	
	/**
	 * @param str
	 * 
	 * 从字符串中提取属性
	 * 
	 * str="key1=value1&key2=value2"
	 * 
	 * 
	 */
	public void parserString(String str){
		String[] str1 = str.split("&");
        for (int i = 0; i < str1.length; i++) {
            String[] str2 = str1[i].split("=");
            if (str2.length == 1) {
            	setAttribute(str2[0], "");
            } else {
            	setAttribute(str2[0],str2[1]);
            }
        }
	}
	
	/**
	 * 
	 * 判断是否具有该属性
	 * @param key
	 * @return boolean 有true,没有false
	 */
	public boolean hasAttribute(Object key){
		return attribute.containsKey(key);
	}
	/**
	 * 清空参数
	 */
	public void clear(){
		attribute.clear();
	}
	
	/**
	 * 提取request中数据
	 * 将request的数据封装到map中。对数组数据，只封装第一个数据
	 * @param request
	 */
	public void parserRequest(HttpServletRequest request){
		Enumeration enume = request.getParameterNames();
		while(enume.hasMoreElements()){
			String pName = (String)enume.nextElement();
			String value=request.getParameter(pName);
			if(value!=null){
				if(pName.endsWith("_C")){//多选
					setAttribute(pName,request.getParameterValues(pName));
				}else if(pName.endsWith("_A")){
					setAttribute(pName,request.getParameter(pName).split(","));
				}else{
					setAttribute(pName,request.getParameter(pName));
				}
				
			}
				//setAttribute(pName,CommonUtil.reconvert(request.getParameter(pName)));
			value=null;
		}
	}
	
	/**
	 * 将数组形式的数据封装成List+map
	 * @param request
	 * @param startIndex 对接受的到数组，从第 startIndex 开始封装
	 * @param mustKey 检查没一组里面的值是否含有mustKey，如果没有，则不封装该组数据
	 * @return
	 */
	public List parserRequestForArray(HttpServletRequest request, int startIndex, String mustKey) {
        Enumeration enume = request.getParameterNames();
        List list = new ArrayList();
        Map tmp;
        if (startIndex < 0) {
            startIndex = 0;
        }
        String[] mustValue = null;
        List toSkep = new ArrayList();
        if (mustKey != null && !mustKey.equals("")) {
            mustValue = request.getParameterValues(mustKey);
            if (mustValue == null) {
                return list;
            }
            for (int i = 0, len = mustValue.length; i < len; i++) {
                if (mustValue[i] == null || mustValue[i].equals("")) {
                    toSkep.add(new Integer(i));
                }
            }
        }
        int skeped = 0;
        int maxSize = -1;
        if (mustValue != null) {
            maxSize = mustValue.length;
        }
        if (enume != null) {
            while (enume.hasMoreElements()) {
                String pName = (String) enume.nextElement();
                skeped = 0;
                String[] value = request.getParameterValues(pName);
                int len = value.length;
                if (maxSize != -1 && len > maxSize) {
                    len = maxSize;
                }
                if (value != null && value.length > startIndex) {
                    for (int i = startIndex; i < len; i++) {
                        if (toSkep.indexOf(new Integer(i)) != -1) {
                            skeped++;
                            continue;
                        }
                        if (list.size() <= (i - startIndex - skeped)) {
                            tmp = new HashMap();
                            tmp.put(pName.toUpperCase(), CommonUtil
                                    .reconvert(value[i]));
                            list.add(tmp);
                        } else {
                            tmp = (Map) list.get(i - startIndex - skeped);
                            tmp.put(pName, CommonUtil.reconvert(value[i]));
                            list.remove(i - startIndex - skeped);
                            list.add(i - startIndex - skeped, tmp);
                        }
                    }
                }
                value = null;
            }
        }
        return list;
	}
	
	
	/**
	 * 删除某一个key值
	 * @param key
	 */
	public void removeAttribute(String key){
		if(this.hasAttribute(key)){
			this.attribute.remove(key);
		}
	}
	

}

