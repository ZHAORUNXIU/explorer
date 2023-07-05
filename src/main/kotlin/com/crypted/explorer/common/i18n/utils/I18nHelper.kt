package com.crypted.explorer.common.i18n.utils

import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import org.springframework.web.servlet.LocaleResolver
import java.util.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

@Component
class I18nHelper {
    @Resource
    private val localeResolver: LocaleResolver? = null

    @Resource
    private val messageSource: MessageSource? = null
    fun getMessage(request: HttpServletRequest?, code: Int, vararg args: Any?): String? {
        return request?.let { localeResolver!!.resolveLocale(it) }?.let {
            messageSource!!.getMessage(Integer.toString(code), args, null,
                it
            )
        }
    }

    fun getMessage(code: Int, vararg args: Any?): String? {
        return messageSource!!.getMessage(Integer.toString(code), args, null, resolveLocale())
    }

    fun getMessage(key: String?, vararg args: Any?): String? {
        return messageSource!!.getMessage(key!!, args, null, resolveLocale())
    }

    fun getMessage(key: String?, locale: Locale?): String? {
        return locale?.let { messageSource!!.getMessage(key!!, null, null, it) }
    }

    private fun resolveLocale(): Locale {
        // 没有上下文时取默认值
//        val req: ReqContext = ReqContext.get()
//        return if (req == null || req.getRequest() == null) {
//            Locale.CHINESE
//        } else localeResolver!!.resolveLocale(req.getRequest())
        return Locale.KOREA
    }
}