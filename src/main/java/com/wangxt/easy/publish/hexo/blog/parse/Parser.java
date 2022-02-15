package com.wangxt.easy.publish.hexo.blog.parse;

import java.io.InputStream;
import java.util.List;

public interface Parser {

    List<String> parse();

    void unParse(List<String> list);
}
