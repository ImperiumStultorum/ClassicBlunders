package com.stultorum.quiltmc.blunders.events

import com.stultorum.quiltmc.blunders.lang.Bool
import com.stultorum.quiltmc.blunders.lang.exceptions.AlreadyRegisteredException
import com.stultorum.quiltmc.blunders.lang.exceptions.NotRegisteredException
import kotlin.collections.HashSet

open class PersistentDataEvent<Persist, Arg> {
    protected val callbacks: MutableSet<Pair<Persist, (Persist, Arg) -> Unit>> = HashSet()

    open fun addCallback(persist: Persist, callback: (Persist, Arg) -> Unit): Bool = callbacks.add(Pair(persist, callback))
    open fun removeCallback(persist: Persist, callback: (Persist, Arg) -> Unit): Bool = callbacks.remove(Pair(persist, callback))
    open fun call(arg: Arg) { for (callback in callbacks) callback.second(callback.first, arg) }

    open operator fun plusAssign(callback: Pair<Persist, (Persist, Arg) -> Unit>) {
        if (callbacks.add(callback)) throw AlreadyRegisteredException()
    }
    open operator fun minusAssign(callback: Pair<Persist, (Persist, Arg) -> Unit>) {
        if (callbacks.remove(callback)) throw NotRegisteredException()
    }
    open operator fun invoke(arg: Arg) = call(arg)
}
