//package com.work.demos.mybatis.generef.web;
//
//import com.alibaba.fastjson.JSON;
//import com.bailian.mailuo_service.generef.domain.QTGeneInfoEntity;
//import com.bailian.mailuo_service.generef.domain.TGeneAuthorEntity;
//import com.bailian.mailuo_service.generef.domain.TGeneInfoEntity;
//import com.bailian.mailuo_service.generef.service.AuthorService;
//import com.bailian.mailuo_service.generef.service.Refservice;
//import com.bailian.mailuo_service.generef.util.*;
//import com.bailian.mailuo_service.util.PageInfo;
//import com.bailian.mailuo_service.util.Result;
//import com.bailian.mailuo_service.util.ResultConstant;
//import com.querydsl.core.Tuple;
//import com.work.demos.mybatis.generef.util.FileReader;
//import io.swagger.annotations.Api;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.apache.commons.collections.map.HashedMap;
//import org.apache.juli.logging.Log;
//import org.apache.juli.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.util.*;
//
//@RestController
//@RequestMapping(value = "gene_ref")
//@Api(tags = "基因简介及参考文献生成、入库")
//@CrossOrigin
//public class Refweb {
//
//    @Autowired
//    Refservice refservice;
//
//
//
//    @Autowired
//    FileReader fileReader;
//
//
//    private QTGeneInfoEntity infoEntity = QTGeneInfoEntity.tGeneInfoEntity;
//
//    private final Log log = LogFactory.getLog(Refweb.class);
//
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public Result refandgene(@RequestParam("file") MultipartFile file) {
//
//        if (file.isEmpty()) {
//            return new Result(ResultConstant.FAIL_STATUE, "文件为空");
//        }
//        //获取文件名
//        String fileName = file.getOriginalFilename();
//        //获取文件后缀名
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));
//        System.out.println("上传文件啦");
//        //重新生成文件名
//        fileName = UUID.randomUUID() + suffixName;
//        //指定本地文件夹存储文件
//        File file1 = null;
//        String filePath = "/bailian/mailuo/";
//        try {
//            file1 = new File(filePath + fileName);
//            //将图片保存到static文件夹里
//            file.transferTo(file1);
//            log.info("保存的位置为：" + filePath + fileName);
//             String tmp= fileReader.readFileByChars(filePath + fileName);
//            JSONArray jsonarray = JSONArray.fromObject(tmp );
//            List<Gene_info> glist =  JSONArray.toList(jsonarray, Gene_info.class);
//
//
//            log.info("新加的数据条数：" + glist.size());
//
//
//            Map<String, String> res = refservice.save(glist);
//            int code = Integer.parseInt(res.get("code"));
//            String data = res.get("data");
//            if (Refcontent.fail != code)
//                return new Result(ResultConstant.FAIL_STATUE, "插入重复，入库失败", data);
//
//            return new Result(ResultConstant.SUCCESS_STATUE, "保存成功", "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
////            if (file1.exists())
////                file1.delete();
//        }
//
//
//        return new Result();
//    }
//
//    @RequestMapping(value = "getref", method = RequestMethod.GET)
//    public Result getref(@RequestParam int PageNum,int PageSize) throws Exception {
//        refdao refdao=new refdao();
//        refdao.setPageNum(PageNum);
//        refdao.setPageSize(PageSize);
//         Page<TGeneInfoEntity> page = refservice.findbypage(refdao);
//
//        List<TGeneInfoEntity> list = page.getContent();
//        List<JSONObject> result = new ArrayList<>();
//         for (TGeneInfoEntity entity : list) {
//             JSONObject object=new JSONObject();
//             JSONObject jsonObjec=JSONObject.fromObject(entity);
//             object.put("preNum",jsonObjec.getString("preNum"));
//             object.put("perTitle",jsonObjec.getString("perTitle"));
//             object.put("geneName",jsonObjec.getString("geneName"));
//             object.put("abstractZh",jsonObjec.getString("abstractZh"));
//             object.put("abstractEn",jsonObjec.getString("abstractEn"));
//             object.put("geneInfo",jsonObjec.getString("geneInfo"));
//
//            result.add(object);
//         }
//         PageInfo pageInfo=new PageInfo();
//        pageInfo.setPageNum(PageNum);
//        pageInfo.setPageSize(PageSize);
//        pageInfo.setTotalInfo(  page.getTotalElements());
//        pageInfo.setTotalPage(page.getTotalPages());
//        return new Result(ResultConstant.SUCCESS_STATUE, "操作成功", result,pageInfo);
//    }
//
//    private JSONObject getFromTuple(Tuple tuple) {
//        QTGeneInfoEntity qtGeneInfoEntity = QTGeneInfoEntity.tGeneInfoEntity;
//        TGeneInfoEntity tGeneInfoEntity = tuple.get(qtGeneInfoEntity);
////        JSONObject object = JSONObject.fromObject(qtGeneInfoEntity);
//        System.out.println(tuple.get(qtGeneInfoEntity));
////        object.put("preNum", tuple.get(qtGeneInfoEntity ));
////        object.put("geneName", tuple.get(qtGeneInfoEntity.geneName));
////
////        object.put("perTitle", tuple.get(qtGeneInfoEntity.perTitle));
////
////        object.put("abstractEn", tuple.get(qtGeneInfoEntity.abstractEn));
////        object.put("abstractZh", tuple.get(qtGeneInfoEntity.abstractZh));
//return null;
//    }
//
//    @RequestMapping(value = "translate", method = RequestMethod.GET)
//    public Result translate(){
//
//        List<String> res=refservice.findallabstr();
//        long result=0;
//        System.out.println(res.size());
//
//        Iterator it=res.iterator();
//        while (it.hasNext()){
//               String tmp=it.next().toString();
//            if (!tmp.equals("This gene has no abstract")) {
//
//
//                result = refservice.updateall(tmp);
//
//            }
//            }
//
//        System.out.println(res.size());
//        return new Result(ResultConstant.SUCCESS_STATUE,"操作完成",res.size());
//    }
//
//    @RequestMapping(value = "refdowond", method = RequestMethod.GET)
//    public Result refdowond(@RequestParam String type, Integer id) throws Exception {
//        List<Tuple> gnums = refservice.findAll();
//        Iterator num = gnums.iterator();
//        int ang = 1;
//        if ("single".equals(type)) {
//            Map result = new HashedMap();
//            result = refservice.saveone(id);
//            int code= (int) result.get("id");
//            System.out.println(result.toString());
//            TGeneAuthorEntity entity= (TGeneAuthorEntity) result.get("data");
//            List<TGeneAuthorEntity> xx=new ArrayList<>();
//            xx.add(entity);
//            if (0 !=code){
//                authorService.save(xx);
//
//            }
//
//            return new Result(ResultConstant.SUCCESS_STATUE, "更新完成", result.toString());
//        } else if ("all".equals(type)) {
//            List<Gene_info> gene_infos1 = new LinkedList();
//            while (num.hasNext()) {
//                Gene_info gene_infos = new Gene_info();
//                Tuple tuple = (Tuple) num.next();
//                Integer pronum = tuple.get(infoEntity.preNum);
//                String info = tuple.get(infoEntity.abstractEn);
//                String name = tuple.get(infoEntity.geneName);
//                gene_infos.setTid(String.valueOf(pronum));
//                gene_infos.setIntro(info);
//                gene_infos.setName(name);
//                System.out.println(pronum + "-------" + ang + "=========" + info + "----" + name);
//                gene_infos1.add(gene_infos);
//
//                if (ang % 10 == 0) {
//                    System.out.println("走你");
//                    List<TGeneAuthorEntity> entity=refservice.update(gene_infos1);
//                    if (entity.size()>0){
//                         authorService.save(entity);
//                    }
//                    gene_infos1.clear();
//                }else if (gnums.size()-ang<10){
//                    System.out.println("走你");
//                    List<TGeneAuthorEntity> entity= refservice.update(gene_infos1);
//                    if (entity.size()>0){
//                        authorService.save(entity);
//                    }
//                    gene_infos1.clear();
//                }
//
//                ang++;
//            }
//
//        } else {
//            return new Result(ResultConstant.FAIL_STATUE, "非法参数");
//        }
//
//
//        return new Result(ResultConstant.SUCCESS_STATUE, "更新成功");
//    }
//
//    @RequestMapping(value = "getAllref", method = RequestMethod.GET)
//    public Result getAllref(@RequestParam int page){
//        String results =new FileReader().readFileByChars("/home/wangshichen/文档/work/postbody.json");
//        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(results);
//        Mapptmp res=null;
//        for (int i=page;i<10496;i++){
//            log.info("--------------------------------------------------");
//            log.info("第"+i+"页");
//            jsonObject.put("EntrezSystem2.PEntrez.PubMed.Pubmed_ResultsPanel.Pubmed_Pager.CurrPage",i);
//            jsonObject.put("EntrezSystem2.PEntrez.PubMed.Pubmed_Facets.FacetsUrlFrag","filters=;humans");
//             res= refservice.mutationdownland(res,jsonObject);
//        }
//
//
//        return new Result(ResultConstant.SUCCESS_STATUE,"",res);
//    }
//
//    @RequestMapping(value = "myservice", method = RequestMethod.GET)
//    public void myservice(){
//
//        refservice.dosomething();
//    }
//}
