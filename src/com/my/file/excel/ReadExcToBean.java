package com.my.file.excel;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * made by dyong 
 * date 2008-11-24 10:11:28
 **/
public class ReadExcToBean {
	private static String filePath = "" ;	//excel文件全路径名
	
	private int maxRow = 0 ;				//读取文件的最大行号
	private int maxCol = 0 ;				//读取文件的最大列号
	private int nameRow = 0 ;				//对应的bean中的属性名命名行
	
	/**
	 * 
	 * 解析Excel的接口
	 * @param path excel全路径名
	 * @param obj 保存数据的bean
	 * @param maxRow 读取excel的最大行数
	 * @param maxCol 读取excel的最大列数
	 * @param nameRow 命名行
	 * @return
	 */
	public List readExcel(String path,Object obj,int maxRow ,int maxCol,int nameRow){
		filePath = path ;
		this.maxCol = maxCol ;
		this.maxRow = maxRow ;
		this.nameRow = nameRow ;
		return readExl(obj);
	}
	public List readExcel(String path,Object obj){
		filePath = path ;
		return readExl(obj);
	}
	
	/**
	 * 读取excel文件，保存到对象中
	 * @param obj 保存的对象
	 * @return
	 */
	private List readExl(Object obj){
		try {
			Workbook book = Workbook.getWorkbook(new File(filePath));
			//获的第一个工作表对象
			Sheet sheet = book.getSheet(0);
//			System.out.println("==========begin==========");
			int row = sheet.getRows();		//行号
			int col = sheet.getColumns();	//列号
//			设定读取的Excel行数
			if(maxRow>0 && row>maxRow){
				row = maxRow ;
			}
//			设定读取的Excel列数
			if(maxCol>0 && col>maxCol){
				col = maxCol ;
			}
//			设定标题行
			if(nameRow<0 || nameRow>=row){
				nameRow = 0 ;
			}
			ArrayList list = new ArrayList() ;
			for(int r=nameRow;r<row;r++){
//				新建对象
				obj = obj.getClass().newInstance() ;
				for(int c=0;c<col;c++){
//					得到第c列第r行的单元格
					Cell cell = sheet.getCell(c, r);
					String value = cell.getContents();
					
					if(r==nameRow){
						System.out.print(value+"	");
					} else {
						String key = sheet.getCell(c,nameRow).getContents();
//						取得方法名
						String method = setMethodName(key) ;
//						取得参数
						Object[] objs ;
						
						if(cell.getType().toString().equals("Number")){
							if(value.indexOf(".")>-1){
								objs = new Double[]{new Double(Double.parseDouble(value))} ;
							} else {
								objs = new Integer[]{new Integer(Integer.parseInt(value))} ;
							}
						} else {
							objs = new String[]{value} ;
						}
//						执行方法
						executeMethod(obj,method,objs) ;
					}
				}
				list.add(obj) ;
//				System.out.println("	>>>");
			}
			System.out.println();
			book.close();
//			System.out.println("==========end==========");
			return list ;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	/**
	 * 执行方法
	 * @param obj 具体执行的方法的对象
	 * @param methodName 方法名
	 * @param params 参数列表
	 * @return
	 */
	private Object executeMethod(Object obj,String methodName,Object[] params){
		try {
//			方法带的参数类型数组
			ArrayList list = new ArrayList() ;
			int len = params.length ;
			for(int i=0;i<len;i++){
				list.add(params[i].getClass()) ;
			}
			Class[] clas = (Class[]) list.toArray(new Class[0]) ;
//			生成方法名
			Method m0 = obj.getClass().getDeclaredMethod(methodName, clas);
//			执行方法，传递方法类名和参数列表，返回对象
			return m0.invoke(obj, params);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String setMethodName(String name) {
	    String str = "set";
	    str += name.substring(0, 1).toUpperCase();
	    str += name.substring(1);
	    return str;
	}
	
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		String path = "e:/t.xls" ;
//		ReadExcToBean rb = new ReadExcToBean() ;
//		List bean = rb.readExcel(path,new ExcelBean());
//		
//		for(int i=1;i<bean.size();i++){
//			ExcelBean exc = (ExcelBean) bean.get(i) ;
//			System.out.println(exc.getName()+"	"+exc.getSex()+"	"+exc.getAge()+"	"+exc.getDate()) ;
//		}
//	}

}
