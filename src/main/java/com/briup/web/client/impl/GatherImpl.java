package com.briup.web.client.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


import com.briup.bean.Environment;
import com.briup.log.Log;
import com.briup.util.Configuration;
import com.briup.web.client.Gather;

public class GatherImpl implements Gather{
	private String src_file;
	private String record_file;
	private Configuration con;
	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		src_file = properties.getProperty("src-file");
		record_file = properties.getProperty("record-file");
	}

	@Override
	public void setConfiguration(Configuration con) {
		// TODO Auto-generated method stub
		this.con=con;
	}

	@Override
	public Collection<Environment> gather() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader bf = new BufferedReader(new FileReader(src_file));
		String s = null;
		String str = null;
		Environment  environment = null;
		float f=0,temp=0,rh=0;
		String[] strings=new String[9];
		List<Environment> list = new ArrayList<>();
		Log log=con.getLogger();
		log.info("开始采集");
		while((s=bf.readLine())!=null){
			strings=s.split("[|]");	
			if(strings[3].equals("16")){
				str = new BigInteger(strings[6].substring(0,4),16).toString(10);
				f=Float.parseFloat(str);
				temp=(float)((f*0.00268127)-46.85);
				environment=new Environment("温度", strings[0], strings[1], strings[2],strings[3],Integer.parseInt(strings[4]), strings[5], Integer.parseInt(strings[7]), temp,new Timestamp(Long.parseLong(strings[8])) );
				list.add(environment);
				str = new BigInteger(strings[6].substring(4, 8),16).toString(10);
				f=Float.parseFloat(str);
				rh=(float)((f*0.00190735)-6);
				environment=new Environment("湿度", strings[0], strings[1], strings[2],strings[3],Integer.parseInt(strings[4]), strings[5], Integer.parseInt(strings[7]), rh,new Timestamp(Long.parseLong(strings[8])) );
				list.add(environment);
			}else if(strings[3].equals("256")){
				str = new BigInteger(strings[6].substring(0, 4),16).toString(10);
				environment=new Environment("光照强度", strings[0], strings[1], strings[2],strings[3],Integer.parseInt(strings[4]), strings[5], Integer.parseInt(strings[7]), Float.parseFloat(str),new Timestamp(Long.parseLong(strings[8])) );
				list.add(environment);
			}else if(strings[3].equals("1280")){
				str = new BigInteger(strings[6].substring(0, 4),16).toString(10);
				environment=new Environment("二氧化碳", strings[0], strings[1], strings[2],strings[3],Integer.parseInt(strings[4]), strings[5], Integer.parseInt(strings[7]), Float.parseFloat(str),new Timestamp(Long.parseLong(strings[8])) );
				list.add(environment);
			}else{
				log.info("查询出错");
			}
 		}
		log.info("采集成功");
		return list;
	}

}
