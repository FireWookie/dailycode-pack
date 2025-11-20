package ru.dailycode.pack.logger

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private const val EPOCH_SEC = 1_000_000

public abstract class BaseLogger {

    public open fun isEnable(priority: Severity, tag: String?): Boolean = true

    public fun log(priority: Severity, tag: String?, throwable: Throwable?, message: String?) {
        if (isEnable(priority, tag)) {
            performLog(priority, tag, throwable, message)
        }
    }

    internal fun rawLog(
        priority: Severity,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    ) {
        performLog(priority, tag, throwable, message)
    }

    protected abstract fun performLog(
        priority: Severity,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    )



    @OptIn(ExperimentalTime::class)
    private fun now(): String =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            .run { "${month.number}-$day $hour:$minute:$second.${nanosecond / EPOCH_SEC}" }

    protected fun buildLog(
        priority: Severity,
        tag: String,
        throwable: Throwable?,
        message: String?
    ): String {
        val prefix = "${now()} ${tagMap[priority]} $tag - $message"
        return if (throwable != null) {
            "$prefix\n${throwable.stackTraceToString()}"
        } else prefix
    }
}

internal expect fun getBaseLogger(defaultTag: String): BaseLogger

internal val tagMap: HashMap<Severity, String> = hashMapOf(
    Severity.Verbose to "⚙\uFE0F VERBOSE",
    Severity.Debug to "\uD83D\uDEE0\uFE0F DEBUG",
    Severity.Info to "ℹ\uFE0F INFO",
    Severity.Warn to "⚠\uFE0F WARN",
    Severity.Error to "⛔ ERROR",
    Severity.Assert to "\uD83E\uDDE9 ASSERT"
)

internal val severityColor = mapOf(
    Severity.Verbose to "\u001B[37m", // white/gray
    Severity.Debug to "\u001B[36m", // cyan
    Severity.Info to "\u001B[32m", // green
    Severity.Warn to "\u001B[33m", // yellow
    Severity.Error to "\u001B[31m", // red
    Severity.Assert to "\u001B[35m", // magenta
)

internal val cssColors = mapOf(
    Severity.Verbose to "color: gray",
    Severity.Debug to "color: teal",
    Severity.Info to "color: green",
    Severity.Warn to "color: orange",
    Severity.Error to "color: red",
    Severity.Assert to "color: purple",
)
internal const val ANSI_RESET = "\u001B[0m"
