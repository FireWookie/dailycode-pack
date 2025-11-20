package ru.dailycode.pack.logger

internal actual fun getBaseLogger(defaultTag: String): BaseLogger =
    WasiLogger(defaultTag)

private class WasiLogger(
    private val defaultTag: String
) : BaseLogger() {

    override fun performLog(
        priority: Severity,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    ) {
        val log = buildLog(priority, tag ?: defaultTag, throwable, message)
        println("${tagMap[priority]} $log")
    }
}