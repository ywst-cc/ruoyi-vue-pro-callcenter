package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command;

import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.InboundServer;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.freeswitch.esl.client.transport.SendMsg;
import org.freeswitch.esl.client.transport.message.EslMessage;

@Slf4j
public abstract class AbstractCommand<T> {

    public abstract String getCommand(T obj);

    public String executeAsync(T obj) {
        String command = getCommand(obj);
        String msg = SpringUtil.getBean(InboundServer.class).sendAsyncApiCommand(command, "");
        log.info(String.format("\n%s\n%s", command, msg));
        return msg;
    }

    public String executeSync(T obj) {
        String command = getCommand(obj);
        EslMessage eslMessage = SpringUtil.getBean(InboundServer.class).sendSyncApiCommand(command, "");
        StringBuilder msg = new StringBuilder();
        for (String bodyLine: eslMessage.getBodyLines()){
            msg.append(bodyLine);
        }
        log.info(String.format("\n%s\n%s", command, msg));
        return msg.toString();
    }

    public String executeSendMsg(T obj) {
        String command = getCommand(obj);
        SendMsg msg = JSON.parseObject(command, SendMsg.class);

        String response = SpringUtil.getBean(InboundServer.class).sendMsg(msg).getReplyText();
        log.info(String.format("\n%s\n%s", command, response));
        return response;
    }

    public String msg2string(SendMsg sendMsg) {
        return JSON.toJSONString(sendMsg);
    }

    public SendMsg getMsg(String uuid, String eventUuid, String appName, String appArg, Boolean addLock) {
        SendMsg sendMsg = new SendMsg(uuid);
        sendMsg.addCallCommand("execute");
        if (StringUtils.isNotBlank(eventUuid)) {
            sendMsg.addGenericLine("Event-UUID", eventUuid);
        }
        sendMsg.addExecuteAppArg(appName);
        sendMsg.addExecuteAppArg(appArg);
        if (addLock) {
            sendMsg.addEventLock();
        }
        return sendMsg;
    }

    public String getMsgStr(String uuid, String appName, String appArg, Boolean addLock) {
        return msg2string(getMsg(uuid, null, appName, appArg, addLock));
    }

    public String getMsgStr(String uuid, String eventUuid, String appName, String appArg, Boolean addLock) {
        return msg2string(getMsg(uuid, eventUuid, appName, appArg, addLock));
    }

}
