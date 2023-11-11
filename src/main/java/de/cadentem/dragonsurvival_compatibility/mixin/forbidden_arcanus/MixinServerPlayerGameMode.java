package de.cadentem.dragonsurvival_compatibility.mixin.forbidden_arcanus;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ServerPlayerGameMode.class, priority = 1500)
public abstract class MixinServerPlayerGameMode {
    @TargetHandler(mixin = "com.stal111.forbidden_arcanus.core.mixin.ServerPlayerGameModeMixin", name = "forbiddenArcanus_incrementDestroyProgress")
    @Inject(method = "@MixinSquared:Handler", at = @At(value = "HEAD"))
    private void storeBlockState(final BlockState blockState, final BlockPos blockPosition, int destroyProgressStart, final CallbackInfoReturnable<Float> originalCallback, final CallbackInfo callback, @Share("blockState") final LocalRef<BlockState> storedBlockState) {
        if (!ServerConfig.FORBIDDEN_ARCANUS.get()) {
            return;
        }

        storedBlockState.set(blockState);
    }

    @TargetHandler(mixin = "com.stal111.forbidden_arcanus.core.mixin.ServerPlayerGameModeMixin", name = "forbiddenArcanus_incrementDestroyProgress")
    @ModifyArg(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lcom/stal111/forbidden_arcanus/common/item/modifier/ModifierHelper;getModifier(Lnet/minecraft/world/item/ItemStack;)Lcom/stal111/forbidden_arcanus/common/item/modifier/ItemModifier;"))
    private ItemStack switchTool(final ItemStack itemStack, @Share("blockState") final LocalRef<BlockState> storedBlockState) {
        if (!ServerConfig.FORBIDDEN_ARCANUS.get()) {
            return itemStack;
        }

        return Utils.getDragonHarvestTool(itemStack, player, storedBlockState.get());
    }

    @Shadow @Final protected ServerPlayer player;
}
