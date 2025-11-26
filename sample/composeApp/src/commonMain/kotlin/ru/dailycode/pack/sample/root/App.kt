package ru.dailycode.pack.sample.root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.essenty.backhandler.BackHandler
import ru.dailycode.pack.compose_ext.components.input.TestButton
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.DailyModalBottomSheet
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.BackHandlerProvider
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.BottomSheetBackHandle
import ru.dailycode.pack.decompose_ext.compose.BackHandler
import ru.dailycode.pack.sample.root.component.SampleComponent
import ru.dailycode.pack.sample.slot.SampleSlotContent

@Composable
fun App(
    component: SampleComponent
) = CompositionLocalProvider {
    val childSlot by component.childSlot.subscribeAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TestButton(
                text = "Button",
                onClick = { component.onEvent(SampleComponent.Event.OnShowSlotClick) }
            )
        }

        childSlot.child?.let { child ->

            DailyModalBottomSheet(
                backHandlerProvider = EssentyBackHandlerProvider(component.backHandler),
                dismiss = component::dismissSlot,
            ) { hideSheet, paddingValues ->
                when (val instance = child.instance) {
                    SampleComponent.SlotChild.SampleSlot -> SampleSlotContent(
                        onDismiss = { hideSheet() }
                    )
                }
            }
        }
    }
}

@Composable
fun EssentyBackHandlerProvider(backHandler: BackHandler): BackHandlerProvider {
    return BackHandlerProvider { handle ->
        EssentyBottomSheetBackHandler(handle, backHandler)
    }
}

@Composable
internal fun EssentyBottomSheetBackHandler(
    handle: BottomSheetBackHandle,
    backHandler: BackHandler
) {
    BackHandler(
        backHandler = backHandler,
        isEnabled = true,
        onBack = {
            if (handle.canHide) handle.onBack()
        },
        callBackProgress = { event ->
            if (handle.canHide) handle.callbackProgress(event.progress)
        }
    )
}
