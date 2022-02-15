package com.wangxt.easy.publish.hexo.blog.parse;

import com.wangxt.easy.publish.hexo.blog.pojo.ParamEnum;
import com.wangxt.easy.publish.hexo.blog.util.StringUtil;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author wangxt
 * @description md文件解析器
 * @dat
 * e 2022/2/13 10:35
 **/
@Service
public class MarkdownParser implements Parser{

    public static final String HEXO_BLOG_PATH = "source\\_posts";

    private Properties properties;

    public MarkdownParser(){
    }

    public MarkdownParser(Properties properties){
        this.properties = properties;
    }

    @Override
    public List<String> parse() {
        String mdFilePath = String.format("%s\\%s\\%s\\%s", properties.getProperty(ParamEnum.ROOT_PATH.getName()), properties.getProperty(ParamEnum.BLOG_PATH.getName()), HEXO_BLOG_PATH, properties.getProperty(ParamEnum.MD_FILE_NAME.getName()));

        File file = new File(mdFilePath);
        String name = file.getName();
        name = name.split("\\.")[0];
        properties.setProperty(ParamEnum.MD_FILE_NAME.getName(), name);

        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void unParse(List<String> list) {
        String mdFilePath = String.format("%s\\%s\\%s\\%s.md", properties.getProperty(ParamEnum.ROOT_PATH.getName()), properties.getProperty(ParamEnum.BLOG_PATH.getName()), HEXO_BLOG_PATH, properties.getProperty(ParamEnum.MD_FILE_NAME.getName()));

        File file = new File(mdFilePath);
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            for(String str : list){
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
