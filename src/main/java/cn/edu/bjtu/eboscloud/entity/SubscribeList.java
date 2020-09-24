package cn.edu.bjtu.eboscloud.entity;

import java.io.Serializable;
import java.util.Date;

public class SubscribeList implements Serializable {
    private String topic;
    private Date date;
    private String addresses;
    private String path;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SubscribeList(String topic, String addresses, String path, Date date){
        this.topic = topic;
        this.addresses = addresses;
        this.path = path;
        if ( date == null){
            this.date = new Date();
        }else{
            this.date = date;
        }
    }
}
