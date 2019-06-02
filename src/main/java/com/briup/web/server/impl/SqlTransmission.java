package com.briup.web.server.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.briup.bean.Environment;

public class SqlTransmission {
	static void createTable(Connection conn,int x) throws SQLException {
		Statement st=conn.createStatement();
		String sql ="create table monitor_date_"+x+" ( name varchar2(30), srcId varchar2(30),"
				+ "dstId varchar2(30) ,devId varchar2(30) ,sersorAddress varchar2(30) ,count number(5) ,"
				+ "cmd varchar2(30) ,status number(5) ,data number(10) ,gather_date date)";
		st.execute(sql);
		conn.commit();
		System.out.println("建表成功");
	}
	static void insertData(Connection conn,Collection<Environment> coll,int x) throws SQLException{
		PreparedStatement ps=null;
		int count=0;
		String sql = "insert into monitor_date_"+x+" values(?,?,?,?,?,?,?,?,?,?)";
		ps=conn.prepareStatement(sql);
		for(Environment i:coll){
			ps.setString(1, i.getName());
			ps.setString(2, i.getSrcId());
			ps.setString(3, i.getDstId());
			ps.setString(4, i.getDevId());
			ps.setString(5, i.getSersorAddress());
			ps.setInt(6, i.getCount());
			ps.setString(7, i.getCmd());
			ps.setInt(8, i.getStatus());
			ps.setFloat(9, i.getData());
			ps.setTimestamp(10, i.getGather_date());
			ps.addBatch();
			if(count%500==0){
				ps.executeBatch();
				ps.clearBatch();
			}
			count++;
		}
		ps.executeBatch();
		conn.commit();
		System.out.println("加入数据成功");
		if(ps!=null){
			ps.close();
		}
		if(conn!=null){
			conn.close();
		}
	}
	//查询数据的方法
	static void queryData(Connection conn,String table) throws SQLException{
		Statement st=conn.createStatement();
		String sql="select * from "+table;
		st.execute(sql);
		conn.commit();
		//处理查询的结果集
		ResultSet rs=st.executeQuery(sql);
		Environment environment=null;
		List<Environment> list=new ArrayList();
		while(rs.next()){
			String name = rs.getString("name");
			String srcId = rs.getString("srcId");
			String dstId = rs.getString("dstId");
			String devId = rs.getString("devId");
			String sersorAddress = rs.getString("sersorAddress");
			int count = rs.getInt("count");
			String cmd =rs.getString("cmd");
			int status = rs.getInt("status");
			float data = rs.getFloat("data");
			Timestamp gather_date = rs.getTimestamp("gather_date");
			environment=new Environment();
			list.add(environment);
		}
		for(Environment t:list){
			System.out.println(t);
		}
		System.out.println("查询数据成功");
	}
	//更新数据的方法
	static void updateData(Connection conn,String table,String sql) throws SQLException{
		Statement st=conn.createStatement();
		st.execute(sql);
		conn.commit();
		System.out.println("更改数据成功");
	}
	//删除表中的记录
	static void deleteData(Connection conn,String table) throws SQLException{
		Statement st=conn.createStatement();
		String sql="delete from "+table;
		st.execute(sql);
		conn.commit();
		System.out.println("删除表中数据成功");
	}
	//删除表
	static void deleteTable(Connection conn,String table) throws SQLException{
		Statement st=conn.createStatement();
		String sql="drop table "+table;
		st.execute(sql);
		conn.commit();
		System.out.println("删表成功");
	}
}
