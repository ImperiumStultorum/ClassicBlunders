package com.stultorum.quiltmc.blunders.minecraft

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

fun World.throwIfClient() {
    if (this.isClient) throw IllegalStateException()
}

fun BlockPos.adjacent(): Array<BlockPos> = arrayOf(up(), down(), north(), east(), south(), west())