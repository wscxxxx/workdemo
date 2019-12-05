package com.work.demos.mybatis.generef.util;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@SuppressWarnings("deprecation")
@Service
public class FileReader {
    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public String readFileByChars(String fileName) {
        String result="";
         Reader reader = null;
         try {

            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            StringBuffer results = new StringBuffer();
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
//                    System.out.println(  tempchars );
                    results.append(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                             results.append(tempchars[i]);
                        }
                    }
                }

            }
            result=results.toString();


         } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return result;
    }



}
