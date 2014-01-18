package com.my.test.qrcode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import junit.framework.Assert;

import org.junit.Test;

import com.my.file.image.ImageUtils;
import com.my.qrcode.TwoDimensionCode;

public class QRCodeTest {
	private String imgpath = "e:/img/" ;
	public QRCodeTest(){
		imgpath = "e:/img/";
	}
	
	@Test
	public void encoderQRCode1Test(){
		String imgPath = imgpath+"encoderQRCode1Test.jpg";
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
	

	@Test
	public void encoderQRCode2Test(){
		String imgPath = imgpath+"encoderQRCode2Test.jpg";
		String encoderContent = "Hello 大家好：" + "\nURL [ http://www.baidu.com ]" + "\nEMail [ dyong525@gmail.com ]";
		TwoDimensionCode handler = new TwoDimensionCode();
 
		try {
			OutputStream output = new FileOutputStream(imgPath);
			handler.encoderQRCode(encoderContent, output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String decoderContent = handler.decoderQRCode(imgPath);
		Assert.assertEquals(decoderContent, encoderContent) ;
	}
	
	@Test
	public void encoderQRCode3Test(){
		String imgPath = imgpath+"encoderQRCode3Test.jpg";
		String encoderContent = "Hello 大家好：" + "\nURL [ http://www.baidu.com ]" + "\nEMail [ dyong525@gmail.com ]";
		TwoDimensionCode handler = new TwoDimensionCode();
 
		try {
			byte[] byt = encoderContent.getBytes("UTF-8") ;
			handler.encoderQRCode(byt,imgPath) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String decoderContent = handler.decoderQRCode(imgPath);
		Assert.assertEquals(decoderContent, encoderContent) ;
	}

	@Test
	public void encoderQRCode4Test(){
		String imgPath = imgpath+"encoderQRCode4Test.jpg";
		String encoderContent = "Hello 大家好：" + "\nURL [ http://www.baidu.com ]" + "\nEMail [ dyong525@gmail.com ]";
		TwoDimensionCode handler = new TwoDimensionCode();
		try {
			handler.encoderQRCode(encoderContent, imgPath, "jpg");
			
			File file = new File(imgPath) ;
			byte[] bytes = ImageUtils.pressImage(new File(imgpath+"1.jpg"), file , 0, 0, 1f);//测试OK
			ImageUtils.byteOut2File(bytes,file) ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
//		String decoderContent = handler.decoderQRCode(imgPath);
//		System.out.println(decoderContent) ;
//		
//		Assert.assertEquals(decoderContent, encoderContent) ;
		
	}
}
