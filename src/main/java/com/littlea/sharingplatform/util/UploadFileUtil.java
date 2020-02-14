package com.littlea.sharingplatform.util;

import com.littlea.sharingplatform.bo.FileMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 上传文件工具类
 * @author linxinran
 * @date 2019/7/31
 */
public class UploadFileUtil {

    /**
     * 上传文件
     * @param file 要上传的文件
     * @param folderPath 文件夹相对位置
     * @param folderRealPath 文件夹绝对位置
     * @param type 要上传的文件类型（图片-img）
     * @return 文件信息(为空时表示文件格式错误)
     * @throws IOException IO异常
     */
    public static FileMessage uploadFile(MultipartFile file ,String folderPath, String folderRealPath, String type) throws IOException {
        //1、判断文件类型是否则正确
        if(!isTypeRight(file,type)){
            return null;
        }
        //2、获得文件信息
        FileMessage fileMessage = getFileMessage(file,folderPath,folderRealPath);
        //3、上传文件
        InputStream input = file.getInputStream();
        OutputStream out = new FileOutputStream(fileMessage.getFileRealPath());
        byte[] bs = new byte[1024];
        int len = -1;
        //从输入流读取并输出文件内容
        while((len=input.read(bs)) != -1) {
            out.write(bs,0,len);
        }
        out.close();
        input.close();
        return fileMessage;
    }


    /**
     * 获得文件信息
     * @param file 要上传的文件
     * @param folderPath 文件夹相对位置
     * @param folderRealPath 文件夹绝对位置
     * @return 文件信息
     */
    private static FileMessage getFileMessage(MultipartFile file, String folderPath, String folderRealPath){
        //1、获得上传时间
        String uploadTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //2、获得文件相对位置和绝对位置
        String fileName = file.getOriginalFilename();
        //获取后缀名
        String sname = null;
        if (fileName != null) {
            sname = fileName.substring(fileName.lastIndexOf("."));
        }
        //时间格式化格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //获取当前时间并作为时间戳
        String timeStamp = simpleDateFormat.format(new Date());
        //修改文件名.防止文件重名
        fileName = timeStamp+sname;
        //获得重名后的文件相对位置和绝对位置
        String filePath = folderPath + "/" + fileName;
        String fileRealPath = folderRealPath + "/" + fileName;
        return new FileMessage(uploadTime,fileRealPath,filePath);
    }


    /**
     * 判断文件类型是否正确
     *
     * @param file 要上传的文件
     * @param type 要上传的文件类型
     * @return boolean 文件类型是否正确
     */
    private static boolean isTypeRight(MultipartFile file, String type) throws IOException {
        //获得文件名
        String fileName=file.getOriginalFilename();
        //获取后缀名
        String sname = fileName.substring(fileName.lastIndexOf(".")+1);
        //检查两种类型文件：stl和图片
        String stl="stl";
        String img="img";
        //允许的图片格式
        String jpeg="jpeg";
        String jpg="jpg";
        String png="png";
        String gif="gif";
        String bmp="bmp";

        //判断文件是否为图片
        if(type.equals(img)){
            //通过后缀名判断
            if (!sname.equals(jpeg)&&!sname.equals(jpg)&&!sname.equals(png)&&!sname.equals(gif)&&!sname.equals(bmp)) {
                System.out.println("由后缀名知文件类型不是图片");
                return false;
            }
            //通过文件头判断
            InputStream is=file.getInputStream();
            String fType= getFileTypeByHead(is);
            if (!fType.equals(jpg)&&!fType.equals(png)&&!fType.equals(gif)&&!fType.equals(bmp)) {
                System.out.println("由文件头知文件类型不是图片");
                return false;
            }
        }
        return true;
    }

    /**
     * 根据文件头数据获得文件类型
     *
     * @param is 文件输入流
     * @return String 文件类型
     */
    private static String getFileTypeByHead(InputStream is) {
        //读取文件的前几个字节来判断文件格式
        byte[] b = new byte[4];
        try {
            is.read(b, 0, b.length);
            StringBuilder hs = new StringBuilder();
            String stmp;
            for (int i = 0; i < b.length; i++) {
                stmp = Integer.toHexString(b[i] & 0xFF).toUpperCase();
                if (stmp.length() == 1) {
                    hs.append("0").append(stmp);
                } else {
                    hs.append(stmp);
                }
            }
            String type = hs.toString().toUpperCase();
            String jpg="FFD8FF";
            String png="89504E47";
            String gif="47494638";
            String bmp="424D";
            if (type.contains(jpg)) {
                return "jpg";
            } else if (type.contains(png)) {
                return "png";
            } else if (type.contains(gif)) {
                return "gif";
            } else if (type.contains(bmp)) {
                return "bmp";
            } else {
                return "unknown";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
