package com.stultorum.quiltmc.blunders.minecraft

import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.recipe.Ingredient
import net.minecraft.sound.SoundEvent

class SimpleArmorData(private val armor: Array<IArmorItem?>, private val enchantability: Int, private val sound: SoundEvent, private val repairItem: Ingredient, private val name: String, private val toughness: Float, private val knockbackResistance: Float) : ArmorMaterial {
    override fun getDurability(slot: ArmorItem.ArmorSlot): Int = armor[slot.ordinal]?.getDurability() ?: 0
    override fun getProtection(slot: ArmorItem.ArmorSlot): Int = armor[slot.ordinal]?.getProtection() ?: 0
    override fun getEnchantability(): Int = enchantability
    override fun getEquipSound(): SoundEvent = sound
    override fun getRepairIngredient(): Ingredient = repairItem
    override fun getName(): String = name
    override fun getToughness(): Float = toughness
    override fun getKnockbackResistance(): Float = knockbackResistance

    init {
        if (armor.size != 4) throw IllegalArgumentException("Armor array must have 4 items")
    }
}
