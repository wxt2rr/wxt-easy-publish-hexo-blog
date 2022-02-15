package com.wangxt.easy.publish.hexo.blog.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocalProperties {
    @Value("push.rp")
    private String rp;

    @Value("push.gn")
    private String gn;

    @Value("push.bp")
    private String bp;

    @Value("push.ip")
    private String ip;

    @Value("push.mfn")
    private String mfn;

    @Value("gp")
    private String gp;

    public String getGp() {
        return gp;
    }

    public String getRp() {
        return rp;
    }

    public String getGn() {
        return gn;
    }

    public String getBp() {
        return bp;
    }

    public String getIp() {
        return ip;
    }

    public String getMfn() {
        return mfn;
    }
}
