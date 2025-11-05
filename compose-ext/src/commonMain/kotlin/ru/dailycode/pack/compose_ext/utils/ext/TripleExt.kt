package ru.dailycode.pack.compose_ext.utils.ext

infix fun <A, B, C> Pair<A, B>.to(that: C): Triple<A, B, C> =
    Triple(this.first, this.second, that)
