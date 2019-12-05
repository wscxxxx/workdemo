package com.work.demos.mybatis.spider.repository;

import com.alibaba.fastjson.JSONObject;
import com.bailian.servicetk.core.data.BaseRepository;
import com.work.demos.mybatis.generef.util.Tmp_aut;
import com.work.demos.mybatis.generef.web.Mapptmp;
import com.work.demos.mybatis.spider.entity.*;
import com.work.demos.mybatis.spider.manager.Generes;
import com.work.demos.mybatis.spider.manager.emp;
import com.work.demos.mybatis.spider.mapper.GeneinfoEntityMapper;
import com.work.demos.mybatis.spider.mapper.extension.GeneInfoNewExtensionMapper;
import com.work.demos.utils.Httpsend2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SpiderRepository extends BaseRepository<GeneinfoEntity> implements ISpiderRepository{

    private GeneInfoNewExtensionMapper mapper;
    private static final Log log = LogFactory.getLog(SpiderRepository.class);

    public SpiderRepository(GeneInfoNewExtensionMapper mapper) {
        super(mapper);
        this.mapper = mapper;
    }
     @Override
    public Mapptmp mutationdownland(Mapptmp flag, JSONObject para) {

        List<Integer> xx=new ArrayList<>();
        xx.contains(1);
        Long map_id= Long.valueOf(-1);
        Integer au_id=-1;
        Integer com_id=-1;
        Integer gene_id=  -1;
        if (flag!=null){
            map_id= Long.valueOf(flag.getMap_id());
            au_id=flag.getAut_id();
            com_id=flag.getCom_id();
            gene_id=flag.getGene_id();
        }else if (flag==null){
            map_id = mapper.getmappinglast();


            log.info(map_id==null);
            au_id = mapper.getauthorlast();

            com_id = mapper.getcompanylast();

            gene_id=mapper.getgenelast();
            if (gene_id==null||au_id == null || com_id == null || map_id == null) {
                map_id = Long.valueOf(1);
                au_id = 1;
                com_id = 1;
                gene_id=1;
            }else {
                map_id++;
                au_id++;
                com_id++;
                gene_id++;
            }
        }


         log.info(au_id + "哈哈" + com_id);
        List<InfoAuthorEntity> authorEntityList = new ArrayList<>();
        List<InfoCompanyEntity> companyEntityList = new ArrayList<>();
        List<GeneinfoEntity> infoEntities = new ArrayList<>();
        List<InfoMappingEntity> mappingEntityList = new ArrayList<>();


        List<Tmp_aut> alls = new ArrayList<>();

        String result = "";
        try {
            result = Httpsend2.postres("https://www.ncbi.nlm.nih.gov",
                    "/pubmed", para);
        } catch (Exception e) {
            do {
                try {
                    System.err.println("重试中。。。。。");
                    result = Httpsend2.postres("https://www.ncbi.nlm.nih.gov",
                            "/pubmed", para);
                } catch (Exception e1) {

                }
            }while (result!=null);
        }
        Document jsoup = Jsoup.parse(result);
         log.info(jsoup.select("h3[class=result_count left]").text());
        Elements elements = jsoup.select("div[class=rprt abstract]");
        log.info(elements.size());
        int a = 0;
        for (Element element : elements) {
            log.info("-----------------------------------");

            int code = ++a;
            log.info("第" + code + "个");

            String title = element.select("h1").text();
            String publish = element.select("div[class=cit]").text();
            String publishcom = publish.split("[.]")[0];
            String publishtime = publish.split("[;]")[0].split("[.]")[1].split(" ")[1];
            String publishmonth = "";
            if (!(publish.split("[;]")[0].split("[.]")[1].split(" ").length < 3)) {
                publishmonth = publish.split("[;]")[0].split("[.]")[1].split(" ")[2];

            }
            String bookcode = publish.split("[;]")[1].split("[.]")[0];
            String doi="";
            if (!(publish.split("doi[:] ").length<2))
                doi = publish.split("doi[:] ")[1];
            alls = getauthor(element);
            String author = element.select("div[class=auths]").text();
            String company = element.select("div[class=afflist]").select("dd").text();
            String prenum = element.select("div[class=aux]").select("dl[class=rprtid]").select("dd").get(0).text();
            log.info("\n所有信息" + alls);


            String abstr = element.select("div[class=abstr]").select("p").text();
            String keywords = element.select("div[class=keywords]").select("p").text();
            log.info("\n编号：" + prenum + "\n标题：" + title + "\n发布信息公司：" + publishcom + "\n年：" + publishtime
                    + "\n发布月：" + publishmonth + "\n书号：" + bookcode + "\ndoi：" + doi + "\n作者：" + author + "\n" + "机构：" + company +
                    "\n摘要：" + abstr + "\n关键字：" + keywords);
            GeneinfoEntity entity=mapper.geneidlist(Integer.parseInt(prenum));
            if (entity!=null){
                log.info(entity.getPreTitle()+"已有,跳过");
                continue;
            }
            for (Tmp_aut tmp_aut : alls) {
                InfoAuthorEntity authorEntity = new InfoAuthorEntity();
                InfoAuthorEntity aut=mapper. findautbyname( tmp_aut.getAuthor());

                authorEntity.setId(au_id);
                authorEntity.setName(tmp_aut.getAuthor());
                if (tmp_aut.getCompanys() == null) {
                    InfoCompanyEntity companyEntity = new InfoCompanyEntity();
                    InfoMappingEntity mappingEntity = new InfoMappingEntity();
                    companyEntity.setCompany(null);
                    companyEntity.setId(0);
                    mappingEntity.setCompanyId(0);
                    mappingEntity.setAuthorId(au_id);
                    mappingEntity.setId(Long.valueOf(map_id));
                    ++map_id;
                } else {
                    for (String rel_company : tmp_aut.getCompanys()) {
                        InfoCompanyEntity companyEntity = new InfoCompanyEntity();
                        InfoMappingEntity mappingEntity = new InfoMappingEntity();
                        mappingEntity.setAuthorId(au_id);
                        mappingEntity.setId(Long.valueOf(map_id));
                        mappingEntity.setProNum(Integer.valueOf(prenum));
                        mappingEntity.setCompanyId(com_id);
                        mappingEntityList.add(mappingEntity);
                        ++map_id;
                        if (aut!=null){
                            log.info(tmp_aut.getAuthor()+"已有，跳过");
                            continue;
                        }
                        log.info(tmp_aut.getAuthor()+"没有！！");
                        companyEntity.setId(com_id);
                        companyEntity.setCompany(rel_company);
                        companyEntityList.add(companyEntity);



                        ++com_id;
                    }
                }
                if (aut!=null){
                    log.info("已有，跳过");
                    continue;
                }
                ++au_id;
                authorEntityList.add(authorEntity);

            }
            GeneinfoEntity infoEntity = new GeneinfoEntity();
            infoEntity.setPreTitle(title);
            infoEntity.setKeyWords(keywords);
            infoEntity.setAbstractEn(abstr);
            infoEntity.setPreNum(Integer.valueOf(prenum));
            try {
                infoEntity.setPublishYear(Integer.valueOf(publishtime));
                infoEntity.setPublishMonth(getdata(publishmonth));

            }catch (NumberFormatException e){
                infoEntity.setPublishYear(getdata(publishmonth));

            }
            infoEntity.setPublishCompany(publishcom);
            infoEntity.setBookcode(bookcode);
            infoEntity.setDoi(doi);
            infoEntity.setId(gene_id);
            infoEntities.add(infoEntity);
            ++gene_id;
        }
         log.info("文献"+infoEntities.size());
         log.info("总共"+mappingEntityList.size());
         log.info("作者"+authorEntityList.size());
         log.info("机构"+companyEntityList.size());
        Mapptmp results=new Mapptmp();
        if (infoEntities.size()==0){
            return null;
        }


        int g_r=mapper.geneadd(infoEntities);
        int a_r= mapper.authoradd(authorEntityList);
        int c_r=mapper.companyadd(companyEntityList);
        int m_r=mapper.mappingadd(mappingEntityList);
        log.info(g_r+"\n"+a_r+"\n"+c_r+"\n"+m_r);


        results.setAut_id(au_id);
        results.setMap_id(map_id);
        results.setCom_id(com_id);
        results.setGene_id(gene_id);
         log.info("当前："+results+"基因："+gene_id);
         try {
             Thread.sleep(5*1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         return results;
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
                if (cnums.size()==0){
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
                    log.info("xxxxx");
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
    public Mapptmp testmutationdownland( ) {

        InfoAuthorEntity aut=mapper. findautbyname( "McLaugxhlin KA");
        log.info(aut);

         return null;
    }

    @Override
    public GeneinfoEntity getone() {

        return mapper.selectByPrimaryKey(2);
    }
}
