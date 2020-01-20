package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.DataSetEntity;
import com.fhpt.imageqmind.domain.DbInfoEntity;

import com.fhpt.imageqmind.service.AbstractSyncData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mysql同步业务
 * @author Marty
 */
public class SyncMysqlData extends AbstractSyncData {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL_TEMPLATE = "jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf-8&useSSL=false";

    @Override
    public boolean syncData(DataSetEntity dataSet) {
        DbInfoEntity dbInfo = dataSet.getDbInfo();
        List<String> contents = new ArrayList<>();
        try {
            //加载驱动
            Class.forName(JDBC_DRIVER);
            String dbUrl = String.format(DB_URL_TEMPLATE, dbInfo.getIp(), dbInfo.getPort(), dbInfo.getDatabaseName());
            //连接数据库
            Connection connection = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
            Statement statement = connection.createStatement();
            String handelColumn = dataSet.getColumnName();
            String sql = String.format("SELECT %s FROM %s", handelColumn, dbInfo.getTableName());
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String content = resultSet.getString(handelColumn);
                contents.add(content);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return saveToDB(contents, dataSet.getId());
    }
}
