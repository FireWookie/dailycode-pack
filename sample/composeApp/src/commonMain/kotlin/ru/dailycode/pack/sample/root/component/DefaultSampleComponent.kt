package ru.dailycode.pack.sample.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable
import ru.dailycode.pack.decompose_ext.component.DailyComponent
import ru.dailycode.pack.decompose_ext.event.ComponentEventHandler
import ru.dailycode.pack.decompose_ext.event.SuspendEventReducer

fun buildSampleComponent(
    context: ComponentContext
): SampleComponent = DefaultSampleComponent(
    context = context
)

private class DefaultSampleComponent(
    context: ComponentContext
) : SampleComponent,
    DailyComponent<Nothing>(context)
{
    private val slotNavigation = SlotNavigation<SlotConfig>()
    private val stackNavigation = StackNavigation<StackConfig>()

    private val slot = childSlot(
        source = slotNavigation,
        serializer = SlotConfig.serializer(),
        handleBackButton = false,
        childFactory = ::createChildSlot
    )

    private val stack = childStack(
        source = stackNavigation,
        serializer = StackConfig.serializer(),
        initialConfiguration = StackConfig.Home,
        handleBackButton = true,
        childFactory = ::createChildStack
    )

    override val childSlot: Value<ChildSlot<*, SampleComponent.SlotChild>> = slot
    override val childStack: Value<ChildStack<*, SampleComponent.StackChild>> = stack

    private fun createChildSlot(
        config: SlotConfig,
        context: ComponentContext
    ): SampleComponent.SlotChild = when (config) {
        SlotConfig.SampleSlot -> createSampleSlot()
    }

    private fun createChildStack(
        config: StackConfig,
        context: ComponentContext
    ): SampleComponent.StackChild = when (config) {
        StackConfig.Home -> SampleComponent.StackChild.Home
        is StackConfig.Detail -> SampleComponent.StackChild.Detail(config.id)
        StackConfig.Settings -> SampleComponent.StackChild.Settings
    }

    private fun createSampleSlot() = SampleComponent.SlotChild.SampleSlot

    override fun dismissSlot() {
        slotNavigation.dismiss()
    }

    override fun navigateToDetail(id: String) {
        stackNavigation.push(StackConfig.Detail(id))
    }

    override fun navigateToSettings() {
        stackNavigation.push(StackConfig.Settings)
    }

    override fun navigateBack() {
        stackNavigation.pop()
    }

    override fun onEvent(event: SampleComponent.Event) {
        when (event) {
            is SampleComponent.Event.OnShowSlotClick -> slotNavigation.activate(SlotConfig.SampleSlot)
            is SampleComponent.Event.OnNavigateToDetail -> navigateToDetail(event.id)
            SampleComponent.Event.OnNavigateToSettings -> navigateToSettings()
            SampleComponent.Event.OnNavigateBack -> navigateBack()
        }
    }

    @Serializable
    sealed interface SlotConfig {
        @Serializable
        object SampleSlot : SlotConfig
    }

    @Serializable
    sealed interface StackConfig {
        @Serializable
        object Home : StackConfig

        @Serializable
        data class Detail(val id: String) : StackConfig

        @Serializable
        object Settings : StackConfig
    }
}
