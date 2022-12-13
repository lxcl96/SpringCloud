参考资料连接：[doc · milo/milo-springcloud - 码云 - 开源中国 (gitee.com)](https://gitee.com/moyispace/milo-springcloud/tree/master/doc)

# 1. 版本选择

+ SpringCloud：*Hoxton.SR1*
+ SpringBoot：*2.2.2.RELEASE*
+ SpringCloud Alibaba：*2.1.0.RELEASE*
+ JDK：*1.8*
+ Maven：*3.5+*
+ MySQL：*5.7+*

## 1.1SpringBoot和SprigCloud版本选择规则

+ 只使用SpringBoot，那么可以直接选择最新版

+ 同时使用SpringBoot和SpringCloud，则需要以SpringCloud为选择依据（因为SpringCloud太乱了）

  <img src='img\image-20221212153001840.png'>

  <img src='img\image-20221212153104712.png'>

# 2. SpringCloud组件选择

<img src='img\image-20221212155343381.png'>



# 3. 参考文档

+ springcloud官网参考文档：`https://cloud.spring.io/spring-cloud-static/Hoxton.SR1/reference/htmlsingle/`
+ springcloud第三方翻译中文文档：`https://www.bookstack.cn/read/spring-cloud-docs/docs-index.md`
+ springboot官网参考文档：`https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/`

# 4. 微服务项目架构搭建

## 4.1 创建总父结构 cloud2020

### 4.1.1 创建父项目

<img src='img\image-20221212161216736.png'>

### 4.1.2 配置Maven版本

<img src='img\image-20221212162138134.png'>

### 4.1.3 配置字符编码

<img src='img\image-20221212161952277.png'>

### 4.1.4 启用注解自动配置处理器

<img src='img\image-20221212162329708.png'>

### 4.1.5 设置Java编译版本

<img src='img\image-20221212162524203.png'>

### 4.1.6 设置项目SDK及语言

<img src='img\image-20221212162742338.png'>

## 4.2 父级pom指定组件版本

​	可以将项目cloud2020项目下的src文件夹删除，因为该项目是做依赖管理的。**一定要将打包方式改为pom**

## 4.3 跳过Maven test步骤

+ 通过maven插件方式

  ```xml
  <build><!-- maven中跳过单元测试 -->
      <plugins>
          <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-surefire-plugin</artifactId>
              <configuration>
                  <skip>true</skip>
              </configuration>
          </plugin>
      </plugins>
  </build>
  ```

+ idea工具

  <img src='img\image-20221213091843927.png'>

## 4.4 pom依赖

​	比如mirror镜像为阿里云镜像，且仓库有此版本的软件包，但是idea无法拉取。

```sh
# 执行maven命令获取指定版本的jar包
mvn dependency:get -DgroupId=com.alibaba -DartifactId=druid -Dversion=1.1.16
```

## 4.5 热部署

+ 引入依赖

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <scope>runtime</scope>
      <optional>true</optional>
  </dependency>
  ```

+ 父级pom文件中，添加maven插件

  ```xml
  <build>
      <plugins>
          <plugin>
              <!-- 热部署-->
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-maven-plugin</artifactId>
              <configuration>
                  <fork>true</fork>
                  <addResources>true</addResources>
              </configuration>
          </plugin>
      </plugins>
  </build>
  ```

+ idea中开启自动编译

  <img src='img\image-20221213150841584.png'>

+ 更新idea参数

  <img src='img\image-20221213151401220.png'>

  <img src='img\image-20221213151423919.png'>

+ 重启idea

# 5. Rest微服务工程构建*

<img src='img\image-20221213092727607.png'>

## 5.1 提供者支付Module模块 

### 5.1.1 建立子项目 

*支付模块：cloud-provider-payment8001*

<img src='img\image-20221213093101095.png'>

> 父工程pom文件多一个modules标签，子项目多了一个parent标签

### 5.1.2 修改pom文件

添加依赖

```xml
 <dependencies>
        <!-- web和actuator必备-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        <!--mysql-connector-java-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

```

### 5.1.3 修改配置文件

```yaml
server:
  port: 8001

spring:
  application:
    name: cloud-payment-service
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource            # 当前数据源操作类型
    driver-class-name: org.gjt.mm.mysql.Driver              # mysql驱动包 com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db2019?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: 123456


mybatis:
  mapperLocations: classpath:mapper/*.xml
  type-aliases-package: com.ly.springcloud.entities    # 所有Entity别名类所在包
```

### 5.1.4 创建springboot主启动类

```java
package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * FileName:PaymentMain8001.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description: SpringBoot主启动类
 */
@SpringBootApplication
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class,args);
    }
}
```

### 5.1.5 编写业务代码

#### 5.1.5.1 建表SQL

```sql
# 库名 db2019
CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `serial` varchar(200) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

