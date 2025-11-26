package ru.dailycode.pack.sample.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.dailycode.pack.decompose_ext.component.DailyComponent
import ru.dailycode.pack.decompose_ext.event.SuspendEventReducer

fun buildSampleComponent(
    context: ComponentContext
): SampleComponent = DefaultSampleComponent(
    context = context
)

private class DefaultSampleComponent(
    context: ComponentContext
) : SampleComponent, DailyComponent(context), SuspendEventReducer<SampleComponent.Event> {
    private val slotNavigation = SlotNavigation<SlotConfig>()

    private val slot = childSlot(
        source = slotNavigation,
        serializer = SlotConfig.serializer(),
        handleBackButton = false,
        childFactory = ::createChildSlot
    )
    override val childSlot: Value<ChildSlot<*, SampleComponent.SlotChild>> =
        slot

    private fun createChildSlot(
        config: SlotConfig,
        context: ComponentContext
    ): SampleComponent.SlotChild = when (config) {
        SlotConfig.SampleSlot -> createSampleSlot()
    }

    private fun createSampleSlot() =
        SampleComponent.SlotChild.SampleSlot

    override suspend fun reduce(event: SampleComponent.Event) = when (event) {
        SampleComponent.Event.OnShowSlotClick -> {
            slotNavigation.activate(SlotConfig.SampleSlot)
        }
    }

    override fun dismissSlot() {
        slotNavigation.dismiss()
    }

    private fun hideSheet() {

    }
    @Serializable
    sealed interface SlotConfig {
        @Serializable
        object SampleSlot : SlotConfig
    }
}
