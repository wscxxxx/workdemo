package com.work.demos;

import org.junit.Test;

import java.io.File;
import java.net.URI;

public class test2 {
    @Test
    public void test01(){
        String url="/media/wangshichen/文件/webs/prognosis/第129页";
        File tempFile =new File( url);

        System.out.println(tempFile.getParentFile());

    }
}
