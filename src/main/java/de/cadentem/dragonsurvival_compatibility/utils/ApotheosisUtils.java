package de.cadentem.dragonsurvival_compatibility.utils;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.util.ToolUtils;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.affix.effect.OmneticAffix;

public class ApotheosisUtils {
    // FIXME :: Also use this for the x by x block break
    // FIXME :: Tool takes no durability damage for blocks it's not effective against
    public static ItemStack getAffixedDragonHarvestTool(final ItemStack original, final Player player, final BlockState blockState, final Affix affix) {
        if (player != null && blockState != null) {
            DragonStateHandler handler = DragonUtils.getHandler(player);

            if (handler.isDragon() && affix.getClass() == OmneticAffix.class) {
                SimpleContainer clawsInventory = handler.getClawToolData().getClawsInventory();

                if (!ToolUtils.shouldUseDragonTools(original)) {
                    return original;
                }

                float highestLevel = -1;
                ItemStack bestItem = ItemStack.EMPTY;

                for (int i = 0; i < 4; i++) {
                    ItemStack clawTool = clawsInventory.getItem(i);
                    AffixInstance affixInstance = AffixHelper.getAffixes(clawTool).get(affix);

                    if (affixInstance != null && affixInstance.level() > highestLevel) {
                        highestLevel = affixInstance.level();
                        bestItem = clawTool;
                    }
                }

                if (highestLevel != -1) {
                    return bestItem;
                }
            }

            Utils.getDragonHarvestTool(original, player, blockState, handler);
        }

        return original;
    }
}
