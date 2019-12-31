package com.work.demos.utils;

import java.util.Random;

public class User_Agents {
    private   String win7_chrom="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1";

    private   String win7_firefox="Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0";
    private   String win7_safari="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50";
    private   String win7_opera="Opera/9.80 (Windows NT 6.1; U; zh-cn) Presto/2.9.168 Version/11.50";
    private   String win7_ie9="Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; Tablet PC 2.0; .NET4.0E)";
    private   String win7_ie8="Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; InfoPath.3)";
    private   String winxp_ie8="Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB7.0)";
    private   String winxp_ie7="Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)";
    private   String winxp_ie6="Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)";
    private   String mac_firefox="Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1";
    private   String mac_safari="Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50";
    private   String mac_opera="User-Agent:Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11";


    private String[] list={
            this.win7_chrom,
            this.win7_firefox,
            this.win7_safari,
            this.win7_opera,
            this.win7_ie9,
            this.win7_ie8,
            this.winxp_ie8,
            this.winxp_ie7,
            this.winxp_ie6,
            this.mac_firefox,
            this.mac_safari,
            this.mac_opera
    };



    public   String  getagent(){
        return  list[  new Random().nextInt(list.length-1)];
    }
}
