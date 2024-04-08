package com.stultorum.quiltmc.blunders.lang.dicts

open class DualTypeMap<T>: ChainedDualMap<Class<*>, T>() {
    inline fun <reified T> getK1() = getK1(T::class.java)
    inline fun <reified T> getK2() = getK2(T::class.java)
    inline fun <reified T> get() = get(T::class.java)

    inline fun <reified T> containsK1() = k1s.contains(T::class.java)
    inline fun <reified T> containsK2() = k2s.contains(T::class.java)
    inline fun <reified T> containsKey() = k1s.contains(T::class.java) || k2s.contains(T::class.java)
}