package com.piesat.school.rest.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUploadUtils {

    /**
     * 默认大小 4G
     */
    public static final long DEFAULT_MAX_SIZE = 4 * 1024 * 1024 * 1024*1000;

    /**
     * 默认的图片的大小 5M
     */
    public static final long DEFAULT_MAX_PIC_SIZE = 5 * 1024 * 1024;


    /**
     * 默认的文件名最大长度 100
     */
    public static final int FILE_NAME_MAX = 100;

    /**
     * 默认上传的地址
     */
    private static String DEFAULT_BASE_FILE = "/apps/school/upload/";

    /**
     * 默认上传的图片地址
     */
    private static String DEFAULT_BASE_PIC = "pic";



    /**
     * 按照默认的配置上传文件
     *
     * @param file 文件
     * @return 文件名
     * @throws IOException
     */
    public static final String upload(MultipartFile file) throws IOException {

        try {
            return upload(FileUploadUtils.DEFAULT_BASE_FILE, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 按照默认的配置上传图片
     *
     *      @param file 文件
     *      @return 文件名
     *      @throws IOException
     */
    public static final String uploadPicture(MultipartFile file)throws IOException{
        try {
            return uploadPicture(FileUploadUtils.DEFAULT_BASE_PIC, file, MimeTypeUtils.IMAGE_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }
    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws Exception 如果超出最大大小
     * @throws IOException                    比如读写文件出错时
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws Exception {
        //合法性校验
        assertAllowed(file, allowedExtension);

        String fileName = encodingFileName(file);

        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc.getAbsoluteFile());
        return desc.getPath();
    }

    public static final String uploadPicture(String baseDir, MultipartFile file, String[] allowedExtension)
            throws Exception {
        //合法性校验
        assertAllowedPic(file, allowedExtension);

        String fileName = encodingFileName(file);

        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        return desc.getPath();
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir +fileName);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }


    /**
     * 对文件名特殊处理一下
     *
     * @param file 文件
     * @return
     */
    private static String encodingFileName(MultipartFile file) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datePath = simpleDateFormat.format(new Date());
        return datePath + "-" + UUID.randomUUID().toString() + "." + getExtension(file);
    }
    /**
     * 判断文件大小
     *
     * @param len
     *            文件size
     * @param size
     *            限制大小
     * @param unit
     *            限制单位（B,K,M,G）
     * @return
     */
    public static boolean checkFileSize(Long len, int size, String unit) {
//        long len = file.length();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    /**
     * 文件合法性校验
     *
     * @param file 上传的文件
     * @return
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws Exception {

        if (file.getOriginalFilename() != null) {
            int fileNamelength = file.getOriginalFilename().length();

            if (fileNamelength > FILE_NAME_MAX) {
                throw new Exception("文件名过长");
            }
        }

        long size = file.getSize();
        if(!checkFileSize(size,4,"G")){
            throw new Exception("文件过大");
        }

        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            throw new Exception("请上传指定类型的文件！");
        }

    }
    /**
     * 图片合法性校验
     *
     * @param file 上传的图片
     * @return
     */

    public static final void assertAllowedPic(MultipartFile file, String[] allowedExtension)
            throws Exception {

        if (file.getOriginalFilename() != null) {
            int fileNamelength = file.getOriginalFilename().length();

            if (fileNamelength > FILE_NAME_MAX) {
                throw new Exception("文件名过长");
            }
        }

        long size = file.getSize();
        if (size > DEFAULT_MAX_PIC_SIZE) {
            throw new Exception("文件过大");
        }

        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            throw new Exception("请上传指定类型的文件！");
        }

    }


    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = null;
        if (fileName == null) {
            return null;
        } else {
            int index = indexOfExtension(fileName);
            extension = index == -1 ? "" : fileName.substring(index + 1);
        }

        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }

    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int lastUnixPos = filename.lastIndexOf(47);
            int lastWindowsPos = filename.lastIndexOf(92);
            return Math.max(lastUnixPos, lastWindowsPos);
        }
    }

    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int extensionPos = filename.lastIndexOf(46);
            int lastSeparator = indexOfLastSeparator(filename);
            return lastSeparator > extensionPos ? -1 : extensionPos;
        }
    }


    public void setDEFAULT_BASE_FILE(String DEFAULT_BASE_FILE) {
        FileUploadUtils.DEFAULT_BASE_FILE = DEFAULT_BASE_FILE;
    }

    public String getDEFAULT_BASE_FILE() {
        return DEFAULT_BASE_FILE;
    }
}
