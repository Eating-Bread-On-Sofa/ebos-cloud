package cn.edu.bjtu.eboscloud.controller;

//import cn.edu.bjtu.eboscloud.config.ActivemqConfig;
//import cn.edu.bjtu.eboscloud.service.RawSubscribe;
//import cn.edu.bjtu.eboscloud.service.SubscribeService;
import cn.edu.bjtu.eboscloud.entity.ExportData;
import cn.edu.bjtu.eboscloud.service.ConsumerConfig;
import cn.edu.bjtu.eboscloud.service.ExportService;
import cn.edu.bjtu.eboscloud.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cloud")
public class RecieveDataController {

//    public static JSONObject result = new JSONObject();

//    @Autowired
//    SubscribeService subscribeService;
//
//    public static final List<RawSubscribe> status = new LinkedList<>();
//    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 50,3, TimeUnit.SECONDS,new SynchronousQueue<>());

//    @CrossOrigin
//    @PostMapping("/data")
//    public String update(@RequestParam(value = "name") String name,@RequestParam(value = "value") String value){
//        if(name.equals("RandomValue_Int16")){
//            result.put("RandomValue_Int16",Integer.parseInt(value));
//            return "收到";
//        }else if(name.equals("RandomValue_Int8")){
//            result.put("RandomValue_Int8",Integer.parseInt(value));
//            return "收到";
//        }else if(name.equals("RandomValue_Int32")){
//            result.put("RandomValue_Int32",Integer.parseInt(value));
//            return "收到";
//        }else {
//            return "收到";
//        }
//    }
//
//    @CrossOrigin
//    @GetMapping("/data")
//    public JSONObject getData(){
//        return result;
//    }

//    @CrossOrigin
//    @PostMapping("/subscribe")
//    public String newSubscribe(String topic){
//        RawSubscribe rawSubscribe = new RawSubscribe(topic);
//        if(!check(rawSubscribe.getSubTopic())){
//            try{
//                status.add(rawSubscribe);
//                subscribeService.save(rawSubscribe.getSubTopic());
//                threadPoolExecutor.execute(rawSubscribe);
//                return "订阅成功";
//            }catch (Exception e) {
//                e.printStackTrace();
//                return "参数错误!";
//            }
//        }else {
//            return "订阅主题重复";
//        }
//    }
//
//    public static boolean check(String subTopic){
//        boolean flag = false;
//        for (RawSubscribe rawSubscribe : status) {
//            if(subTopic.equals(rawSubscribe.getSubTopic())){
//                flag=true;
//                break;
//            }
//        }
//        return flag;
//    }
//
//    @CrossOrigin
//    @DeleteMapping("/subscribe/{subTopic}")
//    public boolean delete(@PathVariable String subTopic){
//        boolean flag;
//        synchronized (status){
//            flag = status.remove(search(subTopic));
//        }
//        subscribeService.delete(subTopic);
//        return flag;
//    }
//
//    public static RawSubscribe search(String subTopic){
//        for (RawSubscribe rawSubscribe : status) {
//            if(subTopic.equals(rawSubscribe.getSubTopic())){
//                return rawSubscribe;
//            }
//        }
//        return null;
//    }

    @Autowired
    ConsumerConfig consumerConfig;
    @Autowired
    ExportService exportService;
    @Autowired
    TopicService topicService;

    @CrossOrigin
    @PostMapping("/topic")
    public String addTopic(String topic){
        String[] topics = consumerConfig.getTopic();
        for (String t : topics){
            if (t.equals(topic)){
                return "该主题已订阅！";
            }
        }
        topicService.write(topic);
        return consumerConfig.addListenTopic(topic);
    }

    @CrossOrigin
    @GetMapping("/topic")
    public String[] showTopic(){
        return consumerConfig.getTopic();
    }

    @CrossOrigin
    @DeleteMapping("/topic")
    public String delTopic(String topic){
        String[] topics = consumerConfig.getTopic();
        for (String t : topics){
            if (t.equals(topic)){
                topicService.del(topic);
                return consumerConfig.removeListenTopic(topic);
            }
        }
        return "无法删除未订阅主题！";
    }

    @CrossOrigin
    @GetMapping("/data")
    public List<ExportData> showData(String topic){
        return exportService.findByTopic(topic);
    }

    @CrossOrigin
    @GetMapping("/allData")
    public List<ExportData> showAll(){
        return exportService.findAll();
    }

    @CrossOrigin
    @DeleteMapping("/data")
    public String delData(String device,String topic){
        return exportService.del(device,topic);
    }

    @CrossOrigin
    @GetMapping("/ping")
    public String test(){
        return "pong";
    }
}
