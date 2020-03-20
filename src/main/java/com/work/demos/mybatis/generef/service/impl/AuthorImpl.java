//package com.work.demos.mybatis.generef.service.impl;
//
//import com.bailian.mailuo_service.generef.domain.QTGeneAuthorEntity;
//import com.bailian.mailuo_service.generef.domain.TGeneAuthorEntity;
//import com.bailian.mailuo_service.generef.domain.TGeneAuthorRepository;
//import com.bailian.mailuo_service.generef.service.AuthorService;
//import com.bailian.mailuo_service.util.BasicService;
//import com.bailian.mailuo_service.util.PageInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AuthorImpl extends BasicService<TGeneAuthorEntity> implements AuthorService {
//    @Autowired
//    TGeneAuthorRepository authorRepository;
//
//
//
//
//    @Override
//    public TGeneAuthorEntity save(List<TGeneAuthorEntity> tGeneAuthorEntity) throws Exception {
//        QTGeneAuthorEntity authorEntity=QTGeneAuthorEntity.tGeneAuthorEntity;
//        for (TGeneAuthorEntity entity:tGeneAuthorEntity){
//            System.out.println(entity.toString());
//            String title=queryFactory.select( authorEntity.preTitle)
//                   .from(authorEntity)
//                   .where(authorEntity.preNum.eq(entity.getPreNum()))
//                   .fetchOne();
//
//            System.out.println(title+"xxxxxxxx");
//            if (title==null) {
//                authorRepository.save(entity);
//            }
//
//
//
//        }
//
//
//
//
//        return null;
//    }
//
//    @Override
//    public TGeneAuthorEntity save(TGeneAuthorEntity tGeneAuthorEntity) throws Exception {
//        return null;
//    }
//
//    @Override
//    public void remove(TGeneAuthorEntity tGeneAuthorEntity) throws Exception {
//
//    }
//
//    @Override
//    public TGeneAuthorEntity modify(TGeneAuthorEntity tGeneAuthorEntity) throws Exception {
//        return null;
//    }
//
//    @Override
//    public Page<TGeneAuthorEntity> findByPage(TGeneAuthorEntity tGeneAuthorEntity, PageInfo pageInfo) throws Exception {
//        return null;
//    }
//
//    @Override
//    public List<TGeneAuthorEntity> findAll() throws Exception {
//        return null;
//    }
//
//    @Override
//    public TGeneAuthorEntity findById(Integer id) throws Exception {
//        return null;
//    }
//}
