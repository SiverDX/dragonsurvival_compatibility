package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ToolActions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    /** @reason Re-enable sweeping for dragons when Better Combat is installed since it currently does not work with the dragon claw slot */
    @ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 3)
    private boolean dragonsurvival_compatibility$reEnableSweeping(boolean value) {
        if (ClientConfig.BETTERCOMBAT.get()) {
            Object self = this;
            Player player = (Player) self;

            // Only need to overwrite the value if the items were switched out (= dragon claw sword was used)
            if (DragonUtils.getHandler(player).switchedWeapon) {
                return player.getMainHandItem().canPerformAction(ToolActions.SWORD_SWEEP);
            }
        }

        return value;
    }
}
