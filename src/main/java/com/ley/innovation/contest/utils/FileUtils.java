package com.ley.innovation.contest.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * file utility
 *
 * @author ley
 **/
@Slf4j
public class FileUtils {


    /**
     * read file
     **/
    public static byte[] readFile(File file) {
        byte[] bytes = null;
        try {
            bytes = IOUtils.readFully(new FileInputStream(file), (int) file.length());
        } catch (Exception e) {
           log.error("e: {}",e.getMessage());
        }
        return bytes;
    }


    /**
     * read file
     *
     * @param fileName
     **/
    public static byte[] readFile(String fileName) {
        return readFile(new File(fileName));
    }

    /**
     * write file
     **/
    public static void writeFile(byte[] bytes, String outputFile) {
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(outputFile);
            os.write(bytes);
        } catch (Exception e) {
            log.error("e: {}",e.getMessage());
        } finally {
            IOUtils.closeQuietly(os);
        }

    }


    /**
     * delete file
     **/
    public static boolean delFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            log.info(fileName + " 文件不存在!");
            return true;
        } else {
            return file.isFile() ? org.apache.commons.io.FileUtils.deleteQuietly(file) : false;
        }
    }

    /**
     * delete file
     **/
    private static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            file.setWritable(true);
            if (file.delete()) {
                log.info("删除单个文件 " + fileName + " 成功!");
                return true;
            } else {
                log.info("删除单个文件 " + fileName + " 失败!");
                return false;
            }
        } else {
            log.info(fileName + " 文件不存在!");
            return true;
        }
    }


    /**
     * exist
     **/
    public static boolean exists(String path) {
        return (new File(path)).exists();
    }


    /**
     * get file extension
     **/
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }


    /**
     * get file name
     **/
    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}
