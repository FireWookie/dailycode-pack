package ru.dailycode.pack.compose_ext

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
public fun TestButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        content = {
            Text(
                text = text
            )
        }
    )
}