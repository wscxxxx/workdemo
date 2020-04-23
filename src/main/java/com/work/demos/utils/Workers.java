package com.work.demos.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.work.demos.mybatis.generef.util.FileReader;
import com.work.demos.mybatis.generef.web.Mapptmp;
import io.swagger.models.auth.In;
import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

public class Workers {

    private  volatile  static        HttpHost proxy = new HttpHost("58.218.92.69",6210);

    private   void save(String path, String result) {
        File file1 = new File(path);
        String realpath=file1.getParent(); 
        if (!file1.exists()) {
            File tempfile=new File(realpath);
            tempfile.mkdirs();
        } else {
//                mes.delete();
            System.out.println("已存在");
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file1);
            fileWriter.write(result);
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
            System.err.println("写入失败");
            try {
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void dowlandweb(int curpage,String gene) {

//      第一部分，初始化基因的配置  先读取请求体
        String results = new FileReader().readFileByChars("/home/wangshichen/文档/work/postbody.json");
//        获取cookie
        String cookie=new JSUtil().getcookie(gene);
//        获取总页数
        int page=new JSUtil().getpage(gene,cookie);
        ExecutorService threadPool = Executors.newFixedThreadPool(15);
        CompletionService<String> cs = new ExecutorCompletionService<String>(threadPool);
        for (int i = curpage; i < page; i++) {
            int index = i;
            String fileName = "第" + index + "页";
            String filePath = "/media/wangshichen/文件/webs/"+gene+"/";
            cs.submit(() -> {
                System.out.println(fileName+"共："+page+"页");
                File file1 = new File(filePath + fileName);
                if (file1.exists()){
                     return fileName+"已有，跳过";
                }
                // TODO

                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(results);
                jsonObject.put("EntrezSystem2.PEntrez.PubMed.Pubmed_ResultsPanel.Pubmed_Pager.CurrPage", index);
                jsonObject.put("EntrezSystem2.PEntrez.PubMed.Pubmed_Facets.FacetsUrlFrag", "filters=;humans");
                String result = "";
                try {

                    result = Httpsend2.postres("https://www.ncbi.nlm.nih.gov",
                            "/pubmed", jsonObject,null,cookie,0);
                    Document doc = Jsoup.parse(result);
                    System.out.println(doc.select("h3[class=result_count left]").text());
                    //指定本地文件夹存储文件

                     save(filePath + fileName, result);
                    System.out.println("写入成功");
                    System.out.println("保存的位置为：" + filePath + fileName);
                } catch (Exception e) {
                    wait();
                    Thread.sleep(60*1000);
                    Getproxy getproxy=new Getproxy();
                    Map<String,String> proxys=getproxy.getters();
                    HttpHost proxy2=new HttpHost(proxys.get("ip"), Integer.parseInt(proxys.get("port")));
                    proxy=proxy2;
                    e.printStackTrace();
                    do {
                        try {
                            System.err.println("重试中。。。。。");
                            result = Httpsend2.postres("https://www.ncbi.nlm.nih.gov",
                                    "/pubmed", jsonObject,proxy,cookie,0);
                        } catch (Exception e1) {

                        }
                    } while (result != null);
                }

                return result.substring(0,20);
            });
        }
        threadPool.shutdown();


        for(int i =curpage ; i<page;i++) {
            try {

                String rest=cs.take().get();
                System.out.println(rest);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("主线程");


    }


}
