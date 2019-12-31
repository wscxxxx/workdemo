package com.work.demos.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.util.*;

public class Getproxy {

    public Map<String,String> getters(){
        Map param=new LinkedHashMap();
//        http://http.tiqu.alicdns.com/getip3?num=5&type=2&regions=&city=0&mr=1&gm=4&pro=0&pack=71250&sb=0&cs=1&yys=0&pb=4&port=11&lb=1&ys=1&ts=1
//        http://http.tiqu.alicdns.com/getip3?regions=&city=0&mr=1&gm=4&pro=0&pack=71250&sb=0&cs=1&yys=0&pb=4&port=11&lb=1&ys=1&ts=1
//        http://webapi.http.zhimacangku.com/getip?num=1&
//
// type=2&pro=&city=0&yys=0&port=11&time=2&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=
        param.put("num","1");
        param.put("type","2");
        param.put("pro","");
        param.put("city","0");
        param.put("yys","0");
        param.put("port","11");
        param.put("time","2");
//        param.put("pack","71250");
        param.put("ts","0");
        param.put("ys","0");
        param.put("cs","0");
        param.put("lb","1");
        param.put("sb","0");
        param.put("pb","4");
        param.put("mr","1");
        param.put("regions","");
         String result = null;
        try {
            result = new Httpsend2().getsend("http://http.tiqu.alicdns.com","/getip",param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int a = 0;
        System.out.println(result);
        JSONObject jsonpObject= JSON.parseObject(result);
        Map<String,String> results=new HashMap<>();
        List<JSONObject> listObjectSec = JSONArray.parseObject( jsonpObject.getString("data"),List.class);
        Iterator<JSONObject> it=listObjectSec.iterator();
        while (it.hasNext()){
            a++;
            JSONObject ips= JSON.parseObject(it.next().toString());
            String https=ips.getString("ip")+"_"+ips.getString("port");
            System.out.println(https);
             results.put("ip",ips.getString("ip") );
            results.put("port", ips.getString("port"));

        }

return results;
    }
}
