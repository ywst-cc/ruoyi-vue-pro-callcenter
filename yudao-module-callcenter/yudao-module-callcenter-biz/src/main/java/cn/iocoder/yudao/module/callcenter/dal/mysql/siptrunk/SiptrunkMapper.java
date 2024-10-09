package cn.iocoder.yudao.module.callcenter.dal.mysql.siptrunk;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.siptrunk.SiptrunkDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.callcenter.controller.admin.siptrunk.vo.*;

/**
 * 外呼线路 Mapper
 *
 * @author tianyu
 */
@Mapper
public interface SiptrunkMapper extends BaseMapperX<SiptrunkDO> {

    default PageResult<SiptrunkDO> selectPage(SiptrunkPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SiptrunkDO>()
                .likeIfPresent(SiptrunkDO::getName, reqVO.getName())
                .eqIfPresent(SiptrunkDO::getNo, reqVO.getNo())
                .eqIfPresent(SiptrunkDO::getActive, reqVO.getActive())
                .eqIfPresent(SiptrunkDO::getTenantId, reqVO.getTenantId())
                .betweenIfPresent(SiptrunkDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SiptrunkDO::getId));
    }

}