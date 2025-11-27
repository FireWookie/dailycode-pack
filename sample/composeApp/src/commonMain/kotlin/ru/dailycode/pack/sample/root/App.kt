package ru.dailycode.pack.sample.root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.essenty.backhandler.BackHandler
import ru.dailycode.pack.compose_ext.components.input.TestButton
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.DailyModalBottomSheet
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.BackHandlerProvider
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.BottomSheetBackHandle
import ru.dailycode.pack.decompose_ext.animations.platform.nativeAnimator
import ru.dailycode.pack.decompose_ext.compose.BackHandler
import ru.dailycode.pack.sample.root.component.SampleComponent
import ru.dailycode.pack.sample.slot.SampleSlotContent

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun App(
    component: SampleComponent
) = CompositionLocalProvider {
    val childSlot by component.childSlot.subscribeAsState()

    Scaffold {
        Box(modifier = Modifier.padding(it).fillMaxSize()) {
            ChildStack(
                stack = component.childStack,
                modifier = Modifier.fillMaxSize(),
                animation = nativeAnimator(
                    backHandler = component.backHandler,
                    isBackward = false,
                    onBack = component::navigateBack,
                )
            ) { child ->
                when (val instance = child.instance) {
                    is SampleComponent.StackChild.Home -> HomeScreen(component)
                    is SampleComponent.StackChild.Detail -> DetailScreen(instance.id, component)
                    SampleComponent.StackChild.Settings -> SettingsScreen(component)
                }
            }
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
fun HomeScreen(component: SampleComponent) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        TestButton(
            text = "Show Bottom Sheet",
            onClick = { component.onEvent(SampleComponent.Event.OnShowSlotClick) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TestButton(
            text = "Go to Detail",
            onClick = { component.onEvent(SampleComponent.Event.OnNavigateToDetail("123")) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TestButton(
            text = "Go to Settings",
            onClick = { component.onEvent(SampleComponent.Event.OnNavigateToSettings) }
        )
    }
}

@Composable
fun DetailScreen(id: String, component: SampleComponent) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Detail Screen",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ID: $id",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        TestButton(
            text = "Go Back",
            onClick = { component.onEvent(SampleComponent.Event.OnNavigateBack) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TestButton(
            text = "Go to Settings",
            onClick = { component.onEvent(SampleComponent.Event.OnNavigateToSettings) }
        )
    }
}

@Composable
fun SettingsScreen(component: SampleComponent) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Settings Screen",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        TestButton(
            text = "Go Back",
            onClick = { component.onEvent(SampleComponent.Event.OnNavigateBack) }
        )
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
