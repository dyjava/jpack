package com.my.test.qrcode;

import java.io.File;
import java.io.UnsupportedEncodingException;

import com.my.file.image.ImageUtils;
import com.my.qrcode.TwoDimensionCode;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String imgPath = "e:/a.jpg";
		String encoderContent = "Hello 大家好：" + "\nURL [ http://www.baidu.com ]" + "\nEMail [ dyong525@gmail.com ]";
		TwoDimensionCode handler = new TwoDimensionCode();
		try {
			handler.encoderQRCode(encoderContent, imgPath, "jpg");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
//	      try {  
//	          OutputStream output = new FileOutputStream(imgPath);  
//	          handler.encoderQRCode(content, output);  
//	      } catch (Exception e) {  
//	          e.printStackTrace();  
//	      }
		System.out.println("========encoder success");
		
		String decoderContent = handler.decoderQRCode(imgPath);
		System.out.println("解析结果如下：");
		System.out.println(decoderContent);
		System.out.println("========decoder success!!!");
		
//		byte[] byt = ImageUtils.scale(new File("e:/x_mod.jpg"),4,false) ;
//		handler.encoderQRCode(byt,"e:/b.jpg") ;
	}

}
