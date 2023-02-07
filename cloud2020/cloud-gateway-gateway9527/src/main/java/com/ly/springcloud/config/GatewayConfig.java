package com.ly.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName:GatewayConfig.class
 * Author:ly
 * Date:2023/2/6 0006
 * Description:
 */
@Configuration
public class GatewayConfig {

    /**
     * 通过硬编码方式，给gateway注入路由，实现
     *   本机访问：http://localhost:9527/guonei
     *   则会自动跳转到http://news.baidu.com/guonei
     * @param builder RouteLocatorBuilder
     * @return RouteLocator
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        RouteLocatorBuilder.Builder routes = builder.routes();//对应配置文件的spring.cloud.gateway.routes
        routes.route("route_to_baidu",
                predicateSpec ->
                        predicateSpec
                                .path("/guonei")//path就是配置文件中predicates中的Path
                                .uri("http://news.baidu.com/"));//就是yml中的uri
        return routes.build();
    }
}
