package com.wangxt.easy.publish.hexo.blog.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil {

    public static byte[] inputStream2Byte(InputStream in) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count;
        while((count = in.read(data, 0, 1024)) != -1) {
            outStream.write(data, 0, count);
        }

        return outStream.toByteArray();
    }

    public static String inputStream2String(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"))) {
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
