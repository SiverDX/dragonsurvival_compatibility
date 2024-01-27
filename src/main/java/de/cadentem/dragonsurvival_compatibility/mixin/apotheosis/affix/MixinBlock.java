package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.affix;

import com.bawnorton.mixinsquared.TargetHandler;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, priority = 1500)
public abstract class MixinBlock {
    /* Support for Apotheosis Telepathic Affix */
    @Unique private static BlockState dragonsurvival_compatibility$storedBlockState;
    @Unique private static Player dragonsurvival_compatibility$storedPlayer;

    @TargetHandler(mixin = "dev.shadowsoffire.apotheosis.mixin.BlockMixin", name = "apoth_telepathicHead")
    @Inject(method = "@MixinSquared:Handler", at = @At(value = "HEAD"))
    private static void dragonsurvival_compatibility$storeData(final BlockState state, final Level level, final BlockPos blockPosition, @Nullable final BlockEntity blockEntity, final Entity entity, final ItemStack tool, boolean dropExperience, final CallbackInfo original, final CallbackInfo callback) {
        if (!ServerConfig.APOTHEOSIS.get()) {
            return;
        }

        if (entity instanceof Player player) {
            dragonsurvival_compatibility$storedPlayer = player;
            dragonsurvival_compatibility$storedBlockState = state;
        }
    }

    @TargetHandler(mixin = "dev.shadowsoffire.apotheosis.mixin.BlockMixin", name = "apoth_telepathicHead")
    @ModifyArg(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Ldev/shadowsoffire/apotheosis/adventure/affix/AffixHelper;getAffixes(Lnet/minecraft/world/item/ItemStack;)Ljava/util/Map;"))
    private static ItemStack dragonsurvival_compatibility$changeTool(final ItemStack tool) {
        if (!ServerConfig.APOTHEOSIS.get()) {
            return tool;
        }

        ItemStack result = Utils.getDragonHarvestTool(tool, dragonsurvival_compatibility$storedPlayer, dragonsurvival_compatibility$storedBlockState);

        dragonsurvival_compatibility$storedPlayer = null;
        dragonsurvival_compatibility$storedBlockState = null;

        return result;
    }
}
