package com.nc.service;

import java.util.List;
import java.util.Map;

import com.nc.comm.AppException;

public interface ITenderService {

	public List getListData(String statementName,
			Map<String, String> paramMap) throws AppException;

}