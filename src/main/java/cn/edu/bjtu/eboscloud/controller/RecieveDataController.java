package cn.edu.bjtu.eboscloud.controller;

import cn.edu.bjtu.eboscloud.config.ActivemqConfig;
import cn.edu.bjtu.eboscloud.service.RawSubscribe;
import cn.edu.bjtu.eboscloud.service.SubscribeService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/cloud")
public class RecieveDataController {

    public static JSONObject result = new JSONObject();

    @Autowired
    SubscribeService subscribeService;

    public static final List<RawSubscribe> status = new LinkedList<>();
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 50,3, TimeUnit.SECONDS,new SynchronousQueue<>());

    @CrossOrigin
    @PostMapping("/data")
    public String update(@RequestParam(value = "name") String name,@RequestParam(value = "value") String value){
        if(name.equals("RandomValue_Int16")){
            result.put("RandomValue_Int16",Integer.parseInt(value));
            return "收到";
        }else if(name.equals("RandomValue_Int8")){
            result.put("RandomValue_Int8",Integer.parseInt(value));
            return "收到";
        }else if(name.equals("RandomValue_Int32")){
            result.put("RandomValue_Int32",Integer.parseInt(value));
            return "收到";
        }else {
            return "收到";
        }
    }

    @CrossOrigin
    @GetMapping("/data")
    public JSONObject getData(){
        return result;
    }

    @CrossOrigin
    @PostMapping("/subscribe")
    public String newSubscribe(String topic){
        RawSubscribe rawSubscribe = new RawSubscribe(topic);
        if(!check(rawSubscribe.getSubTopic())){
            try{
                status.add(rawSubscribe);
                subscribeService.save(rawSubscribe.getSubTopic());
                threadPoolExecutor.execute(rawSubscribe);
                return "订阅成功";
            }catch (Exception e) {
                e.printStackTrace();
                return "参数错误!";
            }
        }else {
            return "订阅主题重复";
        }
    }

    public static boolean check(String subTopic){
        boolean flag = false;
        for (RawSubscribe rawSubscribe : status) {
            if(subTopic.equals(rawSubscribe.getSubTopic())){
                flag=true;
                break;
            }
        }
        return flag;
    }

    @CrossOrigin
    @DeleteMapping("/subscribe/{subTopic}")
    public boolean delete(@PathVariable String subTopic){
        boolean flag;
        synchronized (status){
            flag = status.remove(search(subTopic));
        }
        subscribeService.delete(subTopic);
        return flag;
    }

    public static RawSubscribe search(String subTopic){
        for (RawSubscribe rawSubscribe : status) {
            if(subTopic.equals(rawSubscribe.getSubTopic())){
                return rawSubscribe;
            }
        }
        return null;
    }

    @CrossOrigin
    @GetMapping("/ping")
    public String test(){
        return "pong";
    }
}
