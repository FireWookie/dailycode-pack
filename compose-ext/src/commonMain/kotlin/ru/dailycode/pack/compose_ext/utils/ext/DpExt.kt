package ru.dailycode.pack.compose_ext.utils.ext

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

public fun Dp.toSize(): DpSize = DpSize(this, this)

public infix fun Dp.x(other: Dp): DpSize = DpSize(this, other)