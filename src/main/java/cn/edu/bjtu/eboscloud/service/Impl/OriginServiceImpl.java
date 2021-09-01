package cn.edu.bjtu.eboscloud.service.Impl;

import cn.edu.bjtu.eboscloud.entity.Origin;
import cn.edu.bjtu.eboscloud.service.OriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Create by ZhiYuan
 * data:2021/9/1
 */
@Service
public class OriginServiceImpl implements OriginService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void write(String content) {
        Origin origin = new Origin();
        origin.setData(content);
        mongoTemplate.save(origin);
    }
}
