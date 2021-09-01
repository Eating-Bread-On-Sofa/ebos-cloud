//package cn.edu.bjtu.eboscloud.service.Impl;
//
//import cn.edu.bjtu.eboscloud.entity.Subscribe;
//import cn.edu.bjtu.eboscloud.service.SubscribeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * Create by ZhiYuan
// * data:2021/6/2
// */
//
//@Service
//public class SubscribeServiceImpl implements SubscribeService {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Override
//    public void save(String subTopic) {
//        Subscribe subscribe = new Subscribe();
//        subscribe.setSubTopic(subTopic);
//        subscribe.setCreated(new Date());
//        mongoTemplate.save(subscribe);
//    }
//
//    @Override
//    public void delete(String subTopic) {
//        Query query = Query.query(Criteria.where("subTopic").is(subTopic));
//        mongoTemplate.remove(query,Subscribe.class,"subscribe");
//    }
//
//    @Override
//    public List<Subscribe> findAll() {
//        return mongoTemplate.findAll(Subscribe.class,"subscribe");
//    }
//
//}
