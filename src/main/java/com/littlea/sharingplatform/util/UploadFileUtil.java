package com.littlea.sharingplatform.util;

import com.littlea.sharingplatform.bo.FileMessage;
import com.littlea.sharingplatform.config.ConfigProperties;
import com.littlea.sharingplatform.entity.Tweet;
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
     * @param filePath 文件的绝对路径
     * @return boolean 上传是否成功
     */
    private static boolean upload(MultipartFile file, String filePath) throws IOException {
        //获得文件输入流
        InputStream input = file.getInputStream();
        //获得文件输出流
        OutputStream out = new FileOutputStream(filePath);
        byte[] bs = new byte[1024];
        int len = -1;
        //从输入流读取并输出文件内容
        while((len=input.read(bs)) != -1) {
            out.write(bs,0,len);
        }
        out.close();
        input.close();
        return true;
    }

    /**
     * 获得文件信息
     *
     * @param file 要上传的文件
     * @return 文件信息
     */
    private static FileMessage getFileMessage(MultipartFile file){
        //1、获得上传时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uploadTime = df.format(new Date());
        //2、获得文件相对位置和绝对位置
        String folderPath = ConfigProperties.folderPath;
        String folderRealPath = ConfigProperties.folderRealPath;
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
     * @param type 正在上传的文件类型
     * @return boolean 文件类型是否正确
     */
    private static boolean isTypeRight(MultipartFile file, String type) throws IOException {
        //获得文件名
        String fileName=file.getOriginalFilename();
        //获取后缀名
        String sname = fileName.substring(fileName.lastIndexOf(".")+1);
        //检查两种类型文件：stl和图片
        String stl="stl";
        String img="image";
        //允许的图片格式
        String jpeg="jpeg";
        String jpg="jpg";
        String png="png";
        String gif="gif";
        String bmp="bmp";

        //判断文件是否为stl
        if(type.equals(stl)) {
            //通过后缀名判断
            if(!sname.equals(stl)) {
                System.out.println("由后缀名知文件类型不是stl");
                return false;
            }
            //通过计算判断
            File f = null;
            f=File.createTempFile("tmp", null);
            file.transferTo(f);
            if(UploadFileUtil.isStl(f)==false) {
                System.out.println("由计算知文件类型不是stl");
                f.deleteOnExit();
                return false;
            }
        }
        //判断文件是否为图片
        else if(type.equals(img)){
            //通过后缀名判断
            if (!sname.equals(jpeg)&&!sname.equals(jpg)&&!sname.equals(png)&&!sname.equals(gif)&&!sname.equals(bmp)) {
                System.out.println("由后缀名知文件类型不是图片");
                return false;
            }
            //通过文件头判断
            InputStream is=file.getInputStream();
            String fType=getFtypeByHead(is);
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
    private static String getFtypeByHead(InputStream is) {
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


    /**
     * 判断是否stl格式
     *
     * @param file 文件
     * @return boolean true binary false ascii
     */
    private static boolean isStl(File file) {
        // 以二进制方式计算的文件大小;
        long expect = 0;
        // 一个三角片大小
        int faceSize = (32 / 8 * 3) + ((32 / 8 * 3) * 3) + (16 / 8);
        // 三角片数量
        int nFacetNum = 0;
        RandomAccessFile stl = null;
        try {
            stl = new RandomAccessFile(file, "r");
            stl.seek(80);
            byte[] arr = { 0, 0, 0, 0 };
            stl.read(arr);
            nFacetNum = stlFaceNum(arr);
            expect = 80 + (32 / 8) + (nFacetNum * faceSize);
            if (expect == stl.length()) {
                stl.close();
                return true;
            }
            //某些二进制文件的大小与预期的大小不同，检查低于ASCII的字符以确认是二进制的
            long fileLength = stl.length();
            stl.seek(0);
            for (long index = 0; index < fileLength; index++) {
                if (stl.readByte() < 0) {
                    stl.close();
                    return true;
                }
            }
            stl.close();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 用于正数,因为负数存储是补码 第81~84四个字节
     *
     * @param arr
     * @return
     */
    private static int stlFaceNum(byte[] arr) {
        int rightLength=4;
        if (arr != null && arr.length == rightLength) {
            // 防止低位转二进制后是变成负数
            int a = arr[0] & (0xFF);
            int b = (arr[1] << 8) & (0xFFFF);
            int c = (arr[2] << 16) & (0xFFFFFF);
            int d = (arr[3] << 24) & (0xFFFFFFFF);
            return a + b + c + d;
        }
        return -1;
    }

    /**
     * 上传文章图片并获得相关信息
     * @param cover
     * @param files
     * @param strings
     * @return
     * @throws IOException
     */
    public static Tweet uploadText(MultipartFile cover, MultipartFile[] files, String[] strings) throws IOException {
        //1、上传封面和内容图片
        String type = "image";
        //图片相对地址列表
        List<String> imagePathList = new ArrayList<>();
        //先上传内容图片，最后再上传封面
        MultipartFile imageFile = null;
        String imagePath = null;
        for(int i = 0;i < files.length+1;i++){
            //先上传内容图片
            if(i != files.length){
                imageFile = files[i];
            }
            //最后再上传封面
            else{
                imageFile = cover;
            }
            //判断是否为image文件
            if(!UploadFileUtil.isTypeRight(imageFile,type)) {
                return null;
            }
            //获得文件信息集合
            FileMessage fileMessage = getFileMessage(imageFile);
            //获得文件的相对路径
            imagePath = fileMessage.getFilePath();
            imagePathList.add(imagePath);
            //获得文件的绝对路径
            String imageRealPath = fileMessage.getFileRealPath();
            //上传
            UploadFileUtil.upload(imageFile,imageRealPath);
        }
        //最后一个文件相对路径即为封面地址
        String coverAddress = imagePath;
        //2、获得主体内容
        int i = 0;
        String content = "";
        if(strings != null){
            for(;i < strings.length;i++){
                content += strings[i];
                if(i < imagePathList.size()){
                    content += imagePathList.get(i);
                }
            }
        }else {
            for (String s : imagePathList) {
                content = content +" "+ s;
            }
        }
        //图片过多时
        for(;i< imagePathList.size();i++){
            content = content +" "+ imagePathList.get(i);
        }
        //3、获取发表时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String releaseDate = df.format(new Date());
        return new Tweet(coverAddress,content,releaseDate);
    }

}
