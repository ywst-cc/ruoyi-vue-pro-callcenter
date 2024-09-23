package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables;

import lombok.extern.slf4j.Slf4j;
import org.freeswitch.esl.client.transport.event.EslEvent;

@Slf4j
public abstract class AbstractEventRunnable implements Runnable{

    private final EslEvent eslEvent;

    public AbstractEventRunnable(EslEvent eslEvent) {
        this.eslEvent = eslEvent;
    }

    @Override
    public void run() {
        // todo: 以freeswitch channel uuid 为key，添加fair lock 保证事件按顺序执行
        handle();
    }

    public abstract void handle();

}
