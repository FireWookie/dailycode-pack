package ru.dailycode.pack.compose_ext.utils.ext

// %02d
fun String.kFormat(vararg args: Any): String {
    val sb = StringBuilder()
    var argIndex = 0
    var i = 0
    while (i < this.length) {
        val c = this[i]
        if (c == '%' && i + 1 < this.length) {
            var j = i + 1
            val spec = StringBuilder()
            while (j < this.length && this[j].isLetterOrDigit()) {
                spec.append(this[j])
                j++
            }

            when (spec.toString()) {
                "s" -> {
                    sb.append(args[argIndex++].toString())
                }
                "d" -> {
                    sb.append((args[argIndex++] as Int).toString())
                }
                "02d" -> {
                    val value = args[argIndex++] as Int
                    sb.append(value.toString().padStart(2, '0'))
                }
                else -> {
                    sb.append("%").append(spec) // если формат неизвестный — выводим как есть
                }
            }
            i = j
        } else {
            sb.append(c)
            i++
        }
    }
    return sb.toString()
}

fun String.maskEmail(maskChar: Char = '*'): String {
    val parts = this.split("@")
    if (parts.size != 2) return this

    val username = parts[0]
    val domain = parts[1]

    val maskedUsername = when {
        username.length <= 2 -> {
            username.map { maskChar }.joinToString("")
        }
        else -> {
            val first = username.first()
            val last = username.last()
            val middle = maskChar.toString().repeat(username.length - 2)
            "$first$middle$last"
        }
    }

    return "$maskedUsername@$domain"
}

fun String.normalize(): String {
    return this
        .lowercase()
        .replace(Regex("[\\s,\\.\\-]"), "")
}

private val urlRegex = Regex(
    pattern = "^(https?://)?([\\w-]+\\.)+[\\w-]+(:\\d+)?(/[\\w- ./?%&=]*)?$",
    option = RegexOption.IGNORE_CASE
)

fun String.isLink(): Boolean = this.matches(urlRegex)

fun String.normalizeLink(): String {
    val trimmed = this.lowercase().trim()

    return when {
        trimmed.startsWith("http://", ignoreCase = true) -> trimmed
        trimmed.startsWith("https://", ignoreCase = true) -> trimmed
        else -> "https://$trimmed"
    }
}


fun String.orNull(): String? = this.takeIf { it.isNotEmpty() }