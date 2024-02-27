package com.stultorum.quiltmc.blunders.events

import com.stultorum.quiltmc.blunders.lang.Bool
import com.stultorum.quiltmc.blunders.lang.exceptions.AlreadyRegisteredException
import com.stultorum.quiltmc.blunders.lang.exceptions.NotRegisteredException

open class PersistentEvent<Persist> {
    protected val callbacks: MutableSet<Pair<Persist, (Persist) -> Unit>> = HashSet()

    open fun addCallback(persist: Persist, callback: (Persist) -> Unit): Bool = callbacks.add(Pair(persist, callback))
    open fun removeCallback(persist: Persist, callback: (Persist) -> Unit): Bool = callbacks.remove(Pair(persist, callback))
    open fun call() { for (callback in callbacks) callback.second(callback.first) }

    open operator fun plusAssign(callback: Pair<Persist, (Persist) -> Unit>) {
        if (callbacks.add(callback)) throw AlreadyRegisteredException()
    }
    open operator fun minusAssign(callback: Pair<Persist, (Persist) -> Unit>) {
        if (callbacks.remove(callback)) throw NotRegisteredException()
    }
    open operator fun invoke() = call()
}
