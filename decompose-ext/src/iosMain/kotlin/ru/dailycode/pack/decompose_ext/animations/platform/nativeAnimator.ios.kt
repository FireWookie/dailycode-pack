package ru.dailycode.pack.decompose_ext.animations.platform

@OptIn(ExperimentalDecomposeApi::class)
actual fun <C : Any, T : Any> nativeAnimator(
    backHandler: BackHandler,
    isReverse: Boolean,
    onBack: () -> Unit,
): StackAnimation<C, T> =
    stackAnimation(
        animator = iosSlide(),
        predictiveBackParams = {
            PredictiveBackParams(
                backHandler = backHandler,
                onBack = onBack,
                animatable = ::iosPredictiveBackAnimatable
            )
        }
    )
