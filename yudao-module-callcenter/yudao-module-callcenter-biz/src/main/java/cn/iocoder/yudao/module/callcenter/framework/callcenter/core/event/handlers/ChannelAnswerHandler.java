package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.handlers;

import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables.ChannelAnswerRunnable;
import org.freeswitch.esl.client.transport.event.EslEvent;

import static cn.iocoder.yudao.module.callcenter.enums.FreeswitchConstants.CHANNEL_ANSWER;

public class ChannelAnswerHandler extends AbstractEventHandler{
    @Override
    public boolean isHandler(EslEvent eslEvent) {
        return CHANNEL_ANSWER.equals(eslEvent.getEventName());
    }

    @Override
    public Runnable handle(EslEvent eslEvent) {
        return new ChannelAnswerRunnable(eslEvent);
    }
}
