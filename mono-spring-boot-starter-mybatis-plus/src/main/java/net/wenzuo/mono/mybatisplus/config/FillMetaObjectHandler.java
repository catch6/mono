package net.wenzuo.mono.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.mybatisplus.properties.MybatisPlusProperties;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author Catch
 * @since 2021-07-11
 */
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mono.mybatis-plus.auto-fill", matchIfMissing = false)
@Configuration
public class FillMetaObjectHandler implements MetaObjectHandler {

    private final MybatisPlusProperties mybatisPlusProperties;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, mybatisPlusProperties.getCreateTimeField(), LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, mybatisPlusProperties.getUpdateTimeField(), LocalDateTime::now, LocalDateTime.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, mybatisPlusProperties.getUpdateTimeField(), LocalDateTime::now, LocalDateTime.class);
    }

}
