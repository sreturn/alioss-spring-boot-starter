# alioss-spring-boot-starter

#### 介绍
整合阿里云OSS操作,实现基本的上传、下载、查询等操作，开箱即用，方便快捷

#### 软件架构
maven仓库安装

将文件 alioss-spring-boot-starter-1.0-SNAPSHOT.jar 放置D盘,cmd运行以下命令安装到仓库
```shell script
mvn install:install-file -DgroupId=com.github.sreturn -DartifactId=alioss-spring-boot-starter -Dversion=1.0-SNAPSHOT -Dpackaging=jar -Dfile=D:/alioss-spring-boot-starter-1.0-SNAPSHOT.jar  
```

#### 1. 快速开始

##### 1.1 添加maven 依赖
```xml
  <dependency>
      <groupId>com.github.sreturn</groupId>
      <artifactId>alioss-spring-boot-starter</artifactId>
      <version>1.0-SNAPSHOT</version>
  </dependency>

```
##### 1.2 配置项目文件
```yaml
# 微信支付配置
aliyun:
  oss:
    file:
      endpoint: oss-cn-shanghai.aliyuncs.com #节点
      keyid: LTA***********  # 你的id
      keysecret: PfA************** # 你的秘钥
      bucketname: # 你的bucketname
```
完成所有配置

##### 1.3 添加注解
>⚠️ 在springboot启动类上或者配置类上添加@EnableAlioss注解

```java
@EnableAlioss
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```
##### 1.4 在Controller中使用
>在controller中使用，通过注入AliOssFileService，使用该Service提供的方法进行上传、下载、查询和删除

```java
@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    AliOssFileService fileService;

    @PostMapping("upload")
    public String  upload( MultipartFile file){
        OssFileUploadResult ossFileUploadResult = fileService.fileUpload(file);
        return ossFileUploadResult.getFileUploadPath() + ossFileUploadResult.getObjectName();
    }

    @GetMapping("list")
    public List<OssFileDetail> list(){
        List<OssFileDetail> ossFileDetails = fileService.fileSummary();
        return ossFileDetails;
    }

    @GetMapping("delete")
    public void delete(String objectName){
        fileService.fileDelete(objectName);
    }
    @GetMapping("download")
    public void download(String objectName,String filePath){
        fileService.fileDownload(objectName,filePath);
    }
}
```

#### 后记

1.  后续会继续完成阿里云OSS其他高级操作，比如断点续传等
2.  喜欢请不要吝啬，点上一个star
3.  有任何问题可评论
