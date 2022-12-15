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

  ```sh
  # --------postman直接发送json请求体
  POST /payment/create HTTP/1.1   						#	请求行
  cookie:JSESSIONID=5D8272F448D81F519356938A5F1953966		  #|
  content-type:application/jsonn							#|
  user-agent:PostmanRuntime/7.28.00					     #|
  accept:*/**											   #|
  cache-control:no-cachee								    #| 	请求头
  postman-token:a8972621-ce80-4ba1-adb9-59526538bca88		  #|
  host:localhost:80011								   #|
  accept-encoding:gzip, deflate, brr						#|
  connection:keep-alivee								   #|
  content-length:244									   #|
  													 #	空一行隔开
  {											    #+
      "serial":77777								 #+ 请求体
  }											    #+
  
  
  # -------通过consumer包装后发送的请求
  POST /payment/create HTTP/1.1
  accept:application/json, application/*+jsonn
  content-type:application/jsonn
  user-agent:Java/1.8.0_3011
  host:localhost:80011
  connection:keep-alivee
  content-length:299
  
  {"id":null,"serial":"888888"}
  ```

## 5.3 工程重构

显而易见工程consumer和工程provider出线了重复类`CommonResult<T>`和`Payment`，所以建立一个第三方工程用于存放重复的代码。

### 5.3.1 建立第三方工程 

**cloud-api-commons**

<img src='img\image-20221214102524005.png'>

### 5.3.2 修改pom文件

```xml
<dependencies>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
        <version>5.1.0</version>
    </dependency>
</dependencies>
```

### 5.3.3 将通用类放在该项目下

**Payment**，**CommonResult\<T>**

### 5.3.4 将该项目安装到maven仓库

```bash
mvn clean
mvn test
mvn package
mvn install
```

### 5.3.5 修改consumer和provider项目

+ 直接删除重复的两个类`Payment`、`CommonResult<T>`（**然后会报错**）

+ 这个两个报错的项目分别引入刚才打包的cloud-api-commons依赖

  ```xml
  <dependency>
      <groupId>com.ly.springcloud</groupId>
      <artifactId>cloud-api-commons</artifactId>
      <version>1.0-SNAPSHOT</version>
  </dependency>
  ```

### 5.3.6 运行测试

运行正常

# 6. Eureka服务注册与发现

Eureka官网：[Issues · Netflix/eureka · GitHub](https://github.com/Netflix/eureka/wiki)

## 6.1 Eureka基础知识

### 6.1.1 服务治理

  Spring Cloud 封装了 Netflix 公司开发的 Eureka 模块来实现服务治理

  在传统的rpc远程调用框架中，管理每个服务与服务之间依赖关系比较复杂，管理比较复杂，所以需要使用**服务治理，管理服务于服务之间依赖关系，可以实现服务调用、负载均衡、容错等，实现服务发现与注册**。

### 6.1.2 服务注册

​	Eureka采用了CS的设计架构，Eureka Server 作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务，使用 Eureka的客户端连接到 Eureka Server并维持心跳连接。这样系统的维护人员就可以通过 Eureka Server 来监控系统中各个微服务是否正常运行。
​	在服务注册与发现中，有一个注册中心。当服务器启动的时候，会把当前自己服务器的信息 比如 服务地址通讯地址等以别名方式注册到注册中心上。另一方（消费者|服务提供者），以该别名的方式去注册中心上获取到实际的服务通讯地址，然后再实现本地RPC调用RPC远程调用。框架核心设计思想：在于注册中心，因为使用注册中心管理每个服务与服务之间的一个依赖关系(服务治理概念)。在任何rpc远程框架中，都会有一个注册中心(存放服务地址相关信息(接口地址))。

​		<font color='red'>***下左图是Eureka系统架构，右图是Dubbo的架构，请对比***</font>

<img src='img\image-20221214110858093.png'>

### 6.1.3 Eureka两大组件

+ **Eureka Server**提供服务注册服务

  ​	各个微服务节点通过配置启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观看到。

+ **EurekaClient**通过注册中心进行访问

  ​	是一个Java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询(round-robin)负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳(默认周期为30秒)。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除（默认90秒）

## 6.2 单机Eureka构建步骤

### 6.2.1 创建服务注册中心项目

#### 6.2.1.1 创建服务注册中心

创建项目**cloud-eureka-server7001**

<img src='img\image-20221214112906209.png'>

#### 6.2.1.2 修改pom文件

```xml
dependencies>
        <!--eureka-server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
        <dependency>
            <groupId>com.ly.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
        <!--boot web actuator-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!--一般通用配置-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
    </dependencies>
```

#### 6.2.1.3 修改配置文件

```yaml
server:
  port: 7001
spring:
  application:
    name: cloud-eureka-server
eureka:
  instance:
    hostname: localhost #eureka服务端的实例名称
  client:
    #false表示不向注册中心注册自己。
    register-with-eureka: false
    #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址。
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

#### 6.2.1.4 创建启动类并启用server端

```java
@SpringBootApplication
@EnableEurekaServer //表明当前服务是一个eureka的sever
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class,args);
    }
}
```

#### 6.2.1.5 测试

<img src='img\image-20221214120000248.png'>

### 6.2.2 将项目（EurekaClient）provider注册到EurekaServer

#### 6.2.2.1 引入Eureka Client端依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

#### 6.2.2.2 修改配置文件，配置eureka

```yaml
eureka:
  client:
  #表示是否将自己注册进EurekaServer默认为true。
    register-with-eureka: true
  #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    fetchRegistry: true
    service-url:
      defaultZone: http://localhost:7001/eureka
