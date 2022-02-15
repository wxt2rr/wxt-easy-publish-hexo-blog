package com.wangxt.easy.publish.hexo.blog.pojo;

public enum ParamEnum {
    ROOT_PATH("rp","项目根路径"),

    GITHUB_NAME("gn", "github账号"),

    BLOG_PATH("bp", "博客项目相对路经"),

    IMAGE_PATH("ip", "image项目相对路径"),

    GIT_PATH("gp", "git的sh.exe的安装路径"),

    MD_FILE_NAME("mfn", "md文件名称");

    private String name;
    private String desc;

    ParamEnum(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
