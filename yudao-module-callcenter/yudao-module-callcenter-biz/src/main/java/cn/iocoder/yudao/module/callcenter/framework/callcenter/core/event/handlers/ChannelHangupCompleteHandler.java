package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.handlers;

import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables.ChannelHangupCompleteRunnable;
import org.freeswitch.esl.client.transport.event.EslEvent;

import static cn.iocoder.yudao.module.callcenter.enums.FreeswitchConstants.CHANNEL_HANGUP_COMPLETE;

public class ChannelHangupCompleteHandler extends AbstractEventHandler{
    @Override
    public boolean isHandler(EslEvent eslEvent) {
        return CHANNEL_HANGUP_COMPLETE.equals(eslEvent.getEventName());
    }

    @Override
    public Runnable handle(EslEvent eslEvent) {
        return new ChannelHangupCompleteRunnable(eslEvent);
    }
}
