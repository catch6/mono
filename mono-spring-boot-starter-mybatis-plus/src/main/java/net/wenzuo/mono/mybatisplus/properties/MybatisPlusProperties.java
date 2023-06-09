package net.wenzuo.mono.mybatisplus.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Data
@ConfigurationProperties(prefix = "mono.mybatis-plus")
public class MybatisPlusProperties {

    /**
     * 是否启用 mybatis-plus 模块
     */
    private boolean enabled = true;

    /**
     * 是否启用 mysql 分页插件
     */
    private boolean interceptor = true;

    /**
     * 是否启用自动填充 create_time update_time 为当前时间(mapper.xml 中除外)
     */
    private Boolean autoFill = false;

    /**
     * entity 创建时间字段名(Java 对象的字段, 非数据库字段),在执行 insert 时会自动填充 LocalDateTime::now
     * 注意: 在 mapper.xml 中写的 sql 不会自动填充
     */
    private String createTimeField = "createTime";
    /**
     * entity 更新时间字段名(Java 对象的字段, 非数据库字段),在执行 insert/update 时会自动填充 LocalDateTime::now
     * 注意: 在 mapper.xml 中写的 sql 不会自动填充
     */
    private String updateTimeField = "updateTime";

}
