package cn.iocoder.yudao.module.callcenter.service.call;

import cn.iocoder.yudao.module.callcenter.controller.admin.call.vo.HangupReqVO;
import cn.iocoder.yudao.module.callcenter.controller.admin.call.vo.MakecallReqVO;

/**
 * 外呼接口 Service
 */
public interface CallService {

    /**
     * Http接口执行外呼
     *
     * @param userId 用户UserID
     * @param reqVO 外呼参数
     * @return 通话ID
     */
    String makecall(Long userId, MakecallReqVO reqVO);

    /**
     * 结束通话
     *
     * @param reqVO 结束参数
     * @return 结束结果
     */
    Boolean hangup(HangupReqVO reqVO);
}
