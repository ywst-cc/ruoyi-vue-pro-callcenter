package cn.iocoder.yudao.module.callcenter.service.extension;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.callcenter.controller.admin.extension.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.extension.ExtensionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * CTI 分机 Service 接口
 *
 * @author tianyu
 */
public interface ExtensionService {

    /**
     * 创建CTI 分机
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createExtension(@Valid ExtensionSaveReqVO createReqVO);

    /**
     * 更新CTI 分机
     *
     * @param updateReqVO 更新信息
     */
    void updateExtension(@Valid ExtensionSaveReqVO updateReqVO);

    /**
     * 删除CTI 分机
     *
     * @param id 编号
     */
    void deleteExtension(Long id);

    /**
     * 获得CTI 分机
     *
     * @param id 编号
     * @return CTI 分机
     */
    ExtensionDO getExtension(Long id);

    /**
     * 获得CTI 分机分页
     *
     * @param pageReqVO 分页查询
     * @return CTI 分机分页
     */
    PageResult<ExtensionDO> getExtensionPage(ExtensionPageReqVO pageReqVO);

    /**
     * 查询CTI 分机
     * @param caller
     * @return
     */
    ExtensionDO getExtensionByCaller(String caller);

}