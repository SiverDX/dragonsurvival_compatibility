package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.affix;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import shadows.apotheosis.adventure.affix.effect.OmneticAffix;
import shadows.apotheosis.adventure.loot.LootRarity;

import java.util.Map;

@Mixin(value = OmneticAffix.class, remap = false)
public interface OmneticAffixAccessor {
    @Accessor("values")
    Map<LootRarity, OmneticDataAccessor> getValues();

    @Invoker("getBaseSpeed")
    static float getBaseSpeed(Player player, ItemStack tool, BlockState state, BlockPos pos) {
        throw new AssertionError();
    }
}
