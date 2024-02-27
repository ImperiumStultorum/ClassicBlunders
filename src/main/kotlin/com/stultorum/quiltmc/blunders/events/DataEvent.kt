package com.stultorum.quiltmc.blunders.events

import com.stultorum.quiltmc.blunders.lang.Bool
import com.stultorum.quiltmc.blunders.lang.exceptions.AlreadyRegisteredException
import com.stultorum.quiltmc.blunders.lang.exceptions.NotRegisteredException
import kotlin.collections.HashSet

open class DataEvent<Arg> {
    protected val callbacks: MutableSet<(Arg) -> Unit> = HashSet()
    
    open fun addCallback(callback: (Arg) -> Unit): Bool = callbacks.add(callback)
    open fun removeCallback(callback: (Arg) -> Unit): Bool = callbacks.remove(callback)
    open fun call(obj: Arg) { for (callback in callbacks) callback(obj) }
    
    open operator fun plusAssign(callback: (Arg) -> Unit) {
        if (!addCallback(callback)) throw AlreadyRegisteredException()
    }
    open operator fun minusAssign(callback: (Arg) -> Unit) {
        if (!removeCallback(callback)) throw NotRegisteredException()
    }
    open operator fun invoke(obj: Arg) = call(obj)
}
