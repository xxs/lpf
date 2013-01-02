package com.tend.servlet;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class TestCustFileReadServlet extends HttpServlet{
	private static final long serialVersionUID = -4464318344629260022L;
	public TestCustFileReadServlet(){}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		// TODO Auto-generated method stub
		//resp.setContentType("text/html;charset=gb2312");
		//PrintWriter out = resp.getWriter();
		String filepath = "F:\\newsynear\\WebRoot\\fileupload\\file\\custfile\\3\\1104110949453Spdd_020251.xls";
		try{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filepath));
			HSSFWorkbook workBook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workBook.getSheetAt(0);
		    int rows = sheet.getPhysicalNumberOfRows();
		    if (rows > 0) { 
                sheet.getMargin(HSSFSheet.TopMargin); 
                for (int j = 0; j < rows; j++) { // 行循环 
                    HSSFRow row = sheet.getRow(j); 
                    if (row != null) { 
                        int cells = row.getLastCellNum();//获得列数 
                        for (short k = 0; k < cells; k++) { // 列循环 
                            HSSFCell cell = row.getCell(k); 
                            // ///////////////////// 
                            if (cell != null) { 
                                String value = ""; 
                                switch (cell.getCellType()) { 
                                case HSSFCell.CELL_TYPE_NUMERIC: // 数值型 
                                	if (HSSFDateUtil.isCellDateFormatted( 
                                     cell)) { 
                                     //如果是date类型则 ，获取该cell的date值 
                                     value = HSSFDateUtil.getJavaDate( 
                                     cell.getNumericCellValue()). 
                                     toString(); 
                                     System.out.println("第"+j+"行,第"+k+"列值："+value+"<br>"); 
                                     }else{//纯数字 
                                      
                                    value = String.valueOf(cell 
                                            .getNumericCellValue()); 
                                    System.out.println("第"+j+"行,第"+k+"列值："+value+"<br>"); 
                                     } 
                                    break; 
                                /* 此行表示单元格的内容为string类型 */ 
                                case HSSFCell.CELL_TYPE_STRING: // 字符串型 
                                    value = cell.getRichStringCellValue() 
                                            .toString(); 
                                    System.out.println("第"+j+"行,第"+k+"列值："+value+"<br>"); 
                                    break; 
                                case HSSFCell.CELL_TYPE_FORMULA://公式型 
                                    //读公式计算值 
                                     value = String.valueOf(cell.getNumericCellValue()); 
                                     if(value.equals("NaN")){//如果获取的数据值为非法值,则转换为获取字符串 
                                          
                                         value = cell.getRichStringCellValue().toString(); 
                                     } 
                                     //cell.getCellFormula();读公式 
                                     System.out.println("第"+j+"行,第"+k+"列值："+value+"<br>"); 
                                break; 
                                case HSSFCell.CELL_TYPE_BOOLEAN://布尔 
                                     value = " " 
                                     + cell.getBooleanCellValue(); 
                                     System.out.println("第"+j+"行,第"+k+"列值："+value+"<br>"); 
                                 break; 
                                /* 此行表示该单元格值为空 */ 
                                case HSSFCell.CELL_TYPE_BLANK: // 空值 
                                    value = ""; 
                                    System.out.println("第"+j+"行,第"+k+"列值："+value+"<br>"); 
                                    break; 
                                case HSSFCell.CELL_TYPE_ERROR: // 故障 
                                    value = ""; 
                                    System.out.println("第"+j+"行,第"+k+"列值："+value+"<br>"); 
                                    break; 
                                default: 
                                    value = cell.getRichStringCellValue().toString(); 
                                System.out.println("第"+j+"行,第"+k+"列值："+value+"<br>"); 
                                } 
                                 
                            }  
                        } 
                    } 
                } 
            } 
        }catch(Exception e){
			
		}
		//out.println();
	}
	
	public static void testpos(){
		String filepath = "F:\\newsynear\\WebRoot\\fileupload\\file\\custfile\\3\\1104110949453Spdd_020251.xls";
		try{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filepath));
			HSSFWorkbook workBook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workBook.getSheetAt(0);
		    int rows = sheet.getPhysicalNumberOfRows();
		    String corpname = "";
			String ordertype = "";
			String orderno = "";
			String zhidandate = "";
			String storeno = "";
			String storename = "";
			String vendorcode = "";
			String vendorname = "";
			String deptname = "";
			String remark = "";
			String invcode = "";
			String invhuohao = "";
			String invtiaoma = "";
			String itemspec1 = "";
			String invname = "";
			String orderjianshu = "";
			String orderquant = "";
			String hanshuimoney = "";
			String wushuimoney = "";
			String tihi = "";
			String bodyremark = "";
		    if (rows > 0) { 
                sheet.getMargin(HSSFSheet.TopMargin); 
                HSSFRow row00 = sheet.getRow(0);
                HSSFCell cel00 = row00.getCell((short) 0); 
                corpname = cel00.getRichStringCellValue().toString();
                HSSFRow row01 = sheet.getRow(1);
                HSSFCell cel01 = row01.getCell((short) 0); 
                ordertype = cel01.getRichStringCellValue().toString();
                HSSFRow row02 = sheet.getRow(2);
                HSSFCell cel02 = row02.getCell((short) 0); 
                orderno = cel02.getRichStringCellValue().toString();
                HSSFCell cel12 = row02.getCell((short) 1); 
                zhidandate = cel12.getRichStringCellValue().toString();
                HSSFRow row03 = sheet.getRow(3);
                HSSFCell cel03 = row03.getCell((short) 0); 
                storeno = cel03.getRichStringCellValue().toString();
                HSSFCell cel13 = row03.getCell((short) 1); 
                storename = cel13.getRichStringCellValue().toString();
                HSSFRow row04 = sheet.getRow(4);
                HSSFCell cel04 = row04.getCell((short) 0); 
                vendorcode = cel04.getRichStringCellValue().toString();
                HSSFCell cel14 = row04.getCell((short) 1); 
                vendorname = cel14.getRichStringCellValue().toString();
                HSSFRow row05 = sheet.getRow(5);
                HSSFCell cel05 = row05.getCell((short) 0); 
                deptname = cel05.getRichStringCellValue().toString();
                HSSFCell cel06 = row05.getCell((short) 1); 
                remark = cel06.getRichStringCellValue().toString();
		    }
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public static void main(String[] args){
		HttpServletRequest req = null;
		HttpServletResponse resp = null;
		TestCustFileReadServlet tt = new TestCustFileReadServlet();
		try {
			tt.doPost(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testpos();
	}
}
