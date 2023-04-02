package com.wangxt.easy.publish.hexo.blog.convert.parse;

import java.io.File;

/**
 * 行解析器
 */
public abstract class AbsRowsParser<T extends RowsResult> implements Parser<RowsResult>{

    @Override
    public RowsResult parse(String path) {
        File file = new File(path);
        if(!file.exists()){
            return new RowsResult();
        }

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
}
