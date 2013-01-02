package com.nc.sendMessage;

import org.apache.axis.client.Service;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

public class SendTextServer {

	public static void dopost(String Content,String Phones) throws IOException {
		String UserNo="37106007207";
		String Pwd="65697086sinian";
		//String Phones="13937110317,13592613515";
		//String Content="测试测试";
		
//      .net webService 地址
		String endpoint = "http://gateway.online.cn/service.asmx";
//      .net webService 命名空间
		String namespace = "http://gateway.online.cn/";
//      .net webService 需调用的方法
		String methodname = "SendText";
		String soapActionURI = "http://gateway.online.cn/SendText";
		Service service = new Service();
		Call call = null;
		
		try {
			call = (Call) service.createCall();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		try {
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setUseSOAPAction(true);		 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		call.setSOAPActionURI(soapActionURI); 
//      设置要调用的.net webService方法
		call.setOperationName(new QName(namespace, methodname)); // MsgReceive为targetNamespace
//      设置该方法的参数，strXML为.net webService中的参数名称
		call.addParameter(new QName(namespace, "UserNo"),XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName(namespace, "Pwd"),XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName(namespace, "Phones"),XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName(namespace, "Content"),XMLType.XSD_STRING, ParameterMode.IN);
//      设置该方法的返回值
		call.setReturnType(XMLType.XSD_STRING);
		call.setUseSOAPAction(true);

		String ret = null;
		try {
			ret = (String) call.invoke(new Object[] { UserNo,Pwd,Phones,Content });

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("message:" + ret);
	}
}
