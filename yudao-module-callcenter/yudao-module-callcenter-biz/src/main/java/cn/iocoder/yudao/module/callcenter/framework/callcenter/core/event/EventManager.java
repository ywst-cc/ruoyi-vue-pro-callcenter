package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event;

import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.handlers.AbstractEventHandler;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.handlers.ChannelParkHandler;
import cn.iocoder.yudao.module.callcenter.framework.threadpool.core.EventExecuteThreadPool;
import jakarta.annotation.Resource;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 注册freeswitch esl需要处理的事件
 */
@Component
public class EventManager {

    @Resource
    private EventExecuteThreadPool eventExecuteThreadPool;

    private final ArrayList<AbstractEventHandler> eventHandlers = new ArrayList<>();

    public EventManager(){
        registerHandler(new ChannelParkHandler());
    }

    private void registerHandler(AbstractEventHandler eventHandler){
        eventHandlers.add(eventHandler);
    }

    public void handler(EslEvent eslEvent) {
        for (AbstractEventHandler eventHandler: eventHandlers) {
            if (eventHandler.isHandler(eslEvent)) {
                // 多线程提交事件请求
                Runnable runnable = eventHandler.handle(eslEvent);
                eventExecuteThreadPool.execute(runnable);
            }
        }
    }

}
