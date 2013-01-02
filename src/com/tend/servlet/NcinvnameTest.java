package com.tend.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tend.servlet.DBOracleconn;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class NcinvnameTest {
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	public static void main(String[] args) {
		String path = "D:\\NC²úÆ·.xls";
		String sql = "";
		try{
			InputStream is = new FileInputStream(new File(path));
			Workbook wbk = null;
			String pk_calbody = "";
			String pk_invbasdoc = "";
			String plannum = "";
			String outnnumber = "";
			String innumber = "";
			int ncquantity = 0;
			int weiquantity = 0;
			int stockquantity = 0 ;
			String month = "";
			wbk = Workbook.getWorkbook(is);
			Sheet sheet = wbk.getSheet(0);
			Cell c00 = sheet.getCell(0,0);
			pk_calbody = c00.getContents();
			Cell c01 = sheet.getCell(2,0);
			pk_invbasdoc = c01.getContents();
			Cell c02 = sheet.getCell(5,0);
			plannum = c02.getContents();
			Cell c03 = sheet.getCell(6,0);
			outnnumber = c03.getContents();
			Cell c04 = sheet.getCell(7,0);
			innumber = c04.getContents();
			conn = DBOracleconn.getDBConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select to_char(sysdate+7,'yyyy-mm') month from dual");
			while(rs.next()){
				month = rs.getString("month");
			}
			try{
				for(int i = 1 ; i < sheet.getRows(); i ++){
					Cell c10 = sheet.getCell(0,i);
					pk_calbody = c10.getContents();
					Cell c11 = sheet.getCell(2,i);
					pk_invbasdoc = c11.getContents();
					Cell c12 = sheet.getCell(5,i);
					plannum = c12.getContents();
					if(plannum.equals("")){
						plannum = "0";
					}
					Cell c13 = sheet.getCell(6,i);
					outnnumber = c13.getContents();
					if(outnnumber.equals("")){
						outnnumber = "0";
					}
					Cell c14 = sheet.getCell(7,i);
					innumber = c14.getContents();
					if(innumber.equals("")){
						innumber = "0";
					}
					rs = stmt.executeQuery("select nc_quantity,stoknnumber from sn_quantity_query where pk_calbody = '"+pk_calbody+"' and nc_prod_pk = '"+pk_invbasdoc+"' and month = '"+month+"'");
					while(rs.next()){
						ncquantity = rs.getInt("nc_quantity");
						weiquantity = rs.getInt("stoknnumber");
					}
					System.out.println(ncquantity-weiquantity+Integer.parseInt(plannum)-Integer.parseInt(outnnumber)+Integer.parseInt(innumber));
					stockquantity = ncquantity-weiquantity+Integer.parseInt(plannum)-Integer.parseInt(outnnumber)+Integer.parseInt(innumber);
					sql = "update sn_quantity_query set plannumber = '"+plannum+"',outnnumber = '"+outnnumber+"',innumber = '"+innumber+"',STOCKQUANTITY='"+stockquantity+"' ";
					sql += " where pk_calbody = '"+pk_calbody+"' and nc_prod_pk = '"+pk_invbasdoc+"' and month = '"+month+"'";
					//stmt.addBatch(sql);
					//stmt.executeBatch();
					stmt.execute(sql);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
		            conn.commit();
		            DBOracleconn.closeDBconn();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
