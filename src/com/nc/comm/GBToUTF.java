package com.nc.comm;

import java.net.URLEncoder;

public class GBToUTF {
	public String GbtoUtf(String fileName) throws Exception{
		String returnFileName = "";   
        try {   
            returnFileName = URLEncoder.encode(fileName, "UTF-8");   
        } catch (Exception e) {   
            e.printStackTrace();  
        }   
        return returnFileName; 
	}
}
