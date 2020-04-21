package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.DataSetEntity;
import com.fhpt.imageqmind.domain.DbInfoEntity;
import com.fhpt.imageqmind.repository.DataSetRepository;
import com.fhpt.imageqmind.service.AbstractSyncData;
import com.fhpt.imageqmind.utils.SpringContextUtil;
import org.springframework.util.StringUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mysql同步业务
 * @author Marty
 */
public class SyncPGData extends AbstractSyncData {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL_TEMPLATE = "jdbc:postgresql://%s:%d/%s";

    @Override
    public boolean syncData(DataSetEntity dataSet) {
        DbInfoEntity dbInfo = dataSet.getDbInfo();
        List<String> contents = new ArrayList<>();
        try {
            //加载驱动
            Class.forName(JDBC_DRIVER);
            String dbUrl = String.format(DB_URL_TEMPLATE, dbInfo.getIp(), dbInfo.getPort(), dbInfo.getDbName());
            //连接数据库
            Connection connection = DriverManager.getConnection(dbUrl, dbInfo.getUserName(), dbInfo.getPassword());
            Statement statement = connection.createStatement();
            String handelColumn = dataSet.getColumnName();
            String sql;
            //用户定义最大同步量
            if (StringUtils.hasText(dbInfo.getSchema())) {
                sql = String.format("SELECT %s FROM %s.%s limit %s offset 0", handelColumn, dbInfo.getSchema(), dbInfo.getTableName(), dataSet.getMaxSize());
            } else {
                sql = String.format("SELECT %s FROM %s limit %s offset 0", handelColumn, dbInfo.getTableName(), dataSet.getMaxSize());
            }
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
//                if (contents.size() == dataSet.getMaxSize()) {
//                    break;
//                }
                String content = resultSet.getString(handelColumn);
                contents.add(content);
            }
            //更新数据量
            DataSetRepository dataSetRepository = SpringContextUtil.getBeanByClass(DataSetRepository.class);
            dataSet.setSize(contents.size());
            dataSetRepository.save(dataSet);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return saveToDB(contents, dataSet.getId());
    }



    @Override
    public boolean connect(String ip, int port, String userName, String password, String dbName, String tableName, String columnName, String schema) {
        try {
            //加载驱动
            Class.forName(JDBC_DRIVER);
            String dbUrl = String.format(DB_URL_TEMPLATE, ip, port, dbName);
            //连接数据库
            Connection connection = DriverManager.getConnection(dbUrl, userName, password);
            Statement statement = connection.createStatement();
            String sql;
            if (StringUtils.hasText(schema)) {
                sql = String.format("SELECT %s FROM %s.%s limit 1 offset 0", columnName, schema, tableName);
            } else {
                sql = String.format("SELECT %s FROM %s limit 1 offset 0", columnName, tableName);
            }
            statement.executeQuery(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
