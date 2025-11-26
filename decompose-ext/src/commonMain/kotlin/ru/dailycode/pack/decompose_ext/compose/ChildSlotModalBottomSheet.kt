package ru.dailycode.pack.decompose_ext.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value

/**
 * Material 3 ModalBottomSheet для отображения BottomSheetContentComponent.
 *
 * Работает как диалог - отображает Popup поверх всего приложения,
 * и не требует заворачивать в себя остальной контент.
 *
 * Показывает BottomSheet на основе ChildSlot из Decompose навгиации.
 */
@Composable
fun<S : Any> ChildSlotModalBottomSheet(
    sheetContentSlotState: Value<ChildSlot<*, BottomSheetContentComponent<S>>>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ((BottomSheetContentComponent<S>?) -> Unit),
) {
    val sheetContentSlot: ChildSlot<*, BottomSheetContentComponent<S>> by sheetContentSlotState.subscribeAsState()

    val sheetContentComponentState: State<BottomSheetContentComponent<S>?> =
        rememberUpdatedState(sheetContentSlot.child?.instance)

//    DecomposeSheetContainer(
//        sheetContentComponentState = sheetContentComponentState,
//        onDismiss = onDismiss,
//    ) { content.invoke(it) }
}
