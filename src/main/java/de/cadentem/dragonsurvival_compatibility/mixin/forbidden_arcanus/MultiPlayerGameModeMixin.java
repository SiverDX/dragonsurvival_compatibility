package de.cadentem.dragonsurvival_compatibility.mixin.forbidden_arcanus;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MultiPlayerGameMode.class, priority = 1500)
public abstract class MultiPlayerGameModeMixin {
    @TargetHandler(mixin = "com.stal111.forbidden_arcanus.core.mixin.MultiPlayerGameModeMixin", name = "forbiddenArcanus_continueDestroyBlock$updateBlockBreaker")
    @Inject(method = "@MixinSquared:Handler", at = @At(value = "HEAD"))
    private void dragonsurvival_compatibility$storeBlockPosition(final BlockPos blockPosition, final Direction facing, final CallbackInfoReturnable<Boolean> originalCallback, final CallbackInfo callback, @Share("blockPosition") final LocalRef<BlockPos> storedBlockPosition) {
        if (!ServerConfig.FORBIDDEN_ARCANUS.get()) {
            return;
        }

        storedBlockPosition.set(blockPosition);
    }

    @TargetHandler(mixin = "com.stal111.forbidden_arcanus.core.mixin.MultiPlayerGameModeMixin", name = "forbiddenArcanus_continueDestroyBlock$updateBlockBreaker")
    @ModifyArg(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lcom/stal111/forbidden_arcanus/common/item/modifier/ModifierHelper;getModifier(Lnet/minecraft/world/item/ItemStack;)Lcom/stal111/forbidden_arcanus/common/item/modifier/ItemModifier;", remap = false))
    private ItemStack dragonsurvival_compatibility$switchTool(final ItemStack itemStack, @Share("blockPosition") final LocalRef<BlockPos> storedBlockPosition) {
        if (!ServerConfig.FORBIDDEN_ARCANUS.get()) {
            return itemStack;
        }

        LocalPlayer localPlayer = Minecraft.getInstance().player;

        if (localPlayer != null) {
            return Utils.getDragonHarvestTool(itemStack, localPlayer, localPlayer.level.getBlockState(storedBlockPosition.get()));
        }

        return itemStack;
    }
}
