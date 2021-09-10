package com.sreturn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * Created by Intellij IDEA
 *
 * @author sunyunlin
 * @create 2021/9/7 19:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OssFileUploadResult {
    /**
     * 文件上传访问路径
     * 例如：https://education-return.oss-cn-shanghai.aliyuncs.com/1.jpg
     */
    private String fileUploadPath;
    /**
     * 文件存储相对路径，调用文件下载时可用
     * 例如 ：/files/2021/07/23/4d3d0be9-fc4a-4694-b871-8bf8d3f30999.jpg
     */
    private String objectName ;
}
