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





