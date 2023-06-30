package me.pulsi_.bungeeworld.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWLogger;
import me.pulsi_.bungeeworld.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiManager implements InventoryHolder {

    private Inventory inventory;
    public static List<String> guisIdentifier = new ArrayList<>();
    public static Map<String, Inventory> inventoryGetter = new HashMap<>();
    public static Map<Player, String> openedInventory = new HashMap<>();

    public void openGui(Player p, String guiIdentifier) {
        if (!inventoryGetter.containsKey(guiIdentifier)) {
            BWMessages.send(p, "invalid_gui");
            return;
        }
        Inventory gui = inventoryGetter.get(guiIdentifier);

        p.openInventory(gui);
        openedInventory.put(p, guiIdentifier);
    }

    public void loadGuis() {
        ConfigManager manager = BungeeWorld.INSTANCE.getConfigs();
        FileConfiguration guis = manager.getConfig(ConfigManager.Type.GUIS);

        guisIdentifier.clear();

        ConfigurationSection section = guis.getConfigurationSection("");
        if (section == null) return;

        guisIdentifier.addAll(section.getKeys(false));
        for (String identifier : guisIdentifier) {

            String pathToGui = identifier + ".";
            int lines = getLines(guis.getInt(pathToGui + "lines"));
            String title = getTitle(guis.getString(pathToGui + "title"));

            String guiType = guis.getString(pathToGui + "gui-type");
            if (guiType != null) {
                try {
                    inventory = Bukkit.createInventory(this, InventoryType.valueOf(guiType), title);
                } catch (IllegalArgumentException e) {
                    BWLogger.error("Invalid gui-type for the gui \"" + identifier + "\"");
                    inventory = Bukkit.createInventory(this, lines, title);
                }
            } else inventory = Bukkit.createInventory(this, lines, title);

            ConfigurationSection items = guis.getConfigurationSection(pathToGui + "items");
            if (items == null) return;

            for (String item : items.getKeys(false)) {
                String pathToItem = pathToGui + "items." + item + ".";

                String material = guis.getString(pathToItem + "material");
                if (material == null) {
                    BWLogger.warn("The path \"" + pathToItem + "material\" is missing! Aborting the item creation..");
                    continue;
                }

                String displayName = guis.getString(pathToItem + "displayname");
                List<String> lore = guis.getStringList(pathToItem + "lore");
                boolean glowing = guis.getBoolean(pathToItem + "glowing");
                int amount = guis.getInt(pathToItem + "amount");

                List<Integer> slots = guis.getIntegerList(pathToItem + "slot");
                if (slots.isEmpty()) {
                    int slot = guis.getInt(pathToItem + "slot") - 1;
                    if (slot < 0 || slot > lines) {
                        BWLogger.warn("The path \"" + pathToItem + "slot\" is missing or has an invalid value! Please correct as soon as possible!");
                        inventory.addItem(ItemCreator.getItem(material, displayName, lore, glowing, amount));
                    } else
                        inventory.setItem(slot, ItemCreator.getItem(material, displayName, lore, glowing, amount));
                } else {
                    for (int i : slots) {
                        int slot = i - 1;
                        if (slot < 0 || slot > lines) {
                            BWLogger.warn("The path \"" + pathToItem + "slot\" is missing or has an invalid value! Please correct as soon as possible!");
                            inventory.addItem(ItemCreator.getItem(material, displayName, lore, glowing, amount));
                        } else
                            inventory.setItem(slot, ItemCreator.getItem(material, displayName, lore, glowing, amount));
                    }
                }
            }

            if (guis.getBoolean(pathToGui + "filler.enabled")) {
                String material = guis.getString(pathToGui + "filler.material");
                if (material != null) {
                    ItemStack filler = ItemCreator.getItem(material, "&f");
                    for (int i = 0; i < inventory.getSize(); i++) if (inventory.getItem(i) == null) inventory.setItem(i, filler);
                } else
                    BWLogger.warn("The path \"" + pathToGui + "filler.material\" is missing! Aborting the item creation..");
            }
            inventoryGetter.put(identifier, inventory);
        }
    }

    private int getLines(int number) {
        switch (number) {
            case 1:
                return 9;
            case 2:
                return 18;
            case 3:
                return 27;
            case 4:
                return 36;
            case 5:
                return 45;
            default:
                return 54;
        }
    }

    private String getTitle(String title) {
        String convertedTitle;
        if (title == null) convertedTitle = "&c&l*CANNOT FIND TITLE*";
        else convertedTitle = title;
        return BWChat.color(convertedTitle);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}