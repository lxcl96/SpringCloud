package com.ly.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * FileName:MyLogGateWayFilter.class
 * Author:ly
 * Date:2023/2/6 0006
 * Description:
 */
@Slf4j
@Component
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    /**
     * 作用：鉴权，日志记录。。 （没写规则就是所有请求）
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (uname == null) {
            log.info("非法入侵，未识别到用户 (；′⌒`)");
            //可以设置个返回头
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        return chain.filter(exchange);//继续执行过滤器链，类似于doFilter
    }

    /**
     * 过滤器顺序，值越小越优先
     * @return 优先值
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
