package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.sugar.Local;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/** Support for Apotheosis Telepathic Affix */
@Mixin(value = Block.class, priority = 1500)
public abstract class BlockMixin {
    @TargetHandler(mixin = "dev.shadowsoffire.apotheosis.mixin.BlockMixin", name = "apoth_telepathicHead")
    @ModifyArg(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Ldev/shadowsoffire/apotheosis/adventure/affix/AffixHelper;getAffixes(Lnet/minecraft/world/item/ItemStack;)Ljava/util/Map;", remap = false))
    private static ItemStack dragonsurvival_compatibility$changeTool(final ItemStack tool, @Local(argsOnly = true) final BlockState state, @Local(argsOnly = true) final Entity entity) {
        if (!ServerConfig.APOTHEOSIS.get() || !(entity instanceof Player player)) {
            return tool;
        }

        return Utils.getDragonHarvestTool(tool, player, state);
    }
}
