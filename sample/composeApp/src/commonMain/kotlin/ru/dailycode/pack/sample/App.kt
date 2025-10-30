package ru.dailycode.pack.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import ru.dailycode.pack.TestButton

@Composable
fun App() {
    Column {
        TestButton(
            text = "Button",
            onClick = {}
        )
    }
}