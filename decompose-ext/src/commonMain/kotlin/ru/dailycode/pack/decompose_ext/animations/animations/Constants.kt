package ru.dailycode.pack.decompose_ext.animations.animations

/* Duration of the slide-in animation for screen entering */
internal const val ENTER_DURATION: Int = 500

/* Duration of the fade-in animation for the entering screen */
internal const val FADE_IN_DURATION: Int = 400

/* Duration of the fade-out animation for the exiting screen */
internal const val FADE_OUT_DURATION: Int = 150

/* Delay before starting the fade-in animation of the entering screen,
 * calculated as a portion of the fade-out duration of the exiting screen */
internal const val FADE_ENTER_DELAY: Int = FADE_OUT_DURATION - (FADE_OUT_DURATION / 3)
