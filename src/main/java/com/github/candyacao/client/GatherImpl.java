package com.github.candyacao.client;


import com.github.candyacao.bean.Environment;
import com.github.candyacao.config.ModuleInit;
import com.github.candyacao.logger.Log;
import com.github.candyacao.logger.LogImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.*;

public class GatherImpl implements Gather, ModuleInit {

	Collection<Environment> ens = new ArrayList<>();
	private static Map<String, List<String>> name = new HashMap<>();

	private static Log log = new LogImpl();
	/** 文件路径 */
	private String filePath;
	static {
		List<String> co2 = new ArrayList<>();
		name.put("1280", co2);
		co2.add("co2");
		List<String> illumination = new ArrayList<>();
		name.put("256", illumination);
		illumination.add("illumination");
		List<String> tem = new ArrayList<>();
		tem.add("tem");
		tem.add("humidity");
		name.put("16", tem);
	}

	@Override
	public void init(Properties properties) {
		filePath = properties.getProperty("file");
	}

	/**
	 * 解析文件，获得数据对象的集合
	 * 
	 * @return 数据对象集合
	 */
	@Override
	public Collection<Environment> gather() {
		// 解析文件
		// 创建随机处理流对象
		log.info("开始读取文件。。。。。。。。");
		RandomAccessFile rsf = null;
		String line = null;
		try {
			log.debug("rsf发生空指针异常");
			rsf = new RandomAccessFile(filePath, "r");
			System.out.println(rsf);
			while ((line = rsf.readLine()) != null) {
				// 处理每行记录，封装成一个Environment对象并且放入集合中
				lineToEnvironment(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				log.warn("关闭rsf流");
				rsf.close();
				log.info("文件读取结束。。。。。。。");
			} catch (IOException e) {
//						e.printStackTrace();
				log.error("I/O异常，文件关闭错误。。。。。。。。");
			}
		}
		return ens;
	}

	/**
	 * 字符串转化成Environment对象，把该对象放到集合中
	 * 
	 * @param line 每行记录
	 * 
	 */
	private void lineToEnvironment(String line) {
		// 分割字符串
		String[] strs = line.split("[|]");
		Environment env = new Environment();
		if (name.containsKey(strs[3])) {
			if ("16".equals(strs[3])) {
				// 获取温度对象
				env.setName(name.get(strs[3]).get(0));
				env.setData(Integer.parseInt(strs[6].substring(0, 4), 16) * 0.00268127 - 46.85);
				setEnv(env, strs);

				// 获取湿度对象
				Environment hum = new Environment();
				hum.setName(name.get(strs[3]).get(1));
				hum.setData(Integer.parseInt(strs[6].substring(4, 8), 16) * 0.00190735 - 6);
				setEnv(hum, strs);
			} else {
				env.setName(name.get(strs[3]).get(0));
				env.setData(Integer.parseInt(strs[6].substring(0, 4), 16) * 1.0);
				setEnv(env, strs);
			}
		}
	}

	/**
	 * 给环境变量赋值
	 * 
	 * @param env  赋值的环境对象
	 * @param strs 字符串数组
	 */
	private void setEnv(Environment env, String... strs) {
		env.setSrcID(strs[0]);
		env.setDesID(strs[1]);
		env.setDevID(strs[2]);
		env.setSersorAddress(strs[3]);
		env.setCount(Integer.parseInt(strs[4]));
		env.setCmd(strs[5]);
		env.setStatus(Integer.parseInt(strs[7]));
		env.setGather_time(new Timestamp(Long.parseLong(strs[8])));
		ens.add(env);
	}
	
}
