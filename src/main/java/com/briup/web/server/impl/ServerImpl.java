package com.briup.web.server.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.bean.Environment;
import com.briup.log.Log;
import com.briup.util.Configuration;
import com.briup.web.server.Server;

public class ServerImpl implements Server{
	ServerSocket serverSocket = null;
	Socket socket = null;
	DBStoreImpl dbs = null;
	private Configuration con;
	private static boolean b = true;
	private int port;
	Collection<Environment> list = null;
	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		port = Integer.parseInt(properties.getProperty("port"));
	}

	@Override
	public void setConfiguration(Configuration con) {
		// TODO Auto-generated method stub
		this.con=con;
	}

	@Override
	public Collection<Environment> reciver() throws Exception {
		// TODO Auto-generated method stub
		/*
		 * 接受数据端发送的数据 Collection<Environment> 
		 * 1.构建服务器对象
		 * 2.等待客户端连接
		 * 3.连接成功获取到客户端对象，然后获取网络输入流
		 * 4.使用对象输入流包装网络输入流
		 * 5.读取客户端发送过来的集合对象  readObject
		 * 6.io关闭资源
		 */
		Log log = con.getLogger();
		log.info("开启服务器");
		serverSocket = new ServerSocket(port);
		while(b){
			socket = serverSocket.accept();
			SeverThread severThread=new SeverThread(socket);
			severThread.start();
			severThread.join();
			list = severThread.getList();
			dbs = (DBStoreImpl) con.getDbStore();
			dbs.saveDb(list);
		}
		log.info("访问结束");
		shutdown();
		return list;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		//1.关闭客户端对象
		//2.关闭服务器对象
		try {
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
