package cn.edu.bjtu.eboscloud.service;

import cn.edu.bjtu.eboscloud.controller.RecieveDataController;
import cn.edu.bjtu.eboscloud.entity.SubscribeList;
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

import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
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
    SaveToHdfs saveToHdfs;

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
            adapter = new MqttPahoMessageDrivenChannelAdapter(clientId,mqttPahoClientFactory(),"anyway");
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
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            for (SubscribeList subscribeList : RecieveDataController.getStatus()){
                if (topic.equals(subscribeList.getTopic())){
                    try {
                        saveToHdfs.save(content,subscribeList.getAddresses(),subscribeList.getPath());
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(content);
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
