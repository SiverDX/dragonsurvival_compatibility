package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.client.render.entity.dragon.DragonRenderer;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import de.cadentem.dragonsurvival_compatibility.compat.bettercombat.AnimationUtils;
import net.minecraft.world.entity.player.Player;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = DragonRenderer.class, remap = false)
public abstract class DragonRendererMixin {
    @ModifyExpressionValue(method = "renderRecursively(Lcom/mojang/blaze3d/vertex/PoseStack;Lby/dragonsurvivalteam/dragonsurvival/common/entity/DragonEntity;Lsoftware/bernie/geckolib/cache/object/GeoBone;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZFIIFFFF)V", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, ordinal = 0))
    private boolean dragonsurvival_compatibility$hideHeldItem(boolean renderHeldItem, @Local final Player player) {
        return renderHeldItem && !AnimationUtils.shouldHideModel(player);
    }
}
