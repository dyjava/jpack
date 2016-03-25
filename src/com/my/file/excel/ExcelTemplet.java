package com.my.file.excel;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.Number;

/**
 * 嵌套模板生成excel
 * @author diyong
 *
 */
public class ExcelTemplet {

	public void createTempletExcel(File temp, File out){
		try {
			Workbook wb = Workbook.getWorkbook(temp) ;
			WritableWorkbook wwb = Workbook.createWorkbook(out, wb) ;
//			ByteArrayOutputStream targetFile = new ByteArrayOutputStream();
//			WritableWorkbook wwb2 = Workbook.createWorkbook(targetFile, wb); 
			
			WritableSheet wws = wwb.getSheet("Sheet1"); 

			//选择单元格，写入动态值，根据单元格的不同类型转换成相应类型的单元格： 
			Label A1 = (Label)wws.getWritableCell(0,0) ; 
			A1.setString("单元格内容") ;
			Number A2 = (Number)wws.getWritableCell(0,1) ;//Number是jxl.write.Number 
			A2.setValue(3.3) ;
			//也可以创建新的单元格并且加入到Sheet中 
			Label C1 = new Label(2,0,"单元格内容"); 
			wws.addCell(C1); 
			 
			Number C2 = new Number(2,0,3.3) ;
			wws.addCell(C2) ;
			
			wwb.close() ;
			wb.close() ;
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
