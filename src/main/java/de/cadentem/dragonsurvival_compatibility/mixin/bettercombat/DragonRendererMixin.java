package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.client.render.entity.dragon.DragonRenderer;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(value = DragonRenderer.class, remap = false)
public abstract class DragonRendererMixin {
    @ModifyExpressionValue(method = "renderRecursively", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, ordinal = 2))
    private boolean dragonsurvival_compatibility$hideHeldItem(boolean renderHeldItem) {
        return renderHeldItem && Utils.HIDE_MODEL_LENGTH <= 0;
    }
}
