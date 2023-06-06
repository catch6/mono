# Mono

Mono是一个基于SpringBoot和SpringCloud的企业级常用组件封装库，它提供了丰富的功能和强大的扩展性，可以帮助开发者快速构建高效、稳定的应用程序。

## Core

配置项

```yaml
mono:
  core:
    enabled: true
    async: true
    json: true
```

## Jwt

配置项

```yaml
mono:
  jwt:
    secret: xxxxxxxxxxxx
```

## Mybatis Plus

配置项

```yaml
spring:
  datasource:
    username: dev
    password: dev
    url: jdbc:mysql://10.0.222.163:3306/mono_dev?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&rewriteBatchedStatements=true

mono:
  mybatis-plus:
    enabled: true
    auto-fill: false
    create-time-field: createTime
    update-time-field: updateTime
```

