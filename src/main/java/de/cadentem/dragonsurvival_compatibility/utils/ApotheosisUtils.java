package de.cadentem.dragonsurvival_compatibility.utils;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.util.ToolUtils;
import com.mojang.datafixers.util.Pair;
import de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.affix.OmneticAffixAccessor;
import de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.affix.OmneticDataAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.affix.effect.OmneticAffix;
import shadows.apotheosis.adventure.affix.effect.RadialAffix;

import java.util.Map;
import java.util.Set;

public class ApotheosisUtils {
    public static ItemStack getAffixedDragonHarvestTool(final ItemStack original, final Player player, final BlockState blockState, @NotNull final Affix affix) {
        if (player != null && blockState != null) {
            DragonStateHandler handler = DragonUtils.getHandler(player);

            if (handler.isDragon() && affix.getClass() == OmneticAffix.class || affix.getClass() == RadialAffix.class) {
                SimpleContainer clawsInventory = handler.getClawToolData().getClawsInventory();

                if (!handler.switchedTool && !ToolUtils.shouldUseDragonTools(original)) {
                    return original;
                }

                float highestLevel = -1;
                ItemStack bestItem = handler.switchedTool ? original : ItemStack.EMPTY;

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

    /** Returns the Omnetic (first) and Radial (second) affix levels */
    public static Pair<Float, Float> getRelevantAffixLevels(final ItemStack itemStack) {
        Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(itemStack);
        Set<Affix> keys = affixes.keySet();

        float omneticLevel = 0;
        float radialLevel = 0;

        for (Affix key : keys) {
            if (key.getClass() == OmneticAffix.class) {
                omneticLevel = affixes.get(key).level();
            } else if (key.getClass() == RadialAffix.class) {
                radialLevel = affixes.get(key).level();
            }
        }

        return Pair.of(omneticLevel, radialLevel);
    }

    public static float getOmneticSpeed(final Player player, final ItemStack itemStack, final BlockState blockState) {
        Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(itemStack);
        float baseSpeed = Utils.getHarvestSpeed(itemStack, blockState);

        for (Affix key : affixes.keySet()) {
            if (key instanceof OmneticAffix omneticAffix) {
                return getOmneticSpeed(player, itemStack, blockState, omneticAffix, baseSpeed);
            }
        }

        return baseSpeed;
    }

    public static float getOmneticSpeed(final Player player, final ItemStack itemStack, final BlockState blockState, final OmneticAffix affix, float speed) {
        if (!itemStack.isEmpty()) {
            AffixInstance affixInstance = AffixHelper.getAffixes(itemStack).get(affix);

            if (affixInstance != null) {
                OmneticDataAccessor omneticData = ((OmneticAffixAccessor) affix).getValues().get(affixInstance.rarity());

                for (ItemStack omneticItem : omneticData.getItems()) {
                    speed = Math.max(OmneticAffixAccessor.getBaseSpeed(player, omneticItem, blockState, BlockPos.ZERO), speed);
                }
            }
        }

        return speed;
    }
}
