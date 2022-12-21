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

## 7.1 centos7安装zookeeper3.4.9

+ 创建并进入目录

  ```sh
  mkdir -p /usr/local/services/zookeeper
  cd /usr/local/services/zookeeper
  ```

+ 下载zookeeper3.4.9并解压

  ```sh
  wget http://archive.apache.org/dist/zookeeper/zookeeper-3.4.9/zookeeper-3.4.9.tar.gz
  tar -zxvf zookeeper-3.4.9.tar.gz
  ```

+ 进入加压后的zookeeper的conf文件夹，复制 zoo_sample.cfg 文件的并命名为为 zoo.cfg：

  ```sh
  cd zookeeper-3.4.9/conf/
  cp zoo_sample.cfg zoo.cfg
  ```

+ 用 vim 打开 zoo.cfg 文件并修改其内容为如下：

  ```sh
  	# The number of milliseconds of each tick
   
      # zookeeper 定义的基准时间间隔，单位：毫秒
      tickTime=2000
   
      # The number of ticks that the initial
      # synchronization phase can take
      initLimit=10
      # The number of ticks that can pass between
      # sending a request and getting an acknowledgement
      syncLimit=5
      # the directory where the snapshot is stored.
      # do not use /tmp for storage, /tmp here is just
      # example sakes.
      # dataDir=/tmp/zookeeper
   
      # 数据文件夹
      dataDir=/usr/local/services/zookeeper/zookeeper-3.4.9/data
   
      # 日志文件夹
      dataLogDir=/usr/local/services/zookeeper/zookeeper-3.4.9/logs
   
      # the port at which the clients will connect
      # 客户端访问 zookeeper 的端口号
      clientPort=2181
  ```

+ 进入到 /usr/local/services/zookeeper/zookeeper-3.4.9/bin 目录中

  ```sh
   cd /usr/local/services/zookeeper/zookeeper-3.4.9/bin/
  ```

+ 用 vim 打开 /etc/ profile， 并在其尾部追加如下内容：

  ```sh
   vim /etc/profile
   # 并在其尾部追加如下内容：
  ```

  ```sh
  # idea - zookeeper-3.4.9 config start - 2016-09-08
  
  export ZOOKEEPER_HOME=/usr/local/services/zookeeper/zookeeper-3.4.9/
  export PATH=$ZOOKEEPER_HOME/bin:$PATH
  export PATH
  
  # idea - zookeeper-3.4.9 config start - 2016-09-08
  ```

+ 使 /etc/ 目录下的 profile 文件即可生效：

  ```sh
  source /etc/profile
  ```

+ 启动 zookeeper 服务：

  ```sh
  zkServer.sh start
  ```

  > 如打印如下信息则表明启动成功：
  >     ZooKeeper JMX enabled by default
  >     Using config: /usr/local/services/zookeeper/zookeeper-3.4.9/bin/../conf/zoo.cfg
  >     Starting zookeeper ... STARTED

+ 查询 zookeeper 状态

  ```sh
  zkServer.sh status
  ```

  > ZooKeeper JMX enabled by default
  > Using config: /usr/local/services/zookeeper/zookeeper-3.4.9/bin/../conf/zoo.cfg
  > Mode: standalone

+ 关闭 zookeeper 服务：

  ```sh
  zkServer.sh stop
  ```

+ 重启 zookeeper 服务：

  ```sh
  zkServer.sh restart
  ```

## 7.2 zookeeper代替Eureka实现服务注册与发现

<img src='img\image-20221215162553393.png'>

### 7.2.1 注册中心zookeeper

+ zookeeper是一个分布式协调工具，可以实现注册中心功能
+ 关闭Linux服务器防火墙后启动zookeeper服务器
+ zookeeper服务器取代Eureka服务器，zk作为服务注册中心

### 7.2.2  服务提供者 provider 8004

#### 7.2.2.1 创建项目

<img src='img\image-20221215152028005.png'>

#### 7.2.2.2 修改pom

```xml
<dependencies>
        <!-- SpringBoot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.ly.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
        <!-- SpringBoot整合zookeeper客户端 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
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

#### 7.2.2.3 修改配置文件

```yaml
server:
  port: 8004

spring:
  application:
    name: cloud-provider-payment
  # 连接zookeeper服务端
  cloud:
    zookeeper:
      connect-string: 192.168.77.3:2181
```

#### 7.2.2.4 创建主启动类

```java
//该注解用于向使用consul或者zookeeper作为注册中心时注册服务
@EnableDiscoveryClient//谁连接，谁提供用
@SpringBootApplication
public class PaymentMain8004 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8004.class,args);
    }
}
```

#### 7.2.2.5 controller

```java
// 一个简单的controller，用于展示服务是否成功注册进入zookeeper
@Slf4j
@RestController
public class PaymentController {
    @Value("${server.port}")
    private Integer port;


