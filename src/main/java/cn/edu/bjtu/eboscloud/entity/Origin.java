package cn.edu.bjtu.eboscloud.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Create by ZhiYuan
 * data:2021/9/1
 */
@Document(collection = "origin")
public class Origin implements Serializable {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
