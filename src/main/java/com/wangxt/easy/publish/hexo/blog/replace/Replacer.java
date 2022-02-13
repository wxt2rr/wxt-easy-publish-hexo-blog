package com.wangxt.easy.publish.hexo.blog.core;

import com.wangxt.easy.publish.hexo.blog.pojo.MdType;
import com.wangxt.easy.publish.hexo.blog.pojo.ParamEnum;
import com.wangxt.easy.publish.hexo.blog.util.StringUtil;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class Replacer {

    private Properties properties;

    public Replacer(){

    }

    public Replacer(Properties properties){
        this.properties = properties;
    }

    public void replace(List<String> list) throws Exception{
        List<String> result = new ArrayList<>();
        for (String line : list) {
            if (line.contains(MdType.IMAGE.getName())) {
                int i = line.indexOf(MdType.IMAGE.getName());
                // 解析出来的图片源地址
                String filePath = line.substring(i + MdType.IMAGE.getName().length() + 1, line.indexOf(")"));
                // 图片后缀名
                String mime = filePath.split("\\.")[1];
                // 临时图片名称
                String fileName = String.format("%s.%s", UUID.randomUUID(), mime);
                // 图片本地存储路径
                String newFilePath = String.format("%s\\%s\\%s", properties.getProperty(ParamEnum.ROOT_PATH.getName()), properties.getProperty(ParamEnum.IMAGE_PATH.getName()), properties.getProperty(ParamEnum.MD_FILE_NAME.getName()));
                // 图片文件路径
                String fileRootPath = String.format("%s\\%s", newFilePath, fileName);

                if (isWebSite(filePath)) {
                    try (InputStream is = new URL(filePath).openStream()) {
                        File file = new File(newFilePath);
                        boolean mkdirs = file.mkdirs();
                        copyFile(is, fileRootPath);
                        String githubUrl = generateUrl(properties.getProperty(ParamEnum.IMAGE_PATH.getName()), fileName);
                        line = String.format("![image](%s)", githubUrl);
                    }
                } else {
                    byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                    File file = new File(newFilePath);
                    boolean mkdirs = file.mkdirs();
                    copyFile(bytes, fileRootPath);
                    String githubUrl = generateUrl(properties.getProperty(ParamEnum.IMAGE_PATH.getName()), fileName);
                    line = String.format("![image](%s)", githubUrl);
                }
            }
            result.add(line);

            System.out.println(line);
        }
    }

    private String generateUrl(String rootPath, String imagePath){
        String githubName = properties.getProperty(ParamEnum.GITHUB_NAME.getName());
        // https://cdn.jsdelivr.net/gh/wxt1471520488/images@main/
        return String.format("https://cdn.jsdelivr.net/gh/%s/%s@main/%s", githubName, rootPath, imagePath);
    }

    private void copyFile(byte[] bytes, String newPath) throws Exception{
        FileCopyUtils.copy(bytes, new File(newPath));
    }

    private void copyFile(InputStream is, String newPath) throws Exception{
        FileCopyUtils.copy(inputStream2Byte(is), new File(newPath));
    }

    private boolean isWebSite(String str){
        return StringUtil.isNotBlank(str) && (str.startsWith("http") || str.startsWith("https"));
    }

    public static byte[] inputStream2Byte(InputStream in) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count;
        while((count = in.read(data, 0, 1024)) != -1) {
            outStream.write(data, 0, count);
        }

        return outStream.toByteArray();
    }
}