    @RequestMapping("/payment/zk")
    public CommonResult paymentZK(){
        return new CommonResult(
                200,
                "成功",
                "serverPort:" + port
        );
    }
}
```

#### 7.2.2.6 验证测试

+ 先启动zookeeper服务

  ```sh
  zkServer.sh start
  ```

+ 再连上zookeeper客户端

  ```sh
  ./zkCli.sh
  ```

  ```sh
  # 运行查看当前zookeeper信息，只有一个默认的quota节点
  [zk: localhost:2181(CONNECTED) 6] ls / 
  [zookeeper]
  [zk: localhost:2181(CONNECTED) 7] get /zookeeper
  
  cZxid = 0x0
  ctime = Wed Dec 31 16:00:00 PST 1969
  mZxid = 0x0
  mtime = Wed Dec 31 16:00:00 PST 1969
  pZxid = 0x0
  cversion = -1
  dataVersion = 0
  aclVersion = 0
  ephemeralOwner = 0x0
  dataLength = 0
  numChildren = 1
  [zk: localhost:2181(CONNECTED) 8] ls /zookeeper
  [quota]
  
  ```

+ 运行微服务

  报错了经过排查发现是**jar包冲突，服务器的zookeeper为3.4.9而引入的org.springframework.cloud:spring-cloud-starter-zookeeper-discovery:2.2.0.RELEASE为3.5.3.beta版**

  <img src='img\image-20221215160234656.png'>

  <img src='img\image-20221215155925793.png'>

  ***解决方法，排除此依赖，重新引入依赖***

  ```xml
  <!-- SpringBoot整合zookeeper客户端 -->
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
      <!-- 排除内部的依赖zookeeper3.5.3-->
      <exclusions>
          <exclusion>
              <groupId>org.apache.zookeeper</groupId>
              <artifactId>zookeeper</artifactId>
          </exclusion>
      </exclusions>
  </dependency>
  <!-- 引入服务器版本zookeeper依赖3.4.9-->
  <dependency>
      <groupId>org.apache.zookeeper</groupId>
      <artifactId>zookeeper</artifactId>
      <version>3.4.9</version>
  </dependency>
  ```

***再次运行测试，***

```sh
[zk: localhost:2181(CONNECTED) 18] ls /
[services, zookeeper]
[zk: localhost:2181(CONNECTED) 19] ls /services
[cloud-provider-payment]
[zk: localhost:2181(CONNECTED) 20] 
```

<img src='img\image-20221215161151016.png'>

#### 7.2.2.7 验证测试2

zookeeper中查看注册的服务信息：

+ `31efec0b-2427-4e79-80a1-1a5882010080`就为cloud-provider-payment服务下实例名id，

```sh
[zk: localhost:2181(CONNECTED) 21] ls /services
[cloud-provider-payment]
[zk: localhost:2181(CONNECTED) 22] ls /services/cloud-provider-payment
[31efec0b-2427-4e79-80a1-1a5882010080]
[zk: localhost:2181(CONNECTED) 23] ls /services/cloud-provider-payment/31efec0b-2427-4e79-80a1-1a5882010080
[]
[zk: localhost:2181(CONNECTED) 24] get /services/cloud-provider-payment/31efec0b-2427-4e79-80a1-1a5882010080
{"name":"cloud-provider-payment","id":"31efec0b-2427-4e79-80a1-1a5882010080","address":"DESKTOP-O5VMOIK","port":8004,"sslPort":null,"payload":{"@class":"org.springframework.cloud.zookeeper.discovery.ZookeeperInstance","id":"application-1","name":"cloud-provider-payment","metadata":{}},"registrationTimeUTC":1671091740425,"serviceType":"DYNAMIC","uriSpec":{"parts":[{"value":"scheme","variable":true},{"value":"://","variable":false},{"value":"address","variable":true},{"value":":","variable":false},{"value":"port","variable":true}]}}
cZxid = 0xa
ctime = Thu Dec 15 00:09:02 PST 2022
mZxid = 0xa
mtime = Thu Dec 15 00:09:02 PST 2022
pZxid = 0xa
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x185149d73640002
dataLength = 536
numChildren = 0
[zk: localhost:2181(CONNECTED) 25] 

```

#### 7.2.2.8 思考

zookeeper中保存的服务是临时节点还是永久节点？

```sh
# 关闭微服务，过了一会发现服务被zookeeper删除了
# 再重新启动后，又会把微服务注册进zookeeper中，但是id发送了变化
```

<img src='img\image-20221215162513671.png'>

### 7.2.3 服务消费者consumerzk-80

#### 7.2.3.1 创建项目

<img src='img\image-20221215163108347.png'>

#### 7.2.3.2 修改pom

```xml
<dependencies>
    <!-- SpringBoot整合Web组件 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
        <groupId>com.ly.springcloud</groupId>
        <artifactId>cloud-api-commons</artifactId>
        <version>${project.version}</version>
    </dependency>
    <!-- SpringBoot整合zookeeper客户端 -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
        <!-- 排除内部的依赖zookeeper3.5.3-->
        <exclusions>
            <exclusion>
                <groupId>org.apache.zookeeper</groupId>
                <artifactId>zookeeper</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <!-- 引入服务器版本zookeeper依赖3.4.9-->
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId>
        <version>3.4.9</version>
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

