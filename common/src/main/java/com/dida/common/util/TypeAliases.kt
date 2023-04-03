package com.dida.common.util

typealias Invoker = (() -> Unit)

typealias InvokerT<T> = ((T) -> Unit)

typealias InvokerRetBool = (() -> Boolean)

val NOTHING : Invoker = {}