```

#### 6.2.2.3 启动类开启Eureka  Client

```java
@EnableEurekaClient//客户端
@SpringBootApplication
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class,args);
    }
}
```

#### 6.2.2.4 测试

先启动Eureka Server段，再起动provider端。

<img src='img\image-20221214133850159.png'>

### 6.2.3 将项目（EurekaClient）consumer注册到EurekaServer

和provider端配置完全一样，见上。

<img src='img\image-20221214134540129.png'>

## 6.3 ureka集群

### 6.3.1 Eureka集群原理说明

**问题**：微服务RPC远程服务调用最核心的是什么 ?

答：高可用，试想你的注册中心只有一个only one， 它出故障了那就完了，会导致整个为服务环境不可用。

**解决办法：搭建Eureka注册中心集群 ，实现负载均衡+故障容错**

<img src='img\image-20221214135857456.png'>

### 6.3.2 Eureka集群环境搭建步骤

***Eureka Server 7001和7002相互注册，互相守望***

#### 6.3.2.1 创建新Eureka Server7002

<img src='img\image-20221214140518536.png'>

#### 6.3.2.2 修改pom

其实和7001的依赖完全一样

```xml
<dependencies>
    <!--eureka-server-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
    <dependency>
        <groupId>com.ly.springcloud</groupId>
        <artifactId>cloud-api-commons</artifactId>
        <version>${project.version}</version>
    </dependency>
    <!--boot web actuator-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!--一般通用配置-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
    </dependency>
</dependencies>
```

#### 6.3.2.3 修改本机hosts文件

```sh
# 为了让机器能够互相识别 C:\Windows\System32\drivers\etc\hosts 追加
127.0.0.1       eureka7001.com
127.0.0.1       eureka7002.com
```

#### 6.3.2.4 修改yaml（7001）文件

```yaml
server:
  port: 7001
spring:
  application:
    name: cloud-eureka-server-7001
eureka:
  instance:
    hostname:  eureka7001.com #eureka服务端的实例名称
  client:
    #false表示不向注册中心注册自己。
    register-with-eureka: false
    #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址。
      #集群就是相互注册对方的url
      defaultZone: http://eureka7002.com:7002/eureka/
```

#### 6.3.2.5 修改yaml（7002）文件

```yaml
server:
  port: 7002
spring:
  application:
    name: cloud-eureka-server-7002
eureka:
  instance:
    hostname:  eureka7002.com #eureka服务端的实例名称
  client:
    #false表示不向注册中心注册自己。
    register-with-eureka: false
    #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址。
      #集群就是相互注册对方的url
      defaultZone: http://eureka7001.com:7001/eureka/
```

#### 6.3.2.6 7001启动类

```java
@SpringBootApplication
@EnableEurekaServer //表明当前服务是一个eureka的sever
public class EurekaMain7002 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7002.class,args);
    }
}
```

#### 6.3.2.7 测试

虽然配置时的连接是  `defaultZone: http://eureka7001.com:7001/eureka/` ，实际不要加上最后的eureka

<img src='img\image-20221214143425704.png'>

<img src='img\image-20221214143505765.png'>

### 6.3.3 将微服务provider发布到Eureka集群中

微服务consumer端同样配置

```yaml
# 只需修改配置文件的server url即可
eureka:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/
```

### 6.3.4 测试1

先启动eureka集群7001和7002，然后启动provider和consumer

<img src='img\image-20221214144836834.png'>

### 6.3.5 服务provider集群搭建

<img src='img\image-20221214152431241.png'>

#### 6.3.5.1 创建provider 8002

<img src='img\image-20221214145440105.png'>

#### 6.3.5.2 修改pom

同 8001的 

#### 6.3.5.3 修改配置文件

同 8001的 ，除了port端口不一样为8002

#### 6.3.5.4 修改controller（8001和8002）

