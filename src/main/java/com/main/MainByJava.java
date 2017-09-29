package com.main;

import com.entity.SysInfo;
import com.entity.SysInfoAll;
import com.service.ValidateDBService2;
import com.util.DbUtils;
import com.util.SysInfoSinger;

/**
 * Created by roy.zhuo on 2017/9/29.
 */


public class MainByJava {
    public static void main(String[] args) {
//        DbUtils.executeReader(null);
        ValidateDBService2 validateDBService2=new ValidateDBService2();
        SysInfoAll sysInfoAll=SysInfoSinger.getSysInfo();
        SysInfoSinger.initSysinfoParames(sysInfoAll);
        validateDBService2.startSchedule1();
        validateDBService2.startSchedule2();
    }


}
