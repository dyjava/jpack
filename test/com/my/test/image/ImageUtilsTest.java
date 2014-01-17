package com.my.test.image;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import com.my.file.image.ImageUtils;

public class ImageUtilsTest {

	/**
	 * @param args
	 */

    /**
     * 程序入口：用于测试
     * @param args
     */
    public static void main(String[] args) {
    	File src = new File("e:/abc.jpg") ;
        // 1-缩放图像：
        // 方法一：按比例缩放
        ImageUtils.scale(src, "e:/abc_scale.jpg", 2, true);//测试OK
        byte[] bytes = ImageUtils.scale(src, 2, true);//测试OK
        ImageUtils.byteOut2File(bytes,new File("e:/out_abc_scale.jpg")) ;
        
        
        // 方法二：按高度和宽度缩放
        bytes = ImageUtils.scale2(src, 500, 300, true);//测试OK

        // 2-切割图像：
        // 方法一：按指定起点坐标和宽高切割
        bytes = ImageUtils.cut(src, 0, 0, 400, 400 );//测试OK
        // 方法二：指定切片的行数和列数
        List<ByteArrayOutputStream> list = ImageUtils.cut2(src, 2, 2 ) ;//测试OK
        // 方法三：指定切片的宽度和高度
        list = ImageUtils.cut3(src, 300, 300 );//测试OK
        
        int i=0 ;
        for(ByteArrayOutputStream stream:list){
        	ImageUtils.byteOut2File(stream.toByteArray(), new File("e:/out_abc_cut3_"+i+++".jpg")) ;
        }
        // 3-图像类型转换：
        bytes = ImageUtils.convert(src, "GIF" );//测试OK

        // 4-彩色转黑白：
        bytes = ImageUtils.gray(src );//测试OK

        // 5-给图片添加文字水印：
        // 方法一：
        bytes = ImageUtils.pressText("我是水印文字",src, "宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);//测试OK
        // 方法二：
        bytes = ImageUtils.pressText2("我也是水印文字", src , "黑体", 36, Color.white, 80, 0, 0, 0.5f);//测试OK
       
        // 6-给图片添加图片水印：
        bytes = ImageUtils.pressImage(new File("e:/abc2.jpg"), src , 0, 0, 0.5f);//测试OK
    }

}
