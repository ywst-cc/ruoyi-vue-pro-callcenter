package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables;

import org.freeswitch.esl.client.transport.event.EslEvent;

public class ChannelParkRunnable extends AbstractEventRunnable{

    private final EslEvent eslEvent;

    public ChannelParkRunnable(EslEvent eslEvent) {
        super(eslEvent);
        this.eslEvent = eslEvent;
    }

    @Override
    public void handle() {

    }
}
