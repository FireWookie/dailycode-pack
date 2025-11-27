//package ru.dailycode.pack.decompose_ext.logger
//
//import ru.dailycode.pack.logger.Severity
//
//interface ComponentLogger {
//
//    public val enabled: Boolean
//    public fun log(message: Any?, tag: String?)
//
//    public fun d(message: Any?, tag: String? = null, throwable: Throwable? = null): Unit =
//        ru.dailycode.pack.logger.DailyLogger.log(Severity.Debug, tag, "$message", throwable)
//    public fun e(message: Any?, tag: String? = null, throwable: Throwable? = null): Unit =
//        ru.dailycode.pack.logger.DailyLogger.log(Severity.Error, tag, "$message", throwable)
//    public fun i(tag: String? = null, message: Any?, throwable: Throwable? = null): Unit =
//        ru.dailycode.pack.logger.DailyLogger.log(Severity.Info, tag, "$message", throwable)
//    public fun v(tag: String? = null, message: Any?, throwable: Throwable? = null): Unit =
//        ru.dailycode.pack.logger.DailyLogger.log(Severity.Verbose, tag, "$message", throwable)
//}