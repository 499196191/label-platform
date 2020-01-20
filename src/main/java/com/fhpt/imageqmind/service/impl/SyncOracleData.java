package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.DataSetEntity;

import com.fhpt.imageqmind.domain.DbInfoEntity;
import com.fhpt.imageqmind.service.AbstractSyncData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * oracle数据同步
 * @author Marty
 */
public class SyncOracleData extends AbstractSyncData {

    private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    private static final String DB_URL_TEMPLATE = "jdbc:oracle:thin:@%s:%d:%s";

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
            String sql = String.format("SELECT %s FROM %s.%s", handelColumn, dbInfo.getUsername(), dbInfo.getTableName());
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
