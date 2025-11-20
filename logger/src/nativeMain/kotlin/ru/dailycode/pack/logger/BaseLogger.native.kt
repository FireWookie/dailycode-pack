package ru.dailycode.pack.logger

import kotlin.experimental.ExperimentalNativeApi

internal actual fun getBaseLogger(defaultTag: String): BaseLogger =
    NativeConsoleLogger(defaultTag)

@OptIn(ExperimentalNativeApi::class)
private class NativeConsoleLogger(
    private val defaultTag: String,
) : BaseLogger() {

    private val supportAnsi: Boolean by lazy { detectAnsi() }

    override fun performLog(
        priority: Severity,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    ) {
        val log = buildLog(priority, tag ?: defaultTag, throwable, message)

        if (supportAnsi) {
            val color = severityColor[priority] ?: ""
            println("$color$log$ANSI_RESET")
        } else {
            println("${tagMap[priority]} $log")
        }
    }

    private fun detectAnsi(): Boolean {
        return when (Platform.osFamily) {
            OsFamily.LINUX -> true
            OsFamily.WINDOWS -> true
            OsFamily.MACOSX -> false
            else -> false
        }
    }
}
