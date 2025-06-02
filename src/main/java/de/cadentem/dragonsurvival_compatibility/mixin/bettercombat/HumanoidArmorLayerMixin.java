package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.llamalad7.mixinextras.sugar.Local;
import de.cadentem.dragonsurvival_compatibility.compat.bettercombat.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class) // Hide the armor when attacking (player model is hidden by setting the part visibility)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {
    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void dragonsurvival_compatibility$hideArmor(final CallbackInfo callback, @Local(argsOnly = true) final T entity) {
        if (entity instanceof Player player && AnimationUtils.isAttacking(player) && DragonUtils.isDragon(player)) {
            callback.cancel();
        }
    }
}
