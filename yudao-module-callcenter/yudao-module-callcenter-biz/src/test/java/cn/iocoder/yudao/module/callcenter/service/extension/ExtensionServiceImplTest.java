package cn.iocoder.yudao.module.callcenter.service.extension;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.callcenter.controller.admin.extension.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.extension.ExtensionDO;
import cn.iocoder.yudao.module.callcenter.dal.mysql.extension.ExtensionMapper;
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
 * {@link ExtensionServiceImpl} 的单元测试类
 *
 * @author tianyu
 */
@Import(ExtensionServiceImpl.class)
public class ExtensionServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ExtensionServiceImpl extensionService;

    @Resource
    private ExtensionMapper extensionMapper;

    @Test
    public void testCreateExtension_success() {
        // 准备参数
        ExtensionSaveReqVO createReqVO = randomPojo(ExtensionSaveReqVO.class).setId(null);

        // 调用
        Long extensionId = extensionService.createExtension(createReqVO);
        // 断言
        assertNotNull(extensionId);
        // 校验记录的属性是否正确
        ExtensionDO extension = extensionMapper.selectById(extensionId);
        assertPojoEquals(createReqVO, extension, "id");
    }

    @Test
    public void testUpdateExtension_success() {
        // mock 数据
        ExtensionDO dbExtension = randomPojo(ExtensionDO.class);
        extensionMapper.insert(dbExtension);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ExtensionSaveReqVO updateReqVO = randomPojo(ExtensionSaveReqVO.class, o -> {
            o.setId(dbExtension.getId()); // 设置更新的 ID
        });

        // 调用
        extensionService.updateExtension(updateReqVO);
        // 校验是否更新正确
        ExtensionDO extension = extensionMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, extension);
    }

    @Test
    public void testUpdateExtension_notExists() {
        // 准备参数
        ExtensionSaveReqVO updateReqVO = randomPojo(ExtensionSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> extensionService.updateExtension(updateReqVO), EXTENSION_NOT_EXISTS);
    }

    @Test
    public void testDeleteExtension_success() {
        // mock 数据
        ExtensionDO dbExtension = randomPojo(ExtensionDO.class);
        extensionMapper.insert(dbExtension);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbExtension.getId();

        // 调用
        extensionService.deleteExtension(id);
       // 校验数据不存在了
       assertNull(extensionMapper.selectById(id));
    }

    @Test
    public void testDeleteExtension_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> extensionService.deleteExtension(id), EXTENSION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetExtensionPage() {
       // mock 数据
       ExtensionDO dbExtension = randomPojo(ExtensionDO.class, o -> { // 等会查询到
           o.setExtension(null);
           o.setDomain(null);
           o.setOwnerUserId(null);
           o.setCreateTime(null);
           o.setTenantId(null);
           o.setActive(null);
       });
       extensionMapper.insert(dbExtension);
       // 测试 extension 不匹配
       extensionMapper.insert(cloneIgnoreId(dbExtension, o -> o.setExtension(null)));
       // 测试 domain 不匹配
       extensionMapper.insert(cloneIgnoreId(dbExtension, o -> o.setDomain(null)));
       // 测试 ownerUserId 不匹配
       extensionMapper.insert(cloneIgnoreId(dbExtension, o -> o.setOwnerUserId(null)));
       // 测试 createTime 不匹配
       extensionMapper.insert(cloneIgnoreId(dbExtension, o -> o.setCreateTime(null)));
       // 测试 tenantId 不匹配
       extensionMapper.insert(cloneIgnoreId(dbExtension, o -> o.setTenantId(null)));
       // 测试 active 不匹配
       extensionMapper.insert(cloneIgnoreId(dbExtension, o -> o.setActive(null)));
       // 准备参数
       ExtensionPageReqVO reqVO = new ExtensionPageReqVO();
       reqVO.setExtension(null);
       reqVO.setDomain(null);
       reqVO.setOwnerUserId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setTenantId(null);
       reqVO.setActive(null);

       // 调用
       PageResult<ExtensionDO> pageResult = extensionService.getExtensionPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbExtension, pageResult.getList().get(0));
    }

}