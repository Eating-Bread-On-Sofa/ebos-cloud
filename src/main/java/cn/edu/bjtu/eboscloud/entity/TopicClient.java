package cn.edu.bjtu.eboscloud.entity;

import javax.websocket.Session;
import java.io.Serializable;

public class TopicClient implements Serializable {

    private String topic;
    private Session session;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public TopicClient (String topic, Session session){
        this.topic = topic;
        this.session = session;
    }
}
