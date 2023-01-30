package com.example.ranfomfactnumbers.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// TODO change to StateFlow/SharedFlow
interface Communication {

    interface Observe<T> {

        fun observe(owner: LifecycleOwner, action: (T) -> Unit)

    }

    interface Mutate<T> : Mapper.Unit<T>

    interface Mutable<T> : Observe<T>, Mutate<T>

    abstract class Abstract<T>(
        protected val state: MutableStateFlow<T>
    ) : Mutable<T> {

        override fun observe(owner: LifecycleOwner, action: (T) -> Unit) {
            owner.lifecycleScope.launchWhenResumed {
                state.onEach(action::invoke).launchIn(owner.lifecycleScope)
            }
        }

    }

    abstract class Post<T>(
        initialValue: T
    ) : Abstract<T>(MutableStateFlow(initialValue)) {

        override fun map(source: T) {
            state.value = source
        }

    }

}