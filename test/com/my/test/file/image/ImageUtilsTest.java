package com.my.test.file.image;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import org.junit.Test;

import com.my.file.image.ImageUtils;

public class ImageUtilsTest {
	private String inpath = "e:/data/0.jpg" ;			//处理的图片
	private String presspath = "e:/data/1.jpg" ;		//水印图片
	private String outpath = "e:/data/imageutilsDeal/" ;//处理结果存放地址
	private File src  ;
	private byte[] bytes = null ;

	public ImageUtilsTest(){
		src = new File(inpath) ;
		bytes = ImageUtils.scale(src, 2, true);//测试OK
		File dir = new File(outpath) ;
		if(!dir.exists()){
			dir.mkdirs() ;
		}
	}
	
	/**
	 * 图片缩放
	 * 按比例缩放
	 */
	@Test
	public void scale1Test(){
		ImageUtils.scale(src, outpath+"scale1Test.jpg", 2, true);//测试OK
	}

	/**
	 * 图片缩放
	 * 按比例扩大
	 */
	@Test
	public void scale11Test(){
		ImageUtils.scale(src, outpath+"scale11Test.jpg", 2, false);//测试OK
	}

	/**
	 * 图片缩放
	 * 按比例缩放 字节
	 */
	@Test
	public void scale12Test(){
		byte[] bytes = ImageUtils.scale(src, 2, true);//测试OK
		ImageUtils.byteOut2File(bytes,new File(outpath+"scale12Test.jpg")) ;
	}
	
	
	/**
	 * 图片缩放
	 * 按高度和宽度缩放
	 */
	@Test
	public void scale2Test(){
		bytes = ImageUtils.scale2(src, 500, 300, true);//测试OK
		ImageUtils.byteOut2File(bytes,new File(outpath+"scale2Test.jpg")) ;
	}
	
	/**
	 * 切割
	 * 按指定起点坐标和宽高切割
	 */
	@Test
	public void cutTest(){
		bytes = ImageUtils.cut(src, 0, 0, 400, 400 );//测试OK
		ImageUtils.byteOut2File(bytes,new File(outpath+"cutTest.jpg")) ;
	}

	/**
	 * 切割  切片
	 * 指定切片的行数和列数
	 */
	@Test
	public void cut1Test(){
		List<ByteArrayOutputStream> list = ImageUtils.cut2(src, 2, 2 ) ;//测试OK
		int i=0 ;
        for(ByteArrayOutputStream stream:list){
        	ImageUtils.byteOut2File(stream.toByteArray(), new File(outpath+"cut1Test_"+i+++".jpg")) ;
        }
	}

	/**
	 * 切割  切片
	 * 指定切片的宽度和高度
	 */
	@Test
	public void cut2Test(){
		List<ByteArrayOutputStream> list = ImageUtils.cut3(src, 300, 300 );//测试OK
		int i=0 ;
        for(ByteArrayOutputStream stream:list){
        	ImageUtils.byteOut2File(stream.toByteArray(), new File(outpath+"cut2Test_"+i+++".jpg")) ;
        }
	}
	
	/**
	 * 图像类型转换
	 */
	@Test
	public void convertTest(){
		bytes = ImageUtils.convert(src, "GIF" );//测试OK
		ImageUtils.byteOut2File(bytes,new File(outpath+"convertTest.jpg")) ;
	}
	
	/**
	 * 彩色转黑白
	 */
	@Test
	public void grayTest(){
		bytes = ImageUtils.gray(src );//测试OK
		ImageUtils.byteOut2File(bytes,new File(outpath+"grayTest.jpg")) ;
	}

	/**
	 * 给图片添加水印
	 */
	@Test
	public void pressTextTest(){
		bytes = ImageUtils.pressText("我是水印文字",src, "宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);//测试OK
		ImageUtils.byteOut2File(bytes,new File(outpath+"pressTextTest.jpg")) ;
	}

	/**
	 * 给图片添加水印
	 */
	@Test
	public void pressText2Test(){
		bytes = ImageUtils.pressText2("我也是水印文字", src , "黑体", 36, Color.white, 80, 0, 0, 0.5f);//测试OK
		ImageUtils.byteOut2File(bytes,new File(outpath+"pressText2Test.jpg")) ;
	}
	
	/**
	 * 给图片添加水印
	 * 图片水印
	 */
	@Test
	public void pressImageTest(){
		bytes = ImageUtils.pressImage(new File(presspath), src , 0, 0, 0.5f);//测试OK
		ImageUtils.byteOut2File(bytes,new File(outpath+"pressImageTest.jpg")) ;
	}

}
