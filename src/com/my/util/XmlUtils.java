package com.my.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlUtils {

	@SuppressWarnings("unchecked")
	public static <T> T xml2obj(String xml,Class<T> type){
		T obj = null;
		try {
			JAXBContext context = JAXBContext.newInstance(type) ;
			Unmarshaller unmarshaller = context.createUnmarshaller() ;
			obj = (T) unmarshaller.unmarshal(new StringReader(xml)) ;
		} catch (JAXBException e) {
			e.printStackTrace() ;
		}
		return obj ;
	}
	public static String obj2xml(Object obj){
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			
			// 下面代码演示将对象转变为xml
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);//是否格式化生成的xml串  
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);		//是否省略xml头信息
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");	//编码格式
			//转换所有的适配字符，包括xml实体字符&lt;和&gt;，xml实体字符在好多处理xml的框架中是处理不了的，除非序列化
//			marshaller.setProperty("com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler",
//                    new CharacterEscapeHandler() {
//                @Override
//                public void escape(char[] ch, int start,int length, boolean isAttVal,
//                        Writer writer) throws IOException {
//                    writer.write(ch, start, length);
//                }
//
//            });
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(obj, os);
			return os.toString("utf-8");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "" ;
	}

	public static String obj2xml(Object obj, List<String> reg){
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			
			// 下面代码演示将对象转变为xml
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);//是否格式化生成的xml串  
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);		//是否省略xml头信息
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");	//编码格式
			//转换所有的适配字符，包括xml实体字符&lt;和&gt;，xml实体字符在好多处理xml的框架中是处理不了的，除非序列化
//			marshaller.setProperty("com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler",
//                    new CharacterEscapeHandler() {
//                @Override
//                public void escape(char[] ch, int start,int length, boolean isAttVal,
//                        Writer writer) throws IOException {
//                    writer.write(ch, start, length);
//                }
//
//            });
			
//			marshaller.setListener(new Marshaller.Listener(){
//				public void beforeMarshal(Object source){
//					System.out.println("123");
//				}
//				public void afterMarshal(Object source){
//					System.out.println("456");
//				}
//			});
//			
//			marshaller.setAdapter(new XmlAdapter<Object, Object>(){
//				@Override
//				public Object unmarshal(Object v) throws Exception {
//					System.out.println("-------------------------------");
//					return v;
//				}
//				@Override
//				public Object marshal(Object v) throws Exception {
//					// TODO Auto-generated method stub
//					System.out.println("-------------------------------"+v.getClass());
//					return v;
//				}
//			});
//			marshaller.setSchema(new Schema(){
//
//				@Override
//				public Validator newValidator() {
//					// TODO Auto-generated method stub
//					return null;
//				}
//
//				@Override
//				public ValidatorHandler newValidatorHandler() {
//					// TODO Auto-generated method stub
//					
//					return null;
//				}
//				
//			});
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(obj, os);
			return os.toString("utf-8");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "" ;
	}
	
	
}
