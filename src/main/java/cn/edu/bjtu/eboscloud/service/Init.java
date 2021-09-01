package cn.edu.bjtu.eboscloud.service;

import cn.edu.bjtu.eboscloud.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Create by ZhiYuan
 * data:2021/7/7
 */
@Component
@Order(1)
public class Init implements ApplicationRunner {

    @Autowired
    TopicService topicService;
    @Autowired
    ConsumerConfig consumerConfig;

    @Override
    public void run(ApplicationArguments arguments) {
        new Thread(() -> {
            List<Topic> topics = topicService.findAll();
            for (Topic topic : topics){
                consumerConfig.addListenTopic(topic.getTopic());
            }
        }).start();
    }
}
