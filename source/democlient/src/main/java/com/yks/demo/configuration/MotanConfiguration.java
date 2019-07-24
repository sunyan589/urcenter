package com.yks.demo.configuration;


import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
  *  motan client端和server端共用配置信息
  *  包括注册中心配置、协议配置
  * @author lvcr
  * @version 1.0 
  * @see MotanConfiguration 
  * @since JDK1.8
  * @date 2018/6/1 8:53
  */

@Configuration
public class MotanConfiguration {

    /**
     * Description: 需要一个返回类型为AnnotationBean的bean定义，这个用来扫描包来解析motan的注解进行暴露服务或引用服务
     *
     * @return: AnnotationBean
     * @auther: lvcr
     * @date: 2018/5/29 14:57
     * @see
     */
    @Bean
    public AnnotationBean motanAnnotationBean() {
        return new AnnotationBean();
    }

    /**
     * Description:注册中心配置 使用不同注册中心需要依赖对应的jar包。如果不使用注册中心，可以把check属性改为false，忽略注册失败。
     *
     * @auther: lvcr
     * @date: 2018/5/29 14:58
     * @see
     */
    @Bean(name = "zkRegistry")
    @ConfigurationProperties(prefix = "motan.registry")
    public RegistryConfigBean registryConfig() {
        return new RegistryConfigBean();
    }

    /**
     * Description: 协议配置。为防止多个业务配置冲突，推荐使用id表示具体协议。
     *
     * @auther: lvcr
     * @date: 2018/5/29 14:59
     * @see
     */
    @Bean(name = "motan2")
    @ConfigurationProperties(prefix = "motan.protocol")
    public ProtocolConfigBean protocolConfig1() {
        return new ProtocolConfigBean();
    }
}
