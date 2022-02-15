package com.wangxt.easy.publish.hexo.blog.pojo;

public enum MdType {
    IMAGE("[image]");

    private String name;

    MdType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
