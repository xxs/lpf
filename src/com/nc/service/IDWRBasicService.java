/***
 * DWR总业务类
 */
package com.nc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nc.comm.AppException;
import com.nc.comm.JsonUtils;

public interface IDWRBasicService {

	public String getListForPage(Map<String,String> queryparam) throws AppException ;

}