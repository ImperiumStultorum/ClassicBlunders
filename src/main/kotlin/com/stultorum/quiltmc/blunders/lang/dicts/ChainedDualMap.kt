package com.stultorum.quiltmc.blunders.lang.dicts

import com.stultorum.quiltmc.blunders.lang.Bool

open class ChainedDualMap<TKey, TVal>: IDualMap<TKey, TKey, TVal> {
    private val base = mutableMapOf<TKey, TVal>()
    private val top = mutableMapOf<TKey, TKey>()

    override val k1s: Set<TKey> = base.keys
    override val k2s: Set<TKey> = top.keys
    override val vs: Collection<TVal> = base.values

    override fun getK2(k2: TKey): TVal? {
        return base[top[k2] ?: throw IllegalStateException("What a Terrible Failure")]
    }

    override fun getK1(k1: TKey): TVal? = base[k1]

    open fun containsKey(k: TKey): Bool = base.containsKey(k) || top.containsKey(k)

    override fun remove(k1: TKey, k2: TKey): TVal? = remove(k2)
    open fun remove (k2: TKey): TVal? {
        return base.remove(top.remove(k2))
    }

    override fun put(k1: TKey, k2: TKey, v: TVal) {
        base[k1] = v
        top[k2] = k1
    }

    public override operator fun get(k: TKey): TVal? {
        return base[k] ?: base[top[k] ?: return null]
    }
}