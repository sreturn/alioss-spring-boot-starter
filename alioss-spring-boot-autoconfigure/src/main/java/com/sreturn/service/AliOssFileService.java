package com.sreturn.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.sreturn.autoconfigure.AliOssProperties;
import com.sreturn.entity.OssFileDetail;
import com.sreturn.entity.OssFileUploadResult;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Description 实现类，实现文件上传功能
 * Created by Intellij IDEA
 *
 * @author sunyunlin
 * @create 2021/9/7 16:18
 */
@Service
public class AliOssFileService {

    @Autowired
    private AliOssProperties aliOssProperties;

    /**
     * 文件上传
     * @param file 上传的文件
     * @return 返回文件上传的路径
     */
    public OssFileUploadResult fileUpload(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = aliOssProperties.getEndpoint();
        String accessKeyId = aliOssProperties.getKeyid();
        String accessKeySecret = aliOssProperties.getKeysecret();
        String bucketName = aliOssProperties.getBucketname();

        String uploadUrl = null;
        String objectName = null;
        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            //OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }

            //获取上传文件流
            InputStream inputStream = file.getInputStream();

            //构建日期路径：avatar/2019/02/26/文件名
            String filePath = new DateTime().toString("yyyy/MM/dd");

            //文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            String fileUrl = "files"+ "/" + filePath + "/" + newName;

            //文件上传至阿里云
            ossClient.putObject(bucketName, fileUrl, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //获取url地址
            // https://education-return.oss-cn-shanghai.aliyuncs.com/1.jpg
            uploadUrl = "https://" + bucketName + "." + endPoint + "/" + fileUrl;
            objectName = fileUrl;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new OssFileUploadResult(uploadUrl,objectName);
    }

    /**
     * 列举oss服务器中所有的文件
     * @return 返回文件存放路劲的列表
     */
    public List<OssFileDetail> fileSummary(){
        //获取阿里云存储相关常量
        String endPoint = aliOssProperties.getEndpoint();
        String accessKeyId = aliOssProperties.getKeyid();
        String accessKeySecret = aliOssProperties.getKeysecret();
        String bucketName = aliOssProperties.getBucketname();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
        ObjectListing objectListing = ossClient.listObjects(bucketName);

        List<OssFileDetail> files = new ArrayList<OssFileDetail>();
        // objectListing.getObjectSummaries获取所有文件的描述信息。
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            /*System.out.println(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");*/
            OssFileDetail detail = new OssFileDetail();
            detail.setObjectName(objectSummary.getKey());
            detail.setSize(objectSummary.getSize());
            detail.setType(objectSummary.getType());
            detail.setETag(objectSummary.getETag());
            detail.setLastModified(objectSummary.getLastModified());
            files.add(detail);

        }

         // 关闭OSSClient。
        ossClient.shutdown();

        return files;
    }

    /**
     * Oss文件下载到特定目录下
     * 注：可以使用浏览器通过文件访问URL下载文件
     * @param objectName 文件在alioss中放置的相对位置，为不包含Bucket名称在内的Object完整路径，例如testfolder/exampleobject.txt。
     * @param filePath 文件下载到本地的路劲，如 D:/localpath/examplefile.txt
     */
    public void fileDownload(String objectName,String filePath){
        //获取阿里云存储相关常量
        String endPoint = aliOssProperties.getEndpoint();
        String accessKeyId = aliOssProperties.getKeyid();
        String accessKeySecret = aliOssProperties.getKeysecret();
        String bucketName = aliOssProperties.getBucketname();

        // objectName填写不包含Bucket名称在内的Object完整路径，例如testfolder/exampleobject.txt。

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
        // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(filePath));

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /**
     * 删除存放在OSS服务器中的文件
     * @param objectName 文件在alioss中放置的相对位置，为不包含Bucket名称在内的Object完整路径，例如testfolder/exampleobject.txt。
     */
    public void fileDelete(String objectName){
        //获取阿里云存储相关常量
        String endPoint = aliOssProperties.getEndpoint();
        String accessKeyId = aliOssProperties.getKeyid();
        String accessKeySecret = aliOssProperties.getKeysecret();
        String bucketName = aliOssProperties.getBucketname();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        // 删除文件或目录。如果要删除目录，目录必须为空。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
