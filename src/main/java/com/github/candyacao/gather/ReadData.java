package com.github.candyacao.gather;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.candyacao.bean.Environment;

public class ReadData {
	Collection<Environment> list = new ArrayList<>();
	Environment env = new Environment();
	static Map<String, List<String>> name = new HashMap<>();
	
	/** 环境名称用map记录 */
	static {
		List<String> co2 = new ArrayList<>();
		co2.add("CO2");
		name.put("1280",co2);
		List<String> photosensitive = new ArrayList<>();
		photosensitive.add("photosensitive");
		name.put("256", photosensitive);
		List<String> tem_hum = new ArrayList<>();
		tem_hum.add("temperature");
		tem_hum.add("humidity");
		name.put("16", tem_hum);
	}
	
	/**
	 * 解析数据，获得Environment对象
	 * @param line   
	 * @return
	 */
	public Collection<Environment> gather(){
		// 解析文件
		// 创建随机处理流对象
		RandomAccessFile raf  = null;
		String line = null;

		try {

			raf = new RandomAccessFile("E:\\MavenDemo\\LombokL\\src\\main\\resources\\radwtmp", "r");
			while((line=raf.readLine())!=null) {
				lineToEnvironment(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
			 	raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 将每行数据转化成Environment对象
	 * 1.构建缓存字符流按行读取
     * 2.根据|分割，根据第4个字段 16：温度湿度 256：光 1280：二氧化碳
     * 3.第七字段，16进制->10
     * （温度2，湿度2字节） 光，二氧化碳（前2个字节）
     * 4.温度和湿度，读取一行要创建2个Enviroment对象
     * 5.添加到集合
	 * @param line  每行记录
	 */ 
	private void lineToEnvironment(String line) {
		//分割字符串
		String[] str = line.split("[|]");
		if (name.containsKey(str[3])) {
			if("16".equals(str[3])) {
				//获取温度对象
				env.setName(name.get(str[3]).get(0));
				env.setData(Integer.parseInt(
						str[6].substring(0, 4),16) * 0.00268127 - 46.85);
				setEnv(env, str);
				//获取湿度对象
				Environment hum = new Environment();
				hum.setName(name.get(str[3]).get(1));
				hum.setData(Integer.parseInt(
							str[6].substring(4, 8),16) * 0.00190735 - 6);
				setEnv(hum,str);
			} else {
				env.setName(name.get(str[3]).get(0));
				env.setData(Integer.parseInt(str[6].substring(0, 4), 16) * 1.0);
				setEnv(env, str);
			}
		}
		
	}
	
	private void setEnv(Environment env,String...str) {
		env.setSrcID(str[0]);
    	env.setDesID(str[1]);
    	env.setDevID(str[2]);
    	env.setSersorAddress(str[3]);
    	env.setCount(Integer.parseInt(str[4]));
    	env.setCmd(str[5]);
    	env.setStatus(Integer.parseInt(str[7]));
    	env.setGather_time(new java.sql.Timestamp(Long.parseLong(str[8])));
    	list.add(env);
	}
	
	
	    
	     


}
