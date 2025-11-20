package ru.dailycode.pack.logger

import android.os.Build
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.util.regex.Pattern
import kotlin.math.min

internal actual fun getBaseLogger(defaultTag: String): BaseLogger = AndroidLogger(defaultTag)

private class AndroidLogger(
    private val defaultTag: String,
) : BaseLogger() {

    private val anonymousClassRegex = ANONYMOUS_CLASS_PATTERN

    override fun performLog(
        priority: Severity,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    ) {
        val resolvedTag = tag ?: resolveTag(defaultTag)

        val fullMessage = when {
            message != null && throwable != null -> {
                StringBuilder(message.length + 300).apply {
                    append(message)
                    append('\n')
                    append(throwable.stackTraceString)
                }.toString()
            }

            message != null -> message

            throwable != null -> throwable.stackTraceString

            else -> return
        }

        if (fullMessage.length <= MAX_LOG_LENGTH) {
            printSingle(resolvedTag, priority, fullMessage)
            return
        }

        printLong(resolvedTag, priority, fullMessage)
    }

    /**
     * ***********************************************************************
     *  Tag resolving
     * ***********************************************************************
     */
    private fun resolveTag(fallback: String): String {
        val trace = Thread.currentThread().stackTrace
        val element = trace
            .firstOrNull { !it.className.startsWith(LOGGER_PACKAGE_PREFIX) }
            ?: return fallback

        return buildTagFromElement(element)
    }

    private fun buildTagFromElement(element: StackTraceElement): String {
        var tag = element.className
        anonymousClassRegex.matcher(tag).replaceAll("").also { tag = it }
        tag = tag.substringAfterLast('.')
        if (tag.length > MAX_TAG_LENGTH && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            tag = tag.take(MAX_TAG_LENGTH)
        }
        return "$tag\$${element.methodName}"
    }

    /**
     * ***********************************************************************
     *  Printing helpers
     * ***********************************************************************
     */
    private fun printSingle(tag: String, p: Severity, msg: String) {
        if (p == Severity.Assert) {
            Log.wtf(tag, msg)
        } else {
            Log.println(p.toAndroid(), tag, msg)
        }
    }

    private fun printLong(tag: String, p: Severity, msg: String) {
        var i = 0
        val length = msg.length

        while (i < length) {
            val newline = msg.indexOf('\n', i).let { if (it == -1) length else it }

            while (i < newline) {
                val end = min(i + MAX_LOG_LENGTH, newline)
                val part = msg.substring(i, end)
                printSingle(tag, p, part)
                i = end
            }
            i++
        }
    }

    /**
     * ***********************************************************************
     *  Helpers & extensions
     * ***********************************************************************
     */
    private val Throwable.stackTraceString: String
        get() = StringWriter(256).use { sw ->
            PrintWriter(sw, false).use { pw ->
                this.printStackTrace(pw)
            }
            sw.toString()
        }

    private fun Severity.toAndroid(): Int = when (this) {
        Severity.Verbose -> Log.VERBOSE
        Severity.Debug -> Log.DEBUG
        Severity.Info -> Log.INFO
        Severity.Warn -> Log.WARN
        Severity.Error -> Log.ERROR
        Severity.Assert -> Log.ASSERT
    }

    companion object {
        private const val MAX_LOG_LENGTH = 4000
        private const val MAX_TAG_LENGTH = 23
        private const val LOGGER_PACKAGE_PREFIX = "ru.dailycode.pack.logger"

        private val ANONYMOUS_CLASS_PATTERN: Pattern = Pattern.compile("(\\$\\d+)+$")
    }
}
