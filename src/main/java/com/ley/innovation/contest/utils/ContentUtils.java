package com.ley.innovation.contest.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * content upload utility class by ftp server
 *
 * @author ley
 **/
@Slf4j
@Component
public class ContentUtils implements EnvironmentAware {

    /**
     * content file suffix
     **/
    private static final String CONTENT_FILE_SUFFIX = ".html";


    /**
     * get spring environment
     **/
    private Environment environment;


    private ContentUtils() {

    }


    /**
     * upload content html
     *
     * @param contentFileName content file name and this content file name is uuid
     * @param content         content
     * @return return content relative path and root path is environment.getProperty("innovation.file.path")
     **/
    public String uploadContentHTML(String contentFileName, String content) throws IOException {

        //上传文件内容本地路径
        String uploadPath = environment.getProperty("innovation.file.path") + FileDirectoryConstant.INNOVATION_CONTEST_HTML;
        String uploadContentFilePath = uploadPath + File.separator + contentFileName + CONTENT_FILE_SUFFIX;
        String contentPath = FileDirectoryConstant.INNOVATION_CONTEST_HTML + File.separator + contentFileName + CONTENT_FILE_SUFFIX;

        //判断存储路径是否存在,不存在重建
        File contentPathParent = new File(uploadPath);
        if (!contentPathParent.exists()) {
            contentPathParent.setWritable(true);
            contentPathParent.mkdirs();
        }

        //写到本地文件系统
        FileUtils.writeFile(content.getBytes("UTF-8"), uploadContentFilePath);

        //返回本地的内容路径
        return contentPath;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
