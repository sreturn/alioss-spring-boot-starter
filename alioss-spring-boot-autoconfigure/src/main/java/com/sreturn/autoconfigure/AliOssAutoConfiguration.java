package com.sreturn.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description
 * Created by Intellij IDEA
 *
 * @author sunyunlin
 * @create 2021/9/7 16:13
 */
@Configuration
@ConditionalOnClass(PrinterClient.class)
@EnableConfigurationProperties(AliOssProperties.class)
public class AliOssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PrinterClient test(){
        return new PrinterClient();
    }

}
