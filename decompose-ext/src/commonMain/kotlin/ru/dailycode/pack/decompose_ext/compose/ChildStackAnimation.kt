package ru.dailycode.pack.decompose_ext.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.stack.animation.isEnter
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackEvent

/**
 * Готовые анимации для ChildStack навигации
 */
object ChildStackAnimations {

    /**
     * Анимация слайда слева направо (как в Android)
     */
    fun slide(
        animationDuration: Int = 300
    ): StackAnimation<Any, Any> = stackAnimation { child, otherChild, direction ->
        val isForward = direction.isEnter

        val enterTransition = slideInHorizontally(
            initialOffsetX = { if (isForward) it else -it },
            animationSpec = tween(animationDuration)
        ) + fadeIn(animationSpec = tween(animationDuration))

        val exitTransition = slideOutHorizontally(
            targetOffsetX = { if (isForward) -it / 3 else it },
            animationSpec = tween(animationDuration)
        ) + fadeOut(animationSpec = tween(animationDuration))

        ContentTransform(enterTransition, exitTransition)
    }

    /**
     * Анимация слайда снизу вверх (для модальных экранов)
     */
    fun slideUp(
        animationDuration: Int = 300
    ): StackAnimation<Any, Any> = stackAnimation { child, otherChild, direction ->
        val isForward = direction.isEnter

        val enterTransition = if (isForward) {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(animationDuration)
            ) + fadeIn(animationSpec = tween(animationDuration))
        } else {
            slideInVertically(
                initialOffsetY = { -it / 10 },
                animationSpec = tween(animationDuration)
            ) + fadeIn(animationSpec = tween(animationDuration))
        }

        val exitTransition = if (isForward) {
            slideOutVertically(
                targetOffsetY = { -it / 10 },
                animationSpec = tween(animationDuration)
            ) + fadeOut(animationSpec = tween(animationDuration))
        } else {
            slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(animationDuration)
            ) + fadeOut(animationSpec = tween(animationDuration))
        }

        ContentTransform(enterTransition, exitTransition)
    }

    /**
     * Простая анимация fade
     */
    fun fade(
        animationDuration: Int = 300
    ): StackAnimation<Any, Any> = stackAnimation { child, otherChild, direction ->
        fadeIn(animationSpec = tween(animationDuration)) togetherWith
                fadeOut(animationSpec = tween(animationDuration))
    }

    /**
     * Анимация масштабирования (zoom)
     */
    fun scale(
        animationDuration: Int = 300,
        initialScale: Float = 0.8f,
        targetScale: Float = 1.1f
    ): StackAnimation<Any, Any> = stackAnimation { child, otherChild, direction ->
        val isForward = direction.isEnter

        val enterTransition = scaleIn(
            initialScale = if (isForward) initialScale else targetScale,
            animationSpec = tween(animationDuration)
        ) + fadeIn(animationSpec = tween(animationDuration))

        val exitTransition = scaleOut(
            targetScale = if (isForward) targetScale else initialScale,
            animationSpec = tween(animationDuration)
        ) + fadeOut(animationSpec = tween(animationDuration))

        ContentTransform(enterTransition, exitTransition)
    }

    /**
     * Комбинированная анимация: слайд + fade + легкий scale
     */
    fun slideFade(
        animationDuration: Int = 300,
        scaleAmount: Float = 0.95f
    ): StackAnimation<Any, Any> = stackAnimation { child, otherChild, direction ->
        val isForward = direction.isEnter

        val enterTransition = slideInHorizontally(
            initialOffsetX = { if (isForward) it else -it },
            animationSpec = tween(animationDuration)
        ) + fadeIn(animationSpec = tween(animationDuration)) +
                scaleIn(
                    initialScale = scaleAmount,
                    animationSpec = tween(animationDuration)
                )

        val exitTransition = slideOutHorizontally(
            targetOffsetX = { if (isForward) -it / 3 else it },
            animationSpec = tween(animationDuration)
        ) + fadeOut(animationSpec = tween(animationDuration))

        ContentTransform(enterTransition, exitTransition)
    }

    /**
     * Анимация "шаг назад" - текущий экран уменьшается и исчезает
     */
    fun scaleDown(
        animationDuration: Int = 300
    ): StackAnimation<Any, Any> = stackAnimation { child, otherChild, direction ->
        val isForward = direction.isEnter

        val enterTransition = if (isForward) {
            scaleIn(
                initialScale = 1.1f,
                animationSpec = tween(animationDuration)
            ) + fadeIn(animationSpec = tween(animationDuration))
        } else {
            scaleIn(
                initialScale = 0.9f,
                animationSpec = tween(animationDuration)
            ) + fadeIn(animationSpec = tween(animationDuration))
        }

        val exitTransition = if (isForward) {
            scaleOut(
                targetScale = 0.9f,
                animationSpec = tween(animationDuration)
            ) + fadeOut(animationSpec = tween(animationDuration))
        } else {
            scaleOut(
                targetScale = 1.1f,
                animationSpec = tween(animationDuration)
            ) + fadeOut(animationSpec = tween(animationDuration))
        }

        ContentTransform(enterTransition, exitTransition)
    }

    /**
     * Без анимации
     */
    fun none(): StackAnimation<Any, Any> = stackAnimation { _, _, _ ->
        EnterTransition.None togetherWith ExitTransition.None
    }
}

