package com.work.demos.utils;


import com.alibaba.fastjson.JSON;
import com.work.demos.mybatis.generef.util.FileReader;
import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;
import java.util.Set;

public class JSUtil {
    public String getcookie(String key) {
        String result = "";
        // 指定chrome driver的获取地址
        System.setProperty("webdriver.chrome.driver", "/usr/local/share/chromedriver");

        //设置chrome headless模式为true
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);

        //实例化webdriver的对象，以headless模式启动谷歌浏览器
        WebDriver driver = new ChromeDriver(options);

        //通过对象driver调用具体的get方法来打开网页
        driver.get("https://www.ncbi.nlm.nih.gov/pubmed/?term=" + key);

        //最大化浏览器窗口
        driver.manage().window().maximize();

        //打印网页标题
        System.out.println(driver.getTitle());

        Set<Cookie> cookies = driver.manage().getCookies();
//        System.out.println(String.format("Domain->\t name ->\t value ->\t expiry ->\t path"));
        System.out.println("正在获取cookie");
        for (Cookie c : cookies) {
//            System.out.println(String.format("%s -> %s -> %s -> %s -> %s", c.getDomain(), c.getName(), c.getValue(), c.getExpiry(), c.getPath()));
            if (c.getName().equals("expires")) {
                continue;
            }
            result += c.getName() + "=" + c.getValue() + ";";
        }
        cookies.remove("path");

//        result = StringUtils.join(cookies.toArray(), ";");

        //退出浏览器
        driver.quit();

        return result;
    }
    public int getpage(String genename,String cookie){
        String results = new FileReader().readFileByChars("/home/wangshichen/文档/work/postbody.json");

        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(results);
         jsonObject.put("term",genename);

        jsonObject.put("EntrezSystem2.PEntrez.PubMed.Pubmed_ResultsPanel.Pubmed_Pager.CurrPage", 1);
        jsonObject.put("EntrezSystem2.PEntrez.PubMed.Pubmed_Facets.FacetsUrlFrag", "filters=;humans");
        int page = 0;
        try {

         String   result = Httpsend2.postres("https://www.ncbi.nlm.nih.gov",
                    "/pubmed", jsonObject,null,cookie,1);
            Document doc = Jsoup.parse(result);
            System.out.println(doc.select("h3[class=page]").get(0).text());
            Thread.sleep(1000);
            page=Integer.parseInt(doc.select("h3[class=page]").get(0).text().split("of ")[1]);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }

}
