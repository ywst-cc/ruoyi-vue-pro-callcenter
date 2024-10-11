package cn.iocoder.yudao.module.callcenter.service.cdr;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.callcenter.controller.admin.cdr.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.cdr.CdrDO;
import cn.iocoder.yudao.module.callcenter.dal.mysql.cdr.CdrMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.callcenter.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link CdrServiceImpl} 的单元测试类
 *
 * @author tianyu
 */
@Import(CdrServiceImpl.class)
public class CdrServiceImplTest extends BaseDbUnitTest {

    @Resource
    private CdrServiceImpl cdrService;

    @Resource
    private CdrMapper cdrMapper;

    @Test
    public void testCreateCdr_success() {
        // 准备参数
        CdrSaveReqVO createReqVO = randomPojo(CdrSaveReqVO.class).setId(null);

        // 调用
        Long cdrId = cdrService.createCdr(createReqVO);
        // 断言
        assertNotNull(cdrId);
        // 校验记录的属性是否正确
        CdrDO cdr = cdrMapper.selectById(cdrId);
        assertPojoEquals(createReqVO, cdr, "id");
    }

    @Test
    public void testUpdateCdr_success() {
        // mock 数据
        CdrDO dbCdr = randomPojo(CdrDO.class);
        cdrMapper.insert(dbCdr);// @Sql: 先插入出一条存在的数据
        // 准备参数
        CdrSaveReqVO updateReqVO = randomPojo(CdrSaveReqVO.class, o -> {
            o.setId(dbCdr.getId()); // 设置更新的 ID
        });

        // 调用
        cdrService.updateCdr(updateReqVO);
        // 校验是否更新正确
        CdrDO cdr = cdrMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, cdr);
    }

    @Test
    public void testUpdateCdr_notExists() {
        // 准备参数
        CdrSaveReqVO updateReqVO = randomPojo(CdrSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> cdrService.updateCdr(updateReqVO), CDR_NOT_EXISTS);
    }

    @Test
    public void testDeleteCdr_success() {
        // mock 数据
        CdrDO dbCdr = randomPojo(CdrDO.class);
        cdrMapper.insert(dbCdr);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbCdr.getId();

        // 调用
        cdrService.deleteCdr(id);
       // 校验数据不存在了
       assertNull(cdrMapper.selectById(id));
    }

    @Test
    public void testDeleteCdr_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> cdrService.deleteCdr(id), CDR_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetCdrPage() {
       // mock 数据
       CdrDO dbCdr = randomPojo(CdrDO.class, o -> { // 等会查询到
           o.setCallId(null);
           o.setCaller(null);
           o.setCallee(null);
           o.setStartTime(null);
           o.setAnswered(null);
           o.setCreateTime(null);
       });
       cdrMapper.insert(dbCdr);
       // 测试 callId 不匹配
       cdrMapper.insert(cloneIgnoreId(dbCdr, o -> o.setCallId(null)));
       // 测试 caller 不匹配
       cdrMapper.insert(cloneIgnoreId(dbCdr, o -> o.setCaller(null)));
       // 测试 callee 不匹配
       cdrMapper.insert(cloneIgnoreId(dbCdr, o -> o.setCallee(null)));
       // 测试 startTime 不匹配
       cdrMapper.insert(cloneIgnoreId(dbCdr, o -> o.setStartTime(null)));
       // 测试 answered 不匹配
       cdrMapper.insert(cloneIgnoreId(dbCdr, o -> o.setAnswered(null)));
       // 测试 createTime 不匹配
       cdrMapper.insert(cloneIgnoreId(dbCdr, o -> o.setCreateTime(null)));
       // 准备参数
       CdrPageReqVO reqVO = new CdrPageReqVO();
       reqVO.setCallId(null);
       reqVO.setCaller(null);
       reqVO.setCallee(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setAnswered(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<CdrDO> pageResult = cdrService.getCdrPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbCdr, pageResult.getList().get(0));
    }

}