/**
 * Extension функция для упрощенного использования анимаций с ChildStack
 */
@Composable
fun <C : Any, T : Any> AnimatedChildStack(
    stack: Value<ChildStack<C, T>>,
    modifier: Modifier = Modifier,
    animation: StackAnimation<C, T> = ChildStackAnimations.slide() as StackAnimation<C, T>,
    content: @Composable (child: T) -> Unit
) {
    Children(
        stack = stack,
        modifier = modifier,
        animation = animation,
        content = { child -> content(child.instance) }
    )
}

/**
 * Predictive Back Gesture поддержка для Android
 */
@OptIn(ExperimentalDecomposeApi::class)
object PredictiveBackAnimation {

    /**
     * Создает анимацию с поддержкой predictive back gesture
     */
    fun <C : Any, T : Any> create(
        animationDuration: Int = 300,
        onBack: ((BackEvent) -> Unit)? = null
    ): StackAnimation<C, T> = stackAnimation(
        animator = predictiveBackAnimator(
            animationDuration = animationDuration,
            onBack = onBack
        )
    )

    private fun <C : Any, T : Any> predictiveBackAnimator(
        animationDuration: Int,
        onBack: ((BackEvent) -> Unit)?
    ): StackAnimator = StackAnimator { direction, isInitial, onFinished, content ->
        AnimatedContent(
            targetState = direction,
            transitionSpec = {
                val isForward = targetState.isEnter

                val enter = slideInHorizontally(
                    initialOffsetX = { if (isForward) it else -it },
                    animationSpec = tween(animationDuration)
                ) + fadeIn(animationSpec = tween(animationDuration))

                val exit = slideOutHorizontally(
                    targetOffsetX = { if (isForward) -it / 3 else it },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(animationSpec = tween(animationDuration))

                enter togetherWith exit
            }
        ) { _ ->
            content()
        }
    }
}

/**
 * Кастомная анимация через builder
 */
fun customStackAnimation(
    animationDuration: Int = 300,
    enterTransition: (isForward: Boolean, fullWidth: Int) -> EnterTransition,
    exitTransition: (isForward: Boolean, fullWidth: Int) -> ExitTransition
): StackAnimation<Any, Any> = stackAnimation { child, otherChild, direction ->
    val isForward = direction.isEnter

    // Получаем ширину экрана (примерное значение, в реальности нужно использовать LocalConfiguration)
    val fullWidth = 1080

    ContentTransform(
        EnterTransition(isForward, fullWidth),
        ExitTransition(isForward, fullWidth)
    )
}
