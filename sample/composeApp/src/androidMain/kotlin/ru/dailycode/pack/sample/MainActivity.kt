package ru.dailycode.pack.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import ru.dailycode.pack.sample.root.App
import ru.dailycode.pack.sample.root.component.SampleComponent
import ru.dailycode.pack.sample.root.component.buildSampleComponent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentContext = defaultComponentContext()
        setContent {
            val component: SampleComponent = buildSampleComponent(componentContext)
            App(component)
        }
    }
}
