package com.crypted.explorer.gateway.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Value("\${cors.allowedOrigins}")
    private lateinit var allowedOrigins: String

    @Bean
    fun corsFilter(): FilterRegistrationBean<*> {

        val allowedOriginList = allowedOrigins.split(',')

        //1.Cors Configuration
        val config = CorsConfiguration()
        //config.addAllowedOrigin("http://manage.leyou.com");
        //config.addAllowedOrigin("http://www.leyou.com");
//        config.addAllowedOrigin("*")
//        config.allowedOriginPatterns = listOf("*")
        config.allowedOrigins = allowedOriginList
        config.allowCredentials = true
        config.allowedMethods = listOf("*")
        config.maxAge = 3600L
//        config.addAllowedHeader("*")
        config.allowedHeaders = listOf("*")

        //2.Path
        val configSource = UrlBasedCorsConfigurationSource()
        configSource.registerCorsConfiguration("/**", config)

        //3.CorsFilter
        //return new CorsFilter(configSource);
        val bean: FilterRegistrationBean<CorsFilter> = FilterRegistrationBean<CorsFilter>(CorsFilter(configSource))
        bean.order = 0
        return bean
    }
}