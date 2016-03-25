package com.my.test.file.excel;

import com.my.file.excel.ExcelCommon;

public class ExcelCommonTest {

	public static void main(String[] args) {
		String f = "e:/t.xls";
		ExcelCommon ef = new ExcelCommon();
//		ef.createExl(f);
		ef.UpdateExl(f);
		ef.readExl(f);
	}

}
