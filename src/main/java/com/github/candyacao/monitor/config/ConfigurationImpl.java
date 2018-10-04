package com.github.candyacao.monitor.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.candyacao.monitor.client.Client;
import com.github.candyacao.monitor.client.Gather;
import com.github.candyacao.monitor.logger.Log;
import com.github.candyacao.monitor.server.DBStore;
import com.github.candyacao.monitor.server.Server;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigurationImpl implements Configuration {

	/**
	 * map集合键就是对应模块的标签名，值即为对应模块的对象
	 */
	private static Map<String, ModuleInit> map = new HashMap<>();
	
	static {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read("src/main/resources/config.xml");
			//获取根元素
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			for(Element e: elements) {
				Properties properties = new Properties();
				// 获取到对应模块的权限类名
				String value = e.attributeValue("class");
				Class<?> clazz = Class.forName(value);
				// 获取对应模块的实例对象
				Object object = clazz.newInstance();
				if (object instanceof ModuleInit) {
					ModuleInit module = (ModuleInit) object;
					map.put(e.getName(), module);
					//获取每个模块下的所有子标签
					List<Element> elements2 = e.elements();
					for(Element e2: elements2) {
						properties.setProperty(e2.getName(), e2.getText());
					}
					module.init(properties);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public Client getClient() {
		return (Client) map.get("client");
	}

	@Override
	public Server getServer() {
		return (Server) map.get("server");
	}

	@Override
	public Gather getGather() {
		return (Gather) map.get("gather");
	}

	@Override
	public DBStore getDBStore() {
		return (DBStore) map.get("dbStore");
	}

	@Override
	public Log getLog() {
		return (Log) map.get("logger");
	}	

}
