package cn.edu.bjtu.eboscloud.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Create by ZhiYuan
 * data:2021/9/1
 */
@Document(collection = "topic")
public class Topic implements Serializable {

    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
