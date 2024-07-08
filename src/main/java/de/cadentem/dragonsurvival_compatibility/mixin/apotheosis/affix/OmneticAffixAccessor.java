package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.affix;

import dev.shadowsoffire.apotheosis.adventure.affix.effect.OmneticAffix;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(value = OmneticAffix.class, remap = false)
public interface OmneticAffixAccessor {
    @Accessor("values")
    Map<LootRarity, OmneticDataAccessor> dragonsurvival_compatibility$getValues();

    @Invoker("getBaseSpeed")
    static float dragonsurvival_compatibility$getBaseSpeed(Player player, ItemStack tool, BlockState state, BlockPos pos) {
        throw new AssertionError();
    }
}
