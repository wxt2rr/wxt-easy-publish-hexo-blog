package com.wangxt.easy.publish.hexo.blog.push;

import com.wangxt.easy.publish.hexo.blog.pojo.ParamEnum;
import com.wangxt.easy.publish.hexo.blog.util.StreamUtil;

import java.util.Arrays;
import java.util.Properties;

public class Pusher {

    private Properties properties;

    public Pusher(){
    }

    public Pusher(Properties properties){
        this.properties = properties;
    }

    public void push(String path, String msg) throws Exception{
        String[] command = {String.format("%s %s\\src\\main\\resources\\sh\\push.sh %s %s %s", properties.getProperty(ParamEnum.GIT_PATH.getName()), System.getProperty("user.dir"), path, msg, "main")};
        String main = exeCommand(command);
        System.out.println(main);
    }

    public static String exeCommand(String... command) throws Exception{
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command);
        String successMsg = StreamUtil.inputStream2String(process.getInputStream());
        String errorMsg = StreamUtil.inputStream2String(process.getErrorStream());
        int proc = process.waitFor();
        if (proc == 0) {
            return "ok " + successMsg;
        } else {
            return "err " + errorMsg;
        }
    }

    public static void main(String[] args) {

    }
}
