package ru.dailycode.pack.compose_ext.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
sealed interface StringDesc {
    data class Default(val string: String) : StringDesc
}

@Composable
fun StringDesc.asString(): String = when (this) {
    is StringDesc.Default -> this.string
}