package com.work.demos.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Httpsend2 {
    private  static String head;
    private static URI url;


    private static Log logger = LogFactory.getLog(Httpsend2.class);


    public String getsend(String url, String path) throws Exception {
        return this.getrest(url, path, null);

    }

    public String getsend(String url, String path, Map<String, String> param) throws Exception {
        return this.getrest(url, path, param);

    }


    private String getrest(String url, String path, Map<String, String> param) throws Exception {
        String result = "";
        URIBuilder uri = new URIBuilder(new URI(url));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if ("https".equals(uri.getScheme()))
            this.head = "https";
        else
            this.head = "http";
        //设置https协议
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
        URIBuilder builder = new URIBuilder()
                .setScheme(head)
                .setPath(path)
                .setHost(uri.getHost());


        if (param != null) {
            for (Entry<String, String> entry : param.entrySet())
                builder.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }
        URI realuri = builder.build();
        HttpGet httpGet = new HttpGet(realuri);

        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        logger.info(realuri);
        //        HttpResponse response=new BasicHttpResponse(HttpVersion.HTTP_1_1,HttpStatus.SC_OK,"OK");
        CloseableHttpResponse httpResponse = null;
        do {

            try {
                //TODO
                httpResponse = httpClient.execute(httpGet);
                HttpEntity entity = httpResponse.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpResponse.close();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (httpResponse.getStatusLine().getStatusCode() == 429);
        return result;
    }

    public   static String postres(String url, String path, JSONObject para) throws Exception {
        URIBuilder uri = new URIBuilder(new URI(url));
        if ("https".equals(uri.getScheme()))
            head = "https";
        else
            head = "http";
        //设置https协议访问
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
        URIBuilder builder = new URIBuilder()
                .setScheme(head)
                .setPath(path)
                .setHost(uri.getHost());
        URI realuri = builder.build();
        logger.info(realuri);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(realuri);
        HttpHost proxy = new HttpHost("58.218.214.134", 8892);

        /* 设置超时 */
        RequestConfig defaultRequestConfig = RequestConfig.custom().
                setSocketTimeout(30*1000).setProxy(proxy).
                setConnectTimeout(30*1000).
                setConnectionRequestTimeout(30*1000).
                build();
        httpPost.setConfig(defaultRequestConfig);
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.addHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:70.0) Gecko/20100101 Firefox/70.0");
        httpPost.setHeader("Accept-Encoding","gzip, deflate");
        String vara="ncbi_sid=CE8A5BFCDEE191B1_1236SID; _ga=GA1.2.1917122324.1575885087; _gid=GA1.2.739043716.1575885087; entrezSort=pubmed:PublicationDate; WebEnv=1jSDD2mcCcDKgM0QfA3MvM0_Q4fYB8gQnlvzpurX2jZjByTRo2J0_RySBi-GWLsIzgYc2h4sYEXLlK0kWoGg-gSpUblcKis6y7UAX%40CE8A5BFCDEE191B1_1236SID; _gat_ncbiSg=1; _gat_dap=1; ncbi_pinger=N4IgDgTgpgbg+mAFgSwCYgFwgMIFEAcAQgCICMAnMcbgGIBM5ArOaQAzscf4CC7AbABY6AOgC2cUgHYQAGhABjADbJ5AawB2UAB4AXTKFaYQydcs1xlAZx0zFUAGY6ABKmSWwigIYBPOJag6OiYA5pYy0JYAroo6ln4BQeqhMvIA9uo6UBkpqYoy/jBZcGmKsiCkRhHROnCe8kHpcABGnhAyop4maRlZenIAzORGknSMZQKGWP0C+NJyAgJGSioa2n0gAmNYhvN8Rvaeiv7j0lj4+Hvz+EakQuNDWABWyMEAtOryTchgqWBgUBAyoxJiBEIEwJYMAB6KEAd3hwg+X0RilEiOQiGEwVSMChYEiTVEUFQAGIgRUsMQ3B4fEC6EYAAoApxgTzBKAYJx0QwAXzkkVMqU8qE0un0IH6/X2h2OA0WWB0EEiUDK/WuWFVDxAdAEkkuGxBjDo/TmG3lIAFiiFIrW4y25SBeywuqGckYpxAOxAfAp5Uk+DGfIUqVEonSor0GFA9KwvWgAC9VUZ8YTieMjJ4mtYIHV1vayk6QJns7myh6waJSnJ1dqvVqU8p5J4GupUM2VXJSL6Kp2pVM2AI+NdO+bXO4vL55IhPEkO+VC4cq37pUc56QawdV2VuUY8EQyJRqPQmCxOGeePwhGIJKa6L641B4xgU0TUBhi4rcxgAHIAeW/uDbjGIDwrCiKfMgKJouoGJYji25ahQO4DCCbD4F6/TduQyESsBA76gIfaesIdB8MIPYgIwRHrlRQLmsC0g8jyQA=";
        String biomarker="ncbi_sid=CE8A5BFCDEE191B1_1236SID; _ga=GA1.2.1917122324.1575885087; entrezSort=pubmed:PublicationDate; WebEnv=1TU4lwv0CEoaM3VlKAMNKjKhZG8v3Ud3UzHIESTqAbvrJnZX7-2w7CJhOli6btfq__WTlObCbO2_yDSlqGCk-5yzVF0T3ZWNGYdQg%40CE8A5BFCDEE191B1_1236SID; _gid=GA1.2.1655485549.1576467261; ncbi_pinger=N4IgDgTgpgbg+mAFgSwCYgFwgMIFEAcAIgJwBshATIQGIDspAjEwwAxvsfa1u0CsAdAFs4DYiAA0IAMYAbZFIDWAOygAPAC6ZQLTOACGAc2RK965AHsl4s+plQ4epagSGoEcVMvqoS9R/My4gDOsD5wnjISIAy6gnrGnr4+fri+0ABe1OYQglEAzGJYpHmktPhRACw6RXn4+DGSFRW6svLKapqNvLp5laS6AGZ6MiGVtLpMFDqN5VgVeQ0gFYUgelJmMFDiYK5wckoK4ioaUbzVIIjq6mBBGAD0dwDuz/xKUgBGyK8ygq/IiPwDOYYHcwABXd6CKCoADEpxiWFS6gyAGUAJ5BbyCCj8AAKSIyeIhAFloUTIdC4AAlKBBMEydRBXGOKAyclQ5zMgxuPGuU4UXQAOQ6AAIAHwgAC+kjBShk5j0qGOmgwoDyvSwQxGUHyzSwyLBOsktV0+RWxVoFHGjXOxWIeWmSz1IFl8sVysq3Tmp36WEdvHGfqijB63Wl0nMgkElg9qpAAqwyQy+V04Ip6Eauj070xEDWnRAXpAwazOeR+aigYu6kEkUks3jbCiKzTcikpgsTlMRuiCOiUQYGpApFI+BYtF6kgYztQyCCYBkejR4UQjm5A99qxkdeiVa1oynDf3PamujwRDIlBo9GYrA4964PAEwlEUQofaTUHSGDTHIw2dzfMMEFAB5QVcDfBMQGeR5Xg+L45V+JR/kBYE3xWURT2Nc5WDHfI+0wx08ig1gKn6RohxYfgrX4f0hwYfBeEnQtnV4ZYpUlIA=; _gat_dap=1; _gat_ncbiSg=1";
        httpPost.setHeader("Cookie",biomarker);

//        httpPost.setHeader("Cookie","ncbi_sid=CE8A5BFCDEE191B1_1236SID; WebEnv=14MLB98aVIL0vpDyhPmPCfbZIZ_UZ0Gj7REAtQp_rv01n56zHoxanEzjNIAS5WWA_oWXbT3C2DIu0IMrI9lmnDUr8VvffMwFCloQz%40CE8A5BFCDEE191B1_1236SID; _ga=GA1.2.1917122324.1575885087; _gid=GA1.2.739043716.1575885087; entrezSort=pubmed:PublicationDate; _gat_ncbiSg=1; _gat_dap=1; ncbi_pinger=N4IgDgTgpgbg+mAFgSwCYgFwgMIFEAc2AzEQKwDsAIrrgIwCC2AnLQAzsccBsATJawBZy+AHQBbOLXIgANCADGAG2TyA1gDsoADwAumUK0whk65ZrjKAzjpmKoAMx0ACVMkthFAQwCecS1B0dEwBzSxloSwBXRR1LPwCg9VCZeQB7dR0oDJTUxRl/GCy4NMVZEFojCOidOE95IPS4ACNPCBkxTxM0jKy9OSImI1JSCrkBQywhci4ygQEjJRUNbT6QAVIjQzGZrHtPRX9Z6SwWQbH8I1oiGbHBrAArZGCAWnV5JuQwVLAwKAgy0gTECIQJgSwYAD0EIA7rCRG8PvDFGJ4chECJgqkYBCwJEmmIoKgAMQAipYShuDw+AE8IwABT+TjAnmCUAwTh4hgAvnJIqZUp5UJpdPoQCQjHsDlAykR5lgdBBItL+hcsDK7uVSAIiLMgRQuBcxnKQHzFAKhStZhssKMQKQdiAtnbjo6ylwyWL8FxuXI0mIxOlhXoMKBaVhetAAF4yoy4/GE2ZGTxNawQOqra0gN1JlMK9NlF0gsSlOSqkCcsoauPKeSeBrqVB15XlD22q5GIg8Xj4Ji0uS0Y2udxeXzyRCeJLN2gO/Yl8ouyWHftlxfNitYPCEEgUah0RgsTiH3j8ISiCRSMo8D0RqCRjBxgmoDDJ1PpjAAOQA8u/cJewyBYWheF3mQJEUXUNEMSxS8NVoXsnSIIE2HwBDW3gmV/zYAQbjWHUsFYEQuxEW1SDw8p8FIgFjS1WkuS5IA");


//        httpPost.setHeader("Cookie","_ga=GA1.2.548797481.1574410649; ncbi_sid=CE8C0576DD799941_0252SID; entrezSort=pubmed:PublicationDate; _gid=GA1.2.1081969427.1575733083; _gat_ncbiSg=1; _gat_dap=1; WebEnv=1XE5gRiBopCxyNHZW6CzF5pMWEGBEu-aaEKROHISYEDVlAqV3PGVgbO18TqFk-FtXo90wYDRDqnGPl4aeXKKWu8C8FjPDCeB31iA6%40CE8C0576DD799941_0252SID; ncbi_pinger=N4IgDgTgpgbg+mAFgSwCYgFwgMIFEAcAggCIBsAYibngOzkBMAjAAyttv003OP6MB0AWziMArCAA0IAMYAbZNIDWAOygAPAC6ZQzTOACGAc2TL9G5AHtlE8xtlQ4+5agRGoECdKsaoyjZ4tZCQBnWF84L1lJEEY9QX0TLz9ff1w/aAAvcgsIQWiAZgBOPXz80lJiqQAWXSwyqtFdaqq9OQUVdS1q8SwmkCrSPQAzfVlQ6KqaPULWCfw9Gvx+UjF8/ArSenwZwerirH1pcxgoCTA3OHllRQlVTWjGvUQNDTBgjAB6D4B3X/5laQAI2Q/1kgn+yEQ/EMFhgHzAAFdAYIoKgAMQPWJYNIaTIAZQAnsEfIJ6PwAAo4zIUpEAWVRNORqLgACUoMEEbINMFyU4oLJGSiXLzDO4KW4HvQ9AA5ToAAgAfCAAL5SBHKWQWfSoO5aDCgUrDUbjKT5FpYXEIqAFeZYAr7EClbhTaq1R2kKr4ebNPTqzXa3UTHoxB6DXoPKbhqQrPSMQr5cSqmQWQSCKyB/UgKVYFKZAp6RFM9DVPSoCzSDl5KTB6JhkBlisIqsgSMgZ6CKJSW1Z2ZSB2F+TSMyWZxma1SRhYkMT/J6UT0RqkPqMc315DBMCyfQEiKIJyi6KMOujTsxVsjMbjmLdi8mnt6PBEMiUYjUXB0JjsL+cbi8ATCMRoiYPRcygDIMELIUMAbSsMGlAB5aVcCA7MQF+b5/iBEENXBZRIWhWEgIdFcaF2R03XwfI+nyKcV3oMj8lQ/BmHEapZ16fh6P4WJq3YmIqnoh5VzjWJlWVIA==");


        StringBuffer sb=new StringBuffer();

        for (String key:para.keySet()){
            sb.append(key+"="+para.getString(key));
            sb.append("&");

        }
        System.out.println(sb);
        StringEntity reqEntity= new StringEntity(sb.toString());

        httpPost.setEntity(reqEntity );


        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            result = EntityUtils.toString(respEntity, "UTF-8");
        } catch (Exception e) {
            System.err.println("发送Post请求失败");
        } finally {
            if (response != null) response.close();
            httpClient.close();
        }

          return result;
    }

    public static void main(String[] args) {
        try {
            Map para = new HashMap();
            para.put("term", "27839864");
            para.put("report", "abstract");
            para.put("format", "text");
//            String result=new Httpsend2().getsend( "cancer.sanger.ac.uk","/cosmic/gene/analysis","ANK3");
            String result = new Httpsend2().getsend("https://www.ncbi.nlm.nih.gov", "/pubmed", para);
            System.out.println(result);
            Document doc = Jsoup.parse(result);
            Elements title = doc.select("div[rprt abstract]").select("h1");
            System.out.println(title.text());
            Elements sbstr = doc.select("div[class=abstr]").select("div").select("p");
            System.out.println(sbstr.text());
//             logger.info(result );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
