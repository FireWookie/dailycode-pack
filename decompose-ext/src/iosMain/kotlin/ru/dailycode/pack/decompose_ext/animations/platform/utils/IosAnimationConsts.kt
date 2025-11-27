package ru.dailycode.pack.decompose_ext.animations.platform

import androidx.compose.animation.core.CubicBezierEasing

internal object IosAnimationConsts {

    val iosEasing = CubicBezierEasing(0.25f, 0.46f, 0.45f, 0.94f)

    const val DEFAULT_DURATION = 350
    const val FINISH_DURATION = 250
    const val CANCEL_DURATION = 350

    const val BACK_OFFSET_FACTOR = 0.35f

    const val MAX_OVERLAY_ALPHA = 0.3f

    const val PROGRESS_THRESHOLD = 0.05f
    const val MIN_ENTER_PROGRESS = 0.4f
}
