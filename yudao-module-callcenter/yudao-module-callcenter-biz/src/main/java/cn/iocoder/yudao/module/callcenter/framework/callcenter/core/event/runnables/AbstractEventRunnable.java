package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.utils.FsUtils;
import lombok.extern.slf4j.Slf4j;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

@Slf4j
public abstract class AbstractEventRunnable implements Runnable{

    private final EslEvent eslEvent;

    private RedissonClient redissonClient;

    public AbstractEventRunnable(EslEvent eslEvent) {
        this.eslEvent = eslEvent;
        this.redissonClient = SpringUtil.getBean(RedissonClient.class);
    }

    @Override
    public void run() {
        String uuid = FsUtils.getUuid(eslEvent.getEventHeaders());
        RLock runLock = redissonClient.getFairLock("ESL:UUID_RUN_LOCK:" + uuid);
        runLock.lock();
        try {
            handle();
        } catch (Exception e) {
            log.error("ESL事件 {} - UUID: {}, 执行异常. {}",
                    eslEvent.getEventName(), uuid, ExceptionUtil.stacktraceToOneLineString(e));
        }finally {
            runLock.unlock();
        }
    }

    public abstract void handle();

}
