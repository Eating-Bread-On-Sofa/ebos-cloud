package cn.edu.bjtu.eboscloud.service;

import cn.edu.bjtu.eboscloud.entity.Subscribe;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by ZhiYuan
 * data:2021/6/2
 */

@Service
public interface SubscribeService {
    void save(String subTopic);
    void delete(String subTopic);
    List<Subscribe> findAll();
}
