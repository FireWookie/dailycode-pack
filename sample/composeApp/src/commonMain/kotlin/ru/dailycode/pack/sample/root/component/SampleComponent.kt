package ru.dailycode.pack.sample.root.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.dailycode.pack.decompose_ext.component.DailyUIEvent
import ru.dailycode.pack.decompose_ext.event.ComponentEventHandler

@Stable
interface SampleComponent : BackHandlerOwner, ComponentEventHandler<SampleComponent.Event> {
    val childSlot: Value<ChildSlot<*, SlotChild>>
    val childStack: Value<ChildStack<*, StackChild>>

    fun dismissSlot()

    fun navigateToDetail(id: String)
    fun navigateToSettings()
    fun navigateBack()

    sealed interface SlotChild {
        object SampleSlot : SlotChild
    }

    sealed interface StackChild {
        object Home : StackChild
        data class Detail(val id: String) : StackChild
        object Settings : StackChild
    }

    sealed interface UIEvent {
        data object EventTest: UIEvent
    }


    sealed interface Event {
        object OnShowSlotClick : Event
        data class OnNavigateToDetail(val id: String) : Event
        object OnNavigateToSettings : Event
        object OnNavigateBack : Event
    }
}
