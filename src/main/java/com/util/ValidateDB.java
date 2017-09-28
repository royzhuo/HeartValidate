package com.util;

import com.service.ValidateDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhiyi.zhuo
 * @create 2017-09-21 16:26
 * @desc 检测数据库是否建表
 **/
@Component
public class ValidateDB {


    @Autowired
    private ValidateDBService validateDBService;

    public void testValidateTable(){
        validateDBService.vlidataTableIsCreate();
    }

}
