package com.sreturn.entity;

import com.aliyun.oss.model.OSSObjectSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description
 * Created by Intellij IDEA
 *
 * @author sunyunlin
 * @create 2021/9/7 20:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OssFileDetail {
    /**
     * 文件名
     */
    private String objectName;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 文件ETAG
     */
    private String eTag;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 最后修改时间
     */
    private Date lastModified;

}
