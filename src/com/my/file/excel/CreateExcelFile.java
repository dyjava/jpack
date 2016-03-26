package com.my.file.excel;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.Number;
import jxl.write.WritableSheet;

public class CreateExcelFile {

	private WritableWorkbook book ;
	private WritableSheet sheet ;

	/**
	 * 
	 * @param file excel文件
	 */
	public CreateExcelFile(File file){
		try {
			boolean create = false ;
			if(!file.exists()){
				file.createNewFile() ;
				create = true ;
			}
			book = Workbook.createWorkbook(file);
			if(create){
				sheet = book.createSheet("第一页", 0);
			} else {
				if(book.getSheets().length>0) {
					sheet =  book.getSheet(0) ;
				} else {
					sheet = book.createSheet("Sheet1", 0);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param file excel文件
	 * @param tempfile 模板文件
	 */
	public CreateExcelFile(File file, File tempfile){
		try {
			Workbook wb = Workbook.getWorkbook(tempfile);
			book = Workbook.createWorkbook(file, wb) ;
			sheet =  book.getSheet(0) ;
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CreateExcelFile append(int i, int j, String s){
        try {
			sheet.addCell(new Label(i,j,s)) ;
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return this ;
	}
	public CreateExcelFile append(int i, int j, int s){
        try {
			sheet.addCell(new Number(i,j,s)) ;
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return this ;
	}
	public CreateExcelFile append(int i, int j, long s){
        try {
			sheet.addCell(new Number(i,j,s)) ;
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return this ;
	}
	public CreateExcelFile append(int i, int j, double s){
        try {
			sheet.addCell(new Number(i,j,s)) ;
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return this ;
	}
	
	public void close(){
		try {
			book.write() ;
			book.close() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
