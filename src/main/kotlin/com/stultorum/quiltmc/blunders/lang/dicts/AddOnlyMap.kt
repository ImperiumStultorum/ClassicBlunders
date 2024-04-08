package com.stultorum.quiltmc.blunders.lang.dicts

import com.stultorum.quiltmc.blunders.lang.Bool

open class AddOnlyMap<TKey, TVal>(protected val map: MutableMap<TKey, TVal> = mutableMapOf()): IAddOnlyMap<TKey, TVal> {
    public override fun tryGet(key: TKey): TVal? = map[key]

    public override fun trySet(key: TKey, value: TVal): Bool = map.putIfAbsent(key, value) == null

    public override fun exists(key: TKey): Bool = map.containsKey(key)
}