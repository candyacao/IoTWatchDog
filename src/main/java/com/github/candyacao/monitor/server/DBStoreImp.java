package com.github.candyacao.monitor.server;


import com.github.candyacao.monitor.bean.Environment;
import com.github.candyacao.monitor.config.ModuleInit;
import com.github.candyacao.monitor.logger.Log;
import com.github.candyacao.monitor.logger.LogImpl;
import com.github.candyacao.monitor.util.DBUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import java.util.Collection;
import java.util.Properties;

/**
 * @创建人 candyacao
 * @创建时间 2018/9/26
 * @描述
 */
public class DBStoreImp implements DBStore, ModuleInit {
    private int batchSize;
    private Log log = new LogImpl();
    private PreparedStatement pstmt;
    @Override
    public void init(Properties properties) {
        batchSize = Integer.parseInt(properties.getProperty("batchSize"));
    }
    /**
     * 入库
     *
     * @param collection 集合对象
     */
    @Override
    public void saveToDB(Collection<Environment> collection) {

        log.info("开始链接数据库");
        // 获取连接对象
        Connection connection = DBUtil.getConnection();
        // 用于计算放入批处理的条数
        int count = 0;
        // 用于表示下一天
        int nextDay = 0;
        log.info("开始入库");
        for (Environment env : collection) {
            // 将每个对象插入到对应的表中
            // 拿到这个月的某一天
            int day = getDay(env);

            if (pstmt == null || nextDay != day) {
                nextDay = day;
                // 清除前一天残留的数据
                if (pstmt != null) {
                    try {
                        pstmt.executeBatch();
                        pstmt.clearBatch();
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                String sql = "insert into e_detail_" + day + " values(?,?,?,?,?,?,?,?,?)";
                try {
                    pstmt = connection.prepareStatement(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                pstmt.setObject(1, env.getName());
                pstmt.setObject(2, env.getSrcID());
                pstmt.setObject(3, env.getDesID());
                pstmt.setObject(4, env.getSersorAddress());
                pstmt.setObject(5, env.getCount());
                pstmt.setObject(6, env.getCmd());
                pstmt.setObject(7, env.getStatus());
                pstmt.setObject(8, env.getData());
                pstmt.setObject(9, env.getGather_time());
                pstmt.addBatch();
                if (++count % 1000 == 0) {
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.executeBatch();
                pstmt.clearBatch();
                pstmt.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        log.info("入库完成");
    }

    /**
     * 获取环境对象对应的某一天
     *
     * @param env 环境对象
     * @return 某一天
     */
    private int getDay(Environment env) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(env.getGather_time());
        return calendar.get(calendar.DAY_OF_MONTH);
    }
}
