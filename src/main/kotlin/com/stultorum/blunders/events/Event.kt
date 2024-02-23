package com.stultorum.blunders.events

import com.stultorum.blunders.lang.Bool
import com.stultorum.blunders.lang.exceptions.AlreadyRegisteredException
import com.stultorum.blunders.lang.exceptions.NotRegisteredException
import kotlin.collections.HashSet

open class Event {
    protected val callbacks: MutableSet<() -> Unit> = HashSet()
    
    open fun addCallback(callback: () -> Unit): Bool = callbacks.add(callback)
    open fun removeCallback(callback: () -> Unit): Bool = callbacks.remove(callback)
    open fun call() { for (callback in callbacks) callback() }
    
    open operator fun plusAssign(callback: () -> Unit) {
        if (!addCallback(callback)) throw AlreadyRegisteredException()
    }
    open operator fun minusAssign(callback: () -> Unit) {
        if (!removeCallback(callback)) throw NotRegisteredException()
    }
    open operator fun invoke() = call()
}
