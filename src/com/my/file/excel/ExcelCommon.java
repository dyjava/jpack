package com.my.file.excel;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelCommon {

	/**
	 * 新建excel
	 * @param file
	 */
	public void createExl(String file){
		try {
			//打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(file));
			WritableSheet sheet = book.createSheet("第一页", 0);
			//生成名为"第一页的工作表",参数0表示这是第一页
			Label label = new Label(0, 0, "test");
			//在label对象的构造函数中指定单元格位置是是第一列第一行(0,0)以及单元格内容为test 
			sheet.addCell(label);
			//将定义好的单元格添加到工作表中
			/*生成一个保存数字的单元格 
			 必须使用Number的完整包路径，否则有语法歧义 
			 单元格位置是第二列，第一行，值为789.123*/
			jxl.write.Number number = new jxl.write.Number(1, 0, 123);
			sheet.addCell(number);

			// 作用是从(m,n)到(p,q)的单元格全部合并
			//WritableSheet.mergeCells(int  m, int  n, int  p, int  q);    
			// 合并第一列第一行到第六列第一行的所有单元格    
			sheet.mergeCells( 0 , 0 , 5 , 0 );
			
			// 将第一行的高度设为200    
			sheet.setRowView( 0 , 200 );
			// 将第一列的宽度设为30    
			sheet.setColumnView( 0 , 30 );
			
			//写入数据并关闭文件
			book.write();
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * 读取excel
	 * @param file 文件路径
	 * @param i 第i个工作表。
	 */
	public static void readExl(String file,int i) {
		try {
			Workbook book = Workbook.getWorkbook(new File(file));
			//获的第一个工作表对象
			Sheet sheet = book.getSheet(i);
			//得到第一列第一行的单元格
			Cell cell = sheet.getCell(0, 0);
			String result = cell.getContents();
			System.out.println(result);
			
			
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void readExl(String file){
		try {
			Workbook book = Workbook.getWorkbook(new File(file));
			//获的第一个工作表对象
			Sheet[] sheets = book.getSheets();
			for(int i=0;i<sheets.length;i++){
				Sheet sheet = sheets[i];
				System.out.println("Sheet"+i);
				int col = sheet.getColumns();
				int row = sheet.getRows();
				for(int r=0;r<row;r++){
					for(int c=0;c<col;c++){
//						得到第c列第r行的单元格
						Cell cell = sheet.getCell(c, r);
						String result = cell.getContents();
						System.out.print("     "+result);
					}
					System.out.println();
				}
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 修改 
	 * @param file
	 */
	public static void UpdateExl(String file) {
		try {
			//Excel获得文件 
			Workbook wb = Workbook.getWorkbook(new File(file));
			//打开一个文件的副本,并且指定数据写回到愿文件中
			WritableWorkbook book = Workbook.createWorkbook(new File(file),wb);
			//添加一个工作表
//			WritableSheet sheet = book.createSheet("第二页", 1);
			WritableSheet sheet = book.getSheet(0);
			
			Label label = new Label(0, 0, "第二页的测试数据22");
			sheet.addCell(label);
			book.write();
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
