package cn.edu.bjtu.eboscloud.controller;

import cn.edu.bjtu.eboscloud.entity.SubscribeList;
import cn.edu.bjtu.eboscloud.service.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/cloud")
public class RecieveDataController {

    private static final List<SubscribeList> status = new LinkedList<>();

    public static List<SubscribeList> getStatus() {
        return status;
    }

    @Autowired
    ConsumerConfig consumerConfig;

    @CrossOrigin
    @GetMapping("/list")
    public List<SubscribeList> allInfo(){
        return status;
    }

    @CrossOrigin
    @PostMapping("/subscribe")
    public String subscribe(@RequestBody SubscribeList subscribeList){
        if(!check(subscribeList.getTopic())){
            String result = consumerConfig.addListenTopic(subscribeList.getTopic());
            status.add(subscribeList);
            return result;
        }else{
            return "订阅主题重复";
        }

    }

    public static boolean check(String topic){
        boolean flag = false;
        for (SubscribeList subscribeList : status) {
            if(topic.equals(subscribeList.getTopic())){
                flag=true;
                break;
            }
        }
        return flag;
    }

    @CrossOrigin
    @DeleteMapping("/subscribe/{topic}")
    public String delete(@PathVariable String topic){
        for (SubscribeList subscribeList : status){
            if (topic.equals(subscribeList.getTopic())){
                status.remove(subscribeList);
                break;
            }
        }
        String result = consumerConfig.removeListenTopic(topic);
        return result;
    }

    @CrossOrigin
    @GetMapping("/test")
    public String[] test(){
        return consumerConfig.getTopic();
    }
}
