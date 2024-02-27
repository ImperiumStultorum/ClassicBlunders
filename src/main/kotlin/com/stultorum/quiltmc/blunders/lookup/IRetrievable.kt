package com.stultorum.quiltmc.blunders.lookup

interface IRetrievable<TClass> {
    fun <TFunc: TClass> retrieve(): TFunc
}