```

#### 5.1.3.2 entities

+ 与表对应的实体类

  ```java
  /**
   * FileName:Payment.class
   * Author:ly
   * Date:2022/12/13 0013
   * Description: 对应数据库表payment
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class Payment implements Serializable {
      private Long id;
      private String serial;
  
  }
  ```

+ 回传给前端的json封装类，可以理解为vo

  ```java
  /**
   * FileName:CommonResult.class
   * Author:ly
   * Date:2022/12/13 0013
   * Description: 专门用于传递给前端的数据json封装体，可以理解为vo
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class CommonResult<T> {
      //404 NOT FOUND 
      private Integer code;
      private String message;
      private T data;
  
      public CommonResult(Integer code, String message) {
          this(code,message,null);
      }
  }
  ```

#### 5.1.3.3 dao

+ 接口定义

  ```java
  /**
   * FileName:Payment.class
   * Author:ly
   * Date:2022/12/13 0013
   * Description: dao主类方便使用mapper文件（整合mybatis）
   *  使用@Mapper注解，代替配置文件映射
   */
  @Mapper
  public interface PaymentDao {
  
      /**
       * 新增支付信息
       * @param payment 支付实体类
       * @return 结果
       */
      public int create(Payment payment);
  
      /**
       * 根据id获取支付单据
       * @param id 支付单据id
       * @return payment
       */
      public Payment getPaymentById(@Param("id") Long id);
  }
  ```

+ mapper映射文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <!--指定指定实现接口 即：接口的全类名-->
  <mapper namespace="com.ly.springcloud.dao.PaymentDao">
  
      <sql id="columns"> id,serial </sql>
      <sql id="table"> payment </sql>
  
      <!--
          property:为Java实体类属性
          column:为数据库字段
       -->
      <resultMap id="resultColumns" type="com.ly.springcloud.entieies.Payment">
          <id property="id" column="id" javaType="java.lang.Long" jdbcType="BIGINT" />
          <id property="serial" column="serial" javaType="java.lang.String" jdbcType="VARCHAR" />
      </resultMap>
  
      <select id="getPaymentById" parameterType="long" resultMap="resultColumns">
          select
              <include refid="columns" />
          from
              <include refid="table" /> t
          where
              t.id=#{id}
      </select>
  
      <!--
          配置文件开启了包别名（爆红不影响），
          useGeneratedKeys 使用生产的主键
  		
  	#{serial}而不是#{payment.serial}，mybatis中实体类就是map直接用#{}取即可
          -->
      <insert id="create" parameterType="Payment" useGeneratedKeys="true" keyProperty="id">
          insert into
              <include refid="table" />(serial)
          values
              (#{serial})
      </insert>
  
  </mapper>
  ```

#### 5.1.3.4 service

+ 接口

  ```java
  public interface PaymentService {
  
      public int create(Payment payment);
      public Payment getPaymentById(Long id);
  }
  ```

+ 实现类

  ```java
  @Service
  public class PaymentServiceImpl implements PaymentService {
  
      @Autowired
      private PaymentDao paymentDao;
  
  
      @Override
      public int create(Payment payment) {
          assert payment !=null :"error,payment is null!";
          return paymentDao.create(payment);
      }
  
      @Override
      public Payment getPaymentById(Long id) {
          assert id != null:"error, id is null!";
          return paymentDao.getPaymentById(id);
      }
  }
  ```

#### 5.1.3.5 controller

