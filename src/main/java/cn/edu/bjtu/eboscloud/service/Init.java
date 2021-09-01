//package cn.edu.bjtu.eboscloud.service;
//
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
///**
// * Create by ZhiYuan
// * data:2021/7/7
// */
//@Component
//@Order(1)
//public class Init implements ApplicationRunner {
//    @Value("${export.name}")
//    private String name;
//    @Value("${export.ip}")
//    private String ip;
//    @Value("${export.port}")
//    private int port;
//    @Value("${export.publisher}")
//    private String publisher;
//    @Value("${export.user}")
//    private String user;
//    @Value("${export.password}")
//    private String password;
//    @Value("${export.topic}")
//    private String topic;
//    @Autowired
//    RestTemplate restTemplate;
//
//    private String url = "http://localhost:48071/api/v1/registration";
//
//    @Override
//    public void run(ApplicationArguments arguments) {
//        new Thread(() -> {
//            JSONObject export = new JSONObject();
//            JSONObject addressable = new JSONObject();
//            JSONObject filter = new JSONObject();
//            addressable.put("name","mqtt");
//            addressable.put("protocol","tcp");
//            addressable.put("address",ip);
//            addressable.put("port",port);
//            addressable.put("publisher",publisher);
//            addressable.put("user",user);
//            addressable.put("password",password);
//            addressable.put("topic",topic);
//            export.put("name",name);
//            export.put("addressable",addressable);
//            export.put("format","JSON");
//            export.put("enable",true);
//            export.put("destination","MQTT_TOPIC");
//            filter.put("deviceIdentifiers", new String[]{"Random-Integer-Generator01"});
//            export.put("filter",filter);
//            try {
//                restTemplate.postForObject(url, export, String.class);
//            }
//            catch (HttpClientErrorException.BadRequest e){
//                restTemplate.put(url, export);
//            }
//        }).start();
//    }
//}
