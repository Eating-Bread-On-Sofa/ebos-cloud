package cn.edu.bjtu.eboscloud.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cloud")
public class RecieveDataController {

    public static JSONObject result = new JSONObject();

    @CrossOrigin
    @PostMapping("/data")
    public String update(@RequestParam(value = "name") String name,@RequestParam(value = "value") int value){
        if(name.equals("RandomValue_Int16")){
            result.put("RandomValue_Int16",value);
            return "收到";
        }else if(name.equals("RandomValue_Int32")){
            result.put("RandomValue_Int32",value);
            return "收到";
        }else {
            result.put("RandomValue_Int8",value);
            return "收到";
        }
    }

    @CrossOrigin
    @GetMapping("/data")
    public JSONObject getData(){
        return result;
    }

//    private static final List<SubscribeList> status = new LinkedList<>();
//
//    public static List<SubscribeList> getStatus() {
//        return status;
//    }
//
//    @Autowired
//    ConsumerConfig consumerConfig;
//
//    @CrossOrigin
//    @GetMapping("/list")
//    public List<SubscribeList> allInfo(){
//        return status;
//    }
//
//    @CrossOrigin
//    @PostMapping("/subscribe")
//    public JSONObject subscribe(@RequestBody SubscribeList subscribeList){
//        if(!check(subscribeList.getTopic())){
//            String result = consumerConfig.addListenTopic(subscribeList.getTopic());
//            status.add(subscribeList);
//            JSONObject json = new JSONObject();
//            json.put("message",result);
//            return json;
//        }else{
//            JSONObject jo = new JSONObject();
//            jo.put("message","订阅主题重复");
//            return jo;
//        }
//
//    }
//
//    public static boolean check(String topic){
//        boolean flag = false;
//        for (SubscribeList subscribeList : status) {
//            if(topic.equals(subscribeList.getTopic())){
//                flag=true;
//                break;
//            }
//        }
//        return flag;
//    }
//
//    @CrossOrigin
//    @DeleteMapping("/subscribe/{topic}")
//    public JSONObject delete(@PathVariable String topic){
//        for (SubscribeList subscribeList : status){
//            if (topic.equals(subscribeList.getTopic())){
//                status.remove(subscribeList);
//                break;
//            }
//        }
//        String result = consumerConfig.removeListenTopic(topic);
//        JSONObject json = new JSONObject();
//        json.put("message",result);
//        return json;
//    }

    @CrossOrigin
    @GetMapping("/ping")
    public String test(){
        return "pong";
    }
}