```java
@Slf4j
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping(path = {"/payment/create"})
    public CommonResult create(Payment payment) {

        int ret = paymentService.create(payment);
        if (ret > 0) {
           //插入成功
            return new CommonResult(
                    ReturnResultEnum.NORMAL.getCode(),
                    ReturnResultEnum.NORMAL.getMessage(),
                    ret
            );
        }

        log.error("插入数据库失败");
        return new CommonResult(
                ReturnResultEnum.INNER_ERROR.getCode(),
                ReturnResultEnum.INNER_ERROR.getMessage()
                );
    }

    @GetMapping(path = {"/payment/get/{id}"})
    public CommonResult getPaymentById(@PathVariable("id") Long id) {

        if (null == id || 0 >= id) {
            log.info("查询的id {} 不正确",id);

            return new CommonResult(
                    ReturnResultEnum.NOT_FOUND.getCode(),
                    ReturnResultEnum.NOT_FOUND.getMessage()
            );
        }


        Payment payment = paymentService.getPaymentById(id);
        if (null != payment) {
            return new CommonResult(
                    ReturnResultEnum.NORMAL.getCode(),
                    ReturnResultEnum.NORMAL.getMessage(),
                    payment
            );
        }

        //失败
        return new CommonResult(
                ReturnResultEnum.NOT_FOUND.getCode(),
                ReturnResultEnum.NOT_FOUND.getMessage()
        );
    }
}
```

### 5.1.6 测试

<img src='img\image-20221213150139141.png'>

<img src='img\image-20221213150257936.png'>

## 5.2 消费者订单Module模块

### 5.2.1 建立子项目

如上，项目名**cloud-consumer-order80**

### 5.2.2 修改pom文件

引入依赖：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### 5.2.3 修改配置文件

```yaml
server:
  port: 80

spring:
  application:
    name: cloud-order-service
```

### 5.2.4 创建SpringBoot1启动类

```java
@SpringBootApplication
public class OrderMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
```

### 5.2.5 编写业务代码

#### 5.2.5.1 实体类（同上）

+ Payment类
+ CommonResult\<T>类

#### 5.2.5.2 RestTemplate

是spring对HttpClient进行的封装类，用于处理和发生网络请求。

RestTemplate提供了多种便捷访问远程Http服务的方法， 
是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具集。

**官网地址**

https://docs.spring.io/spring-framework/docs/5.2.2.RELEASE/javadoc-api/org/springframework/web/client/RestTemplate.html

**使用**
使用restTemplate访问restful接口非常的简单粗暴无脑。
(url, requestMap, ResponseBean.class)这三个参数分别代表 
REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。

```java
//加入ioc容器中
@Configuration
public class ApplicationContextConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
```

#### 5.2.5.3 controller

```java
/**
 * FileName:OrderController.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description: 消费者服务
 */
@Slf4j
@RestController
public class OrderController {
    public static final String PAYMENT_URL = "http://localhost:8001";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        log.info("consumer 接收到一条新增payment消息 serial={}",payment.getSerial());
        //调用cloud-payment-service(请求地址，参数，返回消息的内容)
        return restTemplate.postForObject(
                PAYMENT_URL + "/payment/create",
                payment,
                CommonResult.class
        );
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        log.info("consumer 接收到一条请求payment消息 id={}",id);
        //(请求地址，返回消息的内容)
        return restTemplate.getForObject(
                PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }
}
```

### 5.2.6 测试

+ get请求，没问题

  <img src='img\image-20221213163126001.png'>

+ post请求（以get方式代发）,**看着没问题，但实际上并没有保存到数据中**

  <img src='img\image-20221213163219183.png'>

  <img src='img\image-20221213163435169.png'>

出现这个的原因就是因为我们是以get方式访问consumer连接，自动将参数封装到Payment payment中，但是内部又以post方式提交到provider端（实际上也只接收post信息），导致参数是被封装好的payment在请求体制中，此时Springmvc就不能直接将连接问号？直接封装到新的Payment payment（因为实际就是没有），所为保存的数据就为空。

只有加上@RequestBody注解，表示从post方式的请求体(json数据)中获取payment的参数（不是payment，而是根据属性如serial封装到payment）。

**经过两次请求包装，导致请求参数发生了变化，无法直接从链接中获取（只能从请求体中）**

```java
//修改provider端获取参数的方式，从请求体中取

// provider端必须是从@RequestBody 请求体获取参数，因为请求被封在请求体中，而不是直链
    @PostMapping(path = {"/payment/create"})
    public CommonResult create(@RequestBody Payment payment) {...}
```

**此时再次发送上面的请求就成功插入了**

<img src='img\image-20221213165004558.png'>

#### 5.2.6.1 注意

***上面改了provider端获取参数的方式，则不能再使用原来的直链请求post方式访问。因为现在参数是直接从请求体找的了（所以必须要为json格式）**

+ 错误请求发送：

  <img src='img\image-20221213165351490.png'>

+ 正确请求发送：

  <img src='img\image-20221213170122243.png'>
