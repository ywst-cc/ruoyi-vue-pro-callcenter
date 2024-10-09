package cn.iocoder.yudao.module.callcenter.framework.callcenter.core;

import cn.iocoder.yudao.module.callcenter.enums.CommandKey;
import cn.iocoder.yudao.module.callcenter.enums.CommandType;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.AbstractCommand;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.Bridge;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.Originate;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.UuidKill;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 向FS提交指令模块
 */
@Slf4j
@Component
public class TradeMain {

    private final ConcurrentHashMap<CommandKey, AbstractCommand<Object>> map = new ConcurrentHashMap<>();

    @PostConstruct
    public void commandInit() {
        register(CommandKey.Originate, new Originate());
        register(CommandKey.UuidKill, new UuidKill());
        register(CommandKey.Bridge, new Bridge());
    }

    public String exec(CommandKey key, Object obj) {
        if (null != key.getType()) {
            return exec(key, key.getType(), obj);
        }else {
            log.error("CommandKey [{}] 未设置 CommandType, 无法执行.", key);
            return null;
        }
    }


    private String exec(CommandKey key, CommandType type, Object obj) {
        String msg = "";
        AbstractCommand<Object> command = map.get(key);
        if (null == command) {
            msg = " " + key.name() + " NOT FOUND";
        }else {
            switch (type) {
                case Async -> {
                    msg = command.executeAsync(obj);
                }
                case Sync -> {
                    msg = command.executeSync(obj);
                }
                case Msg -> {
                    msg = command.executeSendMsg(obj);
                }
                default -> log.error("CommandType [{}] 未定义.", type);
            }
        }
        return msg;
     }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void register(CommandKey key, AbstractCommand command) {
        map.put(key, command);
    }
}
