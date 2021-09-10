package com.sreturn.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description
 * Created by Intellij IDEA
 *
 * @author sunyunlin
 * @create 2021/9/7 16:34
 */

@Service
public class PrinterClient {
    @Autowired
    private AliOssProperties aliOssProperties;

    public void print(){
        System.out.println(aliOssProperties.toString());
    }

}
