package com.github.candyacao.server;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import com.github.candyacao.bean.Environment;
import com.github.candyacao.util.DBUtil;


public class DBStore {
	public void saveToDB(Collection<Environment> envs) {
		DBUtil.getConnection();
		for(Environment env:envs) {
			Timestamp timestamp = env.getGather_time();
			int day = timestamp.getDate();
			String sql = "insert into e_detail_"+day+" values(?,?,?,?,?,?,?,?,?)";
			DBUtil.executeUpdate(sql,  new Object[] {env.getName(),env.getSrcID(),
					env.getDesID(),env.getSersorAddress(),env.getCount(),
					env.getCmd(),env.getStatus(),env.getData(),env.getGather_time()});
		}
		
		DBUtil.close();
	}
}
