package com.crypted.explorer.gateway.handler

import com.crypted.explorer.common.constant.Code
import com.crypted.explorer.common.exception.BusinessException
import com.crypted.explorer.common.i18n.utils.I18nHelper
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import com.crypted.explorer.common.util.Log.Companion.format
import com.crypted.explorer.common.util.Log.Companion.kv
import com.fasterxml.jackson.databind.ObjectMapper
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Text
import org.springframework.dao.EmptyResultDataAccessException


@RestController
@ControllerAdvice
class WebExceptionHandler {

    @Resource
    private val i18nHelper: I18nHelper? = null

    /**
     * HttpStatus 400 - Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        req: HttpServletRequest,
        res: HttpServletResponse,
        e: HttpMessageNotReadableException
    ) {
        LOG.error(
            format(
                LOG_PREFIX + "Parameter parsing failed",
                kv(URI, req.requestURI),
                e.message?.let { kv(MESSAGE, it) },
                kv(HEADERS, getHeaders(req)),
//                kv(USER_ID, ReqContext.get().getUserId())
            )
        )
        write(res, Code.ILLEGAL_REQUEST, e.message)
    }

    /**
     * HttpStatus 400 - Resource Not Found
     */
    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccessException(
        req: HttpServletRequest,
        res: HttpServletResponse,
        e: EmptyResultDataAccessException) {
        LOG.error(
            format(
                LOG_PREFIX + "Resource Not Found",
                kv(URI, req.requestURI),
                e.message?.let { kv(MESSAGE, it) },
                kv(HEADERS, getHeaders(req)),
//                kv(USER_ID, ReqContext.get().getUserId())
            ), e
        )
        write(res, Code.NOT_FOUND, e.message)
    }

    /**
     * HttpStatus 405 - Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(
        req: HttpServletRequest,
        res: HttpServletResponse,
        e: HttpRequestMethodNotSupportedException
    ) {
        LOG.error(
            format(
                LOG_PREFIX + "Unsupported request method",
                kv(URI, req.requestURI),
                e.message?.let { kv(MESSAGE, it) },
                kv(HEADERS, getHeaders(req)),
//                kv(USER_ID, ReqContext.get().getUserId())
            )
        )
        write(res, Code.ILLEGAL_REQUEST, e.message)
    }

    /**
     * HttpStatus 415 - Unsupported Media Type
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleHttpMediaTypeNotSupportedException(req: HttpServletRequest, res: HttpServletResponse, e: Exception) {
        LOG.error(
            format(
                LOG_PREFIX + "Unsupported media type",
                kv(URI, req.requestURI),
                e.message?.let { kv(MESSAGE, it) },
                kv(HEADERS, getHeaders(req)),
//                kv(USER_ID, ReqContext.get().getUserId())
            )
        )
        write(res, Code.ILLEGAL_REQUEST, e.message)
    }

    /**
     * 504 - Missing parameter exception
     */
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun missingServletRequestPartException(
        req: HttpServletRequest,
        res: HttpServletResponse,
        e: MissingServletRequestParameterException
    ) {
        LOG.error(
            format(
                LOG_PREFIX + "Missing parameter exception",
                kv(URI, req.requestURI),
                kv(MESSAGE, e.message),
                kv(HEADERS, getHeaders(req)),
//                kv(USER_ID, ReqContext.get().getUserId())
            )
        )
        write(res, Code.PARAM_MISSING, e.message)
    }

    /**
     * 502 - Method argument exception
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(
        req: HttpServletRequest,
        res: HttpServletResponse,
        e: MethodArgumentNotValidException
    ) {
        LOG.warn(
            format(
                LOG_PREFIX + "Method parameter exception",
                kv(URI, req.requestURI),
                kv(MESSAGE, e.message),
                kv(HEADERS, getHeaders(req)),
//                kv(USER_ID, ReqContext.get().getUserId())
            )
        )
        write(res, Code.ILLEGAL_PARAM, e.message)
    }

    /**
     * Business exception
     */
    @ExceptionHandler(BusinessException::class)
    fun businessException(req: HttpServletRequest, res: HttpServletResponse, e: BusinessException) {
        var message: String? = e.message
        val code = e.code
        LOG.warn(
            format(
                LOG_PREFIX + "Business exception",
                kv(URI, req.requestURI),
                message?.let { kv(MESSAGE, it) },
                kv(CODE, code),
                kv(HEADERS, getHeaders(req)),
//                kv(USER_ID, ReqContext.get().getUserId())
            )
        )
        if (Text.isEmpty(message)) {
            message = i18nHelper!!.getMessage(req, code)
        }
        if (Text.isEmpty(message)) {
            message = code.toString()
        }
        write(res, e.code, message)
    }

    /**
     * HttpStatus 500 - Internal Server Error (unknown error)
     */
    @ExceptionHandler(Exception::class)
    fun handleException(req: HttpServletRequest, res: HttpServletResponse, e: Exception) {
        LOG.error(
            format(
                LOG_PREFIX + "Unknown error occurred",
                kv(URI, req.requestURI),
                e.message?.let { kv(MESSAGE, it) },
                kv(HEADERS, getHeaders(req)),
//                kv(USER_ID, ReqContext.get().getUserId())
            ), e
        )
        write(res, Code.SYSTEM_ERROR, e.message)
    }

    private fun write(res: HttpServletResponse, code: Int, message: String?) {
        try {
            val responseBody = message?.let { Result.failure(code, null, it) } ?: Result.failure(code) // Create an error response object that includes the error code
            val objectMapper = ObjectMapper() // Create an ObjectMapper object for serializing objects to JSON strings

            res.status = code // Set the response status code
            res.contentType = MediaType.APPLICATION_JSON_VALUE // Set the response content type to JSON
            res.characterEncoding = "UTF-8"
            res.writer.write(objectMapper.writeValueAsString(responseBody)) // Serialize the error response object to a JSON string and write it to the response
            res.writer.flush() // Flush the buffer to ensure the data is written to the response

        } catch (e: IOException) {
            LOG.error(format("Failed to output exception information"), e)
        }
    }

    /**
     * Get Header
     *
     * @param req request
     * @return String
     */
    private fun getHeaders(req: HttpServletRequest): String {
        val builder = StringBuilder()
        var key: String
        val e = req.headerNames
        while (e.hasMoreElements()) {
            key = e.nextElement()
            builder.append(key).append("=").append(req.getHeader(key)).append(";")
        }
        return builder.toString()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(WebExceptionHandler::class.java)
        private const val LOG_PREFIX = "WebExceptionHandler:"
        private const val URI = "uri"
        private const val CODE = "code"
        private const val MESSAGE = "message"
        private const val USER_ID = "userId"
        private const val HEADERS = "Headers"
    }
}
