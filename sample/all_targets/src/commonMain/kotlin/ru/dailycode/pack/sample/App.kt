package ru.dailycode.pack.sample

import ru.dailycode.pack.logger.DailyLogger

class App {
    init {
        DailyLogger.e(message = "error")
        DailyLogger.d(message = "debug")
        DailyLogger.v(message = "verbose")
        DailyLogger.i(message = "info")
    }
}
