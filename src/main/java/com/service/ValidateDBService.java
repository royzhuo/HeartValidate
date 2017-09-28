package com.service;

import com.entity.SysInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhiyi.zhuo
 * @desc 检测数据库是否建表
 * @create 2017-09-21 16:28
 **/
@Service
public class ValidateDBService {

    private static Logger logger = LoggerFactory.getLogger(ValidateDBService.class);

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private SysInfo sysInfo;

    public void vlidataTableIsCreate(){
        try {
           Connection connection= dataSource.getConnection();
            queryTable(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    //发送邮件
    public boolean sendMail(){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setText(getCurrentTime()+"  数据库异常");
        //发送者邮箱
        simpleMailMessage.setFrom(sysInfo.getSender());
        //主题
        simpleMailMessage.setSubject("数据异常");
        //接受者
        if (sysInfo.getRecipient()!=null&&(!sysInfo.getRecipient().equals(""))){
            if (sysInfo.getRecipient().indexOf(",")!=-1){
                //有多个发送邮箱
                String[] tos=sysInfo.getRecipient().split(",");
                simpleMailMessage.setTo(tos);
            }else {
                simpleMailMessage.setTo(sysInfo.getRecipient());
            }
        }
        //抄送
        if (sysInfo.getCc()!=null&&(!sysInfo.getCc().equals(""))){
            if (sysInfo.getCc().indexOf(",")!=-1){
                //有多个抄送人
                String[] ccs=sysInfo.getCc().split(",");
                simpleMailMessage.setCc(ccs);
            }else{
                simpleMailMessage.setCc(sysInfo.getCc());
            }
        }
        javaMailSender.send(simpleMailMessage);
        logger.info("发送成功");
        return false;
    }

    public String getCurrentTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }


    public void queryTable(Connection connection){
        StringBuffer sql1=new StringBuffer("select table_name  from INFORMATION_SCHEMA.TABLES where");
        if (sysInfo.getDbName()!=null&& !sysInfo.getDbName().equals("")){
            sql1.append(" TABLE_SCHEMA = '"+sysInfo.getDbName()+"'");
        }
        if (sysInfo.getTableName()!=null&&!sysInfo.getTableName().equals("")){
            if (sysInfo.getTableName().indexOf(",")!=-1){
                //有多张表格
                String[] tableNames=sysInfo.getTableName().split(",");
                for (int i=0;i<tableNames.length;i++){
                    String tableName=tableNames[i];
                    if (i==0){
                        sql1.append(" AND TABLE_NAME like '"+tableName+"%' ");
                    }else{
                        sql1.append(" AND TABLE_NAME like '"+tableName+"%' ");
                    }
                }
            }else{
                sql1.append(" AND TABLE_NAME like '"+sysInfo.getTableName()+"%' ");
            }
        }
        String currentTime=getCurrentTime();
        sql1.append(" AND create_time like '"+currentTime+"%'");
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql1.toString());
            ResultSet resultSet=preparedStatement.executeQuery();
            boolean isSuccess=false;
            while (resultSet.next()){
                isSuccess=true;
                break;
            }
            if (isSuccess==false){
                logger.info("数据表创建失败");
                sendMail();
            }else{
                logger.info("数据表创建成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }
}
