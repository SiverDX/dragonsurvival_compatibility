package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.enchant;

import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadows.apotheosis.ench.enchantments.twisted.MinersFervorEnchant;

@Mixin(value = MinersFervorEnchant.class, remap = false)
public class MixinMinersFervorEnchant {
    @Unique private Player breakSpeed_player;
    @Unique private BlockState breakSpeed_blockState;

    @Inject(method = "breakSpeed", at = @At("HEAD"))
    public void breakSpeed_storeData(final PlayerEvent.BreakSpeed event, final CallbackInfo callback) {
        breakSpeed_player = event.getEntity();
        breakSpeed_blockState = event.getState();
    }

    @ModifyVariable(method = "breakSpeed", at = @At("STORE"), ordinal = 0)
    public ItemStack breakSpeed_provideDragonTool(final ItemStack itemStack) {
        return Utils.getDragonHarvestTool(itemStack, breakSpeed_player, breakSpeed_blockState);
    }
}
