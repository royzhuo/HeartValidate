package com.entity;

/**
 * Created by roy.zhuo on 2017/9/29.
 */


public class SysInfoAll {

    //db
    private String userName;
    private String pwd;
    private String url;
    private String dbName;
    private String tableName;
    private String driver;
    //mail
    private String sender;
    private String recipient;
    private String cc;
    private String host;
    private String port;
    private String emaiUserName;
    private String eamilPwd;
    private String timeOut;
    private String auth;
    private String enable;



    //time secher
    private String hmu1;
    private String hmu2;

    public SysInfoAll(){}

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getEmaiUserName() {
        return emaiUserName;
    }

    public void setEmaiUserName(String emaiUserName) {
        this.emaiUserName = emaiUserName;
    }

    public String getEamilPwd() {
        return eamilPwd;
    }

    public void setEamilPwd(String eamilPwd) {
        this.eamilPwd = eamilPwd;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getHmu1() {
        return hmu1;
    }

    public void setHmu1(String hmu1) {
        this.hmu1 = hmu1;
    }

    public String getHmu2() {
        return hmu2;
    }

    public void setHmu2(String hmu2) {
        this.hmu2 = hmu2;
    }

    @Override
    public String toString() {
        return "SysInfoAll{" +
                "userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", url='" + url + '\'' +
                ", dbName='" + dbName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", driver='" + driver + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", cc='" + cc + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", emaiUserName='" + emaiUserName + '\'' +
                ", eamilPwd='" + eamilPwd + '\'' +
                ", timeOut='" + timeOut + '\'' +
                ", auth='" + auth + '\'' +
                ", hmu1='" + hmu1 + '\'' +
                ", hmu2='" + hmu2 + '\'' +
                '}';
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }





}
