package cn.iocoder.yudao.module.callcenter.controller.admin.siptrunk;

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

import cn.iocoder.yudao.module.callcenter.controller.admin.siptrunk.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.siptrunk.SiptrunkDO;
import cn.iocoder.yudao.module.callcenter.service.siptrunk.SiptrunkService;

@Tag(name = "管理后台 - 外呼线路")
@RestController
@RequestMapping("/callcenter/siptrunk")
@Validated
public class SiptrunkController {

    @Resource
    private SiptrunkService siptrunkService;

    @PostMapping("/create")
    @Operation(summary = "创建外呼线路")
    @PreAuthorize("@ss.hasPermission('callcenter:siptrunk:create')")
    public CommonResult<Long> createSiptrunk(@Valid @RequestBody SiptrunkSaveReqVO createReqVO) {
        return success(siptrunkService.createSiptrunk(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新外呼线路")
    @PreAuthorize("@ss.hasPermission('callcenter:siptrunk:update')")
    public CommonResult<Boolean> updateSiptrunk(@Valid @RequestBody SiptrunkSaveReqVO updateReqVO) {
        siptrunkService.updateSiptrunk(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-master")
    @Operation(summary = "更新线路配置为 Master")
    @PreAuthorize("@ss.hasPermission('callcenter:siptrunk:update')")
    public CommonResult<Boolean> updateFileConfigMaster(@RequestParam("id") Long id) {
        siptrunkService.updateSiptrunkMaster(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除外呼线路")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('callcenter:siptrunk:delete')")
    public CommonResult<Boolean> deleteSiptrunk(@RequestParam("id") Long id) {
        siptrunkService.deleteSiptrunk(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得外呼线路")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('callcenter:siptrunk:query')")
    public CommonResult<SiptrunkRespVO> getSiptrunk(@RequestParam("id") Long id) {
        SiptrunkDO siptrunk = siptrunkService.getSiptrunk(id);
        return success(BeanUtils.toBean(siptrunk, SiptrunkRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得外呼线路分页")
    @PreAuthorize("@ss.hasPermission('callcenter:siptrunk:query')")
    public CommonResult<PageResult<SiptrunkRespVO>> getSiptrunkPage(@Valid SiptrunkPageReqVO pageReqVO) {
        PageResult<SiptrunkDO> pageResult = siptrunkService.getSiptrunkPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SiptrunkRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出外呼线路 Excel")
    @PreAuthorize("@ss.hasPermission('callcenter:siptrunk:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSiptrunkExcel(@Valid SiptrunkPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SiptrunkDO> list = siptrunkService.getSiptrunkPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "外呼线路.xls", "数据", SiptrunkRespVO.class,
                        BeanUtils.toBean(list, SiptrunkRespVO.class));
    }

}