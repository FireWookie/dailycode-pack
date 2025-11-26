package ru.dailycode.pack.compose_ext.utils.ext


val Long.normalizeEpoch: Long
    get() = when (this.toString().length) {
        10 -> this * 1000
        13 -> this
        else -> error("Unexpected epoch format: $this")
    }

fun Long.formattedDuration(): String {
    val hours = this / (1000 * 60 * 60)
    val minutes = (this % (1000 * 60 * 60)) / (1000 * 60)
    val seconds = (this % (1000 * 60)) / 1000

    val formattedHours = hours.toString().padStart(2, '0')
    val formattedMinutes = minutes.toString().padStart(2, '0')
    val formattedSeconds = seconds.toString().padStart(2, '0')

    return "$formattedHours:$formattedMinutes:$formattedSeconds"
}

fun Long?.orEmpty() = this ?: 0



//fun Long.formattedDate(
//    divider: String = "/",
//): String {
//    val localDate = this.toLocalDate
//    val month = localDate.month.number.toString().padStart(1, '0')
//    val day = localDate.day.toString().padStart(1, '0')
//    val year = (localDate.year % 100).toString().padStart(2, '0') // последние 2 цифры года
//    return "$month$divider$day$divider$year"
//}