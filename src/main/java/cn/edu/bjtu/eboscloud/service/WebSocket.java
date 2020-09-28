package cn.edu.bjtu.eboscloud.service;

import cn.edu.bjtu.eboscloud.entity.TopicClient;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{topic}")
@Component
public class WebSocket {

    private static CopyOnWriteArraySet<TopicClient> webSockets =new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam(value="topic") String topic) {
        this.session = session;
        webSockets.add(new TopicClient(topic,session));
        System.out.println("订阅主题为:"+ topic + "数据的连接成功！");
    }

    @OnClose
    public void onClose() {
        webSockets.forEach(topicClient -> {
            if (topicClient.getSession().getId().equals(session.getId())) {
                System.out.println("订阅主题为"+topicClient.getTopic()+"的连接断开");
                webSockets.remove(topicClient);
            }
        });
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("收到客户端消息:"+message);
    }

    @OnError
    public void onError(Throwable error) {
        webSockets.forEach(topicClient -> {
            if (topicClient.getSession().getId().equals(session.getId())) {
                System.out.println("订阅主题为"+topicClient.getTopic()+"的连接出错");
                webSockets.remove(topicClient);
                error.printStackTrace();
            }
        });
    }

    public synchronized static void sendMessage(String topic,String message){
        webSockets.forEach(topicClient -> {
            if (topic.equals(topicClient.getTopic())) {
                try {
                    topicClient.getSession().getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

