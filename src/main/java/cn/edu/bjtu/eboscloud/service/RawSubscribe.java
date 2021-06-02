package cn.edu.bjtu.eboscloud.service;

import cn.edu.bjtu.eboscloud.config.ActivemqConfig;
import cn.edu.bjtu.eboscloud.controller.RecieveDataController;
import cn.edu.bjtu.eboscloud.util.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.MessageConsumer;

/**
 * Create by ZhiYuan
 * data:2021/6/2
 */
public class RawSubscribe implements Runnable{

    private ActivemqConfig activemqConfig = ApplicationContextProvider.getBean(ActivemqConfig.class);

    private String subTopic;

    public RawSubscribe(String subTopic){
        this.subTopic = subTopic;
    }

    public String getSubTopic() {
        return subTopic;
    }

    @Override
    public void run() {
        try{
            MessageConsumer consumer = activemqConfig.ConsumerConfig(subTopic);
            while (true){
                try {
                    String msg = activemqConfig.subscribe(consumer);
                    if(!RecieveDataController.check(subTopic)){
                        break;
                    }
                    System.out.println(msg);
                }catch (Exception e){e.printStackTrace();break;}
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
