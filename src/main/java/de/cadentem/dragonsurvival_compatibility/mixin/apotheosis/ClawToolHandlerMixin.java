package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.capability.subcapabilities.ClawInventory;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ClawToolHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.util.ToolUtils;
import com.mojang.datafixers.util.Pair;
import de.cadentem.dragonsurvival_compatibility.apotheosis.ApotheosisUtils;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Search for the best (harvest related) affixed Apotheosis item */
@Mixin(value = ClawToolHandler.class, remap = false)
public abstract class ClawToolHandlerMixin {
    @Inject(method = "getDragonHarvestToolAndSlot", at = @At("HEAD"), cancellable = true)
    private static void getAffixedDragonHarvestToolAndSlot(final Player player, final BlockState blockState, final CallbackInfoReturnable<Pair<ItemStack, Integer>> callback) {
        if (!ServerConfig.APOTHEOSIS.get()) {
            return;
        }

        DragonStateHandler handler = DragonUtils.getHandler(player);

        if (handler.isDragon()) {
            if (!handler.switchedTool && !ToolUtils.shouldUseDragonTools(player.getMainHandItem())) {
                return;
            }

            SimpleContainer clawsInventory = handler.getClawToolData().getClawsInventory();
            ItemStack result = handler.switchedTool ? player.getMainHandItem() : ItemStack.EMPTY;
            int toolSlot = handler.switchedToolSlot;

            Pair<Float, Float> affixes = ApotheosisUtils.getRelevantAffixLevels(result);
            float compareRadialLevel = affixes.getSecond();
            float compareHarvestSpeed = ApotheosisUtils.getOmneticSpeed(player, result, blockState);

            for (int slot = 0; slot < ClawInventory.Slot.size(); slot++) {
                ItemStack clawTool = clawsInventory.getItem(slot);

                if (clawTool == ItemStack.EMPTY) {
                    continue;
                }

                affixes = ApotheosisUtils.getRelevantAffixLevels(clawTool);
                float clawToolHarvestSpeed = ApotheosisUtils.getOmneticSpeed(player, clawTool, blockState);

                boolean switchItems = false;

                if (clawToolHarvestSpeed > compareHarvestSpeed) {
                    switchItems = true;
                } else if (affixes.getSecond() > compareRadialLevel) {
                    switchItems = true;
                }

                if (switchItems) {
                    compareRadialLevel = affixes.getSecond();
                    compareHarvestSpeed = clawToolHarvestSpeed;

                    result = clawTool;
                    toolSlot = slot;
                }
            }

            if (toolSlot != -1) {
                callback.setReturnValue(Pair.of(result, toolSlot));
            }
        }
    }
}
