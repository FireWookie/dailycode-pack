package ru.dailycode.pack.decompose_ext.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import ru.dailycode.pack.decompose_ext.lifecycle.coroutineScope
import ru.dailycode.pack.decompose_ext.logger.ComponentLogger
import ru.dailycode.pack.decompose_ext.logger.DefaultComponentLogger

abstract class DailyComponent(
    context: ComponentContext
) : ComponentContext by context {
    open val loggerDelegate: ComponentLogger = DefaultComponentLogger()

    val componentScope = coroutineScope(Dispatchers.Main.immediate)

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
