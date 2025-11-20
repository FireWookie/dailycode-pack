package ru.dailycode.pack.logger

internal actual fun getBaseLogger(defaultTag: String): BaseLogger =
    JvmConsoleLogger(defaultTag)

private class JvmConsoleLogger(
    private val defaultTag: String,
) : BaseLogger() {

    override fun performLog(
        priority: Severity,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    ) {
        val color = severityColor[priority] ?: ""
        val log = buildLog(priority, tag ?: defaultTag, throwable, message)
        println("$color$log$ANSI_RESET")
    }
}
