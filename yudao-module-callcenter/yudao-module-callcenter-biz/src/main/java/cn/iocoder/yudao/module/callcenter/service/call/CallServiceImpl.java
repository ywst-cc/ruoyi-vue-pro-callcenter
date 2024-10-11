package cn.iocoder.yudao.module.callcenter.service.call;

import cn.hutool.core.util.IdUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.callcenter.controller.admin.call.vo.MakecallReqVO;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.extension.ExtensionDO;
import cn.iocoder.yudao.module.callcenter.dal.mysql.extension.ExtensionMapper;
import cn.iocoder.yudao.module.callcenter.enums.CommandKey;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.CdrSessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.SessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.TradeMain;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para.ParaOriginate;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.callcenter.enums.ErrorCodeConstants.CALL_EXTENSION_NOT_EXISTS;

@Slf4j
@Service
public class CallServiceImpl implements CallService {

    @Resource
    private TradeMain tradeMain;

    @Resource
    private ExtensionMapper extensionMapper;

    @Resource
    private SessionCacheDao sessionCacheDao;
    @Resource
    private CdrSessionCacheDao cdrSessionCacheDao;

    @Override
    public String makecall(Long userId, MakecallReqVO reqVO) {
        log.info("[makecall request] -- [userId({}) reqVO({})]", userId, JSON.toJSONString(reqVO));
        //  1. 查找用户绑定分机
        ExtensionDO extensionDO = extensionMapper.selectOne(
                new LambdaQueryWrapperX<ExtensionDO>()
                        .eq(ExtensionDO::getOwnerUserId, userId)
        );
        if (null == extensionDO) {
            throw exception(CALL_EXTENSION_NOT_EXISTS);
        }

        // 获取外呼的主叫分机号
        String caller = extensionDO.getExtension();
        // 被叫号码
        String callee = reqVO.getCallee();

        //  2. 是否指定线路外呼

        //  3. 执行外呼
        String cdrSessionId = IdUtil.simpleUUID();
        String uuid = IdUtil.randomUUID();
        sessionCacheDao.bind(uuid, cdrSessionId);
        cdrSessionCacheDao.handlerSessionCreate(cdrSessionId, caller, callee, uuid, reqVO.getExtra());

        tradeMain.exec(CommandKey.Originate, new ParaOriginate(uuid, caller, callee));
        return cdrSessionId;
    }
}