#### 7.2.3.3 修改配置文件

```yaml
server:
  port: 80

spring:
  application:
    name: cloud-consumer-order

  cloud:
    zookeeper:
      connect-string: 192.168.77.3:2181
```

#### 7.2.3.4 创建主启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class OrderZKMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderZKMain80.class,args);
    }
}
```

#### 7.2.3.5 配置类

```java
package com.ly.springcloud.config;
...
    
@Configuration
public class ApplicationContextConfig {

    /**
     * 使用@LoadBalanced注解的目的:
     *  1.负载均衡
     *  2.用zookeeper中服务名代替确定的ip:port 
     * @return restTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
```

#### 7.2.3.6 controller

```java
@Slf4j
@RestController
public class OrderZkController {
    @Autowired
    private RestTemplate restTemplate;
    //就是zookeeper中的服务名
    private static final String url = "http://cloud-provider-payment";

    @RequestMapping("/consumer/payment/{id}")
    public CommonResult consume(@PathVariable("id") Integer id) {
        log.info("接收到请求：id={}",id);
        return restTemplate.getForObject(
                //第一个记得加/
            url + "/payment/zk",
                CommonResult.class
        );
    }

}
```

### 7.2.4 测试

<img src='img\image-20221215165940801.png'>

```sh
[zk: localhost:2181(CONNECTED) 42] ls /services
[cloud-provider-payment, cloud-consumer-order]
[zk: localhost:2181(CONNECTED) 43] 
```

## 7.3 应用集群模式

***项目：test-cloud-provider-payment8005***

新建一个provider项目`test-cloud-provider-payment8005`，设置相同的服务名`cloud-provider-payment`，设置不同的端口，经测试可以实现负载均衡和动态切换。

```sh
# zookeeper注册中心
[zk: localhost:2181(CONNECTED) 49] ls /services
[cloud-provider-payment, cloud-consumer-order]
[zk: localhost:2181(CONNECTED) 50] ls /services/cloud-provider-payment
[9e7fa101-9928-415f-b2d7-23e811abee87, b06ca75b-0265-4190-a5a5-97f1a1087143]
[zk: localhost:2181(CONNECTED) 51] 
```

<img src='img\image-20221216091603707.png'>

<img src='img\image-20221216091653069.png'>

***一旦断开连接，zookeeper会立马将断开的服务从注册中心删除，比如8005断开连接就会立马删除，以后所有的请求都走8004了。***

# 8. Consul服务注册与发现

## 8.1 Consul简介

**官网：**https://www.consul.io/intro/index.html

### 8.1.1 是什么

Consul 是一套开源的分布式服务发现和配置管理系统，由 HashiCorp 公司用 Go 语言开发。

提供了微服务系统中的服务治理、配置中心、控制总线等功能。这些功能中的每一个都可以根据需要单独使用，也可以一起使用以构建全方位的服务网格，总之Consul提供了一种完整的服务网格解决方案。

它具有很多优点。包括： 基于 raft 协议，比较简洁； 支持健康检查, 同时支持 HTTP 和 DNS 协议 支持跨数据中心的 WAN 集群 提供图形界面 跨平台，支持 Linux、Mac、Windows。

### 8.1.2 能干什么？

+ 服务发现，提供HTTP和DNS两种发现方式

+ 健康检测，支持多种方式、HTTP、TCP、Docker、Shell脚本定制化监控

+ KV键值对存储（内存）

  > https://www.cnblogs.com/duanxz/p/9660766.html

+ 支持多数据中心

+ 提供可视化web界面

<img src='img\image-20221219091715731.png'>

### 8.1.3 下载

**官网：**https://developer.hashicorp.com/consul/downloads

### 8.1.4 第三方教程

https://www.springcloud.cc/spring-cloud-consul.html

> 翻译自springCloud consul官方文档，https://cloud.spring.io/spring-cloud-static/Hoxton.SR1/reference/htmlsingle/#spring-cloud-consul

## 8.2 安装并运行Consul

下载windows 1.6.1版本

打开https://developer.hashicorp.com/consul/downloads，右上角选择版本1.6.1 点击下载，加压后只有一个consul.exe文件（不需要安装，直接运行）。

**查看版本：**

```sh
consul --version
```

<img src='img\image-20221219093320319.png'>

**服务启动：**

```sh
consul agent -dev
```

<img src='img\image-20221219093555276.png'>

访问http://localhost:8500即可看到consul自带的web管理端

<img src='img\image-20221219093623991.png'>

## 8.3 服务提供者Provider

### 8.3.1 新建项目8006

<img src='img\image-20221219094502329.png'>

### 8.3.2 改pom

```xml
<dependencies>
        <!--SpringCloud consul-server -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>
        <!-- SpringBoot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    	<dependency>
            <groupId>com.ly.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--日常通用jar包配置-->
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

### 8.3.3 修改配置文件

```yaml
server:
  port: 8006

spring:
  application:
    name: consul-provider-payment
  #  将本地服务注册到consul中
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        service-name: ${spring.application.name}
```

### 8.3.4 创建主启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain8006 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8006.class,args);
    }
}
```

### 8.3.5 创建controller

```java
@Slf4j
@RestController
public class PaymentController {
    @Value("${server.port}")
    private Integer port;


    @RequestMapping("/payment/consul")
    public CommonResult paymentZK(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("serverPort",port);
        map.put("serialNo", UUID.randomUUID().toString());
        return new CommonResult(
                200,
                "成功",
                map
        );
    }
}
```

### 8.3.6 测试

<img src='img\image-20221219100659399.png'>

接口：http://localhost:8006/payment/consul 测试通过

## 8.4 服务消费者Consumer

### 8.4.1 创建项目

<img src='img\image-20221219101023728.png'>

### 8.4.2 修改pom

```xml
<dependencies>
    <!--SpringCloud consul-server -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>
    <!-- SpringBoot整合Web组件 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!--日常通用jar包配置-->
    <dependency>
        <groupId>com.ly.springcloud</groupId>
        <artifactId>cloud-api-commons</artifactId>
        <version>1.0-SNAPSHOT</version>
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

### 8.4.3 修改配置文件

```yaml
server:
  port: 80

spring:
  application:
    name: cloud-consumer-order
  # 注册到consul中 ip host可以不写，默认就是本机
  cloud:
    consul:
      discovery:
        service-name: ${spring.application.name}
```

### 8.4.4 创建主启动类

```java
@EnableDiscoveryClient
@SpringBootApplication
public class OrderConsulMain80 {
   public static void main(String[] args){
     SpringApplication.run(OrderConsulMain80.class, args);
   }
}
```

### 8.4.5 创建配置类

```java
@Configuration
public class ApplicationContextConfig {

    /**
     * 使用@LoadBalanced注解的目的:
     *  1.负载均衡
     *  2.用consul中服务名代替确定的ip:port
     * @return restTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
```

### 8.4.6 创建controller

```java
@Slf4j
@RestController
public class OrderConsulController {
    @Autowired
    private RestTemplate restTemplate;
    private static final String url = "http://consul-provider-payment";

    @RequestMapping("/consumer/payment/{id}")
    public CommonResult consume(@PathVariable("id") Integer id) {
        log.info("接收到请求：id={}",id);
        return restTemplate.getForObject(
                //第一个记得加/
            url + "/payment/consul",
                CommonResult.class
        );
    }

}
```

### 8.4.7 测试

<img src='img\image-20221219102826409.png'>

<img src='img\image-20221219103050830.png'>

## 8.5 应用集群模式

***项目：test-cloud-provider-payment8006***

<img src='img\image-20221219104230246.png'>

<img src='img\image-20221219104410613.png'>

<img src='img\image-20221219104508558.png'>

# 9 Eureka、Zookeeper和Consul的异同点

<img src='img\image-20221219110114458.png'>

## 9.1 CAP

CAP理论关注粒度是数据，而不是整体系统设计的策略（不是AP就是CP）

<img src='img\image-20221219132222930.png'>

+ Consistency（强一致性）
+ Availablity（可用性）
+ Parition tolerance（分区容错性）

## 9.2 经典CAP图

**AP架构（Eureka）**
当网络分区出现后，为了保证可用性，系统B可以返回旧值，保证系统的可用性。
结论：违背了一致性C的要求，只满足可用性和分区容错，即AP

<img src='img\image-20221219132821126.png'>

**CP架构（zookeeper/consul）**

当网络分区出现后，为了保证一致性，就必须拒接请求，否则无法保证一致性
结论：违背了可用性A的要求，只满足一致性和分区容错，即CP

<img src='img\image-20221219132949640.png'>

# 10 Ribbon负载均衡与服务调用

## 10.1 概述

Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端       负载均衡的工具。

简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供**客户端的软件负载均衡算法和服务调用**。Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。简单的说，就是在配置文件中列出Load Balancer（简称LB）后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器。我们很容易使用Ribbon实现自定义的负载均衡算法。

**官网资料：**https://github.com/Netflix/ribbon/wiki/Getting-Started

由于Ribbon也进入了维护模式，以后的替代为Spring Cloud LoadBalancer

<img src='img\image-20221219143740702.png'>



## 10.2 能干嘛

Load Balance即负载均衡

**LB负载均衡(Load Balance)是什么?**
	简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA（高可用）。
常见的负载均衡有软件Nginx，LVS，硬件 F5等。

**Ribbon本地负载均衡客户端 VS Nginx服务端负载均衡区别?**
	Nginx是服务器负载均衡，客户端所有请求都会交给nginx，然后由nginx实现转发请求。即负载均衡是由服务端实现的。Ribbon本地负载均衡，在调用微服务接口时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，从而在本地实现RPC远程服务调用技术。

 可分为：

+ **集中式 Load Balance**

  ​	即在服务的消费方和提供方之间使用独立的LB设施(可以是硬件，如F5, 也可以是软件，如nginx), 由该设施负责把访问请求通过某种策略转发至服务的提供方。（中间件可以理解为**服务端**）

+ **进程内 Load Balance**

  ​	将**LB逻辑集成到消费方**，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器。**Ribbon就属于进程内LB**，它只是一个类库，**集成于消费方进程**，消费方通过它来获取到服务提供方的地址。




 **总结：Ribbon就是负载均衡+RestTemplate**

## 10.3 Ribbon负载均衡演示

<img src='img\image-20221219152148745.png' style='zoom:19'>

总结：Ribbon其实就是一个**软负载均衡的客户端组件**，他可以和其他所需请求的客户端结合使用，和eureka结合只是其中的一个实例。

### 10.3.1 依赖分析

<img src='img\image-20221219152844187.png'>

### 10.3.2 RestTemplate

官网api文档：https://docs.spring.io/spring-framework/docs/5.2.2.RELEASE/javadoc-api/org/springframework/web/client/RestTemplate.html

主要方法是两种：

+ `getForObject()`和`getForEntity()`

  + getForObject()**返回对象为响应体中数据转化成的对象**，基本上可以理解为Json

    <img src='img\image-20221219153535656.png'>

  + getForEntity()**返回对象为ResponseEntity对象**，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等

    <img src='img\image-20221219153611323.png'>

+ `postForObject()`和`postForEntity()`

  + postForObject()返回类型同getForObject()
  + postForEntity()返回类型同getForEntity()

## 10.4 Ribbon核心组件IRule*

### 10.4.1 IRule （接口）

根据特定算法中从服务列表中选取一个要访问的服务，**Ribbon自带的算法**规则（IRule实现类）如下：

+ RoundRobinRule（轮询规则）
+ RandomRule（随机）
+ RetryRule（先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重试，获取可用的服务）
+ WeightedResponseTimeRule（对RoundRobinRule的扩展，响应速度越快的实例选择权重越大，越容易被选择）
+ BestAvailableRule（会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务）
+ AvailabilityFilteringRule（先过滤掉故障实例，再选择并发较小的实例）
+ ZoneAvoidanceRule（默认规则,复合判断server所在区域的性能和server的可用性选择服务器）

<img src='img\image-20221219163407777.png'>



### 10.4.2 替换规则

以Eureka项目中**cloud-consumer-order80**为例

#### 10.4.2.1 规则配置细节

官方文档明确给出了警告：
这个**自定义配置类不能放在@ComponentScan所扫描的当前包下以及子包下**，否则我们自定义的这个配置类就会被所有的Ribbon客户端所共享，达不到特殊化定制的目的了。

<img src='img\image-20221220095427365.png'>

#### 10.4.2.2 新建package

+ 项目包：`com.ly.springcloud`
+ ribbon特殊规则包：`com.ly.myrule`

#### 10.4.2.3 创建配置类

```java
package com.ly.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName:MySelfRule.class
 * Author:ly
 * Date:2022/12/20 0020
 * Description: 自定义ribbon负载均衡选择服务器的规则（不与主项目同包com.ly.springcloud）
 */
@Configuration
public class MySelfRule {

