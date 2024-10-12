package cn.iocoder.yudao.module.callcenter.service.siptrunk;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.callcenter.controller.admin.siptrunk.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.siptrunk.SiptrunkDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 外呼线路 Service 接口
 *
 * @author tianyu
 */
public interface SiptrunkService {

    /**
     * 创建外呼线路
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSiptrunk(@Valid SiptrunkSaveReqVO createReqVO);

    /**
     * 更新外呼线路
     *
     * @param updateReqVO 更新信息
     */
    void updateSiptrunk(@Valid SiptrunkSaveReqVO updateReqVO);

    /**
     * 删除外呼线路
     *
     * @param id 编号
     */
    void deleteSiptrunk(Long id);

    /**
     * 获得外呼线路
     *
     * @param id 编号
     * @return 外呼线路
     */
    SiptrunkDO getSiptrunk(Long id);

    /**
     * 获得外呼线路分页
     *
     * @param pageReqVO 分页查询
     * @return 外呼线路分页
     */
    PageResult<SiptrunkDO> getSiptrunkPage(SiptrunkPageReqVO pageReqVO);

    /**
     * 获取当前租户主配置线路
     * @param tenantId 租户ID
     * @return
     */
    SiptrunkDO getTenantMasterSiptrunk(Long tenantId);

    /**
     * 设置线路为主配置
     * @param id
     */
    void updateSiptrunkMaster(Long id);
}