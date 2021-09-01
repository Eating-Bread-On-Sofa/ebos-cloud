package cn.edu.bjtu.eboscloud.service;

import cn.edu.bjtu.eboscloud.entity.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by ZhiYuan
 * data:2021/9/1
 */
@Service
public interface TopicService {
    void write(String topic);
    void del(String topic);
    List<Topic> findAll();
}
