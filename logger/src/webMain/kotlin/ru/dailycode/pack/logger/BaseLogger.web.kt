package ru.dailycode.pack.logger

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.js

internal actual fun getBaseLogger(defaultTag: String): BaseLogger =
    JsLogger(defaultTag)

@OptIn(ExperimentalWasmJsInterop::class)
private class JsLogger(
    private val defaultTag: String,
) : BaseLogger() {



    override fun performLog(
        priority: Severity,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    ) {
        val css = cssColors[priority] ?: "color: black"
        val log = buildLog(priority, tag ?: defaultTag, throwable, message)

        js("console.log('%c' + log, css)")
    }
}