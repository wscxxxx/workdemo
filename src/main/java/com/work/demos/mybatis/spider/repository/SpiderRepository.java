package com.work.demos.mybatis.spider.repository;

import com.alibaba.fastjson.JSONArray;
import com.bailian.servicetk.core.data.BaseRepository;
import com.bailian.servicetk.core.data.IMapper;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.work.demos.mybatis.generef.util.FileReader;
import com.work.demos.mybatis.generef.util.Tmp_aut;
import com.work.demos.mybatis.generef.web.Mapptmp;
import com.work.demos.mybatis.spider.entity.*;
import com.work.demos.mybatis.spider.mapper.InfoAuthorEntityMapper;
import com.work.demos.mybatis.spider.mapper.extension.GeneInfoNewExtensionMapper;
import com.work.demos.mybatis.spider.mapper.extension.InfoAuthorExtensionMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
public class SpiderRepository extends BaseRepository implements ISpiderRepository {

    private GeneInfoNewExtensionMapper mapper;
    private static final Log log = LogFactory.getLog(SpiderRepository.class);


    private volatile static Integer com_id = -1;
    private static List<InfoCompanyEntity> companyEntityList = new CopyOnWriteArrayList<>();
    private static List<GeneinfoEntity> infoEntities = new CopyOnWriteArrayList<>();
    private static List<InfoMappingEntity> mappingEntityList = new CopyOnWriteArrayList<>();

    public SpiderRepository(GeneInfoNewExtensionMapper mapper) {
        super(mapper);
        this.mapper = mapper;
    }


    private synchronized Integer com_idadd() {
        SpiderRepository.com_id++;
        return SpiderRepository.com_id - 1;
    }

