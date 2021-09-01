package cn.edu.bjtu.eboscloud.service.Impl;

import cn.edu.bjtu.eboscloud.entity.ExportData;
import cn.edu.bjtu.eboscloud.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Create by ZhiYuan
 * data:2021/9/1
 */

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void write(ExportData exportData) {
        ExportData data = new ExportData();
        data.setDevice(exportData.getDevice());
        data.setKey1(exportData.getKey1());
        data.setValue1(exportData.getValue1());
        data.setTopic(exportData.getTopic());
        data.setTime(new Date());
        mongoTemplate.save(data);
    }

    @Override
    public List<ExportData> findByTopic(String topic) {
        Query query = Query.query(Criteria.where("topic").is(topic));
        return mongoTemplate.find(query,ExportData.class,"exportData");
    }

    @Override
    public List<ExportData> findAll() {
        return mongoTemplate.findAll(ExportData.class,"exportData");
    }

    @Override
    public ExportData findByDevice(String device) {
        Query query = Query.query(Criteria.where("device").is(device));
        return mongoTemplate.findOne(query,ExportData.class,"exportData");
    }

    @Override
    public String del(String device,String topic) {
        Query query = Query.query(Criteria.where("device").is(device).and("topic").is(topic));
        mongoTemplate.remove(query,ExportData.class,"exportData");
        return "删除成功！";
    }

    @Override
    public void update(ExportData exportData) {
        Query query = new Query(Criteria.where("device").is(exportData.getDevice()));
        Update update = new Update();
        update.set("time",new Date());
        if(exportData.getKey1()==null){
            update.set("key1",exportData.getKey1());
            update.set("value1",exportData.getValue1());
        }else {
            update.set("key2",exportData.getKey2());
            update.set("value2",exportData.getValue2());
        }
        mongoTemplate.upsert(query,update,ExportData.class,"exportData");
    }
}
