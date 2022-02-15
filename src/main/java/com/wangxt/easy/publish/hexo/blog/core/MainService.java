package com.wangxt.easy.publish.hexo.blog.core;

import com.wangxt.easy.publish.hexo.blog.parse.MarkdownParser;
import com.wangxt.easy.publish.hexo.blog.parse.Parser;
import com.wangxt.easy.publish.hexo.blog.pojo.GlobalConfig;
import com.wangxt.easy.publish.hexo.blog.pojo.LocalProperties;
import com.wangxt.easy.publish.hexo.blog.pojo.ParamEnum;
import com.wangxt.easy.publish.hexo.blog.push.Pusher;
import com.wangxt.easy.publish.hexo.blog.replace.Replacer;
import com.wangxt.easy.publish.hexo.blog.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class MainService {
    
    @Autowired
    private LocalProperties localProperties;

    public void handle(String... param) throws Exception{
        // 获取参数
        Properties properties = getProperties(param);
        if(properties.isEmpty()){
            return;
        }

        // 解析md
        Parser parser = new MarkdownParser(properties);
        List<String> parse = parser.parse();
        if(null == parse || parse.isEmpty()){
            return;
        }

        // 替换图片地址
        Replacer replacer = new Replacer(properties);
        List<String> list = replacer.replace(parse);

        // 保存替换之后的md文件
        parser.unParse(list);

        // 推送图片到远程仓库
        Pusher pusher = new Pusher(properties);
        pusher.push(String.format("%s\\\\%s", properties.getProperty(ParamEnum.ROOT_PATH.getName()), "images"), properties.getProperty(ParamEnum.MD_FILE_NAME.getName()) + "OfCommitImageForBlog");

        // 推送博客到远程仓库
        pusher.push(String.format("%s\\\\%s", properties.getProperty(ParamEnum.ROOT_PATH.getName()), properties.getProperty(ParamEnum.BLOG_PATH.getName())), properties.getProperty(ParamEnum.MD_FILE_NAME.getName()) + "OfCommitForBlog");
    }

    private Properties getProperties(String... param){
        Properties properties = new Properties();

        ApplicationContext context = GlobalConfig.getContext();
        Environment environment = context.getEnvironment();
        String rp = environment.getProperty("push.rp");
        String gn = environment.getProperty("push.gn");
        String bp = environment.getProperty("push.bp");
        String ip = environment.getProperty("push.ip");
        String mfn = environment.getProperty("push.mfn");
        String gp = environment.getProperty("push.gp");

        // 填充配置文件中的配置
        properties.setProperty(ParamEnum.ROOT_PATH.getName(), rp);
        properties.setProperty(ParamEnum.GITHUB_NAME.getName(), gn);
        properties.setProperty(ParamEnum.BLOG_PATH.getName(), bp);
        properties.setProperty(ParamEnum.IMAGE_PATH.getName(), ip);
        properties.setProperty(ParamEnum.MD_FILE_NAME.getName(), mfn);
        properties.setProperty(ParamEnum.GIT_PATH.getName(), gp);

        // 如果传了参数就覆盖配置文件中的配置
        for(String p : param){
            if(StringUtil.isNotBlank(p)){
                String[] split = p.split("=");
                properties.setProperty(split[0], split[1]);
            }
        }

        return properties;
    }
}
