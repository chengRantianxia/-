package com.briup.web.server.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Collection;
import java.util.Properties;

import com.briup.bean.Environment;
import com.briup.log.Log;
import com.briup.log.impl.LogImpl;
import com.briup.util.Configuration;
import com.briup.web.server.DBStore;

public class DBStoreImpl implements DBStore{
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private static String batch_size;
	private Configuration con;
	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		driver = properties.getProperty("driver");
		url = properties.getProperty("url");
		username = properties.getProperty("username");
		password = properties.getProperty("password");
//		batch_size = properties.getProperty("batch-size");
	}

	@Override
	public void setConfiguration(Configuration con) {
		// TODO Auto-generated method stub
		this.con=con;
	}

	@Override
	public void saveDb(Collection<Environment> coll) throws Exception {
		// TODO Auto-generated method stub
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(url,username,password);
		//关闭自动提交
		conn.setAutoCommit(false);
		Log logImpl = con.getLogger();
		logImpl.info("开始入库");
		//删除表
//		for(int i=1;i<=31;i++){
//			SqlTransmission.deleteTable(conn, "monitor_date_"+i);
//			}
		//建表语句
//		for(int x=1;x<=31;x++){
//		SqlTransmission.createTable(conn,x);
//		}
		//插入语句
//		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);//获取日历对象，然后获取今天是这个月的第几天
//		SqlTransmission.insertData(conn, coll,day);
		logImpl.info("入库完成");
	}
}
