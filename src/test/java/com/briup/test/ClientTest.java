package com.briup.test;

import com.briup.util.impl.ConfigurationImpl;
import com.briup.web.client.Client;
import com.briup.web.client.Gather;

public class ClientTest {
	public static void main(String[] args) throws Exception {
		ConfigurationImpl con = new ConfigurationImpl();
		Gather g = con.getGather();
		Client c = con.getClient();
		c.send(g.gather());
	}
}
