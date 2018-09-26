package com.github.candyacao.bean;


import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 环境存储实体类，包括环境种类(温度，湿度，二氧化碳，光照强度)
 * 100|101|2|256|1|3|001003|1|1516361343403
 * 发送方id、树莓派id,实验箱模块id,传感器地址,传感器个数，指令标号，环境值，状态，采集时间
 * @author candyacao
 * @created 2018年9月20日 下午12:21:01
 */
public class Environment implements Serializable {
    /**
	 * 串行化版本ID
	 */
	private static final long serialVersionUID = 6203161476075260625L;
	/** 环境名称 */
    private String name;
    /** 发送方id */
    private String srcID;
   /** 树莓派id */
    private  String desID;
    /** 实验箱模块id(1-8) */
    private String devID;
    /** 传感器地址 */
    private String sersorAddress;
    /** 传感器个数 */
    private Integer count;
    /** 发送指令号    3：接收  16：发送 */
    private String cmd;
    /** 环境值 */
    private double data;
    /** 状态   1代表成功 */
    private Integer status;
    /** 采集时间 */
    private Timestamp gather_time;

    /**
    * 无参构造函数
    * 全参构造函数
    * get和set方法
    * 重写toString方法
    */

    public  Environment() {}
	public Environment(String name, String srcID, String desID, String devID, String sersorAddress, int count,
			String cmd, float data, int status, Timestamp gather_time) {
		this.name = name;
		this.srcID = srcID;
		this.desID = desID;
		this.devID = devID;
		this.sersorAddress = sersorAddress;
		this.count = count;
		this.cmd = cmd;
		this.data = data;
		this.status = status;
		this.gather_time = gather_time;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSrcID() {
		return srcID;
	}

	public void setSrcID(String srcID) {
		this.srcID = srcID;
	}

	public String getDesID() {
		return desID;
	}

	public void setDesID(String desID) {
		this.desID = desID;
	}

	public String getDevID() {
		return devID;
	}

	public void setDevID(String devID) {
		this.devID = devID;
	}

	public String getSersorAddress() {
		return sersorAddress;
	}

	public void setSersorAddress(String sersorAddress) {
		this.sersorAddress = sersorAddress;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public double getData() {
		return data;
	}

	public void setData(double value) {
		this.data = value;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getGather_time() {
		return gather_time;
	}

	public void setGather_time(Timestamp gather_time) {
		this.gather_time = gather_time;
	}
	@Override
	public String toString() {
		return "Environment [name=" + name + ", srcID=" + srcID + ", desID=" + desID + ", devID=" + devID
				+ ", sersorAddress=" + sersorAddress + ", count=" + count + ", cmd=" + cmd + ", data=" + data
				+ ", status=" + status + ", gather_time=" + gather_time + "]";
	}
	
    
	
}
