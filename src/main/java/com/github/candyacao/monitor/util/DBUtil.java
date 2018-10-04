package com.github.candyacao.monitor.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


/**
 * 数据库连接的工具类
 * 适合单线程
 * @author CLY
 *
 */
public class DBUtil {
	final static Logger log = Logger.getLogger(DBUtil.class);
	private static String driver;
	private static String url;
	private static String username;
	private static String passwd;
	private static Connection connection;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	static {
		Properties properties = new Properties();
		//读取文件
		try {

			properties.load(DBUtil.class.getClassLoader()
					.getResourceAsStream("DBSource.properties"));
//			System.out.println(properties);
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			passwd = properties.getProperty("passwd");
//			System.out.println(driver);
//			System.out.println(url);
//			System.out.println(username);
//			System.out.println(passwd);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取连接对象
	 * @return
	 */
	public static Connection getConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url,username,passwd);
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 使用preparedStatement执行查询语句
	 * @param sql
	 * @param params
	 * @return
	 */
	public static ResultSet executeQuery(String sql,Object...params) {
		getConnection();
		try {
			pstmt=connection.prepareStatement(sql);
			for(int i=0;i<params.length;i++) {
				pstmt.setObject(i+1, params[i]);
			}
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return rs;
	}
	
	/**
	 * 使用preparedStatement执行insert，update，delete语句
	 * @param sql
	 * @param params
	 * @return
	 */
	static int count = 0;
	static int batchsize = 1000;
	public static PreparedStatement executeUpdate(String sql,Object...params) {

		try {
			pstmt=connection.prepareStatement(sql);
			for(int i=0;i<params.length;i++) {
				pstmt.setObject(i+1, params[i]);
			}
			pstmt.addBatch();
			
			if (count++%batchsize==0) {
				pstmt.executeBatch();
				connection.commit();
				pstmt.clearBatch();
				System.out.println(count);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pstmt;
	}
	
	
	/**
	 * 关闭资源
	 */
	public static void close() {
		try {
				if(rs!=null) {
					rs.close();
				}
				if (pstmt!=null) {
					pstmt.close();
				}
				if (connection!=null) {
					connection.close();
				}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
		
}
