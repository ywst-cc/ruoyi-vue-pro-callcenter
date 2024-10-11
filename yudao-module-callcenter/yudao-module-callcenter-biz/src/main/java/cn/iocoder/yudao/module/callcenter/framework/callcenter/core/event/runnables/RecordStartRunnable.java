package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables;

import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.CdrSessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.SessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.utils.FsUtils;
import org.freeswitch.esl.client.transport.event.EslEvent;

import java.util.Map;

public class RecordStartRunnable extends AbstractEventRunnable{

    private final EslEvent eslEvent;
    private final SessionCacheDao sessionCacheDao;
    private final CdrSessionCacheDao cdrSessionCacheDao;

    public RecordStartRunnable(EslEvent eslEvent){
        super(eslEvent);
        this.eslEvent = eslEvent;
        this.sessionCacheDao = SpringUtil.getBean(SessionCacheDao.class);
        this.cdrSessionCacheDao = SpringUtil.getBean(CdrSessionCacheDao.class);
    }

    @Override
    public void handle() {
        Map<String, String> eventHeaders = eslEvent.getEventHeaders();
        // 当前channel uuid
        String uuid = FsUtils.getUuid(eventHeaders);
        String cdrSessionId = sessionCacheDao.getCdrSessionId(uuid);
        String recordFilePath = FsUtils.getRecordFilePath(eventHeaders);
        cdrSessionCacheDao.setRecordFilePath(cdrSessionId, recordFilePath);
    }
}
