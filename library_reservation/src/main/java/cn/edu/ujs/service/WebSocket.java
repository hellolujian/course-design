package cn.edu.ujs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by DELL on 2018/1/2.
 */
@Component
@ServerEndpoint("/webSocket")
public class WebSocket {

    private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        logger.info("【websocket消息】有新的连接，总数:{}",webSocketSet.size());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        logger.info("【websocket消息】连接断开,总数：{}",webSocketSet.size());

    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("【websocket消息】收到客户端发来的消息:{}",message);
    }

    public void sendMessage(String message) {
        for (WebSocket webSocket : webSocketSet) {
            logger.info("【websocket消息】广播消息,message={}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
