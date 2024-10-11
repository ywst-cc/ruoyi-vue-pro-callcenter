package cn.iocoder.yudao.module.callcenter.framework.callcenter.cache;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class SessionCacheDao {

    public static final String SESSION_CACHE_KEY = "session_cache";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 绑定 uuid 和 cdrSessionId
     * @param uuid
     * @param cdrSessionId
     */
    public void bind(String uuid, String cdrSessionId) {
        stringRedisTemplate.opsForHash().put(SESSION_CACHE_KEY, uuid, cdrSessionId);
    }


    /**
     * 解绑 uuid 和 cdrSessionId
     * @param uuid
     */
    public void unbind(String uuid) {
        stringRedisTemplate.opsForHash().delete(SESSION_CACHE_KEY, uuid);
    }


    /**
     * 获取当前uuid对应的cdrSessionId
     *
     * @param uuid
     * @return
     */
    public String getCdrSessionId(String uuid) {
        return (String) stringRedisTemplate.opsForHash().get(SESSION_CACHE_KEY, uuid);
    }
}
