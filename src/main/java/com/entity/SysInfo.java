package com.entity;

import org.springframework.stereotype.Component;

/**
 * @author zhiyi.zhuo
 * @create 2017-09-22 10:08
 **/

@Component
public class SysInfo {

    public SysInfo() {

    }

    //数据库名称
    private String dbName;
    //表名
    private String tableName;
    //发送人
    private String sender;



    //抄送人
    private String cc;
    //接受人
    private String recipient;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "SysInfo{" +
                "dbName='" + dbName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", sender='" + sender + '\'' +
                ", cc='" + cc + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}
