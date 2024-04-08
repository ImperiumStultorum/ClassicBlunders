package com.stultorum.quiltmc.blunders.lang.dicts

import com.stultorum.quiltmc.blunders.lang.Bool
import com.stultorum.quiltmc.blunders.lang.exceptions.AlreadyRegisteredException
import com.stultorum.quiltmc.blunders.lang.exceptions.NotRegisteredException

open class AddOnlyTypeMap<TVal>(map: MutableMap<Class<*>, TVal> = mutableMapOf()): AddOnlyMap<Class<*>, TVal>(map) {
    override val exceptions: Pair<(Class<*>) -> Exception, (Class<*>) -> Exception>
        get() = Pair({NotRegisteredException(it.canonicalName)}, {AlreadyRegisteredException(it.canonicalName)})

    inline fun <reified T> tryGet(): TVal? = tryGet(T::class.java)
    inline fun <reified T> get(): TVal = get(T::class.java)

    inline fun <reified T> trySet(value: TVal): Bool = trySet(T::class.java, value)
    inline fun <reified T> set(value: TVal): Unit = set(T::class.java, value)

    inline fun <reified T> exists(): Bool = exists(T::class.java)
}