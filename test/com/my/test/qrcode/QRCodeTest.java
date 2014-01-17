package com.my.test.qrcode;

import java.io.UnsupportedEncodingException;

import junit.framework.Assert;

import org.junit.Test;

import com.my.qrcode.TwoDimensionCode;

public class QRCodeTest {

	@Test
	public void maintest(){
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
		
		String decoderContent = handler.decoderQRCode(imgPath);
//		System.out.println("解析结果如下：");
//		System.out.println(decoderContent);
		
		Assert.assertEquals(decoderContent, encoderContent) ;
		
//		byte[] byt = ImageUtils.scale(new File("e:/x_mod.jpg"),4,false) ;
//		handler.encoderQRCode(byt,"e:/b.jpg") ;
	}
}
