package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables;

import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.CdrSessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.SessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.utils.FsUtils;
import org.freeswitch.esl.client.transport.event.EslEvent;

import java.util.Map;

public class ChannelAnswerRunnable extends AbstractEventRunnable{

    private final EslEvent eslEvent;
    private final SessionCacheDao sessionCacheDao;
    private final CdrSessionCacheDao cdrSessionCacheDao;

    public ChannelAnswerRunnable(EslEvent eslEvent) {
        super(eslEvent);
        this.eslEvent = eslEvent;
        this.sessionCacheDao = SpringUtil.getBean(SessionCacheDao.class);
        this.cdrSessionCacheDao = SpringUtil.getBean(CdrSessionCacheDao.class);
    }

    @Override
    public void handle() {
        Map<String, String> eventHeaders = eslEvent.getEventHeaders();

        String uuid = FsUtils.getUuid(eventHeaders);
        String cdrSessionId = sessionCacheDao.getCdrSessionId(uuid);

        String currentDateTime = FsUtils.getEventDateLocal(eventHeaders);
        // 设置LEG A create_time
        cdrSessionCacheDao.handlerSetAnswerTime(cdrSessionId, uuid, currentDateTime);
    }
}
