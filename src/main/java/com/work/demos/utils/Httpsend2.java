package com.work.demos.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
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
        /* 设置超时 */
        RequestConfig defaultRequestConfig = RequestConfig.custom().
                setSocketTimeout(30*1000).
                setConnectTimeout(30*1000).
                setConnectionRequestTimeout(30*1000).
                build();
        httpPost.setConfig(defaultRequestConfig);
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.addHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:70.0) Gecko/20100101 Firefox/70.0");
        httpPost.setHeader("Accept-Encoding","gzip, deflate");


        httpPost.setHeader("Cookie","_ga=GA1.2.548797481.1574410649; ncbi_sid=CE8C0576DD799941_0252SID; entrezSort=pubmed:PublicationDate; _gid=GA1.2.1081969427.1575733083; _gat_ncbiSg=1; _gat_dap=1; WebEnv=1xWZik6ea_z0zEh_a-ql93rn2lS9TktveuFwyqsewgGi-P04npehaBk_i_ryWXwytc_EVix6zm8rEKW-soq_3Ct6e-jBA1HObD5%40CE8C0576DD799941_0252SID; ncbi_pinger=N4IgDgTgpgbg+mAFgSwCYgFwgIIBYCsATPgGwBiAHACICiAQtiSdgIwAMHnn+A7AMxtcLQgDoAtnBY8QAGhABjADbJ5AawB2UAB4AXTKDaYQAM2SK4MAIaKZpxTqgQ4AcwhgZty/Kg7P3nXDyAPbqvsE2OgDuQYFBirIgLEZwiACuEpbqyH4+UKhwAM5QlhDyiAl8AJxGlWx8JAm4hlgsFGwU1XK4uEZKKhrael34RoZdDVjG1kWN0i0kYyC4FEZ8PJ1L1VgJ+M0giDo6YAUYAPSnkZci6vIARsjXimLXyIgizkEwp2Cpt2J5AGIdkltnIiEYABLpTIFEAAXzkqXUiiCllQml0+hAfD4Rimihmcj4PSwOggqSgFRWoOxWxAtR4hEae1awhGXRJICRKLRGKGSxGLR2ExAi14owSJBB2IoJGkCIUQTEYhCfKxTKwUFC0AAXhUjD8/nlGkZLLcCmSvPzBSBJabzZb5Py5vsdGJ4nJqSBCBwEnTDcp5JYdMgQqhg5S5CxpUko7isBQKPgiCso5zUMgCmBFJYAJ6BRCZZyRxIi6wexIu/GExJe6sln1GPBEUiUWgMJisLjd3gCISiCRSBKEaVaslQHUYQ3/VAYM0WiBWjAAOQA8suaMONSBLpFrncHsjnlk3h8YMO6SxcDwGkS9hQBBUY7hCLfsdu2uylvHRSJXyJYxAfAfyvV8dk5F8VjhOEgA==");
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
