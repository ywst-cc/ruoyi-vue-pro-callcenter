package cn.iocoder.yudao.module.callcenter.service.siptrunk;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.callcenter.controller.admin.siptrunk.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.siptrunk.SiptrunkDO;
import cn.iocoder.yudao.module.callcenter.dal.mysql.siptrunk.SiptrunkMapper;
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
 * {@link SiptrunkServiceImpl} 的单元测试类
 *
 * @author tianyu
 */
@Import(SiptrunkServiceImpl.class)
public class SiptrunkServiceImplTest extends BaseDbUnitTest {

    @Resource
    private SiptrunkServiceImpl siptrunkService;

    @Resource
    private SiptrunkMapper siptrunkMapper;

    @Test
    public void testCreateSiptrunk_success() {
        // 准备参数
        SiptrunkSaveReqVO createReqVO = randomPojo(SiptrunkSaveReqVO.class).setId(null);

        // 调用
        Long siptrunkId = siptrunkService.createSiptrunk(createReqVO);
        // 断言
        assertNotNull(siptrunkId);
        // 校验记录的属性是否正确
        SiptrunkDO siptrunk = siptrunkMapper.selectById(siptrunkId);
        assertPojoEquals(createReqVO, siptrunk, "id");
    }

    @Test
    public void testUpdateSiptrunk_success() {
        // mock 数据
        SiptrunkDO dbSiptrunk = randomPojo(SiptrunkDO.class);
        siptrunkMapper.insert(dbSiptrunk);// @Sql: 先插入出一条存在的数据
        // 准备参数
        SiptrunkSaveReqVO updateReqVO = randomPojo(SiptrunkSaveReqVO.class, o -> {
            o.setId(dbSiptrunk.getId()); // 设置更新的 ID
        });

        // 调用
        siptrunkService.updateSiptrunk(updateReqVO);
        // 校验是否更新正确
        SiptrunkDO siptrunk = siptrunkMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, siptrunk);
    }

    @Test
    public void testUpdateSiptrunk_notExists() {
        // 准备参数
        SiptrunkSaveReqVO updateReqVO = randomPojo(SiptrunkSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> siptrunkService.updateSiptrunk(updateReqVO), SIPTRUNK_NOT_EXISTS);
    }

    @Test
    public void testDeleteSiptrunk_success() {
        // mock 数据
        SiptrunkDO dbSiptrunk = randomPojo(SiptrunkDO.class);
        siptrunkMapper.insert(dbSiptrunk);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbSiptrunk.getId();

        // 调用
        siptrunkService.deleteSiptrunk(id);
       // 校验数据不存在了
       assertNull(siptrunkMapper.selectById(id));
    }

    @Test
    public void testDeleteSiptrunk_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> siptrunkService.deleteSiptrunk(id), SIPTRUNK_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetSiptrunkPage() {
       // mock 数据
       SiptrunkDO dbSiptrunk = randomPojo(SiptrunkDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setNo(null);
           o.setActive(null);
           o.setCreateTime(null);
       });
       siptrunkMapper.insert(dbSiptrunk);
       // 测试 name 不匹配
       siptrunkMapper.insert(cloneIgnoreId(dbSiptrunk, o -> o.setName(null)));
       // 测试 no 不匹配
       siptrunkMapper.insert(cloneIgnoreId(dbSiptrunk, o -> o.setNo(null)));
       // 测试 active 不匹配
       siptrunkMapper.insert(cloneIgnoreId(dbSiptrunk, o -> o.setActive(null)));
       // 测试 createTime 不匹配
       siptrunkMapper.insert(cloneIgnoreId(dbSiptrunk, o -> o.setCreateTime(null)));
       // 准备参数
       SiptrunkPageReqVO reqVO = new SiptrunkPageReqVO();
       reqVO.setName(null);
       reqVO.setNo(null);
       reqVO.setActive(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<SiptrunkDO> pageResult = siptrunkService.getSiptrunkPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbSiptrunk, pageResult.getList().get(0));
    }

}