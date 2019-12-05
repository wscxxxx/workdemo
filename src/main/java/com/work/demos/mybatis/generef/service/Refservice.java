//package com.work.demos.mybatis.generef.service;
//
//import com.alibaba.fastjson.JSONObject;
//
//import com.querydsl.core.Tuple;
//import org.springframework.data.domain.Page;
//
//import java.util.List;
//import java.util.Map;
//
//public interface Refservice extends SimpleService<Tuple> {
//
//    Page<TGeneInfoEntity> findbypage(refdao PageInfo) throws Exception;
//
//    //更新全部列表  慎用
//    List<TGeneAuthorEntity> update(List<Gene_info> gene_infos) throws Exception;
//
//    //    根据json文件新增基因
//    Map<String, String> save(List<Gene_info> list);
//
//    //    单独更新一个基因文献，解析网页来更新
//    Map<String, String> saveone(Integer id) throws Exception;
//
//    //    更新另一个表reptile_gene_reference
//    List<TGeneInfoEntity> findAll(String type);
//
//    //    更新全部未翻译的摘要
//    long updateall(String abstr_en);
//    //key:mutation爬取全部的文献
//    Mapptmp mutationdownland(Mapptmp page, JSONObject para);
//    //    获取全部的英文摘要
//    List<String> findallabstr();
//
////  自定义接口
//    void dosomething();
//}
