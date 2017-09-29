package com.util;

import com.entity.SysInfo;
import com.entity.SysInfoAll;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.service.ValidateDBService2;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by roy.zhuo on 2017/9/29.
 */


public class DbUtils {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ValidateDBService2.class);

    //c3p0数据库连接池
    private static ComboPooledDataSource dataSource;
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static SysInfoAll sysInfo;

    public static void closeConnection(){
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeConnection2(){
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static {
       sysInfo=SysInfoSinger.getSysInfo();
       try {
           dataSource=new ComboPooledDataSource();
           dataSource.setUser(sysInfo.getUserName());
           dataSource.setPassword(sysInfo.getPwd());
           dataSource.setDriverClass(sysInfo.getDriver());
           dataSource.setJdbcUrl(sysInfo.getUrl());
           dataSource.setMaxPoolSize(40);//设置连接池的最大连接数
           dataSource.setMinPoolSize(2);//设置连接池的最小连接数
           dataSource.setInitialPoolSize(5);//设置连接池的初始连接数
           dataSource.setMaxStatements(100);//设置连接池的缓存Statement的最大数
       }catch (Exception e){
           logger.error(e.getMessage());
       }
    }

    public static ComboPooledDataSource getDataSource(){
        return dataSource;
    }



    public static ResultSet executeReader(String sql,String[] params){
        try {
            connection=dataSource.getConnection();
            System.out.println("connection: "+connection);
            preparedStatement=connection.prepareStatement(sql);
            if (params!=null&&params.length>0){
                for (int i=0;i<params.length;i++){
                    preparedStatement.setObject(i+1,params[i]);
                }
            }
            return resultSet=preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("dberror",e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return  null;
    }


}
