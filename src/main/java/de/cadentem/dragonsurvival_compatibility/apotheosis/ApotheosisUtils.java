package de.cadentem.dragonsurvival_compatibility.apotheosis;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.capability.subcapabilities.ClawInventory;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.util.ToolUtils;
import com.mojang.datafixers.util.Pair;
import de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.affix.OmneticAffixAccessor;
import de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.affix.OmneticDataAccessor;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import dev.shadowsoffire.apotheosis.adventure.affix.Affix;
import dev.shadowsoffire.apotheosis.adventure.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.adventure.affix.AffixInstance;
import dev.shadowsoffire.apotheosis.adventure.affix.AffixRegistry;
import dev.shadowsoffire.apotheosis.adventure.affix.effect.OmneticAffix;
import dev.shadowsoffire.apotheosis.adventure.affix.effect.RadialAffix;
import dev.shadowsoffire.placebo.reload.DynamicHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

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

                for (int i = 0; i < ClawInventory.Slot.size(); i++) {
                    ItemStack clawTool = clawsInventory.getItem(i);
                    AffixInstance affixInstance = AffixHelper.getAffixes(clawTool).get(AffixRegistry.INSTANCE.holder(affix));

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
        Map<DynamicHolder<? extends Affix>, AffixInstance> affixes = AffixHelper.getAffixes(itemStack);
        Set<DynamicHolder<? extends Affix>> keys = affixes.keySet();

        float omneticLevel = 0;
        float radialLevel = 0;

        for (DynamicHolder<? extends Affix> key : keys) {
            Affix affix = key.get();

            if (affix.getClass() == OmneticAffix.class) {
                omneticLevel = affixes.get(key).level();
            } else if (affix.getClass() == RadialAffix.class) {
                radialLevel = affixes.get(key).level();
            }
        }

        return Pair.of(omneticLevel, radialLevel);
    }

    public static float getOmneticSpeed(final Player player, final ItemStack itemStack, final BlockState blockState) {
        Map<DynamicHolder<? extends Affix>, AffixInstance> affixes = AffixHelper.getAffixes(itemStack);
        float baseSpeed = Utils.getHarvestSpeed(itemStack, blockState);

        for (DynamicHolder<? extends Affix> key : affixes.keySet()) {
            if (key.get() instanceof OmneticAffix omneticAffix) {
                return getOmneticSpeed(player, itemStack, blockState, omneticAffix, baseSpeed);
            }
        }

        return baseSpeed;
    }

    public static float getOmneticSpeed(final Player player, final ItemStack itemStack, final BlockState blockState, final OmneticAffix affix, float speed) {
        if (!itemStack.isEmpty()) {
            AffixInstance affixInstance = AffixHelper.getAffixes(itemStack).get(AffixRegistry.INSTANCE.holder(affix));

            if (affixInstance != null) {
                OmneticDataAccessor omneticData = ((OmneticAffixAccessor) affix).dragonsurvival_compatibility$getValues().get(affixInstance.rarity().get());

                for (ItemStack omneticItem : omneticData.dragonsurvival_compatibility$getItems()) {
                    speed = Math.max(OmneticAffixAccessor.dragonsurvival_compatibility$getBaseSpeed(player, omneticItem, blockState, BlockPos.ZERO), speed);
                }
            }
        }

        return speed;
    }
}
