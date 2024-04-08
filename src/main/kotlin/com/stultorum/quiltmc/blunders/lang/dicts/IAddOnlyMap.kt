package com.stultorum.quiltmc.blunders.lang.dicts

import com.stultorum.quiltmc.blunders.lang.Bool
import com.stultorum.quiltmc.blunders.lang.exceptions.AlreadyRegisteredException
import com.stultorum.quiltmc.blunders.lang.exceptions.NotRegisteredException

interface IAddOnlyMap<TKey, TVal> {
    /** First is for ensureExists, second is for ensureNotExists */
    val exceptions: Pair<(TKey) -> Exception, (TKey) -> Exception>
        get() = Pair({NotRegisteredException(it.toString())}, {AlreadyRegisteredException(it.toString()) })

    public fun tryGet(key: TKey): TVal?
    public fun trySet(key: TKey, value: TVal): Bool

    public fun exists(key: TKey): Bool

    public fun ensureExists(key: TKey) {
        if (!exists(key)) throw exceptions.first(key)
    }
    public fun ensureNotExists(key: TKey) {
        if (exists(key)) throw exceptions.second(key)
    }

    public operator fun set(key: TKey, value: TVal) {
        ensureNotExists(key)
        trySet(key, value)
    }
    public operator fun get(key: TKey): TVal {
        ensureExists(key)
        return tryGet(key)!!
    }
}