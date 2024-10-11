package cn.iocoder.yudao.module.callcenter.controller.admin.cdr;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.callcenter.controller.admin.cdr.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.cdr.CdrDO;
import cn.iocoder.yudao.module.callcenter.service.cdr.CdrService;

@Tag(name = "管理后台 - 话务数据")
@RestController
@RequestMapping("/callcenter/cdr")
@Validated
public class CdrController {

    @Resource
    private CdrService cdrService;

    @GetMapping("/page")
    @Operation(summary = "获得话务数据分页")
    @PreAuthorize("@ss.hasPermission('callcenter:cdr:query')")
    public CommonResult<PageResult<CdrRespVO>> getCdrPage(@Valid CdrPageReqVO pageReqVO) {
        PageResult<CdrDO> pageResult = cdrService.getCdrPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CdrRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出话务数据 Excel")
    @PreAuthorize("@ss.hasPermission('callcenter:cdr:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCdrExcel(@Valid CdrPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CdrDO> list = cdrService.getCdrPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "话务数据.xls", "数据", CdrRespVO.class,
                        BeanUtils.toBean(list, CdrRespVO.class));
    }

}