package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.incubating

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.flow.drop




private fun log(message: String, throwable: Throwable? = null) {
    if (throwable != null) {
        println("\"ComponentMBS\", $message, $throwable")
//        Log.d()
    } else {
        println("\"ComponentMBS\", $message")
    }
}
//
///**
// * Мы хотим отображать затемнение BottomSheet на весь экран, edge-to-edge,
// * включая статус бар и панель навгиации.
// *
// * На API < 30 есть баг https://issuetracker.google.com/issues/290893168
// * Проблема в том, что WindowInsets нормально работает только начиная с Android 11 (SDK 30).
// *
// * https://medium.com/androiddevelopers/animating-your-keyboard-fb776a8fb66d
// *
// * Compat-реализация поддерживается только для основных компонентов вроде Activity, но не
// * поддерживается в Window, в котором открывается Material 3 BottomSheet.
// */
//@Composable
//internal fun bottomSheetWindowInsets(): WindowInsets {
//    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//        // Начиная с Android 11  (SDK 30) мы передаём в BottomSheet
//        // нулевые инсеты, так как внутри контента можно будет использовать
//        // safeDrawingPadding и прочие модификаторы.
//        WindowInsets(0.dp)
//    } else {
//        // Для Android < 11 (SDK < 30) приходится применять инсеты на верхнем уровне,
//        // так как safeDrawingPadding и аналоги не будут работать в контенте.
//        // Чтобы не закрашивать status bar, передаём только нижнюю часть инсетов.
//        WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
//    }
//}
