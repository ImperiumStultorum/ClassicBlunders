package com.stultorum.blunders.lang

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

public typealias Bool = Boolean
public typealias Disposable = AutoCloseable

internal fun World.throwIfClient() {
    if (this.isClient) throw IllegalStateException()
}

internal fun BlockPos.adjacent(): Array<BlockPos> = arrayOf(up(), down(), north(), east(), south(), west())