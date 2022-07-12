package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.managers.ConfigManager;
import me.pulsi_.bungeeworld.managers.GuiManager;
import me.pulsi_.bungeeworld.utils.BWChat;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Entity entity = e.getWhoClicked();
        if (!(entity instanceof Player)) return;
        Player p = (Player) entity;

        if (!(e.getInventory().getHolder() instanceof GuiManager)) return;
        e.setCancelled(true);

        String identifier = GuiManager.openedInventory.get(p);
        if (identifier == null) return;

        ConfigManager manager = BungeeWorld.getInstance().getConfigs();
        FileConfiguration guis = manager.getConfig(ConfigManager.Type.GUIS);

        ConfigurationSection items = guis.getConfigurationSection(identifier + ".items");
        if (items == null) return;

        int slot = e.getSlot() + 1;
        for (String item : items.getKeys(false)) {
            String pathToItem = identifier + ".items." + item + ".";
            int itemSlot = guis.getInt(pathToItem + "slot");
            if (slot != itemSlot) continue;

            ConfigurationSection requirements = guis.getConfigurationSection(pathToItem + "requirements");
            if (requirements != null) {
                for (String requirement : requirements.getKeys(false)) {

                    String pathToRequirement = pathToItem + "requirements." + requirement + ".";
                    String requirementType = guis.getString(pathToRequirement + "type");
                    if (requirementType == null) continue;

                    int amount = guis.getInt(pathToRequirement + "amount");
                    String denyMessage = guis.getString(pathToRequirement + "deny-message");

                    switch (requirementType.toLowerCase()) {
                        case "items":
                            String requirementItemType = guis.getString(pathToRequirement + "item-type");
                            int itemsAmount = 0;
                            for (ItemStack inventoryItems : p.getInventory().getContents()) {
                                if (inventoryItems != null && inventoryItems.getType().toString().equals(requirementItemType))
                                    itemsAmount += inventoryItems.getAmount();
                            }
                            if (itemsAmount < amount) {
                                if (denyMessage != null) p.sendMessage(BWChat.color(denyMessage));
                                return;
                            }
                    }
                }
            }

            List<String> actions = guis.getStringList(pathToItem + "actions");
            if (actions.isEmpty()) continue;
            ActionProcessor.executeActions(p, actions);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Entity entity = e.getPlayer();
        if (!(entity instanceof Player)) return;
        Player p = (Player) entity;

        GuiManager.openedInventory.remove(p);
    }
}