为了显示当前调用的是哪个服务

```java
@Value("${server.port}")
private String serverPort;

 public CommonResult create(@RequestBody Payment payment) {
        //map存放返回的信息
        Map<String,Object> map = new HashMap<>();
        map.put("serverPort",serverPort);
     ...
 }
```

#### 6.3.5.5 运行

<img src='img\image-20221214152640379.png'>

<img src='img\image-20221214152806696.png'>

### 6.3.6  ==负载均衡（从注册中获取服务url）*==

***eureka从注册中心获取provider，不再是80中写死ip和端口。***<font color='red'>***而是从Eureka Server中获取可用的provider url实现负载均衡和高可用。***</font>

#### 6.3.6.1 修改80端访问的url

```java
@Slf4j
@RestController
public class OrderController {
    //public static final String PAYMENT_URL = "http://localhost:8001";
    //用eureka的服务名代替真实ip，当其中一个provider掉线或者繁忙就会自动调用另一个provider(大小写随意)
    public static final String PAYMENT_URL = "http://cloud-payment-service";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        log.info("consumer 接收到一条新增payment消息 serial={}",payment.getSerial());
        return restTemplate.postForObject(
                PAYMENT_URL + "/payment/create",
                payment,
                CommonResult.class
        );
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        log.info("consumer 接收到一条请求payment消息 id={}",id);
        return restTemplate.getForObject(
                PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }
}
```

#### 6.3.6.2 consumer端开启负载均衡

```java
@Configuration
public class ApplicationContextConfig {
    
    @Bean
    @LoadBalanced //否则无法直接识别eruka的服务名，ribbon负载均衡，默认采用轮询方式
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
```

不加`@LoadBalanced`的话会导致错误：

<img src='img\image-20221214154716078.png'>

### 6.3.7 测试2

<img src='img\image-20221214155544560.png'>

### 6.3.8 负载均衡原理 *

​	如果不加负载均衡即`@LoadBalanced`注解，则配置的provider url就是一个普通的url，当RestTemplate发送post请求后，就直接发送了。但是如果给RestTemplate加上了该注解，则在发送post请求前会自动解析provider url成为一个真正的ip地址。

> 由于加了@LoadBalanced注解，使用RestTemplateCustomizer对所有标注了@LoadBalanced的RestTemplate Bean添加了一个LoadBalancerInterceptor拦截器。利用RestTempllate的拦截器，spring可以对restTemplate bean进行定制，加入loadbalance拦截器进行ip:port的替换，也就是将请求的地址中的服务逻辑名转为具体的服务地址。
>
> 具体实现还是loadbalance拦截器中LoadBalancerClient loadBalancer 即RibbonLoadBalancerClient（Eureka Client）

## 6.4 actuator微服务信息完善

### 6.4.1 eureka不显示服务的主机名

```yaml
eureka:
# 显示的服务名，唯一id
  instance:
    instance-id: payment8001
```

<img src='img\image-20221215093854851.png'>

### 6.4.2 访问服务有ip地址显示

```yaml
eureka:  
  instance:
    # 优先使用ip而不是主机名
    prefer-ip-address: true
```

<img src='img\image-20221215094610474.png'>

## 6.5 服务发现Discovery

对于注册进eureka里面的微服务，可以通过服务（consumer80可以）发现`@EnableDiscoveryClient`来获得该服务的信息。

### 6.5.1 payment8001引入DiscoverClient

```java
@EnableDiscoveryClient //springcloud包
@EnableEurekaClient
@SpringBootApplication
public class OrderMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
```

### 6.5.2  将微服务信息暴露出去

```java
@Slf4j
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    // 搭配eureka server
    private DiscoveryClient discoveryClient;

    @GetMapping("/payment/discovery")
    public Object discovery() {
        //获取eureka server上注册在线的所有服务(application)信息
        //如cloud-payment-service和cloud-order-service
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("微服务名：{}",service);
        }

        //根据服务名（application），获取所有的微服务信息 如payment80001的所有信息
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        instances.forEach(consumer -> {
            log.info(
                    "微服务：host={},instanceId={},metaData={},port={},serviceId={},uri={}",
                    consumer.getHost(),
                    consumer.getInstanceId(),
                    consumer.getMetadata(),
                    consumer.getPort(),
                    consumer.getServiceId(),
                    consumer.getUri()
            );
        });
        return this.discoveryClient;
    }
    ...
}
```

### 6.5.3 测试

```sh
# 日志
微服务名：cloud-payment-service
    
微服务：host=192.168.77.1,instanceId=payment8002,metaData={management.port=8002},port=8002,serviceId=CLOUD-PAYMENT-SERVICE,uri=http://192.168.77.1:8002

微服务：host=192.168.77.1,instanceId=payment8001,metaData={management.port=8001},port=8001,serviceId=CLOUD-PAYMENT-SERVICE,uri=http://192.168.77.1:8001
```

