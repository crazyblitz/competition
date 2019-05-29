package com.ley.innovation.contest.utils;

import com.ley.innovation.contest.typehandler.CloseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * file upload and download
 *
 * @author ley
 **/
@Slf4j
@Component
public class FileUploadUtils {

    /**
     * innovation file path
     **/
    @Value("${innovation.file.path}")
    private String uploadRootPath;


    private String downloadPath;

    /**
     * allow upload file suffix
     **/
    private final List<String> allowFiles = Arrays.asList(new String[]{
            "xls", "xlsx", "txt", "png", "doc", "docx", "pdf", "gif",
            "jpg", "jpeg", "bmp", "ppt"
    });


    /**
     * upload file and return file download path
     *
     * @param path upload relative path
     * @return return relative upload path
     * @see File#mkdirs()
     * @see File#mkdir()
     **/
    public String upload(MultipartFile file, String path) {
        String result = "";
        if (file == null) {
            return null;
        }
        String uploadFileName = "";
        String uploadRootPath = this.uploadRootPath;
        if (file.isEmpty()) {
            result = "文件未上传";
        } else {
            String _fileName = file.getOriginalFilename();
            //文件扩展名
            String suffix = _fileName.substring(_fileName.lastIndexOf(".") + 1);
            if (StringUtils.isNotBlank(suffix)) {
                if (isAllowUploadFile(suffix)) {

                    // 使用UUID生成文件名称
                    uploadFileName = UUID.randomUUID().toString() + "." + suffix;

                    String uploadPath = uploadRootPath + path;
                    log.info("开始上传文件,上传的文件名:{},上传的路径:{},新文件名:{}", _fileName, uploadPath, uploadFileName);

                    //判断上传路径是否存在
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        //使文件可以改,因为Tomcat发布服务后,文件的权限不一定是可以改的
                        uploadDir.setWritable(true);
                        //使用dirs是为了解决上传的路径中,如果有文件夹的没有创建,其会自动创建文件夹
                        uploadDir.mkdir();
                    }


                    //上传文件
                    File restore = new File(uploadPath, uploadFileName);

                    try {
                        //将文件存储到服务器
                        //设置文件可写
                        restore.setWritable(true);


                        //方式1:使用MultipartFile的transferTo方法进行文件的复制
//                        file.transferTo(restore);

                        //方式2:使用commons-io 2.5包下的copyInputStreamToFile进行文件的复制
                        FileUtils.copyInputStreamToFile(file.getInputStream(), restore);
                        //返回存储文件的相对位置(配置nginx服务访问)
                        result = path + File.separator + uploadFileName;

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    result = "文件格式不对,只能上传ppt、ptx、doc、docx、xls、xlsx、pdf、png、jpg、jpeg、gif、bmp格式.";
                }
            }
        }

        //返回新的文件相对路径
        return result;
    }


    /**
     * get file restore absolute path
     **/
    public String getFilePath(String path) {
        String fileAbsolutePath = this.uploadRootPath + path;
        return fileAbsolutePath;
    }


    /**
     * is allow upload file suffix
     **/
    private boolean isAllowUploadFile(String fileSuffix) {
        String fileSuffixLower = fileSuffix.toLowerCase();
        if (allowFiles.contains(fileSuffixLower)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * download file
     **/
    public String downloadFile(HttpServletResponse response, String fileName) {
        String fileNamePath = downloadPath + File.separator + fileName;
        File file = new File(fileNamePath);

        //如果文件不存在
        if (!file.exists()) {
            log.error("文件不存在!");
            return "文件不存在";
        }


        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //设置响应属性
            response.setContentType("application/x-download");
            response.addHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");

            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            //将文件输入流拷贝到响应输出流中
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();

            return "文件下载成功";
        } catch (Exception e) {
            log.error("e: {}", e.getMessage());
            return "文件下载出错";
        } finally {
            CloseUtils.close(inputStream, outputStream);
        }
    }


    /**
     * get upload root path
     **/
    public String getUploadRootPath() {
        return uploadRootPath;
    }
}
