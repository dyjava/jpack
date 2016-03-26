package com.my.test.file.excel;

import java.io.File;

import com.my.file.excel.CreateExcelFile;

public class CreateExcelFileTest {

	public static void main(String[] args) {
		File file1 = new File("e:/data/excel/file1.xls") ;
		CreateExcelFile cf = new CreateExcelFile(file1) ;
		cf.append(0, 0, "Name")
		.append(1, 0, "Value")
		.append(0, 1, "姓名")
		.append(0, 2, "性别")
		.append(0, 3, "年龄")
		.append(0, 4, "地址")
		.append(0, 5, "成绩")
		.append(0, 6, "身高")
		.append(0, 7, "手机号码")
		.close() ;

		File file2 = new File("e:/data/excel/file2.xls") ;
		File file3 = new File("e:/data/excel/file1.xls") ;
		CreateExcelFile excel = new CreateExcelFile(file2, file3) ;
		excel
		.append(1, 1, "张三")
		.append(1, 2, "男")
		.append(1, 3, 23)
		.append(1, 4, "beijing")
		.append(1, 5, 35.5)
		.append(1, 6, 173.3)
		.append(1, 7, "139876543999988765454333323313")
		.close() ;
	}

}
