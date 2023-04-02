package com.wangxt.easy.publish.hexo.blog.convert.replace;

import com.wangxt.easy.publish.hexo.blog.pojo.MdType;
import com.wangxt.easy.publish.hexo.blog.pojo.ParamEnum;
import com.wangxt.easy.publish.hexo.blog.util.StreamUtil;
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

    public List<String> replace(List<String> list) throws Exception{
        List<String> result = new ArrayList<>();
        for (String line : list) {
            if (line.contains(MdType.IMAGE.getName())) {
                int i = line.indexOf(MdType.IMAGE.getName());
                // 解析出来的图片源地址
                String filePath = line.substring(i + MdType.IMAGE.getName().length() + 1, line.indexOf(")"));
                // 图片后缀名
                String[] filePathArray = filePath.split("\\.");
                String mime = filePathArray[filePathArray.length - 1];
                mime = checkMime(mime);
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
        }

        return result;
    }

    private String generateUrl(String rootPath, String imagePath){
        String githubName = properties.getProperty(ParamEnum.GITHUB_NAME.getName());
        // https://cdn.jsdelivr.net/gh/wxt1471520488/images@main/hexo/e6ffa7ea-a5c3-40e6-bc2c-dcd09c774dae.png
        String[] split = rootPath.split("\\\\");
        return String.format("https://cdn.jsdelivr.net/gh/%s/%s@main/%s/%s/%s", githubName, split[0], split[1], properties.getProperty(ParamEnum.MD_FILE_NAME.getName()), imagePath).replaceAll("\\\\", "/");
    }

    private void copyFile(byte[] bytes, String newPath) throws Exception{
        FileCopyUtils.copy(bytes, new File(newPath));
    }

    private void copyFile(InputStream is, String newPath) throws Exception{
        FileCopyUtils.copy(StreamUtil.inputStream2Byte(is), new File(newPath));
    }

    private boolean isWebSite(String str){
        return StringUtil.isNotBlank(str) && (str.startsWith("http") || str.startsWith("https"));
    }

    private String checkMime(String mime){
        return "png".equals(mime) || "jpg".equals(mime) || "jepg".equals(mime) || "gif".equals(mime) || "svg".equals(mime) ? mime : "png";
    }
}
