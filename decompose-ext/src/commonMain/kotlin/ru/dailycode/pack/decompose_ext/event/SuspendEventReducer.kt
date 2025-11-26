package ru.dailycode.pack.decompose_ext.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface SuspendEventReducer<E : Any> : ComponentEventHandler<E> {

    val scope: CoroutineScope

    suspend fun reduce(event: E) {

    }

    override fun onEvent(event: E) {
        println("Event received: $event")
        scope.launch {
            reduce(event)
        }
    }
}