    @Bean
    //组件RandomRule放入的不是spring的上下文，而是ribbon自己的上下文
    public IRule myRule(){
        //给ioc容器加入随机组件
        return new RandomRule();
    }
}
```

#### 10.4.2.4 主启动类加@RibbonClient注解

```java
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
//名字必须和controller中调用的一样才有效果，否则还是默认的
//	所以不随spring容器初始化而创建，仅在调用负载均衡服务时创建
@RibbonClient(name = "cloud-payment-service",configuration = {MySelfRule.class})
public class OrderMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
```

#### 10.4.2.5 测试

先后启动Eureka集群，provider集群8001，8002和consumer80

参考连接：https://cloud.tencent.com/developer/article/2150851?from=15425

<img src='img\image-20221220132548608.png'>

## 10.5 Ribbon负载均衡算法

### 10.5.1 原理

负载均衡算法：

+ **rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标  **
+ 每次服务重启动后rest接口计数从1开始。

```java
List\<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

如：   List [0] instances = 127.0.0.1:8002
　　　List [1] instances = 127.0.0.1:8001

8001+ 8002 组合成为集群，它们共计2台机器，集群总数为2， 按照轮询算法原理：

当总请求数为1时： 1 % 2 =1 对应下标位置为1 ，则获得服务地址为127.0.0.1:8001
当总请求数位2时： 2 % 2 =0 对应下标位置为0 ，则获得服务地址为127.0.0.1:8002
当总请求数位3时： 3 % 2 =1 对应下标位置为1 ，则获得服务地址为127.0.0.1:8001
当总请求数位4时： 4 % 2 =0 对应下标位置为0 ，则获得服务地址为127.0.0.1:8002
如此类推......
```

### 10.5.2 源码

```JAVA
//com.netflix.loadbalancer.RoundRobinRule

//创建类时
public RoundRobinRule() {
        nextServerCyclicCounter = new AtomicInteger(0);//设置原子数
    }

//核心源码 由choose()调用
 private int incrementAndGetModulo(int modulo) {
     //这里用的是cas+自旋锁
        for (;;) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            //CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。
            if (nextServerCyclicCounter.compareAndSet(current, next))
                return next;
        }
    }
