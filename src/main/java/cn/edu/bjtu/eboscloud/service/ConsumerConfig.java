package cn.edu.bjtu.eboscloud.service;

import cn.edu.bjtu.eboscloud.entity.ExportData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Configuration
@Service
public class ConsumerConfig {
    @Value("${mqtt.serverUrls}")
    private String serverUrl;
    @Value("${mqtt.username}")
    private String username;
    @Value("${mqtt.password}")
    private String password;
    @Value("${mqtt.timeout}")
    private Integer timeout;
    @Value("${mqtt.keepalive}")
    private Integer keepalive;
    @Value("${mqtt.clientId}")
    private String clientId;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OriginService originService;
    @Autowired
    ExportService exportService;

    private MqttPahoMessageDrivenChannelAdapter adapter;

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();

        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setServerURIs(serverUrl.split(","));
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(timeout);
        options.setKeepAliveInterval(keepalive);

        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducerSupport mqttInbound() {

        if (adapter == null){
            adapter = new MqttPahoMessageDrivenChannelAdapter(clientId,mqttPahoClientFactory(), "data");
        }

        adapter.setCompletionTimeout(timeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setRecoveryInterval(10000);
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            String content = message.getPayload().toString();
            originService.write(content);
            try{
                JSONObject j = JSON.parseObject(content);
                JSONArray ja = (JSONArray) j.get("readings");
                JSONObject js = ja.getJSONObject(0);
                String device = (String) js.get("device");
                String key = (String) js.get("name");
                switch (key){
                    case "Temperature":
                        key = "温度";
                        break;
                    case "Humidity":
                        key = "湿度";
                        break;
                    case "Relay":
                        key = "继电器开关";
                        break;
                    case "Status":
                        key = "继电器状态";
                        break;
                    default:
                        System.out.println("未知");
                }
                String value = (String) js.get("value");
                ExportData es = exportService.findByDevice(device);
                if (device.equals("Random-Integer-Generator01")){
                    System.out.println(device);
                }else if(es != null){
                    if (es.getKey1().equals(key)){
                        ExportData exportData = new ExportData();
                        exportData.setKey1(key);
                        exportData.setValue1(value);
                        exportService.update(exportData);
                    }else {
                        ExportData exportData = new ExportData();
                        exportData.setKey2(key);
                        exportData.setValue2(value);
                        exportService.update(exportData);
                    }
                }else {
                    ExportData exportData = new ExportData();
                    exportData.setDevice(device);
                    exportData.setTopic(Objects.requireNonNull(message.getHeaders().get("mqtt_receivedTopic")).toString());
                    exportData.setKey1(key);
                    exportData.setValue1(value);
                    exportService.write(exportData);
                }
            }catch (Exception ignored){}
//            String name = (String) js.get("name");
//            System.out.println(name);
//            String value = (String) js.get("value");
//            System.out.println(value);
//            MultiValueMap<String,String> result = new LinkedMultiValueMap<String,String>();
//            result.add("name",name);
//            result.add("value",value);
//            String url = "http://localhost:8333/api/cloud/data";
//            try {
//                restTemplate.postForObject(url,result,String.class);
//            }catch (Exception ignored){}
        };
    }

    public String addListenTopic(String topic){
        if (adapter == null){
            adapter = new MqttPahoMessageDrivenChannelAdapter(clientId,mqttPahoClientFactory(),"");
        }
        adapter.addTopic(topic,1);
        return "订阅成功";
    }

    public String removeListenTopic(String topic){
        adapter.removeTopic(topic);
        return "取消订阅成功";
    }

    public String[] getTopic(){
        return adapter.getTopic();
    }
}

//    @Autowired
//    SaveToHdfs saveToHdfs;
//    @Autowired
//    WebSocket websocket;

//            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
//            for (SubscribeList subscribeList : RecieveDataController.getStatus()){
//                if (topic.equals(subscribeList.getTopic())){
//                    try {
//                        saveToHdfs.save(content,subscribeList.getAddresses(),subscribeList.getPath());
//                    } catch (IOException | URISyntaxException e) {
//                        e.printStackTrace();
//                    }
//                    websocket.sendMessage(topic,content);
//                    break;
//                }
////            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(content);