<img src='img\image-20221215101947331.png'>

## 6.6 Eureka自我保护

### 6.6.1 概述

保护模式主要用于一组客户端和Eureka Server之间存在网络分区场景下的保护。一旦进入保护模式，Eureka Server将会尝试保护其服务注册表中的信息，不再删除服务注册表中的数据，也就是不会注销任何微服务。

一句话：**某时刻某一个微服务不可用了，Eureka不会立刻清理（页面上消失），依旧会对该服务的信息进行保存。**

**属于微服务cloud中的CAP的AP分支**

> CAP的解释：
>
> 1、一致性（Consistency）：（等同于所有节点访问同一份最新的数据副本）
>
> 2、可用性（Availability）：（每次请求都能获取到非错的响应——但是不保证获取的数据为最新数据）
>
> 3、分区容错性（Partition tolerance）：（以实际效果而言，分区相当于对通信的时限要求。系统如果不能在时限内达成数据一致性，就意味着发生了分区的情况，必须就当前操作在C和A之间做出选择，保证分布式网络中部分网络不可用时, 系统依然正常对外提供服务。

如果在Eureka Server的首页看到以下这段提示，则说明Eureka进入了保护模式：

> <font color='red'>EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.</font>
>
> 紧急!Eureka可能错误地声称实例已经启动，而实际上它们并没有启动。更新次数小于阈值，因此为了安全起见，实例没有过期。

### 6.6.2 产生原因

**为什么会产生Eureka自我保护机制？**
	为了防止EurekaClient可以正常运行，但是 与 EurekaServer网络不通情况下，EurekaServer不会立刻将EurekaClient服务剔除

**什么是自我保护模式？**
	默认情况下，如果EurekaServer在一定时间内没有接收到某个微服务实例的心跳，EurekaServer将会注销该实例（默认90秒）。但是当网络分区故障发生(延时、卡顿、拥挤)时，微服务与EurekaServer之间无法正常通信，以上行为可能变得非常危险了——因为微服务本身其实是健康的，此时本不应该注销这个微服务。Eureka通过“自我保护模式”来解决这个问题——当EurekaServer节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。

<img src='img\image-20221215104840625.png'>

**在自我保护模式中，Eureka Server会保护服务注册表中的信息，不再注销任何服务实例。**
它的设计哲学就是宁可保留错误的服务注册信息，也不盲目注销任何可能健康的服务实例。一句话讲解：好死不如赖活着

综上，自我保护模式是一种应对网络异常的安全保护措施。它的架构哲学是宁可同时保留所有微服务（健康的微服务和不健康的微服务都会保留）也不盲目注销任何健康的微服务。使用自我保护模式，可以让Eureka集群更加的健壮、稳定。

###  6.6.3 禁用Eureka自我保护

Eureka server 7001和 7002配置文件中关闭自我保护，更改心跳连接的时长。

```yaml
server:
  port: 7001
spring:
  application:
    name: cloud-eureka-server-7001
    
eureka:
  instance:
    hostname: eureka7001.com #eureka服务端的实例名称
    # 指示eureka客户端需要多长时间(以秒为单位)向eureka服务器发送一次心跳，以表明它仍处于活动状态。默认30秒
    # 如果在leaseExpirationDurationInSeconds中指定的时间内没有接收到心跳，eureka服务器将从其视图中删除该实例，并禁止该实例的流量。
    lease-renewal-interval-in-seconds: 1
    # 指示eureka客户端需要多长时间(以秒为单位)向eureka服务器发送一次心跳，以表明它仍处于活动状态。默认90秒
    # 如果在leaseExpirationDurationInSeconds中指定的时间内没有接收到心跳，eureka服务器将从其视图中删除该实例，并禁止该实例的流量。
    lease-expiration-duration-in-seconds: 2
    
  client: 
    #false表示不向注册中心注册自己。
    register-with-eureka: false
    #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址。
      #集群就是相互注册对方的url
      defaultZone: http://eureka7002.com:7002/eureka/
      
  # 默认开启自我保护
  server:
    enable-self-preservation: false
    # 设置 检测下线服务 驱逐时间间隔 默认是60s
    eviction-interval-timer-in-ms: 2000
```

### 6.6.4 测试

<img src='img\image-20221215131843805.png'>



***

# 7. Zookeeper服务注册与发现

由于Eureka停止更新，SpringCloud整合Zookeeper代替Eureka。

## 7.2 

### 7.2.1 

### 7.2.2 

### 7.2.3 

### 7.2.4 

