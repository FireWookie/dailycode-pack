package ru.dailycode.pack.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val app = App()
        super.onCreate(savedInstanceState)

        setContent {
        }
    }
}