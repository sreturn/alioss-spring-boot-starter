package com.sreturn.annotation;

import com.sreturn.autoconfigure.AliOssAutoConfiguration;
import com.sreturn.service.AliOssFileService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Description
 * Created by Intellij IDEA
 *
 * @author sunyunlin
 * @create 2021/9/7 16:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AliOssAutoConfiguration.class, AliOssFileService.class})
public @interface EnableAlioss {
}
