package com.util;

import com.entity.SysInfo;
import com.entity.SysInfoAll;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by roy.zhuo on 2017/9/29.
 */


public class SysInfoSinger {

    private static SysInfoAll sysInfo=null;

    private SysInfoSinger(){}

    public static SysInfoAll getSysInfo(){
        if (sysInfo==null){
            synchronized (SysInfo.class){
                if (sysInfo==null){
                    sysInfo=new SysInfoAll();
                }
            }
        }
        return sysInfo;
    }

    public static void initSysinfoParames(SysInfoAll sysInfo){
        Properties properties=new Properties();
        InputStream inputStream=DbUtils.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(inputStream);
            sysInfo=SysInfoSinger.getSysInfo();
            sysInfo.setUrl(properties.getProperty("mysql.url"));
            sysInfo.setUserName(properties.getProperty("mysql.user"));
            sysInfo.setPwd(properties.getProperty("mysql.pwd"));
            sysInfo.setDriver(properties.getProperty("mysql.driver"));
            sysInfo.setTableName(properties.getProperty("mysql.tablename"));
            sysInfo.setDbName(properties.getProperty("mysql.dbName"));
            properties.clear();
            properties.load(DbUtils.class.getClassLoader().getResourceAsStream("heartbeat.properties"));
            sysInfo.setHmu1(properties.getProperty("date.hmu1"));
            sysInfo.setHmu2(properties.getProperty("date.hmu2"));

            sysInfo.setSender(properties.getProperty("email.sender"));
            sysInfo.setRecipient(properties.getProperty("email.recipient"));
            sysInfo.setCc(properties.getProperty("email.cc"));
            sysInfo.setHost(properties.getProperty("email.host"));
            sysInfo.setPort(properties.getProperty("email.port"));
            sysInfo.setTimeOut(properties.getProperty("email.smtp.timeout"));
            sysInfo.setAuth(properties.getProperty("email.smtp.auth"));
            sysInfo.setEnable(properties.getProperty("email.smtp.starttls.enable"));
            sysInfo.setEmaiUserName(properties.getProperty("email.username"));
            sysInfo.setEamilPwd(properties.getProperty("email.pwd"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
