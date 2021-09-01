//package cn.edu.bjtu.eboscloud.service;
//
//import cn.edu.bjtu.eboscloud.controller.RecieveDataController;
//import cn.edu.bjtu.eboscloud.entity.Subscribe;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.concurrent.SynchronousQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * Create by ZhiYuan
// * data:2021/6/2
// */
//
//@Component
//@Order(1)
//public class InitSubscribe implements ApplicationRunner {
//
//    @Autowired
//    SubscribeService subscribeService;
//
//    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 50,3, TimeUnit.SECONDS,new SynchronousQueue<>());
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        List<Subscribe> subscribes = subscribeService.findAll();
//
//        for (Subscribe subscribe : subscribes){
//            RawSubscribe rawSubscribe = new RawSubscribe(subscribe.getSubTopic());
//            RecieveDataController.status.add(rawSubscribe);
//            threadPoolExecutor.execute(rawSubscribe);
//        }
//    }
//}
