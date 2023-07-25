package com.crypted.explorer.gateway.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Raine.Jo
 */
@Configuration
class SpringDocConfig {

    @Value("\${server.url}")
    private lateinit var serverUrl: String

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(info())
            .addServersItem(Server().url(serverUrl))
    }

    fun info(): io.swagger.v3.oas.models.info.Info {
        return io.swagger.v3.oas.models.info.Info()
            .title("Ks Explorer Service")
            .version("V1")
            .description("")
            .contact(Contact().name("Raine Jo").email("raine.jo@crypted.co.kr"))
            .summary("")
    }

    @Bean
    fun explorerServiceApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .displayName("ks-explorer-service API V1")
            .group("ks-explorer-service")
            .packagesToScan("com.crypted.explorer.gateway.action")
            .build()
    }
}

