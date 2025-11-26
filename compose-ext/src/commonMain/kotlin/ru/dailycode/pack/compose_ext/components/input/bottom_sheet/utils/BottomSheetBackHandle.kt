package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils

public data class BottomSheetBackHandle(
    val canHide: Boolean,
    val onBack: () -> Unit,
    val callbackProgress: (Float) -> Unit,
)
