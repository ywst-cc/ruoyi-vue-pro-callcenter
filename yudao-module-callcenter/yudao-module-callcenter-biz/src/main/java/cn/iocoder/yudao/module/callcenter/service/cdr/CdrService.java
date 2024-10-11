package cn.iocoder.yudao.module.callcenter.service.cdr;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.callcenter.controller.admin.cdr.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.cdr.CdrDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 话务数据 Service 接口
 *
 * @author tianyu
 */
public interface CdrService {

    /**
     * 获得话务数据分页
     *
     * @param pageReqVO 分页查询
     * @return 话务数据分页
     */
    PageResult<CdrDO> getCdrPage(CdrPageReqVO pageReqVO);


    /**
     * 创建话务数据
     *
     * @param cdrDO cdr
     * @return 主键ID
     */
    Long createCdr(CdrDO cdrDO);
}