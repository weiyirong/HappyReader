package com.weiyi.reader.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 功能：负责处理数据流的各项操作
 * 
 * @author 魏艺荣
 * @version 1.0
 * */
public class StreamUtil {
	/**
	 * 通过输入流，将其转换成字节byte数组
	 * 
	 * @param is
	 *            输入的数据流
	 * @return byte[] 转化后的字节数组
	 * */
	public static byte[] getByteByStream(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int length = 0;
		while (length != -1) {
			try {
				length = is.read(b);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (length != -1) {
				baos.write(b, 0, length);
			}
		}
		if (is != null) {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return baos.toByteArray();
	}
	/**
	 * 通过URL，获取输入流InputStream
	 * 
	 * @param url
	 *            URL地址
	 * @return InputStream 获取输入流InputStream
	 * */
	public static InputStream getInputStreamByUrl(String url){
		InputStream is = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);//请求方式为get
		try {
			HttpResponse response = httpClient.execute(request);//服务端响应
			is = response.getEntity().getContent();//获取响应InputStream
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
		
	}
}
