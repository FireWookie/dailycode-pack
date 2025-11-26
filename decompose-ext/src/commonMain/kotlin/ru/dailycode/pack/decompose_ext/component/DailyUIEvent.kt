package ru.dailycode.pack.decompose_ext.component

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface DailyUIEvent<EVENT: Any?> {
    val sideEffect: SharedFlow<EVENT>
}