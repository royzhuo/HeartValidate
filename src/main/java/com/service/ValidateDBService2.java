package com.service;

import com.entity.SysInfoAll;
import com.util.DbUtils;
import com.util.SysInfoSinger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by roy.zhuo on 2017/9/29.
 */


public class ValidateDBService2  {


    private static Logger logger = LoggerFactory.getLogger(ValidateDBService2.class);


    /**
     * 查询是否存在表格
     */
    public void queryTable(){
        SysInfoAll sysInfo= SysInfoSinger.getSysInfo();
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
            ResultSet resultSet= DbUtils.executeReader(sql1.toString(),null);
            boolean isSuccess=false;
            while (resultSet.next()){
                isSuccess=true;
                break;
            }
            if (isSuccess==false){
                logger.info("数据表创建失败");
                //sendMail();
            }else{
                logger.info("数据表创建成功");
                DbUtils.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * 获取当前时间
     * @return
     */
    public String getCurrentTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 定时器1
     */
    public void startSchedule1(){
        //设置定时时间
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DATE);
        SysInfoAll sysInfoAll=SysInfoSinger.getSysInfo();
        if (sysInfoAll.getHmu1()!=null){
            String hums[]=sysInfoAll.getHmu1().split(",");
            calendar.set(year,month,day,Integer.valueOf(hums[2]),Integer.valueOf(hums[1]),Integer.valueOf(hums[0]));
            Date date=calendar.getTime();
            if (date.before(new Date())){
                date=this.addDay(date,1);
            }
            System.out.println("data:"+date);
            Timer timer=new Timer();
            TimerTask timerTask=new TimerTask() {
                @Override
                public void run() {
                    queryTable();
                }
            };
            timer.schedule(timerTask,date);
        }else{
            logger.info("没有配置启动时间");
        }

    }

    /**
     * 定时器2
     */
    public void startSchedule2(){
        //设置定时时间
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DATE);
        SysInfoAll sysInfoAll=SysInfoSinger.getSysInfo();
        if (sysInfoAll.getHmu2()!=null){
            String[] hmus=sysInfoAll.getHmu2().split(",");
            calendar.set(year,month,day,Integer.valueOf(hmus[2]),Integer.valueOf(hmus[1]),Integer.valueOf(hmus[0]));
            Date date=calendar.getTime();
            if (date.before(new Date())){
                date=this.addDay(date,1);
            }
            System.out.println("data:"+date);
            Timer timer=new Timer();
            TimerTask timerTask=new TimerTask() {
                @Override
                public void run() {
                    queryTable();
                }
            };
            timer.schedule(timerTask,date);
        }else{
            logger.info("没有配置启动时间");
        }

    }

    // 增加或减少天数
    public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    public void sendMail(SysInfoAll sysInfo){

        //1.配置连接邮件服务器参数
        Properties properties=new Properties();
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.host",sysInfo.getHost());
        properties.setProperty("mail.smtp.auth",sysInfo.getAuth());
        properties.setProperty("mail.smtp.port",sysInfo.getPort());
        properties.setProperty("mail.smtp.timeout",sysInfo.getTimeOut());
        properties.setProperty("mail.smtp.starttls.enable",sysInfo.getEnable());
        properties.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback","false");

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session=Session.getInstance(properties);
        session.setDebug(true); //设置成debug，可以查看详细发送的log
        //3.创建一封邮件
        MimeMessage mimeMessage=new MimeMessage(session);
        try {
            //设置发件人
            mimeMessage.setFrom(sysInfo.getSender());
            //设置收件人
            if (sysInfo.getRecipient()!=null&&(!sysInfo.getRecipient().equals(""))){
                if (sysInfo.getRecipient().indexOf(",")!=-1){
                    //有多个发送邮箱
                    String[] tos=sysInfo.getRecipient().split(",");
                   mimeMessage.setRecipients(Message.RecipientType.TO, String.valueOf(tos));
                }else {
                    mimeMessage.setRecipients(Message.RecipientType.TO,sysInfo.getRecipient());
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
