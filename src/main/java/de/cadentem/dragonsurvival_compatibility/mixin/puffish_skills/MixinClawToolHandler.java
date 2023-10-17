package de.cadentem.dragonsurvival_compatibility.mixin.puffish_skills;

import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ClawToolHandler;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.puffish.skillsmod.access.EntityAttributeInstanceAccess;
import net.puffish.skillsmod.server.PlayerAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClawToolHandler.Event_busHandler.class)
public class MixinClawToolHandler {
    /** Re-enable the attribute modifiers when you're a dragon */
    @ModifyVariable(method = "modifyBreakSpeed", at = @At(value = "STORE", target = "Lnet/minecraftforge/event/entity/player/PlayerEvent$BreakSpeed;setNewSpeed(F)V"), remap = false)
    public float applyAttribute(float value, /* Method parameters */ final PlayerEvent.BreakSpeed event) {
        if (ServerConfig.PUFFISH_SKILLS.get()) {
            EntityAttributeInstanceAccess attribute = (EntityAttributeInstanceAccess) event.getEntity().getAttribute(PlayerAttributes.MINING_SPEED);

            if (attribute != null) {
                return (float) attribute.computeValueForInitial(value);
            }
        }

        return value;
    }
}
