package com.stultorum.quiltmc.blunders.events

import com.stultorum.quiltmc.blunders.lang.Bool
import com.stultorum.quiltmc.blunders.lang.exceptions.AlreadyRegisteredException
import com.stultorum.quiltmc.blunders.lang.exceptions.NotRegisteredException

public open class PreconditionalEvent<PreconditionArg> {
    protected val callbacks: HashMap<(PreconditionArg) -> Unit, (PreconditionArg) -> Bool> = HashMap()

    open fun addCallback(precondition: PreconditionArg, callback: () -> Unit) = addCallback(precondition, { _ -> callback() })
    open fun addCallback(precondition: (PreconditionArg) -> Bool, callback: () -> Unit) = addCallback(precondition, { _ -> callback() })
    open fun addCallback(precondition: PreconditionArg, callback: (PreconditionArg) -> Unit) = addCallback({it == precondition}, callback)
    open fun addCallback(precondition: (PreconditionArg) -> Bool, callback: (PreconditionArg) -> Unit): Bool = callbacks.putIfAbsent(callback, precondition) == null
    open fun removeCallback(callback: () -> Unit): Bool = callbacks.remove({ _ -> callback() }) != null
    open fun removeCallback(callback: (PreconditionArg) -> Unit): Bool = callbacks.remove(callback) != null
    open fun call(arg: PreconditionArg) = callbacks.filter { it.value(arg) }.forEach { it.key(arg) }

    @JvmName("plusAssignSimple")
    @Suppress("INAPPLICABLE_JVM_NAME")
    open operator fun plusAssign(obj: Pair<PreconditionArg, () -> Unit>) {
        if (!addCallback(obj.first, obj.second)) throw AlreadyRegisteredException()
    }
    @JvmName("plusAssignComplexPrecondition")
    @Suppress("INAPPLICABLE_JVM_NAME")
    open operator fun plusAssign(obj: Pair<(PreconditionArg) -> Bool, () -> Unit>) {
        if (!addCallback(obj.first, obj.second)) throw AlreadyRegisteredException()
    }
    @JvmName("plusAssignComplexCallback")
    @Suppress("INAPPLICABLE_JVM_NAME")
    open operator fun plusAssign(obj: Pair<PreconditionArg, (PreconditionArg) -> Unit>) {
        if (!addCallback(obj.first, obj.second)) throw AlreadyRegisteredException()
    }
    @JvmName("plusAssignComplex")
    @Suppress("INAPPLICABLE_JVM_NAME")
    open operator fun plusAssign(obj: Pair<(PreconditionArg) -> Bool, (PreconditionArg) -> Unit>) {
        if (!addCallback(obj.first, obj.second)) throw AlreadyRegisteredException()
    }
    open operator fun minusAssign(callback: () -> Unit) {
        if (!removeCallback(callback)) throw NotRegisteredException()
    }
    open operator fun minusAssign(callback: (PreconditionArg) -> Unit) {
        if (!removeCallback(callback)) throw NotRegisteredException()
    }
    open operator fun invoke(arg: PreconditionArg) = call(arg)
}