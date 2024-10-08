package cn.iocoder.yudao.module.callcenter.dal.mysql.extension;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.extension.ExtensionDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.callcenter.controller.admin.extension.vo.*;

/**
 * CTI 分机 Mapper
 *
 * @author tianyu
 */
@Mapper
public interface ExtensionMapper extends BaseMapperX<ExtensionDO> {

    default PageResult<ExtensionDO> selectPage(ExtensionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ExtensionDO>()
                .eqIfPresent(ExtensionDO::getExtension, reqVO.getExtension())
                .likeIfPresent(ExtensionDO::getDomain, reqVO.getDomain())
                .eqIfPresent(ExtensionDO::getOwnerUserId, reqVO.getOwnerUserId())
                .betweenIfPresent(ExtensionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ExtensionDO::getTenantId, reqVO.getTenantId())
                .eqIfPresent(ExtensionDO::getActive, reqVO.getActive())
                .orderByDesc(ExtensionDO::getId));
    }

}