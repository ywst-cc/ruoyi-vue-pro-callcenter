package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.handlers;

import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables.RecordStartRunnable;
import org.freeswitch.esl.client.transport.event.EslEvent;

import static cn.iocoder.yudao.module.callcenter.enums.FreeswitchConstants.RECORD_START;

public class RecordStartHandler extends AbstractEventHandler{
    @Override
    public boolean isHandler(EslEvent eslEvent) {
        return RECORD_START.equals(eslEvent.getEventName());
    }

    @Override
    public Runnable handle(EslEvent eslEvent) {
        return new RecordStartRunnable(eslEvent);
    }
}
