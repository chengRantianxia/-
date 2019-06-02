package com.briup.test;

import java.util.Collection;

import com.briup.bean.Environment;
import com.briup.util.impl.ConfigurationImpl;
import com.briup.web.server.DBStore;
import com.briup.web.server.Server;

public class SeverTest {
	public static void main(String[] args) throws Exception {
		ConfigurationImpl con = new ConfigurationImpl();
		Server server = con.getServer();
		Collection<Environment> coll = server.reciver();
		DBStore db = con.getDbStore();
		db.saveDb(coll);
	}
}
