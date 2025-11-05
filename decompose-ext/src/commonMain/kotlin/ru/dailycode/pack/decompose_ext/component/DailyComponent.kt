package ru.dailycode.pack.decompose_ext.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.dailycode.pack.decompose_ext.lifecycle.coroutineScope

abstract class DailyComponent(
    context: ComponentContext
) : ComponentContext by context {
    protected val scope = coroutineScope(Dispatchers.Main.immediate)

//    protected fun <Label : Any> Store<*, *, Label>.collectLabels(
//        action: (Label) -> Unit
//    ) = this.labels.onEach(action).launchIn(scope)

//    protected inline fun <reified T : Any> get(): T = Injector.get()
//
//    protected inline fun <reified T : Any> get(
//        noinline parameters: ParametersDefinition
//    ): T = Injector.get(parameters)
}
