package ru.dailycode.pack.compose_ext.utils.ext

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

fun Dp.toSize(): DpSize = DpSize(this, this)

infix fun Dp.x(other: Dp): DpSize = DpSize(this, other)