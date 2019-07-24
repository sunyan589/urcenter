package com.yks.demo.configuration;

import com.weibo.api.motan.config.springsupport.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * motan client端配置信息类
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/5/29 15:11
 * @see MotanClientConfiguration
 * @since JDK1.8
 */
@Configuration
public class MotanClientConfiguration {

    /**
     * Description:
     *   1、根据配置文件里motan.client.enabled=true配置是否启用motan client功能
     *        eg:motan.client.enabled=true 启用
     *           motan.client.enabled=false 关闭
     * 2、通用referer基础配置
     *
     * @auther: lvcr
     * @date: 2018/6/1 9:03
     * @see
     */
    @ConditionalOnProperty(prefix = "motan.client", value = "enabled", havingValue = "true")
    @Bean(name = "basicRefererConfig")
    @ConfigurationProperties(prefix = "motan.client.basicreferer")
    public BasicRefererConfigBean basicRefererConfigBean() {
        return new BasicRefererConfigBean();
    }

    @ConditionalOnProperty(prefix = "motan.client", value = "enabled", havingValue = "true")
    @Bean(name = "refererConfigOms")
    @ConfigurationProperties(prefix = "motan.client.refereroms")
    public RefererConfigBean refererConfigBeanOms() {
        return new RefererConfigBean();
    }


    @ConditionalOnProperty(prefix = "motan.client", value = "enabled", havingValue = "true")
    @Bean(name = "refereromsordermanage")
    @ConfigurationProperties(prefix = "motan.client.refereromsordermanage")
    public RefererConfigBean refererConfigBeanOmsOrderManage() {
        return new RefererConfigBean();
    }


}
