package cn.iocoder.yudao.module.callcenter.dal.mysql.cdr;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.cdr.CdrDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.callcenter.controller.admin.cdr.vo.*;

/**
 * 话务数据 Mapper
 *
 * @author tianyu
 */
@Mapper
public interface CdrMapper extends BaseMapperX<CdrDO> {

    default PageResult<CdrDO> selectPage(CdrPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CdrDO>()
                .eqIfPresent(CdrDO::getCallId, reqVO.getCallId())
                .eqIfPresent(CdrDO::getCaller, reqVO.getCaller())
                .eqIfPresent(CdrDO::getCallee, reqVO.getCallee())
                .betweenIfPresent(CdrDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(CdrDO::getAnswered, reqVO.getAnswered())
                .betweenIfPresent(CdrDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CdrDO::getId));
    }

}