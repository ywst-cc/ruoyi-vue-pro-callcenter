package cn.iocoder.yudao.module.callcenter.service;

import cn.hutool.core.util.IdUtil;
import cn.iocoder.yudao.module.callcenter.controller.admin.call.vo.MakecallReqVO;
import cn.iocoder.yudao.module.callcenter.enums.CommandKey;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.TradeMain;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para.ParaOriginate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CallServiceImpl implements CallService {

    @Resource
    private TradeMain tradeMain;

    @Override
    public String makecall(Long userId, MakecallReqVO reqVO) {

        // todo：
        //  1. 查找用户绑定分机
        //  2. 是否指定线路外呼
        //  3. 执行外呼

        String cdrSessionId = IdUtil.simpleUUID();
        String uuid = IdUtil.randomUUID();

        tradeMain.exec(CommandKey.Originate, new ParaOriginate());

        return cdrSessionId;
    }
}
