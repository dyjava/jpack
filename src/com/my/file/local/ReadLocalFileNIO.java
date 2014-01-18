package com.my.file.local;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

/**
 * made by dyong 
 * 读取本地资源文件 NIO
 **/
public class ReadLocalFileNIO {

    /**
     * 
     * @param filepath 相对路径
     * @return
     * @throws IOException 
     */
    public static ArrayList<String> readLocalFile2(String filepath)
    		throws IOException{
    	return readLocalFile2(filepath,"utf-8") ;
    }
    public static ArrayList<String> readLocalFile2(String filepath,String code)
    		throws IOException{
		try {
			ArrayList<String> list = new ArrayList<String>();
			InputStream is = ReadLocalFileNIO.class.getResourceAsStream(filepath);
			ReadableByteChannel channel = Channels.newChannel(is);
			ByteBuffer bs = ByteBuffer.allocate(10240);
			while(channel.read(bs)>-1){
				// flip方法让缓冲区可以将新读入的数据写入另一个通道  
				bs.flip();
				list.add(new String(bs.array(),code)) ;
				// clear方法重设缓冲区，使它可以接受读入的数据  
				bs.clear();
			}
			return list ;
		}catch(IOException e){
			throw new IOException("readLocalFile err:"+e.getMessage()) ;
		}
	}
    
    /**
     * 
     * @param filepath 绝对路径
     * @return
     * @throws IOException
     */
    public static ArrayList<String> readLocalFile(String filepath)
    		throws IOException{
    	return readLocalFile(filepath,"utf-8") ;
    }
    public static ArrayList<String> readLocalFile(String filepath,String code)
    		throws IOException{
		try {
	    	ArrayList<String> list = new ArrayList<String>();
			InputStream is = new FileInputStream(filepath);
			ReadableByteChannel channel = Channels.newChannel(is);
			ByteBuffer bs = ByteBuffer.allocate(10240);
			while(channel.read(bs)>-1){
				// flip方法让缓冲区可以将新读入的数据写入另一个通道  
				bs.flip();
				list.add(new String(bs.array(),code)) ;
				// clear方法重设缓冲区，使它可以接受读入的数据  
				bs.clear();
			}
			is.close();
			return list ;
		}catch(IOException e){
			throw new IOException("readLocalFile err:"+e.getMessage()) ;
		}
	}
	
}
