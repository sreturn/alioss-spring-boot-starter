package com.sreturn.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description
 * Created by Intellij IDEA
 *
 * @author sunyunlin
 * @create 2021/9/7 16:05
 */
@Data
@ConfigurationProperties("aliyun.oss.file")
public class AliOssProperties {
    /**
     * yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
     */
    private String endpoint;
    /**
     *  阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
     */
    private String keyid;
    /**
     * yourAccessKeySecret
     */
    private String keysecret;
    /**
     * 填写Bucket名称，例如examplebucket。
     */
    private String bucketname;

}
