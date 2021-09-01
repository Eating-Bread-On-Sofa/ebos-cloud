package cn.edu.bjtu.eboscloud.service.Impl;

import cn.edu.bjtu.eboscloud.entity.Topic;
import cn.edu.bjtu.eboscloud.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by ZhiYuan
 * data:2021/9/1
 */

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void write(String topic) {
        Topic t = new Topic();
        t.setTopic(topic);
        mongoTemplate.save(t);
    }

    @Override
    public void del(String topic) {
        Query query = Query.query(Criteria.where("topic").is(topic));
        mongoTemplate.remove(query, Topic.class,"topic");
    }

    @Override
    public List<Topic> findAll() {
        return mongoTemplate.findAll(Topic.class,"topic");
    }
}