```

> CAS：Compare and Swap, 翻译成比较并交换。
>
> java.util.concurrent包中借助CAS实现了区别于synchronouse同步锁的一种乐观锁，使用这些类在多核CPU的机器上会有比较好的性能.
>
> ```java
> //java.util.concurrent.atomic.AtomicInteger
> public final boolean compareAndSet(int expect, int update) {
>     /*
>     	compareAndSet()有3个操作数，内存值valueOffset，旧的预期值expect，要修改的新值update。当且仅当预期值expect和内存值valueOffset相同时，将内存值valueOffset修改为update，否则什么都不做。
>     */
>         return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
>     }
> ```
>
> JUC视频连接：https://www.bilibili.com/video/BV1ar4y1x727/?vd_source=bd522cbca55860eb7b29232fb02c02bd

### 10.5.3 ==>自定义轮询算法和LoadBalancer负载均衡器*==

#### 10.5.3.1 视频写法 

原理借助cloud包下`@EnableDiscoveryClient`注解通过服务名，获取所有实例的环境信息（ip:port），然后自定义负载均衡器再实现轮询。

+ 去掉配置类中组件RestTemplate的@LoadBalanced注解

  ```java
  @Bean
  //@LoadBalanced //否则无法直接识别eruka的服务名
  public RestTemplate restTemplate() {
      return new RestTemplateBuilder().build();
  }
  ```

+ 自定义负载均衡器规范（接口）

  ```java
  package com.ly.springcloud.lb;
  
  import org.springframework.cloud.client.ServiceInstance;
  import java.util.List;
  
  public interface LoadBalancer {
  
      /**
       * 从指定服务中的所有实例中，按照自定义轮询规则取出 实例
       * @param serviceInstances 指定服务名的所有在线实例
       * @return 按照自定义轮询规则取出 可用的实例
       */
      ServiceInstance getInstance(List<ServiceInstance> serviceInstances);
  }
  ```

+ 自定义负载均衡器实现类

  ```java
  package com.ly.springcloud.lb.impl;
  
  import com.ly.springcloud.lb.LoadBalancer;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.cloud.client.ServiceInstance;
  import org.springframework.stereotype.Component;
  
  import java.util.List;
  import java.util.concurrent.atomic.AtomicInteger;
  
  /**
   * FileName:MyRoundLoadBalancer.class
   * Author:ly
   * Date:2022/12/20 0020
   * Description: 基于自定义轮询规则的负载均衡器
   */
  @Slf4j
  @Component
  public class MyRoundLoadBalancer implements LoadBalancer {
      private final AtomicInteger atomicInteger;
  
      public MyRoundLoadBalancer() {
          this(new AtomicInteger(0));
      }
  
      public MyRoundLoadBalancer(AtomicInteger atomicInteger) {
          this.atomicInteger = atomicInteger;
      }
  
      /**
       * 自定义轮询的核心算法(cas+自旋锁)
       * @param instances 所有在线的服务个数
       * @return 实例的下标
       */
      public int getAndIncrementIndex(int instances){
          int current;
          int next;
  
          do {
              current = atomicInteger.get() > Integer.MAX_VALUE ? 0 : atomicInteger.get();
              next = (current + 1) % instances;
          } while (!atomicInteger.compareAndSet(current,next));
          log.info("获取到实例下标为:{}",next);
          return next;
      }
  
      @Override
      public ServiceInstance getInstance(List<ServiceInstance> serviceInstances) {
  
          return serviceInstances.get(
                  getAndIncrementIndex(serviceInstances.size())
          );
      }
  }
  ```

+ controller层调用

  ```java
  @Slf4j
  @RestController
  public class OrderController {
      public static final String PAYMENT_URL = "http://cloud-payment-service";
      public static final String APPLICATION_NAME = "cloud-payment-service";
      @Autowired
      private RestTemplate restTemplate;
      @Autowired //根据服务名获取所有实例信息
      private DiscoveryClient discoveryClient;
      @Autowired //自定义负载均衡器
      private LoadBalancer loadBalancer;
  
      @GetMapping("/consumer/payment/lb/get/{id}")
      public CommonResult<Payment> getPaymentByIdOfLB(@PathVariable("id") Long id){
  
          ServiceInstance instance = loadBalancer.getInstance(
                  discoveryClient.getInstances(APPLICATION_NAME)
          );
  
          return restTemplate.getForObject(
                  instance.getUri() + "/payment/get/" + id, CommonResult.class);
      }
  }
  ```

+ 测试

  <img src='img\image-20221220153440210.png'>

#### 10.5.3.2 自己的写法

原理借助cloud包下`@EnableDiscoveryClient`注解通过服务名，获取所有实例的环境信息（ip:port），然后通过默认的LoadBalancerInterceptor拦截器使用自定义轮询规则进行轮询。 （和10.4.2替换步骤完全一样）

+ ioc容器加入RestTemplate，并开启@LoadBalanced

+ 创建自定义轮询规则

  ```java
  package com.ly.myrule;
  
  import com.netflix.client.config.IClientConfig;
  import com.netflix.loadbalancer.AbstractLoadBalancerRule;
  import com.netflix.loadbalancer.ILoadBalancer;
  import com.netflix.loadbalancer.Server;
  import lombok.extern.slf4j.Slf4j;
  
  import java.util.concurrent.atomic.AtomicInteger;
  
  /**
   * FileName:MyRoundStrategy.class
   * Author:ly
   * Date:2022/12/20 0020
   * Description:
   */
  @Slf4j
  public class MyRoundStrategy extends AbstractLoadBalancerRule {
      private final AtomicInteger atomicInteger;
  
      public MyRoundStrategy() {
          this(new AtomicInteger(0));
      }
  
      public MyRoundStrategy(AtomicInteger atomicInteger) {
          this.atomicInteger = atomicInteger;
      }
  
      public MyRoundStrategy(ILoadBalancer lb) {
          this();
          setLoadBalancer(lb);
      }
  
      /**
       * 自定义轮询的核心算法(cas+自旋锁)
       * @param instances 所有在线的服务个数
       * @return 实例的下标
       */
      public int getAndIncrementIndex(int instances,Object key){
          int current;
          int next;
  
          do {
              current = atomicInteger.get() > Integer.MAX_VALUE ? 0 : atomicInteger.get();
              next = (current + 1) % instances;
          } while (!atomicInteger.compareAndSet(current,next));
          log.info("获取到实例下标为:{}",next);
          return next;
      }
  
      @Override
      public void initWithNiwsConfig(IClientConfig iClientConfig) {
  
      }
      public Server choose(ILoadBalancer lb,Object key) {
          int index = getAndIncrementIndex(lb.getAllServers().size(), key);
          return lb.getAllServers().get(index);
      }
      @Override
      public Server choose(Object key) {
          //log.info("key={}",key);
          return choose(getLoadBalancer(),key);
      }
  }
  ```

+ 修改配置类（主启动类包外）

  ```java
  package com.ly.myrule;
  
  import com.netflix.loadbalancer.IRule;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  /**
   * FileName:MySelfRule.class
   * Author:ly
   * Date:2022/12/20 0020
   * Description: 自定义ribbon负载均衡选择服务器的规则（不与主项目同包）
   */
  @Slf4j
  @Configuration
  public class MySelfRule {
  
      //组件RandomRule放入的不是spring的上下文，而是ribbon自己的上下文，所以不随spring容器初始化而创建，仅在调用负载均衡服务时创建
      @Bean
      public IRule myRoundRule(){
          //给ioc容器加入随机组件
          log.info("MyRoundStrategy 成功加入ribbon ioc。。。");
          return new MyRoundStrategy();
      }
  }
  ```

+ 主启动类开启@RibonCLient

  ```java
  @EnableDiscoveryClient
  @EnableEurekaClient
  @SpringBootApplication
  //服务名一定要和controller（使用的地方）中的一样，区分大小写
  @RibbonClient(name = "cloud-payment-service",configuration = MySelfRule.class)
  public class OrderMain80 {
  
      public static void main(String[] args) {
          SpringApplication.run(OrderMain80.class, args);
      }
  }
  ```

+ 测试

  <img src='img\image-20221220160252772.png'>

# 11  OpenFeign服务接口调用

## 11.1概述

​	**Spring Cloud官网地址：**https://cloud.spring.io/spring-cloud-static/Hoxton.SR1/reference/htmlsingle/#spring-cloud-openfeign

​	**github官方地址：**https://github.com/spring-cloud/spring-cloud-openfeign

​	Feign是一个声明式WebService客户端。使用Feign能**让编写Web Service客户端更加简单（即consumer端）**。
​	它的使用方法是**定义一个服务接口（和provider提供的接口完全一样）然后在上面添加注解**。Feign也支持可拔插式的编码器和解码器。Spring Cloud对Feign进行了封装，使其支持了Spring MVC标准注解和HttpMessageConverters。Feign可以与Eureka和Ribbon组合使用以支持负载均衡

<img src='img\image-20221220202230588.png'>

### 11.1.1 Feign能干什么

Feign旨在使编写Java Http客户端变得更容易。
前面在使用Ribbon+RestTemplate时，利用RestTemplate对http请求的封装处理，形成了一套模版化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处，**往往一个接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用**。所以，Feign在此基础上做了进一步封装，由他来帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，**我们只需创建一个接口并使用注解的方式来配置它(比如Dao接口上面标注Mapper注解,现在是一个微服务接口上面标注一个Feign注解即可)，即可完成对服务提供方的接口绑定**，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量。

**Feign集成了Ribbon**，利用Ribbon维护了Payment的服务列表信息，并且通过轮询实现了客户端的负载均衡。而与Ribbon不同的是，通过feign只需要定义服务绑定接口且以声明式的方法，优雅而简单的实现了服务调用

<img src='img\image-20221220204246691.png'>

### 11.1.2 OpenFeign和Feign的区别

<img src='img\image-20221220203140148.png'>

## 11.2 OpenFeign使用步骤

### 11.2.1 创建新Consumer

<img src='img\image-20221220203741451.png'>

### 11.2.2 修改pom文件

```xml
<dependencies>
    <!--openfeign-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <!--eureka client-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
    <dependency>
        <groupId>com.ly.springcloud</groupId>
        <artifactId>cloud-api-commons</artifactId>
        <version>${project.version}</version>
    </dependency>
    <!--web-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!--一般基础通用配置-->
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

