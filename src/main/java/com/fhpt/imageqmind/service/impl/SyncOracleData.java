package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.DataSetEntity;

import com.fhpt.imageqmind.domain.DbInfoEntity;

import com.fhpt.imageqmind.repository.DataSetRepository;
import com.fhpt.imageqmind.service.AbstractSyncData;
import com.fhpt.imageqmind.utils.SpringContextUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * oracle数据同步
 * @author Marty
 */
public class SyncOracleData extends AbstractSyncData {

    private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    private static final String DB_URL_TEMPLATE = "jdbc:oracle:thin:@%s:%d/%s";

    @Override
    public boolean syncData(DataSetEntity dataSet) {
        DbInfoEntity dbInfo = dataSet.getDbInfo();
        List<String> contents = new ArrayList<>();
        try {
            //加载驱动
            Class.forName(JDBC_DRIVER);
            String dbUrl = String.format(DB_URL_TEMPLATE, dbInfo.getIp(), dbInfo.getPort(), dbInfo.getDbName());
            System.out.println(dbUrl);
            //连接数据库
            Connection connection = DriverManager.getConnection(dbUrl, dbInfo.getUserName(), dbInfo.getPassword());
            Statement statement = connection.createStatement();
            String handelColumn = dataSet.getColumnName();
            //用户定义最大同步量
            String sql = String.format("SELECT %s FROM %s.%s where rownum <= %s", handelColumn, dbInfo.getUserName(), dbInfo.getTableName(), dataSet.getMaxSize());
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

//    public static void main(String[] args){
//        try {
//            //加载驱动
//            Class.forName(JDBC_DRIVER);
//            String dbUrl = String.format(DB_URL_TEMPLATE, "116.62.157.23", 8161);
//            //连接数据库
//            Connection connection = DriverManager.getConnection(dbUrl, "test", "123456");
//            Statement statement = connection.createStatement();
//            String sql = String.format("SELECT %s FROM %s.%s", "sname", "test", "student");
//            statement.executeQuery(sql);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }    }



    @Override
    public boolean connect(String ip, int port, String userName, String password, String dbName, String tableName, String columnName, String schema) {
        try {
            //加载驱动
            Class.forName(JDBC_DRIVER);
            String dbUrl = String.format(DB_URL_TEMPLATE, ip, port, dbName);
            //连接数据库
            Connection connection = DriverManager.getConnection(dbUrl, userName, password);
            Statement statement = connection.createStatement();
            String sql = String.format("SELECT %s FROM %s.%s rownum <= 1", columnName, userName, tableName);
            statement.executeQuery(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
