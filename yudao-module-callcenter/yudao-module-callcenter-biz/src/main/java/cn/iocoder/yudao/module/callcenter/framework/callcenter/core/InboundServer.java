package cn.iocoder.yudao.module.callcenter.framework.callcenter.core;

import cn.iocoder.yudao.module.callcenter.framework.callcenter.config.FreeswitchProperties;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.EventManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.CommandResponse;
import org.freeswitch.esl.client.transport.SendMsg;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.springframework.stereotype.Component;

import static cn.iocoder.yudao.module.callcenter.enums.FreeswitchConstants.*;

/**
 * freeswitch mod_event_socket 连接客户端 inbound 模式
 */
@Slf4j
@Component
public class InboundServer {

    @Resource
    private FreeswitchProperties properties;

    @Resource
    private EventManager eventManager;

    private Client client;

    /**
     * 初始化esl client连接，防止检查连接是否断开，断开重连防止创建多个连接
     */
    @PostConstruct
    private synchronized void start(){

        log.info("ESL启动连接FreeSWITCH server.连接地址: [{}:{}]", properties.getHost(), properties.getPort());
        // 确保 freeswitch 宕机后重连client唯一性
        stop();

        this.client = new Client();
        try{
            this.client.connect(properties.getHost(), properties.getPort(), properties.getPassword(), properties.getTimeout());
        }catch (InboundConnectionFailure failure) {
            log.error("ESL连接FreeSWITCH server失败, 失败原因: {}", failure.getMessage());
        }

        // 添加事件回调监听
        this.client.addEventListener(new IEslEventListener() {
            @Override
            public void eventReceived(EslEvent eslEvent) {
                // event 事件回调处理
                eventManager.handler(eslEvent);
            }

            @Override
            public void backgroundJobResultReceived(EslEvent eslEvent) {

            }
        });

        // 订阅ESL事件
        String[] events = new String[]{CHANNEL_PARK};
        this.client.setEventSubscriptions("plain", String.join(" ", events));
    }

    @PreDestroy
    private void stop(){
        if (null != this.client && client.canSend()) {
            client.close();
        }
    }

    /**
     * 检查连接是否断开，断开则重新连接
     */
    protected void checkConnection(){
        if(null == this.client || !this.client.canSend()){
            start();
        }
    }

    /**
     * 发送异步命令
     * @param api freeswitch api
     * @param data 执行api参数
     * @return 返回bgapi执行uuid
     */
    public String sendAsyncApiCommand(String api, String data) {
        checkConnection();
        return this.client.sendAsyncApiCommand(api, data);
    }


    /**
     *  同步执行api命令
     * @param api freeswitch api
     * @param data 执行api参数
     * @return 执行API结果
     */
    public EslMessage sendSyncApiCommand(String api, String data) {
        checkConnection();
        return this.client.sendSyncApiCommand(api, data);
    }

    /**
     * sendmsg 方式发送app指令
     * @param sendMsg app指令发送
     * @return 返回app执行结果
     */
    public CommandResponse sendMsg(SendMsg sendMsg) {
        checkConnection();
        return this.client.sendMessage(sendMsg);
    }

}
