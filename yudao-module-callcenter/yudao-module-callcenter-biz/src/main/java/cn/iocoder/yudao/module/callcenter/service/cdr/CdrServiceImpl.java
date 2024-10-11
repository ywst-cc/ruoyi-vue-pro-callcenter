package cn.iocoder.yudao.module.callcenter.service.cdr;

import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.callcenter.controller.admin.cdr.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.cdr.CdrDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.callcenter.dal.mysql.cdr.CdrMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.callcenter.enums.ErrorCodeConstants.*;

/**
 * 话务数据 Service 实现类
 *
 * @author tianyu
 */
@Service
@Validated
public class CdrServiceImpl implements CdrService {

    @Resource
    private CdrMapper cdrMapper;

    @Override
    @TenantIgnore
    public PageResult<CdrDO> getCdrPage(CdrPageReqVO pageReqVO) {
        return cdrMapper.selectPage(pageReqVO);
    }

    @Override
    @TenantIgnore
    public Long createCdr(CdrDO cdrDO) {
        cdrMapper.insert(cdrDO);
        return cdrDO.getId();
    }

}