//package com.work.demos.mybatis.generef.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.bailian.mailuo_service.generef.domain.*;
//import com.bailian.mailuo_service.generef.service.Refservice;
//import com.bailian.mailuo_service.generef.util.Refcontent;
//import com.bailian.mailuo_service.generef.util.Tmp_aut;
//import com.bailian.mailuo_service.generef.web.Gene_info;
//import com.bailian.mailuo_service.generef.web.Mapptmp;
//import com.bailian.mailuo_service.generef.web.refdao;
//import com.bailian.mailuo_service.independent.services.MailuoService;
//import com.bailian.mailuo_service.independent.services.api.enums.TranslationTypeEnum;
//import com.bailian.mailuo_service.independent.services.api.msg.translation.GetZhTranslationReply;
//import com.bailian.mailuo_service.independent.services.api.msg.translation.GetZhTranslationRequest;
//import com.bailian.mailuo_service.protein.util.Httpsend2;
//import com.bailian.mailuo_service.util.BasicService;
//import com.bailian.mailuo_service.util.PageInfo;
//import com.bailian.servicetk.core.commons.utils.StringUtil;
//import com.querydsl.core.Tuple;
//import com.querydsl.core.types.Predicate;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.work.demos.mybatis.generef.web.Mapptmp;
//import net.sf.json.JSONArray;
//import org.apache.commons.collections.map.HashedMap;
//import org.apache.juli.logging.Log;
//import org.apache.juli.logging.LogFactory;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.sql.Timestamp;
//import java.util.*;
//
//
//@Service
//public class Refserviceimpl extends BasicService<TGeneInfoEntity> implements Refservice {
//
//
//    private final Log log = LogFactory.getLog(Refserviceimpl.class);
//
//    @Autowired
//    TGeneInfoRepository tGeneInfoRepository;
//    @Autowired
//    TReptileRepository tReptileRepository;
//
//    @Autowired
//    TGeneInfoAuthorRepository authorRepository;
//
//    @Autowired
//    TGeneInfoCompanyRepository companyRepository;
//    @Autowired
//    TGeneInfoMappingRepository mappingRepository;
//    @Autowired
//    TGeneInfoNewRepository tGeneInfoNewRepository;
//    @Autowired
//    MailuoService mailuoService;
//
//    private QTGeneInfoAuthorEntity qtAuthorEntity = QTGeneInfoAuthorEntity.tGeneInfoAuthorEntity;
//    private QTGeneInfoCompanyEntity qtCompanyEntity = QTGeneInfoCompanyEntity.tGeneInfoCompanyEntity;
//    private QTGeneInfoMappingEntity qtMappingEntity = QTGeneInfoMappingEntity.tGeneInfoMappingEntity;
//    private QTGeneInfoEntity infoEntity = QTGeneInfoEntity.tGeneInfoEntity;
//    private QTReptileGeneReferenceEntity reptileGeneReferenceEntity = QTReptileGeneReferenceEntity.tReptileGeneReferenceEntity;
//    private QTGeneInfoNewEntity infoNewEntity=QTGeneInfoNewEntity.tGeneInfoNewEntity;
//    @Override
//    public Tuple save(Tuple tuple) throws Exception {
//        return null;
//    }
//
//    @Override
//    public void remove(Tuple tuple) {
//
//    }
//
//    @Override
//    public Tuple modify(Tuple tuple) {
//        return null;
//    }
//
//    @Override
//    public Page<Tuple> findByPage(Tuple tuple, PageInfo pageInfo) {
//        return null;
//    }
//
//    @Override
//    public List<Tuple> findAll() {
//        return queryFactory.select(infoEntity.preNum, infoEntity.abstractEn, infoEntity.geneName).
//                from(infoEntity)
//                .fetch();
//    }
//
//    @Override
//    public Page<TGeneInfoEntity> findbypage(refdao PageInfo) {
//        QTGeneInfoEntity qtGeneInfoEntity = QTGeneInfoEntity.tGeneInfoEntity;
//
//
//        Predicate predicate = qtGeneInfoEntity.abstractEn.isNotNull();
//        ((BooleanExpression) predicate).and(qtGeneInfoEntity.abstractEn.notEqualsIgnoreCase("This gene has no abstract"));
//        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
//        PageRequest request = new PageRequest(PageInfo.getPageNum(), PageInfo.getPageSize(), sort);
//
////        JPAQuery query   = queryFactory.
////                select(infoEntity.preNum, infoEntity.abstractEn, infoEntity.geneName).
////                from(infoEntity).
////                limit(pageInfo.getPageSize())
////                .offset((pageInfo.getPageNum() - 1) * pageInfo.getPageSize()) ;
//        Page<TGeneInfoEntity> tGeneInfoEntityPage = tGeneInfoRepository.findAll(predicate, request);
//        return tGeneInfoEntityPage;
//    }
//
//    @Transactional
//    @Override
//    public List<TGeneInfoEntity> findAll(String type) {
//        List<TGeneInfoEntity> nums = queryFactory.
//                select(infoEntity)
//                .from(infoEntity)
//                .orderBy(infoEntity.geneName.asc())
//                .fetch();
//        Iterator<TGeneInfoEntity> it = nums.iterator();
//
//        while (it.hasNext()) {
//
//            TGeneInfoEntity entity = it.next();
//
//            queryFactory.update(reptileGeneReferenceEntity)
//                    .set(reptileGeneReferenceEntity.referenceNo, entity.getPreNum() + "")
//                    .set(reptileGeneReferenceEntity.latest, true)
//                    .where(reptileGeneReferenceEntity.url.eq("https://www.ncbi.nlm.nih.gov/pubmed/?term=" + entity.getPreNum()))
//                    .execute();
////            if (rep == null){
////                TReptileGeneReferenceEntity entity1=new TReptileGeneReferenceEntity();
////                entity1.setIntro(entity.getAbstractZh());
////                entity1.setIntroEn(entity.getAbstractEn());
////                entity1.setTitleEn(entity.getPerTitle());
////                entity1.setUrl("https://www.ncbi.nlm.nih.gov/pubmed/?term="+entity.getPreNum());
////                log.info("无记录---->"+entity1.toString());
////                tReptileRepository.save(entity1);
//
////            }
//        }
//        return nums;
//    }
//
//    @Transactional
//    @Override
//    public long updateall(String abstr_en) {
//        log.info("原文： " + abstr_en);
//        String zhres = "";
//        try {
//            GetZhTranslationRequest request = new GetZhTranslationRequest(abstr_en);
//            request.setTranslationTypeEnum(TranslationTypeEnum.GOOGLE);
//            GetZhTranslationReply reply = mailuoService.getZhTranslation(request);
//            zhres = reply.getData();
//        } catch (Exception e) {
//            log.error("翻译出错");
//        }
//
//        log.info("中文：" + zhres);
//
//        long a = queryFactory.update(infoEntity)
//                .set(infoEntity.abstractZh, zhres)
//                .where(infoEntity.abstractEn.eq(abstr_en))
//                .execute();
//        try {
//            Thread.sleep(60 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return a;
//    }
//
//    @Transactional
//    @Override
//    public Mapptmp mutationdownland(Mapptmp flag, JSONObject para) {
//
//        Integer map_id=-1;
//        Integer au_id=-1;
//        Integer com_id=-1;
//        Integer gene_id=  -1;
//        if (flag!=null){
//            map_id=flag.getMap_id();
//            au_id=flag.getAut_id();
//            com_id=flag.getCom_id();
//            gene_id=flag.getGene_id();
//        }else if (flag==null){
//            map_id = queryFactory.select(qtMappingEntity.id)
//                    .from(qtMappingEntity)
//                    .orderBy(qtMappingEntity.id.desc())
//                    .limit(1)
//                    .fetchOne();
//            map_id++;
//
//            au_id = queryFactory.select(qtAuthorEntity.id)
//                    .from(qtAuthorEntity)
//                    .orderBy(qtAuthorEntity.id.desc())
//                    .limit(1)
//                    .fetchOne();
//            au_id++;
//            com_id = queryFactory.select(qtCompanyEntity.id)
//                    .from(qtCompanyEntity)
//                    .orderBy(qtCompanyEntity.id.desc())
//                    .limit(1)
//                    .fetchOne();
//            com_id++;
//            gene_id=queryFactory.select(infoNewEntity.id)
//                    .from(infoNewEntity)
//                    .orderBy(infoNewEntity.id.desc())
//                    .limit(1)
//                    .fetchOne();
//            gene_id++;
//        }
//
//        if (au_id == null && com_id == null && map_id == null) {
//            map_id = 1;
//            au_id = 1;
//            com_id = 1;
//            gene_id=1;
//         }
//        System.out.println(au_id + "哈哈" + com_id);
//        List<TGeneInfoAuthorEntity> authorEntityList = new ArrayList<>();
//        List<TGeneInfoCompanyEntity> companyEntityList = new ArrayList<>();
//        List<TGeneInfoNewEntity> infoEntities = new ArrayList<>();
//        List<TGeneInfoMappingEntity> mappingEntityList = new ArrayList<>();
//
//
//        List<Tmp_aut> alls = new ArrayList<>();
//
//        String result = "";
//        try {
//            result = Httpsend2.postres("https://www.ncbi.nlm.nih.gov",
//                    "/pubmed", para);
//        } catch (Exception e) {
//             do {
//                 try {
//                     System.err.println("重试中。。。。。");
//                     result = Httpsend2.postres("https://www.ncbi.nlm.nih.gov",
//                             "/pubmed", para);
//                 } catch (Exception e1) {
//
//                 }
//             }while (result!=null);
//        }
//        Document jsoup = Jsoup.parse(result);
//        System.out.println(jsoup.select("h3[class=result_count left]").text());
//        Elements elements = jsoup.select("div[class=rprt abstract]");
//        log.info(elements.size());
//        int a = 0;
//        for (Element element : elements) {
//            System.out.println("-----------------------------------");
//
//            int code = ++a;
//            log.info("第" + code + "个");
//
//            String title = element.select("h1").text();
//            String publish = element.select("div[class=cit]").text();
//            String publishcom = publish.split("[.]")[0];
//            String publishtime = publish.split("[;]")[0].split("[.]")[1].split(" ")[1];
//            String publishmonth = "";
//            if (!(publish.split("[;]")[0].split("[.]")[1].split(" ").length < 3)) {
//                publishmonth = publish.split("[;]")[0].split("[.]")[1].split(" ")[2];
//
//            }
//            String bookcode = publish.split("[;]")[1].split("[.]")[0];
//            String doi="";
//            if (!(publish.split("doi[:] ").length<2))
//                doi = publish.split("doi[:] ")[1];
//            alls = getauthor(element);
//            String author = element.select("div[class=auths]").text();
//            String company = element.select("div[class=afflist]").select("dd").text();
//            String prenum = element.select("div[class=aux]").select("dl[class=rprtid]").select("dd").get(0).text();
//            log.info("\n所有信息" + alls);
//
//
//            String abstr = element.select("div[class=abstr]").select("p").text();
//            String keywords = element.select("div[class=keywords]").select("p").text();
//            log.info("\n编号：" + prenum + "\n标题：" + title + "\n发布信息公司：" + publishcom + "\n年：" + publishtime
//                    + "\n发布月：" + publishmonth + "\n书号：" + bookcode + "\ndoi：" + doi + "\n作者：" + author + "\n" + "机构：" + company +
//                    "\n摘要：" + abstr + "\n关键字：" + keywords);
//
//            for (Tmp_aut tmp_aut : alls) {
//                TGeneInfoAuthorEntity authorEntity = new TGeneInfoAuthorEntity();
//                authorEntity.setId(au_id);
//                authorEntity.setName(tmp_aut.getAuthor());
//                if (tmp_aut.getCompanys() == null) {
//                    TGeneInfoCompanyEntity companyEntity = new TGeneInfoCompanyEntity();
//                    TGeneInfoMappingEntity mappingEntity = new TGeneInfoMappingEntity();
//                    companyEntity.setCompany(null);
//                    companyEntity.setId(0);
//                    mappingEntity.setCompanyId(0);
//                    mappingEntity.setAuthorId(au_id);
//                    mappingEntity.setId(map_id);
//                     ++map_id;
//                } else {
//                    for (String rel_company : tmp_aut.getCompanys()) {
//                        TGeneInfoCompanyEntity companyEntity = new TGeneInfoCompanyEntity();
//
//                        companyEntity.setId(com_id);
//                        companyEntity.setCompany(rel_company);
//                        companyEntityList.add(companyEntity);
//
//                        TGeneInfoMappingEntity mappingEntity = new TGeneInfoMappingEntity();
//                        mappingEntity.setAuthorId(au_id);
//                        mappingEntity.setId(map_id);
//                        mappingEntity.setProNum(Integer.valueOf(prenum));
//                        mappingEntity.setCompanyId(com_id);
//                        mappingEntityList.add(mappingEntity);
//                        ++map_id;
//                        ++com_id;
//                    }
//                }
//                ++au_id;
//                authorEntityList.add(authorEntity);
//
//            }
//            TGeneInfoNewEntity infoEntity = new TGeneInfoNewEntity();
//            infoEntity.setPreTitle(title);
//            infoEntity.setKeyWords(keywords);
//            infoEntity.setAbstractEn(abstr);
//            infoEntity.setPreNum(Integer.valueOf(prenum));
//            infoEntity.setPublishYear(Integer.valueOf(publishtime));
//            infoEntity.setPublishMonth(getdata(publishmonth));
//            infoEntity.setPublishCompany(publishcom);
//            infoEntity.setBookcode(bookcode);
//            infoEntity.setDoi(doi);
//            infoEntity.setId(gene_id);
//            infoEntities.add(infoEntity);
//            ++gene_id;
//        }
//        System.out.println("文献"+infoEntities.size());
//        System.out.println("总共"+mappingEntityList.size());
//        System.out.println("作者"+authorEntityList.size());
//        System.out.println("机构"+companyEntityList.size());
//        Mapptmp results=new Mapptmp();
//        authorRepository.saveAll(authorEntityList);
//        companyRepository.saveAll(companyEntityList);
//        mappingRepository.saveAll(mappingEntityList);
//        tGeneInfoNewRepository.saveAll(infoEntities);
//
//        results.setAut_id(au_id);
//        results.setMap_id(map_id);
//        results.setCom_id(com_id);
//        results.setGene_id(gene_id);
//        System.out.println("当前："+results+"基因："+gene_id);
//        return results;
//    }
//
//    private List<Tmp_aut> getauthor(Element alls) {
//        Elements authors = alls.select("div[class=auths]").select("a");
//        List<Element> cnums = alls.select("div[class=auths]").select("sup");
//         Elements companys = alls.select("div[class=afflist]").select("dd");
//        if (companys.isEmpty()) {
//            List<Tmp_aut> result = new ArrayList<>();
//            for (int j = 0; j < authors.size(); j++) {
//                Tmp_aut tmp = new Tmp_aut();
//                tmp.setAuthor(authors.get(j).text());
//            }
//
//            return result;
//        }
//
//        int flag = 0;
//        List<Tmp_aut> result = new ArrayList<>();
//        for (int j = 0; j < authors.size(); j++) {
//            Tmp_aut tmp = new Tmp_aut();
//            tmp.setAuthor(authors.get(j).text());
//            flag = 0;
//            List<String> company_t = new ArrayList<>();
//            int xunhuan = cnums.size() + 1;
//            for (int i = 0; i < xunhuan; i++) {
//                if (flag == 1) {
//                    continue;
//                }
//                if (cnums.size()==0){
//                    company_t.add(null);
//                    continue;
//                }
//                if (cnums.get(0).text().equals("#"))
//                    continue;
//                 if (cnums.get(0).text().endsWith(",")) {
//                    company_t.add(companys.get(Integer.parseInt(cnums.get(0).text().substring(0, 1)) - 1).text());
//                    cnums.remove(0);
//                } else {
//                    company_t.add(companys.get(Integer.parseInt(cnums.get(0).text().substring(0, 1)) - 1).text());
//                    log.info("xxxxx");
//                    flag = 1;
//                    cnums.remove(0);
//
//                }
//                tmp.setCompanys(company_t);
//            }
//            result.add(tmp);
//
//        }
//
//
//        return result;
//    }
//
//    private int getdata(String data) {
//        int res = 0;
//        switch (data) {
//            case "Jan":
//                return 1;
//
//            case "Feb":
//                return 2;
//            case "Mar":
//                return 3;
//            case "Apr":
//                return 4;
//            case "May":
//                return 5;
//            case "Jun":
//                return 6;
//            case "Jul":
//                return 7;
//            case "Aug":
//                return 8;
//            case "Sep":
//                return 9;
//            case "Oct":
//                return 10;
//            case "Nov":
//                return 11;
//            case "Dec":
//                return 12;
//            default:
//                res = 0;
//        }
//        return res;
//    }
//
//    @Override
//    public List<String> findallabstr() {
//
//
//        return queryFactory.select(infoEntity.abstractEn)
//                .from(infoEntity)
//                .where(infoEntity.abstractZh.isNull())
//                .distinct()
//                .fetch();
//    }
//
//
//    @Transactional
//    @Override
//    public void dosomething() {
//        List<Integer> nums2 = queryFactory.select(infoEntity.preNum)
//                .from(infoEntity)
//                .fetch();
//        log.info(nums2.size());
//        HashSet<Integer> h = new HashSet<Integer>(nums2);
//        nums2.clear();
//        nums2.addAll(h);
//        log.info(nums2.size());
//        int a = 0;
//        for (Integer tmp : nums2) {
////            String abstr=queryFactory.select(infoEntity.abstractEn)
////                    .from(infoEntity)
////                    .where(infoEntity.preNum.eq(tmp),infoEntity.abstractEn.isNotNull(),infoEntity.abstractZh.isNotNull())
////                    .fetchFirst();
////            String abstr_zh=queryFactory.select(infoEntity.abstractZh)
////                    .from(infoEntity)
////                    .where(infoEntity.preNum.eq(tmp),infoEntity.abstractEn.isNotNull(),infoEntity.abstractZh.isNotNull())
////                    .fetchFirst();
//            String title = queryFactory.select(infoEntity.perTitle)
//                    .from(infoEntity)
//                    .where(infoEntity.preNum.eq(tmp), infoEntity.perTitle.isNotNull())
//                    .fetchFirst();
//            queryFactory.update(infoEntity)
//                    .set(infoEntity.perTitle, title)
//
//                    .where(infoEntity.preNum.eq(tmp), infoEntity.perTitle.isNull())
//                    .execute();
//            a++;
//
//        }
//        log.info("共更新" + a);
//
//        System.out.println(nums2.size());
//
//
//    }
//
//    @Override
//    public Tuple findById(Integer id) {
//
//        return null;
//    }
//
//    @Transactional
//    @Override
//    public List<TGeneAuthorEntity> update(List<Gene_info> gene_infos) throws Exception {
//        int count = 0;
//        List<TGeneAuthorEntity> result = new ArrayList<>();
//        Iterator num = gene_infos.iterator();
//        while (num.hasNext()) {
//            Gene_info tuple = (Gene_info) num.next();
//            Integer pronum = Integer.valueOf(tuple.getTid());
//            String info = tuple.getIntro();
//            if (!(null == info || "".equals(info))) {
//                System.out.println("已有，跳过");
//
//                continue;
//            } else {
//                System.out.println("等待15秒");
//                Thread.sleep(15 * 1000);
//
//            }
//
//            log.info(pronum + "---->" + info);
//            Map<String, String> para = new HashMap();
//            para.put("term", pronum + "");
//
//            String res = new Httpsend2().getsend("https://www.ncbi.nlm.nih.gov", "/pubmed", para);
//            Document doc = Jsoup.parse(res);
//            Elements title = doc.select("div[class=rprt_all]").select("div[class=rprt abstract]").select("h1");
//            Elements sbstr = doc.select("div[class=rprt abstract]").select("div[class=abstr]");
//            sbstr.select("h3").remove();
//            sbstr.select("p[class=copyright]").remove();
//
//            //            获取标题和摘要
//            String titles = title.text();
//            String abstrs = sbstr.text();
//            if (sbstr.eq(0).equals("RETRACTED ARTICLE")) {
//                abstrs = sbstr.eq(1).text();
//            }
//
//            String zhre = "";
//            if (StringUtil.isNUll(abstrs)) {
//                if (StringUtil.isNUll(titles)) {
//                    titles = "This gene has no title";
//                }
//                abstrs = "This gene has no abstract";
//                zhre = "该基因暂无摘要";
//                log.info("标题：" + titles);
//                log.info("原文：" + abstrs);
//                log.info("中文：" + zhre);
//                queryFactory.update(infoEntity).
//                        set(infoEntity.perTitle, titles).
//                        set(infoEntity.abstractEn, abstrs)
////                    .set(infoEntity.abstractZh, zhres)
//                        .where(infoEntity.preNum.eq(pronum))
//                        .execute();
//                return result;
//            }
//            Elements authors = doc.select("div[class=auths]");
//
//            Element author = authors.select("a").get(0);
//            System.out.println(author.text());
//            List<String> company = get_company(doc);
//            if (company == null) {
//
//            }
//            Elements keywords = doc.select("div[class=keywords]");
//
//            System.out.println("company: " + company);
//            log.info("标题：" + titles);
//            log.info("原文：" + abstrs);
//            log.info("作者：" + author.text());
//            log.info("所属公司信息：" + company);
//            log.info("关键字" + keywords.text());
//            Timestamp d = new Timestamp(System.currentTimeMillis());
//            TGeneAuthorEntity entity = new TGeneAuthorEntity();
//
//            entity.setPreTitle(titles);
//            entity.setPreNum(pronum);
//            entity.setKeyWords(keywords.text());
//            entity.setAuthor(author.text());
//            entity.setUpdateTime(d);
//            entity.setAuthorMes(JSONArray.fromObject(company).toString());
////            String model1 = "Comment in";
////            String model2 = "Indexed for MEDLINE";
////            String model3 = "Abstract ";
////            String model4 = "Author information";
////            Pattern p1 = Pattern.compile(model1);
////            Matcher m1 = p1.matcher(abstr);
////            if (m1.find())
////                abstr = result[5].replace("\n", " ");
////            Pattern p2 = Pattern.compile(model2);
////            Matcher m2 = p2.matcher(abstr);
////            if (m2.find()) {
////                abstr = "This resource has no abstract";
////                System.out.println(abstr);
////            }
////            Pattern p3 = Pattern.compile(model3);
////            Matcher m3 = p3.matcher(abstr);
////            if (m3.find()) {
////                abstr = abstr.substring(8, abstr.length());
////                System.out.println("截取摘要：" + abstr);
////
////            }
////            Pattern p4 = Pattern.compile(model4);
////            Matcher m4 = p4.matcher(abstr);
////            if (m4.find()) {
////                abstr = result[5].replace("\n", " ");
////            }
////
////            GetZhTranslationRequest request = new GetZhTranslationRequest(abstr);
////            request.setTranslationTypeEnum(TranslationTypeEnum.GOOGLE);
////            GetZhTranslationReply reply = mailuoService.getZhTranslation(request);
////            String zhres = reply.getData();
////            log.info("中文：" + zhres);
//
//            result.add(entity);
//            queryFactory.update(infoEntity).
//                    set(infoEntity.perTitle, titles).
//                    set(infoEntity.abstractEn, abstrs)
////                    .set(infoEntity.abstractZh, zhres)
//                    .where(infoEntity.preNum.eq(pronum))
//                    .execute();
//
//        }
//        return result;
//    }
//
//    private List<String> get_company(Document doc) {
//        Elements authors = doc.select("div[class=auths]");
//
//        Elements auth_num = authors.select("sup");
//        System.out.println(auth_num.text());
//        if (auth_num.text().equals("")) {
//            return null;
//        }
//        String numbers = auth_num.text().replaceAll("# ", "").trim();
//        String[] fi = numbers.split(" ");
//        List<String> fi2 = new ArrayList();
//        for (String tmp : fi) {
//            if (tmp.length() == 1) {
//                fi2.add(tmp);
//                break;
//            }
//            do {
//                fi2.add(tmp);
//            }
//            while (0 == tmp.lastIndexOf(","));
//        }
//        if (fi2.size() == 0) {
//            return null;
//        }
//
//
//        Elements all = doc.select("div[class=afflist]");
//        Elements companies = all.select("dt");
//
//
//        String[] tmp = auth_num.text().replaceAll("# ", "").split(" ");
//        System.out.println(tmp.length);
//        List<String> fina = new ArrayList<>();
//        for (String tmp2 : tmp) {
//
//            fina.add(tmp2.substring(0, 1));
//            if (!tmp2.endsWith(","))
//                break;
//
//        }
//        List<String> company = new LinkedList<>();
//        for (Element c_tmp : companies) {
//            for (String tmp2 : fina) {
//                if (tmp2.equals(c_tmp.text())) {
//                    company.add(all.select("dd").get(Integer.parseInt(tmp2) - 1).text());
//                }
//
//            }
//        }
//
//        System.out.println(company.size() + "company: " + company);
//        return company;
//    }
//
//    @Override
//    public Map<String, String> save(List<Gene_info> list) {
//        Iterator it = list.iterator();
//        TGeneInfoEntity entity = new TGeneInfoEntity();
//
//        List<TGeneInfoEntity> infolist = new ArrayList<>();
//        Map<String, String> result = new HashedMap();
//        int a = 0;
//        while (it.hasNext()) {
////            if (a == 10)
////                break;
//            Gene_info Gene_info = (Gene_info) it.next();
//            entity.setPreNum(Integer.valueOf(Gene_info.getTid()));
//            entity.setGeneName(Gene_info.getName());
//            entity.setGeneInfo(Gene_info.getIntro());
//            entity.setInfoHash(Gene_info.hashCode());
//            try {
//                tGeneInfoRepository.save(entity);
//                System.out.println(entity.toString());
//                infolist = queryFactory.select(infoEntity)
//                        .from(infoEntity)
//                        .where(infoEntity.preNum.eq(Integer.valueOf(Gene_info.getTid())))
//                        .fetch();
//                if (infolist.size() > 0) {
//                    Iterator itor = infolist.iterator();
//
//                    result.put("infos", infolist.toString());
//
//
//                }
//                result.put("code", Refcontent.success + "");
//                result.put("data", "success");
////                result.put("info",infolist.toString());
////                System.out.println("第"+a+"个");
//                a++;
//            } catch (DataIntegrityViolationException e) {
//                log.error("有重复数据,基因名：" + Gene_info.getName() + "\thashcode:" + Gene_info.hashCode());
//
//            }
//
//
//        }
//
//        result.put("num", a + "");
//
//        return result;
//    }
//
//
//    @Transactional
//    @Override
//    public Map<String, String> saveone(Integer id) throws Exception {
//
//
//        Map<String, String> para = new HashMap();
//        para.put("term", id + "");
//
//        String res = new Httpsend2().getsend("https://www.ncbi.nlm.nih.gov", "/pubmed", para);
//        //            获取标题
//        Document doc = Jsoup.parse(res);
//        Elements title = doc.select("div[class=rprt_all]").select("div[class=rprt abstract]").select("h1");
//        Elements sbstr = doc.select("div[class=rprt abstract]").select("div[class=abstr]");
//        log.info("初始数据：------->" + sbstr.text());
//        sbstr.select("h3").remove();
//        sbstr.select("p[class=copyright]").remove();
//
//        String titles = title.text();
//        String abstrs = sbstr.text();
//        String zhre = "";
//        if (StringUtil.isNUll(abstrs)) {
//            if (StringUtil.isNUll(titles)) {
//                titles = "This gene has no title";
//            }
//            abstrs = "This gene has no abstract";
//            zhre = "该基因暂无摘要";
//            Map resmap = new HashedMap();
//            TGeneAuthorEntity entity = new TGeneAuthorEntity();
//            entity.setPreNum(id);
//            resmap.put("id", 0);
//            resmap.put("title", titles);
//            resmap.put("abstr", abstrs);
//            resmap.put("abstr_zh", zhre);
//            resmap.put("data", entity);
//            return resmap;
//        } else {
//            GetZhTranslationRequest request = new GetZhTranslationRequest(sbstr.text());
//            request.setTranslationTypeEnum(TranslationTypeEnum.GOOGLE);
//            GetZhTranslationReply reply = mailuoService.getZhTranslation(request);
//            zhre = reply.getData();
//        }
//        log.info("标题：" + titles);
//
//        log.info("原文：" + sbstr.text());
//        Elements authors = doc.select("div[class=auths]");
//
//        Element author = authors.select("a").get(0);
//        System.out.println(author.text());
//        List<String> company = get_company(doc);
//        if (company == null) {
//
//        }
//        Elements keywords = doc.select("div[class=keywords]");
//
//        System.out.println("company: " + company);
//        log.info("标题：" + titles);
//        log.info("原文：" + abstrs);
//        log.info("作者：" + author.text());
//        log.info("所属公司信息：" + company);
//        log.info("关键字" + keywords.text());
//        Timestamp d = new Timestamp(System.currentTimeMillis());
//        TGeneAuthorEntity entity = new TGeneAuthorEntity();
//
//        entity.setPreTitle(titles);
//        entity.setPreNum(id);
//        entity.setKeyWords(keywords.text());
//        entity.setAuthor(author.text());
//        entity.setUpdateTime(d);
//        entity.setAuthorMes(JSONArray.fromObject(company).toString());
//
//
//        log.info("中文：" + zhre);
//        queryFactory.update(infoEntity).
//                set(infoEntity.perTitle, titles).
//                set(infoEntity.abstractEn, abstrs)
//                .set(infoEntity.abstractZh, zhre)
//                .where(infoEntity.preNum.eq(id))
//                .execute();
//        Map resmap = new HashedMap();
//        resmap.put("id", id);
//        resmap.put("title", titles);
//        resmap.put("abstr", abstrs);
//        resmap.put("data", entity);
//        resmap.put("abstr_zh", zhre);
//        return resmap;
//    }
//
//
//}
