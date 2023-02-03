package com.ly.springcloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


/**
 * FileName:PaymentHystrixMain8001.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description: 基于Hystrix的主启动类
 */
@EnableEurekaClient//可写可不写，默认开启
//@EnableHystrix
@EnableCircuitBreaker//cloud提供的公共注解，会自动找到断路器的实现即Hystrix 效果等同于@EnableHystrix
@SpringBootApplication
public class PaymentHystrixMain8001 {

    public static void main(String[] args){
      SpringApplication.run(PaymentHystrixMain8001.class, args);
    }

    /*
        自定义bean，不走actuator的url映射地址


     * 配置类中加入组件bean，修改映射地址不走actuator
     *此配置是为了服务监控而配置，与服务容错本身无关，springcloud升级后的坑
     *ServletRegistrationBean因为springboot的默认路径不是"/hystrix.stream"，
     *只要在自己的项目里配置上下面的servlet就可以了

    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

     */
}