### 11.2.3 修改yaml文件

```yaml
server:
  port: 80

spring:
  application:
    name: cloud-order-service
eureka:
  client:
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/
    register-with-eureka: true
```

### 11.2.4 创建主启动类

```java
@EnableFeignClients//代替@RibbonClient和@EurekaClient
@SpringBootApplication
public class OrderFeignMain80 {
    public static void main(String[] args){
      SpringApplication.run(OrderFeignMain80.class, args);
    }
}
```

### 11.2.5 ==创建Openfeign接口*==

<font color='red'>***最重要使用OpenFeign的接口（PaymentFeignService），内部函数定义的返回值必须和provider的controller返回值完全一样才行。***</font>

<img src='img\image-20221221093347811.png'>

```java
package com.ly.springcloud.service;

import com.ly.springcloud.entities.CommonResult;
import com.ly.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FileName：PaymentFeignService.java
 * Author：Ly
 * Date：2022/12/20
 * Description： 与Provider端的service端接口完全一样的feign接口,而且必须加入ioc容器方便使用
 */
@Component
@FeignClient(name = "cloud-payment-service")//provider服务名
public interface PaymentFeignService {

    /**
     * 对应provider端Service接口方法，OpenFeign可以识别Springmvc注解（相当于生产端的controller和service层合并）
     *      返回值必须和service对应的controller的返回值完全一样，否则会导致数据无法封装为null
     * @param payment 参数对应的实体类
     * @return  结果集
     */
    @GetMapping("/payment/create")
    CommonResult create(Payment payment);

    /**
     * 相当于provider生产端的controller和service层合并
     *      返回值必须和service对应的controller的返回值完全一样，否则会导致数据无法封装为null
     *      |比如provider的返回值是 CommonResult|
     *      |我写 CommonResult<Payment> 错     |
     *      |    Payment 错                    |
     * @param id 查询id
     * @return 结果集
     */
    @GetMapping("/payment/get/{id}")
    CommonResult getPaymentById(@PathVariable("id") Long id);
}
```

### 11.2.6 创建controller

```java
@Slf4j
@RestController
public class OrderFeignController {

    @Autowired//就和mybatis的mapper接口一样的用法
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/create")
    public CommonResult create(Payment payment) {
        log.info("consumer 接收到一条新增payment消息 serial={}",payment.getSerial());
        return paymentFeignService.create(payment);

    }

    @GetMapping("/consumer/payment/get/{id}")
    //openfeign接口函数返回值类型必须和 provider的对应controller方法返回值类型完全一样
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }
}
```

### 11.2.7 测试

<img src='img\image-20221221093705595.png'>

## 11.3 OpenFeign超时控制



## 11.4 OpenFeign日志打印功能

# 12 Hystrix断路器



# 13 zuul路由网关