    @Override
    public Mapptmp mutationdownland(int start, int end, Mapptmp flag,String filepath ) {
        log.info(flag);

        if (flag != null) {


            com_id = flag.getCom_id();

//            log.info("哈哈" + com_id);
        } else if (flag == null) {


            SpiderRepository.com_id = mapper.getcompanylast();


            if (SpiderRepository.com_id == null) {


                SpiderRepository.com_id = 1;

            } else {


                com_idadd();

            }
        }

//        log.info("哈哈" + com_id + "gene_id");
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        CompletionService<Boolean> cs = new ExecutorCompletionService<Boolean>(threadPool);
        for (int i = 0; i < 10; i++) {
            int index = i;

            cs.submit(new Callable<Boolean>() {
                public Boolean call() throws Exception {

                    //TODO
//                    log.info("--------------" + index);
                    Boolean xxx = setdata(start + index, filepath);


                    return xxx;
                }
            });
        }
        threadPool.shutdown();

        // TODO

        List<Boolean> result = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            try {
                Boolean res = cs.take().get();
//                if (cs.take().get()!=null)
                result.add(res);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("主线程：\n" + result.size());

        log.info("文献" + infoEntities.size());
        log.info("总共" + mappingEntityList.size());
        log.info("机构" + companyEntityList.size());

        Mapptmp results = new Mapptmp();
        results.setCom_id(com_id);


        if (infoEntities.size() == 0) {
            log.info("当前：" + results);
            return null;
        }
//         log.info(au_id + "哈哈" + com_id+"gene_id"+gene_id+"map_id"+map_id);
        int a_r = 1;
        int c_r = 1;

        if (companyEntityList.size() != 0)
            c_r = mapper.companyadd(companyEntityList);

        Set<GeneinfoEntity> geneSet = new HashSet<>(infoEntities);
        infoEntities = new ArrayList<>(geneSet);

        try {
            int g_r = mapper.geneadd(infoEntities);

        }catch (Exception e){
            JSONArray jsonArray=new JSONArray(Collections.singletonList(infoEntities));
            log.info(jsonArray.toString());
            log.error("报错了");
            e.printStackTrace();
        }

        int m_r = mapper.mappingadd(mappingEntityList);
//        log.info(g_r + "\n" + a_r + "\n" + c_r + "\n" + m_r);


        log.info("当前：" + results+start);
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        infoEntities.clear();
        companyEntityList.clear();
        mappingEntityList.clear();


        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return results;
    }

    private Boolean setdata(int page, String filepath) {
//        log.info("我是第" + page + "页");
        filepath += "第" + page + "页";
        List<InfoAuthorEntity> tauthorEntityList = new CopyOnWriteArrayList<>();
        List<InfoCompanyEntity> tcompanyEntityList = new CopyOnWriteArrayList<>();
        List<GeneinfoEntity> tinfoEntities = new CopyOnWriteArrayList<>();
        List<InfoMappingEntity> tmappingEntityList = new CopyOnWriteArrayList<>();
        FileReader fileRer = new FileReader();
        String result = "";

        result = fileRer.readFileByChars(filepath);

        //TODO
        Document jsoup = Jsoup.parse(result);

//        log.info(jsoup.select("h3[class=result_count left]").text());
        Elements elements = jsoup.select("div[class=rprt abstract]");
//        log.info(elements.size());


        int a = 0;
        List<Tmp_aut> alls = new ArrayList<>();
        try {


        for (Element element : elements) {

            int code = ++a;
//            log.info("第" + code + "个");

            String title = element.select("h1").text();
            String publish = element.select("div[class=cit]").text();
            String publishcom = publish.split("[.]")[0];
            String publishtime ="";
            try {
                  publishtime = publish.split("[;]")[0].split("[.]")[1].split(" ")[1];

            }catch (Exception e){

                log.info(title);
                return false;
            }
            String publishmonth = "";
            if (!(publish.split("[;]")[0].split("[.]")[1].split(" ").length < 3)) {
                publishmonth = publish.split("[;]")[0].split("[.]")[1].split(" ")[2];

            }
            String bookcode = "";
            if (!(publish.split("[;]").length < 2)) {
                bookcode = publish.split("[;]")[1].split("[.]")[0];
            }
            String doi = element.select("dl[class=rprtid]").select("dd").select("a").text();

            alls = getauthor(element);

            String prenum = element.select("div[class=aux]").select("dl[class=rprtid]").select("dd").get(0).text();
//            log.info("\n所有信息" + alls);

            String abstr = element.select("div[class=abstr]").select("p").text();
            String keywords = element.select("div[class=keywords]").select("p").text();

            GeneinfoEntity entity = mapper.geneidlist(Integer.parseInt(prenum));

            if (entity != null) {
                log.info("\n------------------------" + entity.getPreTitle() + "已经有了");
//                log.info("哈哈" + com_id + "gene_id");


                File file2 = new File(filepath);
                if (file2.exists()) {
                    file2.delete();
                }

                continue;
            }
            for (Tmp_aut tmp_aut : alls) {



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

                        int com_id_tm = com_idadd();
                        InfoCompanyEntity companyEntity = new InfoCompanyEntity();
                        InfoMappingEntity mappingEntity = new InfoMappingEntity();
                        mappingEntity.setAuthor(tmp_aut.getAuthor());

                        mappingEntity.setProNum(Integer.valueOf(prenum));
                        mappingEntity.setCompanyId(com_id_tm);
                        tmappingEntityList.add(mappingEntity);

//
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
//            log.info(infoEntity.toString());
            tinfoEntities.add(infoEntity);

//            log.info("=====================" + infoEntities.size());
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        File file = new File(filepath);
        if (file.exists()) {
            log.info("删除文件");
            file.delete();
        }

        if (tinfoEntities.size()==0){
            log.info("一个都没有");
            return false;
        }
        infoEntities.addAll(tinfoEntities);
        companyEntityList.addAll(tcompanyEntityList);
        mappingEntityList.addAll(tmappingEntityList);

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

    @Override
    public Mapptmp testmutationdownland() {

        com_id = mapper.getcompanylast();
//        InfoAuthorEntity aut=mapper. findautbyname( "McLaugxhlin KA");
        log.info("哈哈" + com_id + "gene_id");
        return null;
    }

    @Override
    public Mapptmp addothers(Mapptmp flag) {
        if (flag != null) {

            com_id = flag.getCom_id();
            log.info("哈哈" + com_id);
        } else if (flag == null) {
            SpiderRepository.com_id = mapper.getcompanylast();
            if (SpiderRepository.com_id == null) {

                SpiderRepository.com_id = 1;
            } else {

                com_idadd();
            }
        }
        log.info("哈哈" + com_id + "gene_id");

        String basePath = "/media/wangshichen/文件/webs/";
        String[] list = new File(basePath).list();

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CompletionService<Boolean> cs = new ExecutorCompletionService<Boolean>(threadPool);
        for (int i = 3; i < list.length; i++) {
            int index = i;
            cs.submit(new Callable<Boolean>() {
                public Boolean call() throws Exception {

                    //TODO
                    log.info("--------------" + index);
                    setdatas(list[index]);
                    boolean xxx = true;

                    return xxx;
                }
            });
        }
        threadPool.shutdown();

        // TODO

        List<Boolean> result = new ArrayList<>();

        for (int i = 3; i < list.length; i++) {

            try {
                Boolean res = cs.take().get();
//                if (cs.take().get()!=null)
                result.add(res);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("主线程：\n" + result.size());
        log.info("文献" + infoEntities.size());
        log.info("总共" + mappingEntityList.size());
        log.info("机构" + companyEntityList.size());

        Mapptmp results = new Mapptmp();
        if (infoEntities.size() == 0) {

            return null;
        }


//         log.info(au_id + "哈哈" + com_id+"gene_id"+gene_id+"map_id"+map_id);
        int a_r = 1;
        int c_r = 1;

        if (companyEntityList.size() != 0)
            c_r = mapper.companyadd(companyEntityList);


        int g_r = mapper.geneadd(infoEntities);
        int m_r = mapper.mappingadd(mappingEntityList);
        log.info(g_r + "\n" + a_r + "\n" + c_r + "\n" + m_r);

        results.setCom_id(com_id);


        try {
            log.info("当前：" + results);
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        infoEntities.clear();
        companyEntityList.clear();
        mappingEntityList.clear();
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return results;
    }

    private Boolean setdatas(String filename) {
        log.info("我是 " + filename + "");

        List<InfoAuthorEntity> tauthorEntityList = new CopyOnWriteArrayList<>();
        List<InfoCompanyEntity> tcompanyEntityList = new CopyOnWriteArrayList<>();
        List<GeneinfoEntity> tinfoEntities = new CopyOnWriteArrayList<>();
        List<InfoMappingEntity> tmappingEntityList = new CopyOnWriteArrayList<>();
        FileReader fileRer = new FileReader();
        String result = "";

        result = fileRer.readFileByChars("/media/wangshichen/文件/webs/" + filename);

        //TODO
        Document jsoup = Jsoup.parse(result);

        log.info("-----" + jsoup.select("h3[class=result_count left]").text());
        Elements elements = jsoup.select("div[class=rprt abstract]");
        log.info(elements.size());


        int a = 0;
        List<Tmp_aut> alls = new ArrayList<>();

        for (Element element : elements) {
//            log.info("-----------------------------------");

            int code = ++a;
//            log.info("第" + code + "个");

            String title = element.select("h1").text();
            String publish = element.select("div[class=cit]").text();
            String publishcom = publish.split("[.]")[0];
            String publishtime = "";
            if (!publish.split("[;]")[0].split("[.]")[1].equals(")")) {
                publishtime = publish.split("[;]")[0].split("[.]")[1].split(" ")[1];
            } else {
                publishtime = publish.split("[;]")[0].split("[.]")[2].split(" ")[1];

            }
            String publishmonth = "";
            if (!(publish.split("[;]")[0].split("[.]")[1].split(" ").length < 3)) {
                publishmonth = publish.split("[;]")[0].split("[.]")[1].split(" ")[2];

            }
            String bookcode = "";
            if (!(publish.split("[;]").length < 2)) {
                bookcode = publish.split("[;]")[1].split("[.]")[0];
            }
            String doi = element.select("dl[class=rprtid]").select("dd").select("a").text();
//            if (!(publish.split("doi[:] ").length<2))
//                doi = publish.split("doi[:] ")[1];
            alls = getauthor(element);
            String author = element.select("div[class=auths]").text();
            String company = element.select("div[class=afflist]").select("dd").text();
            String prenum = element.select("div[class=aux]").select("dl[class=rprtid]").select("dd").get(0).text();
//            log.info("\n所有信息" + alls);


            String abstr = element.select("div[class=abstr]").select("p").text();
            String keywords = element.select("div[class=keywords]").select("p").text();
//            log.info( "\n编号：" + prenum + "\n标题：" + title + "\n发布信息公司：" + publishcom + "\n年：" + publishtime
//                    + "\n发布月：" + publishmonth + "\n书号：" + bookcode + "\ndoi：" + doi + "\n作者：" + author + "\n" + "机构：" + company +
//                    "\n摘要：" + abstr + "\n关键字：" + keywords);

//            查重
            GeneinfoEntity entity = mapper.geneidlist(Integer.parseInt(prenum));
            if (entity != null) {
                log.info(entity.getPreTitle() + "已经有了------------------------");
                log.info("哈哈" + com_id + "gene_id");
                File file2 = new File("/media/wangshichen/文件/webs/" + filename);
                if (file2.exists()) {
                    log.info("删除文件");
                    file2.delete();
                }
                continue;
            }
            for (Tmp_aut tmp_aut : alls) {
                //                InfoAuthorEntity aut=mapper. findautbyname( tmp_aut.getAuthor());


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
                        int com_id_tm = com_idadd();
                        InfoCompanyEntity companyEntity = new InfoCompanyEntity();
                        InfoMappingEntity mappingEntity = new InfoMappingEntity();
                        mappingEntity.setAuthor(tmp_aut.getAuthor());

                        mappingEntity.setProNum(Integer.valueOf(prenum));
                        mappingEntity.setCompanyId(com_id_tm);
                        tmappingEntityList.add(mappingEntity);
                        companyEntity.setId(com_id_tm);
                        companyEntity.setCompany(rel_company);
                        tcompanyEntityList.add(companyEntity);
                    }
                }
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

            tinfoEntities.add(infoEntity);


        }
        infoEntities.addAll(tinfoEntities);
        companyEntityList.addAll(tcompanyEntityList);
        mappingEntityList.addAll(tmappingEntityList);

        log.info(tauthorEntityList.size());

        File file = new File("/media/wangshichen/文件/webs/" + filename);
        if (file.exists()) {
            log.info("删除文件");
            file.delete();
        }


        return true;
    }

    @Override
    public void savetomysql() {

    }

    @Override
    public int update_map() {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
        GeneinfoEntity entity = mapper.getgenelast(new Retable("t_reference"));
        System.out.println(entity.getPreTitle());
        return 0;
    }


    @Override
    public GeneinfoEntity getone() {

        return mapper.selectByPrimaryKey(2);
    }
}
