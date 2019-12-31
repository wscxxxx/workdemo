package com.work.demos.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
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
    private static String head;
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

        HttpHost proxy = new HttpHost("163.179.219.74", 4261);
        /* 设置超时 */
        RequestConfig defaultRequestConfig = RequestConfig.custom().
                setSocketTimeout(30 * 1000)
//                .setProxy(proxy)
                .setConnectTimeout(30 * 1000)
                .setConnectionRequestTimeout(30 * 1000)
                .build();
        if (param != null) {
            for (Entry<String, String> entry : param.entrySet())
                builder.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }
        URI realuri = builder.build();
        HttpGet httpGet = new HttpGet(realuri);
        httpGet.setConfig(defaultRequestConfig);

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

    public static String postres(String url, String path, JSONObject para,HttpHost proxy,String cookie,int a) throws Exception {

        return postres(url, path, para, proxy,cookie);
    }

    public static String postres(String url, String path, JSONObject para,  HttpHost proxy2,String cookie) throws Exception {
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
        /* 设置超时 */
        RequestConfig defaultRequestConfig = RequestConfig.custom().
                setSocketTimeout(20 * 1000)
                .setCookieSpec(CookieSpecs.STANDARD)
                .setProxy(proxy2)
                .setConnectTimeout(20 * 1000)
                .setConnectionRequestTimeout(20 * 1000)
                .build();
        String user_agent = new User_Agents().getagent();

        httpPost.setConfig(defaultRequestConfig);
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        logger.info(user_agent);
        httpPost.addHeader("User-Agent", user_agent);
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");



        httpPost.setHeader("Cookie", cookie);
        StringBuffer sb = new StringBuffer();

        for (String key : para.keySet()) {
            sb.append(key + "=" + para.getString(key));
            sb.append("&");

        }
        System.out.println(sb);
        StringEntity reqEntity = new StringEntity(sb.toString());

        httpPost.setEntity(reqEntity);


        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            result = EntityUtils.toString(respEntity, "UTF-8");


        } catch (Exception e) {
            throw e;
        } finally {
            if (response != null) response.close();
            httpClient.close();
        }
        Document jsoup = Jsoup.parse(result);
        Elements elements = jsoup.select("div[class=rslt]");
        System.out.println(elements.size());
//        System.out.println(result);
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
