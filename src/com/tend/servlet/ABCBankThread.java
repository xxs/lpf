/**
 * 
 */
package com.tend.servlet;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.hitrust.b2b.trustpay.client.TrxResponse;
import com.hitrust.b2b.trustpay.client.XMLDocument;
import com.hitrust.b2b.trustpay.client.b2b.QueryTrnxBatchRequest;

/**
 * @author Administrator
 *
 */
public class ABCBankThread extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DBOracleconn db = new DBOracleconn();
    	Statement stmt = null; 
    	ResultSet rs = null;
    	String begindate = "";
    	String yestoday = "";
    	String orderno = "";
    	String snsql = "";
    	String TrnxTime = "";
    	String PayAccountName = "";
    	String PayAccount = "";
    	String abcrderno = "";
    	String trnxstatus = "";
    	String custcode = "";
		while(true){
			try {
				try {
					Connection conn = db.getDBConn();
					stmt = conn.createStatement();
					rs = stmt.executeQuery("select substr(to_char(sysdate,'yyyy-mm-dd'),6,2)||'/'||substr(to_char(sysdate,'yyyy-mm-dd'),9,2)||'/'||substr(to_char(sysdate,'yyyy-mm-dd'),0,4) nowdate,substr(to_char(sysdate-1, 'yyyy-mm-dd'), 6, 2) || '/' || substr(to_char(sysdate-1, 'yyyy-mm-dd'), 9, 2) || '/' || substr(to_char(sysdate-1, 'yyyy-mm-dd'), 0, 4) yestoday  from dual");
					while(rs.next()){
						begindate = rs.getString("nowdate");
						yestoday = rs.getString("yestoday");
					}
					QueryTrnxBatchRequest tQueryTrnxBatchRequest = new QueryTrnxBatchRequest();
					tQueryTrnxBatchRequest.setStartAccDate(yestoday);          //起始查询日期YYYYMMDD （必要信息）
					tQueryTrnxBatchRequest.setEndAccDate(begindate);
					TrxResponse tTrxResponse = tQueryTrnxBatchRequest.postRequest();
					if (tTrxResponse.isSuccess()) {
						XMLDocument tXMLDocument = tTrxResponse.getResponseMessage();
						ArrayList tResults = tXMLDocument.getDocuments("Row");
						if (tResults.size()==0)
							System.out.print("没有交易记录<br>");
						else{
							System.out.print("订单号   |      交易时间      |   付款方账户名   |   付款方帐号   |   交易金额   |   交易状态<br>");
							for(int i=0;i<tResults.size();i++){
								String tRows = tResults.get(i).toString();
								XMLDocument tChild = new XMLDocument(tRows);
								System.out.print( tChild.getValueNoNull("MerchantTrnxNo"  )     + "|");
//								System.out.print( tChild.getValueNoNull("TrnxTime"  )      + "|");
//								System.out.print( tChild.getValueNoNull("PayAccountName"  )   + "|");
//								System.out.print( tChild.getValueNoNull("PayAccount") + "|");
//								System.out.print( tChild.getValueNoNull("TrnxAmount")     + "|");
//								System.out.println( tChild.getValueNoNull("TrnxStatus")     + "<br>");
								TrnxTime = tChild.getValueNoNull("TrnxTime"  )  ;
								PayAccountName = tChild.getValueNoNull("PayAccountName"  )  ;
								PayAccount = tChild.getValueNoNull("PayAccount"  )  ;
								abcrderno = tChild.getValueNoNull("MerchantTrnxNo"  );
								custcode = abcrderno.substring(15);
								try {
									rs = stmt.executeQuery("select orderno,trnxstatus from sn_abcbank_custpay where orderno = '"+tChild.getValueNoNull("MerchantTrnxNo"  )+"'");
									int m = 0;
									while(rs.next()){
									    orderno = rs.getString("orderno");
									    trnxstatus = rs.getString("trnxstatus");
									    System.out.println("&&&&"+orderno);
									    //System.out.println(orderno+"****"+abcrderno+"--"+orderno.equals(abcrderno)+"--"+trnxstatus.equals("0"));
									    if(orderno.equals(abcrderno) && trnxstatus.equals("0")){
									    	m ++;
									    	StringBuffer sb = new StringBuffer();  
											sb.append("money="+ tChild.getValueNoNull("TrnxAmount"));
											sb.append("&custcode="+custcode);
											System.out.println(abcrderno.substring(15));
											String toncurl = "http://192.168.60.66:8999/test/bank.nc";
											URL realURL = new URL(toncurl+"?"+sb.toString());
											System.out.println(toncurl+"?"+sb.toString());
			    		        			HttpURLConnection connection = (HttpURLConnection) realURL.openConnection();
			    		        			connection.setRequestProperty("Content-type", "text/html");
			    		        			connection.setRequestMethod("POST");
			    		        			connection.connect();  
			    		        			InputStream br = new DataInputStream(connection.getInputStream());
			    		        			byte[] b = new byte[br.available()];
			    		        			br.read(b);
			    		        			String result = new String(b, "UTF-8");	
			    		        			//System.out.println(result+"^^^^^^^");
			    		        			br.close(); 
			    		        			if(result.trim().equals("OK")){
			    		        				System.out.println("向NC中写入数据成功！555");
			    		        				System.out.println("插入sql！");
			    		        				snsql += "update sn_abcbank_custpay set trnxtime= '"+TrnxTime+"',payaccountname= '"+PayAccountName+"',PayAccount='"+PayAccount+"',trnxstatus = '1'";
										        snsql += " where orderno="+orderno;
										        System.out.println("sql="+snsql);
										        stmt.executeUpdate(snsql);
										        System.out.println("a1");
									            conn.commit();
									            System.out.println("a2");
			    		        			}else{
			    		        				System.out.println("向NC中写入数据时出错！");
			    		        			}
									    }
									}
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
					}  
					else {
					//5、交易批量查询请求提交失败，商户自定后续动作
						System.out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
						System.out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						db.closeDBconn();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				sleep(6000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
		}
	}

	public static void main(String[] args){
		ABCBankThread abc = new ABCBankThread();
		abc.start();
	}
}
