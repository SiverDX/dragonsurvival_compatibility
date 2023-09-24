package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.enchant;

import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadows.apotheosis.ench.enchantments.masterwork.EarthsBoonEnchant;

@Mixin(value = EarthsBoonEnchant.class, remap = false)
public abstract class MixinEarthsBoonEnchant {
    @Unique private Player provideBenefits_player;
    @Unique private BlockState provideBenefits_blockState;

    @Inject(method = "provideBenefits", at = @At("HEAD"))
    public void provideBenefits_storeData(final BlockEvent.BreakEvent event, final CallbackInfo callback) {
        provideBenefits_player = event.getPlayer();
        provideBenefits_blockState = event.getState();
    }

    @ModifyVariable(method = "provideBenefits", at = @At("STORE"), ordinal = 0)
    public ItemStack provideBenefits_provideDragonTool(final ItemStack itemStack) {
        return Utils.getDragonHarvestTool(itemStack, provideBenefits_player, provideBenefits_blockState);
    }
}
