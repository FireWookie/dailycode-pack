package ru.dailycode.pack.decompose_ext.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

/**
 * Примеры использования ChildStackAnimation
 *
 * Этот файл содержит примеры кода для различных сценариев использования анимаций
 */

// Пример 1: Базовое использование с анимацией slide
@Composable
fun ExampleSlideAnimation(
    stack: Value<ChildStack<*, MyChild>>,
    modifier: Modifier = Modifier
) {
    AnimatedChildStack(
        stack = stack,
        modifier = modifier,
        animation = ChildStackAnimations.slide(animationDuration = 300)
    ) { child ->
        when (child) {
            is MyChild.Screen1 -> Screen1Content()
            is MyChild.Screen2 -> Screen2Content()
        }
    }
}

// Пример 2: Использование fade анимации
@Composable
fun ExampleFadeAnimation(
    stack: Value<ChildStack<*, MyChild>>,
    modifier: Modifier = Modifier
) {
    AnimatedChildStack(
        stack = stack,
        modifier = modifier,
        animation = ChildStackAnimations.fade(animationDuration = 250)
    ) { child ->
        // Ваш контент
    }
}

// Пример 3: Анимация slideUp для модальных экранов
@Composable
fun ExampleSlideUpAnimation(
    stack: Value<ChildStack<*, MyChild>>,
    modifier: Modifier = Modifier
) {
    AnimatedChildStack(
        stack = stack,
        modifier = modifier,
        animation = ChildStackAnimations.slideUp(animationDuration = 350)
    ) { child ->
        // Идеально для модальных экранов
    }
}

// Пример 4: Scale анимация
@Composable
fun ExampleScaleAnimation(
    stack: Value<ChildStack<*, MyChild>>,
    modifier: Modifier = Modifier
) {
    AnimatedChildStack(
        stack = stack,
        modifier = modifier,
        animation = ChildStackAnimations.scale(
            animationDuration = 300,
            initialScale = 0.85f,
            targetScale = 1.05f
        )
    ) { child ->
        // Zoom эффект
    }
}

// Пример 5: Комбинированная анимация slideFade
@Composable
fun ExampleSlideFadeAnimation(
    stack: Value<ChildStack<*, MyChild>>,
    modifier: Modifier = Modifier
) {
    AnimatedChildStack(
        stack = stack,
        modifier = modifier,
        animation = ChildStackAnimations.slideFade(
            animationDuration = 300,
            scaleAmount = 0.97f
        )
    ) { child ->
        // Плавная анимация с легким масштабированием
    }
}

// Пример 6: Анимация scaleDown
@Composable
fun ExampleScaleDownAnimation(
    stack: Value<ChildStack<*, MyChild>>,
    modifier: Modifier = Modifier
) {
    AnimatedChildStack(
        stack = stack,
        modifier = modifier,
        animation = ChildStackAnimations.scaleDown(animationDuration = 300)
    ) { child ->
        // Эффект "ухода назад"
    }
}

// Пример 7: Без анимации
@Composable
fun ExampleNoAnimation(
    stack: Value<ChildStack<*, MyChild>>,
    modifier: Modifier = Modifier
) {
    AnimatedChildStack(
        stack = stack,
        modifier = modifier,
        animation = ChildStackAnimations.none()
    ) { child ->
        // Мгновенная смена экранов
    }
}

// Пример 8: Кастомная анимация
@Composable
fun ExampleCustomAnimation(
    stack: Value<ChildStack<*, MyChild>>,
    modifier: Modifier = Modifier
) {
    AnimatedChildStack(
        stack = stack,
        modifier = modifier,
        animation = customStackAnimation(
            animationDuration = 400,
            enterTransition = { isForward, fullWidth ->
                androidx.compose.animation.slideInHorizontally(
                    initialOffsetX = { if (isForward) fullWidth else -fullWidth },
                    animationSpec = androidx.compose.animation.core.tween(400)
                )
            },
            exitTransition = { isForward, fullWidth ->
                androidx.compose.animation.slideOutHorizontally(
                    targetOffsetX = { if (isForward) -fullWidth / 2 else fullWidth },
                    animationSpec = androidx.compose.animation.core.tween(400)
                )
            }
        )
    ) { child ->
        // Ваша кастомная анимация
    }
}

// Вспомогательные sealed классы для примеров
sealed interface MyChild {
    object Screen1 : MyChild
    object Screen2 : MyChild
}

@Composable
private fun Screen1Content() {
    // Заглушка для примера
}

@Composable
private fun Screen2Content() {
    // Заглушка для примера
}
