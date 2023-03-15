package com.dida.common.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

// https://issuetracker.google.com/issues/270049505#comment2
// Lifecycle.launchWhenX methods and Lifecycle.whenX methods have been deprecated
// as the use of a pausing dispatcher can lead to wasted resources in some cases
fun LifecycleOwner.repeatOnStarted(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return lifecycleScope.launch {
        var isComplete = false
        lifecycle.repeatOnLifecycle(state) {
            if (!isComplete) {
                try {
                    block()
                } finally {
                    isComplete = true
                }
            }
        }
    }
}

fun LifecycleOwner.repeatOnResumed(
    state: Lifecycle.State = Lifecycle.State.RESUMED,
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return lifecycleScope.launch {
        var isComplete = false
        lifecycle.repeatOnLifecycle(state) {
            if (!isComplete) {
                try {
                    block()
                } finally {
                    isComplete = true
                }
            }
        }
    }
}

fun LifecycleOwner.repeatOnCreated(
    state: Lifecycle.State = Lifecycle.State.CREATED,
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return lifecycleScope.launch {
        var isComplete = false
        lifecycle.repeatOnLifecycle(state) {
            if (!isComplete) {
                try {
                    block()
                } finally {
                    isComplete = true
                }
            }
        }
    }
}


