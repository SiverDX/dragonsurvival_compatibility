package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.client.handlers.magic.ClientMagicHUDHandler;
import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientMagicHUDHandler.class)
public abstract class MixinClientMagicHUDHandler {
    @Inject(method = "lambda$cancelExpBar$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I", shift = At.Shift.BEFORE, ordinal = 0))
    private static void dragonsurvival_compatibility$renderExperienceBar1(final Player player, final ForgeGui gui, final GuiGraphics guiGraphics, int x, final DragonStateHandler handler, final CallbackInfo callback) {
        if (ClientConfig.COLD_SWEAT.get() && ConfigSettings.CUSTOM_HOTBAR_LAYOUT.get()) {
            guiGraphics.pose().translate(0.0D, 4.0D, 0.0D);
        }
    }

    @Inject(method = "lambda$cancelExpBar$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I", shift = At.Shift.AFTER, ordinal = 4))
    private static void dragonsurvival_compatibility$renderExperienceBar2(final Player player, final ForgeGui gui, final GuiGraphics guiGraphics, int x, final DragonStateHandler handler, final CallbackInfo callback) {
        if (ClientConfig.COLD_SWEAT.get() && ConfigSettings.CUSTOM_HOTBAR_LAYOUT.get()) {
            guiGraphics.pose().translate(0.0D, -4.0D, 0.0D);
        }
    }
}
