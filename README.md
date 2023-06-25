# Mono

Mono是一个基于SpringBoot和SpringCloud的企业级常用组件封装库，它提供了丰富的功能和强大的扩展性，可以帮助开发者快速构建高效、稳定的应用程序。

## 快速开始

### 引入依赖

```
<parent>
    <groupId>net.wenzuo</groupId>
    <artifactId>mono</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath/>
</parent>

...

<dependency>
    <groupId>net.wenzuo</groupId>
    <artifactId>mono-spring-boot-starter-web</artifactId>
</dependency>
```

### 配置建议

开发环境

```yaml
logging:
  level:
    your.package.mapper: debug
```

测试环境

```yaml
logging:
  level:
    your.package.mapper: debug
mono:
  web:
    cors:
      enabled: false
```

生产环境

```yaml
springdoc:
  api-docs:
    enabled: false
  swagger-ui:
    enabled: false
logging:
  level:
    your.package.mapper: debug
mono:
  web:
    cors:
      enabled: false
```

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

Jwt 秘钥生成: 运行 `net.wenzuo.mono.jwt.GenerateKey` 会在控制台输出随机生成的秘钥.

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

## Web

配置项

```yaml
mono:
  web:
    enabled: true
    controller-advice: true
    logging:
      enabled: true
      max-payload-length: null
    cors:
      enabled: true
      configs:
        - pattern: /**
          allow-credentials: true
          allowed-origins:
            - *
          allowed-origin-patterns: [ ]
          allowed-headers:
            - *
          allowed-methods:
            - *
          exposed-headers:
            - *
```