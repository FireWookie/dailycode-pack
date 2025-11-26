package ru.dailycode.pack.decompose_ext.event

interface ComponentEventHandler<E : Any> {
    fun onEvent(event: E)
}
