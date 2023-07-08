package de.cadentem.dragonsurvival_compatibility.upgradednetherite;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.mojang.datafixers.util.Pair;
import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.utils.check.EchoUtil;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradedNetheriteEventHandler {
    private final Map<String, List<Pair<Integer, ItemStack>>> storedDragonClawInventory = new HashMap<>();

    /** Need to run before the original check (since it will drop the Echo Armor, meaning we cannot check if we have the set equipped) */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingDeathEvent(final LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player && (UpgradedNetheriteConfig.EnableSoulbound || UpgradedNetheriteConfig.EnableKeepItemsChance)) {
            String uuid = player.getStringUUID();
            storedDragonClawInventory.remove(uuid);

            DragonStateHandler handler = DragonUtils.getHandler(player);
            SimpleContainer clawsInventory = handler.getClawToolData().getClawsInventory();

            boolean isWearingEchoArmor = EchoUtil.isWearingEchoArmor(player);

            for (int i = 0; i < clawsInventory.getContainerSize(); i++) {
                ItemStack itemStack = clawsInventory.getItem(i);
                int random = player.getRandom().nextInt(100) + 1;

                boolean canStore = false;

                if (!EnchantmentHelper.hasVanishingCurse(itemStack)) {
                    if (UpgradedNetheriteConfig.EnableSoulbound) {
                        canStore = true;
                    } else if (UpgradedNetheriteConfig.EnableKeepItemsChance && isWearingEchoArmor && random <= UpgradedNetheriteConfig.KeepItemsChance) {
                        canStore = true;
                    }
                }

                if (canStore) {
                    Pair<Integer, ItemStack> toStore = Pair.of(i, itemStack.copy());
                    storedDragonClawInventory.computeIfAbsent(uuid, key -> new ArrayList<>()).add(toStore);
                    itemStack.shrink(itemStack.getCount());
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerRespawnEvent(final PlayerRespawnEvent event) {
        if (UpgradedNetheriteConfig.EnableSoulbound || UpgradedNetheriteConfig.EnableKeepItemsChance) {
            Player player = event.getEntity();
            String uuid = player.getStringUUID();

            if (storedDragonClawInventory.containsKey(uuid)) {
                DragonStateHandler handler = DragonUtils.getHandler(player);
                SimpleContainer clawsInventory = handler.getClawToolData().getClawsInventory();

                List<Pair<Integer, ItemStack>> storedItems = storedDragonClawInventory.get(uuid);

                for (Pair<Integer, ItemStack> storedItem : storedItems) {
                    clawsInventory.setItem(storedItem.getFirst(), storedItem.getSecond());
                }
            }

            storedDragonClawInventory.remove(uuid);
        }
    }
}
