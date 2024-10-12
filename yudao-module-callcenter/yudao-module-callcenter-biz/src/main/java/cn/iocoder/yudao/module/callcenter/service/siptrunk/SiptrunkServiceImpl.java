package cn.iocoder.yudao.module.callcenter.service.siptrunk;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.callcenter.controller.admin.siptrunk.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.siptrunk.SiptrunkDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.callcenter.dal.mysql.siptrunk.SiptrunkMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.callcenter.enums.ErrorCodeConstants.*;

/**
 * 外呼线路 Service 实现类
 *
 * @author tianyu
 */
@Service
@Validated
public class SiptrunkServiceImpl implements SiptrunkService {

    @Resource
    private SiptrunkMapper siptrunkMapper;

    @Override
    @TenantIgnore
    public Long createSiptrunk(SiptrunkSaveReqVO createReqVO) {
        // 插入
        SiptrunkDO siptrunk = BeanUtils.toBean(createReqVO, SiptrunkDO.class);
        siptrunkMapper.insert(siptrunk);
        // 返回
        return siptrunk.getId();
    }

    @Override
    @TenantIgnore
    public void updateSiptrunk(SiptrunkSaveReqVO updateReqVO) {
        // 校验存在
        validateSiptrunkExists(updateReqVO.getId());
        // 更新
        SiptrunkDO updateObj = BeanUtils.toBean(updateReqVO, SiptrunkDO.class);
        siptrunkMapper.updateById(updateObj);
    }

    @Override
    @TenantIgnore
    public void deleteSiptrunk(Long id) {
        // 校验存在
        validateSiptrunkExists(id);
        // 删除
        siptrunkMapper.deleteById(id);
    }

    private void validateSiptrunkExists(Long id) {
        if (siptrunkMapper.selectById(id) == null) {
            throw exception(SIPTRUNK_NOT_EXISTS);
        }
    }

    @Override
    @TenantIgnore
    public SiptrunkDO getSiptrunk(Long id) {
        return siptrunkMapper.selectById(id);
    }

    @Override
    @TenantIgnore
    public PageResult<SiptrunkDO> getSiptrunkPage(SiptrunkPageReqVO pageReqVO) {
        return siptrunkMapper.selectPage(pageReqVO);
    }

    @Override
    @TenantIgnore
    public SiptrunkDO getTenantMasterSiptrunk(Long tenantId) {
        return siptrunkMapper.selectOne(
                new LambdaQueryWrapperX<SiptrunkDO>()
                        .eq(SiptrunkDO::getTenantId, tenantId)
                        .eq(SiptrunkDO::getActive, true)
                        .eq(SiptrunkDO::getMaster, true)
        );
    }

    @Override
    @TenantIgnore
    @Transactional
    public void updateSiptrunkMaster(Long id) {
        // 检验存在
        SiptrunkDO siptrunkDO = siptrunkMapper.selectById(id);
        if (siptrunkDO == null) {
            throw exception(SIPTRUNK_NOT_EXISTS);
        }

        if (siptrunkDO.getTenantId() == null) {
            throw exception(SIPTRUNK_NOT_EXISTS);
        }

        // 更新其他为非 master
        siptrunkMapper.update(
                new LambdaUpdateWrapper<SiptrunkDO>()
                        .eq(SiptrunkDO::getTenantId, siptrunkDO.getTenantId())
                        .set(SiptrunkDO::getMaster, false)
        );
        // 更新
        siptrunkMapper.updateById(new SiptrunkDO().setId(id).setMaster(true));
    }

}