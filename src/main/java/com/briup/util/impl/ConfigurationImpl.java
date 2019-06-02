package com.briup.util.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.log.Log;
import com.briup.util.Configuration;
import com.briup.util.ConfigurationAware;
import com.briup.util.WossModuleInit;
import com.briup.web.client.Client;
import com.briup.web.client.Gather;
import com.briup.web.server.DBStore;
import com.briup.web.server.Server;

public class ConfigurationImpl implements Configuration{
	private Map<String, WossModuleInit> map;
	private static Properties p;
	public ConfigurationImpl() {
		this("src/main/java/com/briup/util/config.xml");
	}
	public ConfigurationImpl(String filePath) {
		//解析配置文件，使用dom4j
		//1.构建SAXReader对象read
		//2.Document document = reader.read("xml文件路径);
		//3.获取root根节点
		//4.遍历root下所有的一级子节点（使用迭代器遍历），获取标签名以及属性值
		//5.在一级子标签的遍历中遍历该一级标签的所有子节点，获取标签名和文本值
		try {
			SAXReader reader = new SAXReader();
			File file = new File(filePath);
			Document document = reader.read(file);
			Element root = document.getRootElement();
			List<Element> list = root.elements();
			p = new Properties();
			map = new HashMap<>();
			for(Element child:list){
				String classvalue = child.attributeValue("class");
				WossModuleInit woss = (WossModuleInit) Class.forName(classvalue).newInstance();
				map.put(child.getName(), woss);
				List<Element> list1 = child.elements();
				for(Element e:list1){
					p.put(e.getName(), e.getText());
				}
				//用所有模块的对象调用自己模块的init方法，完成初始化配置
				woss.init(p);
				//判断该模块对象是否属于ConfigurationAWare类型
				//如果属于。调用该模块的setConfiguration方法，将当前对象传递过去
				if(woss instanceof ConfigurationAware){
					((ConfigurationAware) woss).setConfiguration(this);
					System.out.println("sdasdasdsad");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//解析时候每一个子标签对象对应一个模块
		//通过class属性值得到每个模块的权限定名，反射构建对象
		//构建好的每个模块对象保存到map集合，Map集合Key值用标签名
		//	对应的模块对象做value值，模块对象都是wossmodukeInit类型
		
		//还有获取每个模块标签下的初始化配置信息
		//将这些配置信息保存到Properties对象
		//每一个模块在使用功能方法之前需要先调用init方法进行初始化配置
		
		//每一个模块对象还需要调用setConfiguration（this）方法
		//调用之前需要先判断该模块对象是否属于ConfigurationAware接口类型
		//给每一个模块传一个Configuration对象
	}
	@Override
	public Log getLogger() throws Exception {
		// TODO Auto-generated method stub
		//从map集合中跟基友key值获取
		return (Log) map.get("logger");
	}
	@Override
	public Server getServer() throws Exception {
		// TODO Auto-generated method stub
		return (Server) map.get("server");
	}
	@Override
	public Client getClient() throws Exception {
		// TODO Auto-generated method stub
		return (Client) map.get("client");
	}
	@Override
	public DBStore getDbStore() throws Exception {
		// TODO Auto-generated method stub
		return (DBStore) map.get("dbstore");
	}
	@Override
	public Gather getGather() throws Exception {
		// TODO Auto-generated method stub
		return (Gather) map.get("gather");
	}

}
