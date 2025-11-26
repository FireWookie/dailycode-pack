package ru.dailycode.pack.sample.root.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.dailycode.pack.decompose_ext.event.ComponentEventHandler

@Stable
interface SampleComponent : BackHandlerOwner, ComponentEventHandler<SampleComponent.Event> {
    val childSlot: Value<ChildSlot<*, SlotChild>>


    fun dismissSlot()
    sealed interface SlotChild {
        object SampleSlot : SlotChild
    }

    sealed interface Event {
        object OnShowSlotClick : Event
    }
}
