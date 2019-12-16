package com.work.demos.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.work.demos.mybatis.generef.util.FileReader;
import com.work.demos.mybatis.generef.web.Mapptmp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;

public class Workers {
    private   void save(String path, String result) {
        File file1 = new File(path);


        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
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

    public void dowlandweb(int page) {
        String results = new FileReader().readFileByChars("/home/wangshichen/文档/work/biomarker.json");

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        CompletionService<String> cs = new ExecutorCompletionService<String>(threadPool);
        for (int i = page; i < 3341; i++) {
            int index = i;
            String fileName = "第" + index + "页";
            String filePath = "/media/wangshichen/文件/webs/biomarker/";
            cs.submit(() -> {
                System.out.println(fileName);
                // TODO
                String result1 = null;
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(results);
                jsonObject.put("EntrezSystem2.PEntrez.PubMed.Pubmed_ResultsPanel.Pubmed_Pager.CurrPage", index);
                jsonObject.put("EntrezSystem2.PEntrez.PubMed.Pubmed_Facets.FacetsUrlFrag", "filters=;humans");
                String result = "";
                try {

                    result = Httpsend2.postres("https://www.ncbi.nlm.nih.gov",
                            "/pubmed", jsonObject);
                    Document doc = Jsoup.parse(result);
                    System.out.println(doc.select("h3[class=result_count left]").text());
                    //指定本地文件夹存储文件

                     save(filePath + fileName, result);
                    System.out.println("写入成功");
                    System.out.println("保存的位置为：" + filePath + fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    do {
                        try {
                            System.err.println("重试中。。。。。");
                            result = Httpsend2.postres("https://www.ncbi.nlm.nih.gov",
                                    "/pubmed", jsonObject);
                        } catch (Exception e1) {

                        }
                    } while (result != null);
                }

                return result;
            });
        }
        threadPool.shutdown();


        for(int i =page ; i<3341;i++) {
            try {

                String rest=cs.take().get();


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("主线程");


    }


}
