package com.briup.web.client.impl;

import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.bean.Environment;
import com.briup.log.Log;
import com.briup.util.Configuration;
import com.briup.web.client.Client;

public class ClientImpl implements Client{
	private String ip;
	private int port;
	private Configuration con;
	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		ip = properties.getProperty("ip");
		port = Integer.parseInt(properties.getProperty("port"));
	}

	@Override
	public void setConfiguration(Configuration con) {
		// TODO Auto-generated method stub
		this.con=con;
	}

	@Override
	public void send(Collection<Environment> coll) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * 写一个客户端访问服务器
		 * 给服务器发送一个对象Collection<Environment>
		 * 1.构建客户端对象，连接服务器
		 * 2.通过客户端对象获取网路输出流，给服务器写出数据
		 * 3.因为需要写出一个集合对象给服务器，因此需要使用对象输出流包装网络输出流
		 * 4.使用对象输出流写出集合对象
		 * 5.关闭资源
		 */
		Log log = con.getLogger();
		log.info("开启客户端");
		Socket socket = new Socket(ip, port);
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		oos.writeObject(coll);
		log.info("关闭客户端");
		if(oos!=null){
			oos.close();
		}
		if(socket!=null){
			socket.close();
		}
	}

}
