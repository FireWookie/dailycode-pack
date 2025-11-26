package ru.dailycode.pack.decompose_ext.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.dailycode.pack.decompose_ext.lifecycle.coroutineScope
import ru.dailycode.pack.decompose_ext.logger.ComponentLogger
import ru.dailycode.pack.decompose_ext.logger.DefaultComponentLogger

abstract class DailyComponent<E: Any?>(
    context: ComponentContext
) : ComponentContext by context, DailyUIEvent<E> {
    open val loggerDelegate: ComponentLogger = DefaultComponentLogger()

    val componentScope = coroutineScope(Dispatchers.Main.immediate)

    private val _sideEffect = MutableSharedFlow<E>(
        extraBufferCapacity = 0,
        replay = 0,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    override val sideEffect: SharedFlow<E> = _sideEffect

    protected fun publishEffect(effect: E) {
        componentScope.launch {
            _sideEffect.emit(effect)
        }
    }


    fun log(message: Any?, tag: String?) = loggerDelegate.log(message, tag)

//    protected fun <Label : Any> Store<*, *, Label>.collectLabels(
//        action: (Label) -> Unit
//    ) = this.labels.onEach(action).launchIn(scope)

//    protected inline fun <reified T : Any> get(): T = Injector.get()
//
//    protected inline fun <reified T : Any> get(
//        noinline parameters: ParametersDefinition
//    ): T = Injector.get(parameters)
}
