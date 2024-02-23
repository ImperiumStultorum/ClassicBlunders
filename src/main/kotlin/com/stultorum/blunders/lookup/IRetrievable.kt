package com.stultorum.blunders.lookup

interface IRetrievable<TClass> {
    fun <TFunc: TClass> retrieve(): TFunc
}