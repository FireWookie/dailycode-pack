package ru.dailycode.pack.logger

public object DailyLogger {
    public var defaultTag: String = ""
    public val delegate: BaseLogger = getBaseLogger(defaultTag)

    public fun isEnable(priority: Severity, tag: String?): Boolean = delegate.isEnable(priority, tag)

    public fun d(message: Any?, tag: String? = null, throwable: Throwable? = null): Unit =
        log(Severity.Debug, tag, "$message", throwable)
    public fun e(message: Any?, tag: String? = null, throwable: Throwable? = null): Unit =
        log(Severity.Error, tag, "$message", throwable)
    public fun i(tag: String? = null, message: Any?, throwable: Throwable? = null): Unit =
        log(Severity.Info, tag, "$message", throwable)
    public fun v(tag: String? = null, message: Any?, throwable: Throwable? = null): Unit =
        log(Severity.Verbose, tag, "$message", throwable)

    public fun log(
        priority: Severity,
        tag: String? = null,
        message: String?,
        throwable: Throwable? = null,
    ) {
        if (isEnable(priority, tag)) {
            delegate.rawLog(priority, tag, throwable, message)
        }
    }

    public enum class LoggerType {
        SCREEN,
        STORE,
        COMPONENT,
        DEFAULT,
        ERROR
    }
}
