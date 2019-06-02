package com.briup.web.server.impl;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.List;

import com.briup.bean.Environment;
import com.briup.log.impl.LogImpl;

public class SeverThread extends Thread{
	Socket socket = null;
	List<Environment> list = null;
	public SeverThread() {}
	public SeverThread(Socket socket) {
		this.socket=socket;
	}
	LogImpl log=new LogImpl();
	@Override
	public void run() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			list = (List<Environment>) ois.readObject();
			int count=0;
			for(Environment o:list){
				count++;
			}
			log.info("总个数为:"+count);
			if(ois!=null){
				ois.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Collection<Environment> getList(){
		return list;
		
	}
}
