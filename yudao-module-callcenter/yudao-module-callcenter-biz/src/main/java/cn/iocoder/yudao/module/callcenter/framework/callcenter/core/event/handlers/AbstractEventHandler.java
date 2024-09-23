package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.handlers;

import org.freeswitch.esl.client.transport.event.EslEvent;

public abstract class AbstractEventHandler {

    public abstract boolean isHandler(EslEvent eslEvent);

    public abstract Runnable handle(EslEvent eslEvent);
}
