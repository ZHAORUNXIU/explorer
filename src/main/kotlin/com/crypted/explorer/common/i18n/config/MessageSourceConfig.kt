package com.crypted.explorer.common.i18n.config

import com.crypted.explorer.common.i18n.context.LocalReloadableResourceBundleMessageSource
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*

@Configuration
class MessageSourceConfig {
    @get:Bean(name = ["messageSource"])
    val messageSource: MessageSource
        get() {
            val resourceBundleMessageSource = LocalReloadableResourceBundleMessageSource()
            resourceBundleMessageSource.setDefaultEncoding("UTF-8")
            resourceBundleMessageSource.setBasenames("classpath*:/i18n.messages/explorer")
            return resourceBundleMessageSource
        }

    @get:Bean(name = ["localeResolver"])
    val localeResolver: LocaleResolver
        get() {
            val acceptHeaderLocaleResolver = AcceptHeaderLocaleResolver()
            acceptHeaderLocaleResolver.defaultLocale = Locale.forLanguageTag("ko_KR")
            return acceptHeaderLocaleResolver
        }
}
