package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.CdrSessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.SessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.utils.FsUtils;
import org.apache.commons.lang3.StringUtils;
import org.freeswitch.esl.client.transport.event.EslEvent;

import java.util.Map;

public class ChannelCreateRunnable extends AbstractEventRunnable{

    private final EslEvent eslEvent;
    private final SessionCacheDao sessionCacheDao;
    private final CdrSessionCacheDao cdrSessionCacheDao;

    public ChannelCreateRunnable(EslEvent eslEvent) {
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
        if (StringUtils.isBlank(cdrSessionId)) {
            // 创建cdrSessionId 绑定 uuid
            cdrSessionId = IdUtil.simpleUUID();
            sessionCacheDao.bind(uuid, cdrSessionId);

            String caller = FsUtils.getCaller(eventHeaders);
            String callee = FsUtils.getCallee(eventHeaders);
            cdrSessionCacheDao.handlerSessionCreate(cdrSessionId, caller, callee, uuid);
        }

        String currentDateTime = FsUtils.getEventDateLocal(eventHeaders);
        // 设置LEG A create_time
        cdrSessionCacheDao.handlerSetCreateTime(cdrSessionId, uuid, currentDateTime);
    }
}
