package com.work.demos;

import com.work.demos.mybatis.generef.util.FileReader;
import com.work.demos.mybatis.generef.util.Tmp_aut;
import com.work.demos.mybatis.spider.entity.GeneinfoEntity;
import com.work.demos.mybatis.spider.entity.InfoAuthorEntity;
import com.work.demos.mybatis.spider.entity.InfoCompanyEntity;
import com.work.demos.mybatis.spider.entity.InfoMappingEntity;
import com.work.demos.utils.JSUtil;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class test {
    private Log log = LogFactory.getLog(test.class);
    private int au_id;
    private int com_id;

    private static List<GeneinfoEntity> lists = new ArrayList<>();


    public int getAu_id() {
        setAu_id(++au_id);
        return au_id;
    }

    public void setAu_id(int au_id) {
        this.au_id = au_id;
    }

    public int getCom_id() {
        setCom_id(++com_id);
        return com_id;
    }

    public void setCom_id(int com_id) {
        this.com_id = com_id;
    }

    @Test
    public void test01() {
        String basePath = "/media/wangshichen/文件/webs/diagnosis/";
        String[] list = new File(basePath).list();
//        for (int i=2;i<list.length;i++){
//            System.out.println(list[i]);
//        }
        for (int i = 0; i < list.length; i++) {
            setdata("/media/wangshichen/文件/webs/diagnosis/" + list[i]);
        }
        System.out.println(lists.size());

    }

    private Boolean setdata(String path) {
        System.out.println("我是 " + path + "");

        List<InfoAuthorEntity> tauthorEntityList = new CopyOnWriteArrayList<>();
        List<InfoCompanyEntity> tcompanyEntityList = new CopyOnWriteArrayList<>();
        List<GeneinfoEntity> tinfoEntities = new CopyOnWriteArrayList<>();
        List<InfoMappingEntity> tmappingEntityList = new CopyOnWriteArrayList<>();
        FileReader fileRer = new FileReader();
        String result = "";

        result = fileRer.readFileByChars(path);

        //TODO
        Document jsoup = Jsoup.parse(result);

        System.out.println("-----" + jsoup.select("h3[class=result_count left]").text());
        Elements elements = jsoup.select("div[class=rprt abstract]");
        System.out.println(elements.size());


        int a = 0;
        List<Tmp_aut> alls = new ArrayList<>();

        for (Element element : elements) {
            System.out.println("-----------------------------------");

            int code = ++a;
            System.out.println("第" + code + "个");

            String title = element.select("h1").text();
            String publish = element.select("div[class=cit]").text();
            String publishtime ="";
            String publishcom ="";
            String publishmonth ="";
            String bookcode = "";
            String doi="";
            if (!publish.equals("")) {
                publish=element.select("p[class=aff_inline_book]").text();
                System.out.println(publish);

            }else {
              publishcom = publish.split("[.]")[0];


            if (!publish.split("[;]")[0].split("[.]")[1].equals(")")){
                publishtime=publish.split("[;]")[0].split("[.]")[1].split(" ")[1];

            }else {
                publishtime=publish.split("[;]")[0].split("[.]")[2].split(" ")[1];

            }


            if (!(publish.split("[;]")[0].split("[.]")[1].split(" ").length < 3)) {
                publishmonth = publish.split("[;]")[0].split("[.]")[1].split(" ")[2];

            }
            System.out.println("\n" + title);

            if (!(publish.split("[;]").length < 2)) {
                bookcode = publish.split("[;]")[1].split("[.]")[0];
            }

              doi = element.select("dl[class=rprtid]").select("dd").select("a").text();
//            if (!(publish.split("doi[:] ").length<2))
//                doi = publish.split("doi[:] ")[1];
            }
            alls = getauthor(element);
            String author = element.select("div[class=auths]").text();
            String company = element.select("div[class=afflist]").select("dd").text();
            String prenum = element.select("div[class=aux]").select("dl[class=rprtid]").select("dd").get(0).text();
            System.out.println("\n所有信息" + alls);


            String abstr = element.select("div[class=abstr]").select("p").text();
            String keywords = element.select("div[class=keywords]").select("p").text();
            System.out.println("\n编号：" + prenum + "\n标题：" + title + "\n发布信息公司：" + publishcom + "\n年：" + publishtime
                    + "\n发布月：" + publishmonth + "\n书号：" + bookcode + "\ndoi：" + doi + "\n作者：" + author + "\n" + "机构：" + company +
                    "\n摘要：" + abstr + "\n关键字：" + keywords);
//            GeneinfoEntity entity=mapper.geneidlist(Integer.parseInt(prenum));

//            if (entity!=null){
//                log.info(entity.getPreTitle()+"已经有了------------------------");
//                log.info(au_id + "哈哈" + com_id+"gene_id" );
//
//
//                File file2 = new File("/media/wangshichen/文件/webs/others/第"+page+"页");
//                if (file2.exists()) {
//                    file2.delete();
//                }
//                continue;
//            }
            for (Tmp_aut tmp_aut : alls) {
                InfoAuthorEntity authorEntity = new InfoAuthorEntity();
//                InfoAuthorEntity aut=mapper. findautbyname( tmp_aut.getAuthor());
                int au_id_tm = getAu_id();
                authorEntity.setId(au_id_tm);
                authorEntity.setName(tmp_aut.getAuthor());
                log.info(authorEntity.getId() + "---------------+" + au_id_tm + "+++++++++++++++++++++++" + authorEntity.getName());
                tauthorEntityList.add(authorEntity);


                if (tmp_aut.getCompanys() == null) {
                    InfoCompanyEntity companyEntity = new InfoCompanyEntity();
                    InfoMappingEntity mappingEntity = new InfoMappingEntity();
                    companyEntity.setCompany(null);
                    companyEntity.setId(null);
                    mappingEntity.setCompanyId(0);
                    mappingEntity.setAuthor(tmp_aut.getAuthor());
                    mappingEntity.setProNum(Integer.valueOf(prenum));
                    tmappingEntityList.add(mappingEntity);

                } else {
                    for (String rel_company : tmp_aut.getCompanys()) {

                        int com_id_tm = getCom_id();
                        InfoCompanyEntity companyEntity = new InfoCompanyEntity();
                        InfoMappingEntity mappingEntity = new InfoMappingEntity();
                        mappingEntity.setAuthor(tmp_aut.getAuthor());

                        mappingEntity.setProNum(Integer.valueOf(prenum));
                        mappingEntity.setCompanyId(com_id_tm);
                        tmappingEntityList.add(mappingEntity);

//                        if (aut!=null){
////                            log.info(tmp_aut.getAuthor()+"已有，跳过");
//                            continue;
//                        }
//                        log.info(tmp_aut.getAuthor()+"没有！！");
                        companyEntity.setId(com_id_tm);
                        companyEntity.setCompany(rel_company);
                        tcompanyEntityList.add(companyEntity);


                    }
                }
//                if (aut!=null){
////                    log.info("已有，跳过");
//                    continue;
//                }


            }
            GeneinfoEntity infoEntity = new GeneinfoEntity();
            infoEntity.setPreTitle(title);
            infoEntity.setKeyWords(keywords);
            infoEntity.setAbstractEn(abstr);
            infoEntity.setPreNum(Integer.valueOf(prenum));
            try {
                infoEntity.setPublishYear(Integer.valueOf(publishtime));
                infoEntity.setPublishMonth(getdata(publishmonth));

            } catch (NumberFormatException e) {
                infoEntity.setPublishYear(getdata(publishmonth));

            }

            infoEntity.setPublishCompany(publishcom);
            infoEntity.setBookcode(bookcode);
            infoEntity.setDoi(doi);
            System.out.println(infoEntity.toString());
            tinfoEntities.add(infoEntity);


        }
        lists.addAll(tinfoEntities);
        File file = new File(path);
        if (file.exists()) {
            log.info("删除文件");
//            file.delete();
        }


        return true;
    }

    private List<Tmp_aut> getauthor(Element alls) {
        Elements authors = alls.select("div[class=auths]").select("a");
        List<Element> cnums = alls.select("div[class=auths]").select("sup");
        Elements companys = alls.select("div[class=afflist]").select("dd");


        if (companys.isEmpty()) {

            List<Tmp_aut> result = new ArrayList<>();
            for (int j = 0; j < authors.size(); j++) {
                Tmp_aut tmp = new Tmp_aut();
                tmp.setAuthor(authors.get(j).text());
                result.add(tmp);
            }
            System.out.println(result);
            return result;
        }

        int flag = 0;
        List<Tmp_aut> result = new ArrayList<>();
        for (int j = 0; j < authors.size(); j++) {
            Tmp_aut tmp = new Tmp_aut();
            tmp.setAuthor(authors.get(j).text());
            flag = 0;
            List<String> company_t = new ArrayList<>();
            int xunhuan = cnums.size() + 1;

            for (int i = 0; i < xunhuan; i++) {
                if (flag == 1) {
                    continue;
                }
                if (cnums.size() == 0) {
                    company_t.add(null);
                    continue;
                }
                if (cnums.get(0).text().equals("#"))
                    continue;
                if (cnums.get(0).text().endsWith(",")) {
                    company_t.add(companys.get(Integer.parseInt(cnums.get(0).text().substring(0, 1)) - 1).text());
                    cnums.remove(0);
                } else {
                    company_t.add(companys.get(Integer.parseInt(cnums.get(0).text().substring(0, 1)) - 1).text());

                    flag = 1;
                    cnums.remove(0);

                }
                tmp.setCompanys(company_t);
            }
            result.add(tmp);

        }


        return result;
    }

    private int getdata(String data) {
        int res = 0;
        switch (data) {
            case "Jan":
                return 1;

            case "Feb":
                return 2;
            case "Mar":
                return 3;
            case "Apr":
                return 4;
            case "May":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Aug":
                return 8;
            case "Sep":
                return 9;
            case "Oct":
                return 10;
            case "Nov":
                return 11;
            case "Dec":
                return 12;
            default:
                res = 0;
        }
        return res;
    }

    @Test
    public void test02(){
         String publis="Staff Bull Easton Hosp (Easton, Pa.). 1948 Oct;1(3):15-27.";
        String publishtime = publis.split("[;]")[0].split("[.]")[1].split(" ")[1];
        System.out.println(publishtime);
    }
    @Test
    public void test03(){
        String xx="检测结果仅对本次送检样本负责，如有疑问，请于报告出具后三个工作日内咨询。<br>咨询电话：022-87190699。";
        System.out.println(xx.replaceAll("<br>","\n"));
    }


    @Test
    public void test04(){
        String cookie=new JSUtil().getcookie("mutation");
       int a= new JSUtil().getpage("mutation",cookie);
        System.out.println(a);
    }
}


