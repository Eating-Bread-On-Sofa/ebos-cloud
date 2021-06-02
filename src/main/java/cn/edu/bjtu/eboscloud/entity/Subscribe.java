package cn.edu.bjtu.eboscloud.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Create by ZhiYuan
 * data:2021/6/2
 */

@Document(collection = "subscribe")
public class Subscribe {
    private String subTopic;
    private Date created;

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
