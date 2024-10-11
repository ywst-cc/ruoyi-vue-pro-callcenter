package cn.iocoder.yudao.module.callcenter.service.extension;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.callcenter.controller.admin.extension.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.extension.ExtensionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.callcenter.dal.mysql.extension.ExtensionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.callcenter.enums.ErrorCodeConstants.*;

/**
 * CTI 分机 Service 实现类
 *
 * @author tianyu
 */
@Service
@Validated
public class ExtensionServiceImpl implements ExtensionService {

    @Resource
    private ExtensionMapper extensionMapper;

    @Override
    @TenantIgnore
    public Long createExtension(ExtensionSaveReqVO createReqVO) {
        // 插入
        ExtensionDO extension = BeanUtils.toBean(createReqVO, ExtensionDO.class);
        extensionMapper.insert(extension);
        // 返回
        return extension.getId();
    }

    @Override
    @TenantIgnore
    public void updateExtension(ExtensionSaveReqVO updateReqVO) {
        // 校验存在
        validateExtensionExists(updateReqVO.getId());
        // 更新
        ExtensionDO updateObj = BeanUtils.toBean(updateReqVO, ExtensionDO.class);
        extensionMapper.updateById(updateObj);
    }

    @Override
    @TenantIgnore
    public void deleteExtension(Long id) {
        // 校验存在
        validateExtensionExists(id);
        // 删除
        extensionMapper.deleteById(id);
    }

    private void validateExtensionExists(Long id) {
        if (extensionMapper.selectById(id) == null) {
            throw exception(EXTENSION_NOT_EXISTS);
        }
    }

    @Override
    @TenantIgnore
    public ExtensionDO getExtension(Long id) {
        return extensionMapper.selectById(id);
    }

    @Override
    @TenantIgnore
    public PageResult<ExtensionDO> getExtensionPage(ExtensionPageReqVO pageReqVO) {
        return extensionMapper.selectPage(pageReqVO);
    }

    @Override
    @TenantIgnore
    public ExtensionDO getExtensionByCaller(String caller) {
        return extensionMapper.selectOne(
                new LambdaQueryWrapperX<ExtensionDO>()
                        .eq(ExtensionDO::getExtension, caller)
        );
    }

}