# 1.依赖

依赖

```xml
  <dependency>
      <groupId>commons-lang</groupId>
      <artifactId>commons-lang</artifactId>
  </dependency>
  <dependency>
      <groupId>cn.hutool</groupId>
      <artifactId>hutool-jwt</artifactId>
  </dependency>
  <dependency>
      <groupId>cn.hutool</groupId>
      <artifactId>hutool-crypto</artifactId>
  </dependency>
```

引入该common包的可选依赖

```xml
  <!--可选 使用PageParam必选-->
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>
  <!--可选 使用@EnableRedis相关必选-->
  <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-redis</artifactId>
  </dependency>
  <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-pool2</artifactId>
  </dependency>
```

# 2.功能注解

* 启用`@EnableDateConvertAndFormat`注解会将以下时间格式转换为对应的时间类型(包括Jackson序列化)
    * `yyyy-MM-dd` -> `LocalDate`
    * `yyyy-MM-dd HH:mm:ss` -> `LocalDateTime`
    * `HH:mm:ss` -> `LocalTime`

> 详见:`com.bobabrewery.common.config.LocalDateTimeConfig`

* 启用`@EnableRedis`注解会自动配置`redisTemplate`,`redisCacheManager`
    * redisTemplate<K,V> Value使用Jackson序列化
    * 默认开启redis事务支持
    * Redis缓存注解有效期1天

# 3.预定义拦截器

* 权限拦截
    * 实现`com.bobabrewery.common.interceptor.AuthInterceptor`接口,重写`checkToken`方法,返回`true`为通过
    * Controller中的方法可添加`@NoAuth`避免接口被拦截
* 频率限制
    * 实现`com.bobabrewery.common.interceptor.LimitInterceptor`接口,实现`loadRedis`方法加载`redisClient`,也可以重载`checkLimit`自定义拦截算法
    * 仅添加`@Limit`注解的方法可被拦截,详见`com.bobabrewery.common.annotation.Limit`

> 拦截器配置需要实现`WebMvcConfigurer`配置
