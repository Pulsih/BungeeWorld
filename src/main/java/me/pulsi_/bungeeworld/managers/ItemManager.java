package me.pulsi_.bungeeworld.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWChat;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemManager {

    public static List<String> itemsName = new ArrayList<>();

    public static HashMap<String, ItemStack> itemsList = new HashMap<>();

    public static void loadItems() {
        itemsName.clear();
        itemsList.clear();

        FileConfiguration items = BungeeWorld.getInstance().getConfigs().getConfig(ConfigManager.Type.ITEMS);
        ConfigurationSection section = items.getConfigurationSection("");
        if (section == null) return;

        for (String path : section.getKeys(false)) {
            ItemStack item = getItem(path, items);
            itemsList.put(path, item);
            itemsName.add(path);
        }
    }

    private static ItemStack getItem(String path, FileConfiguration items) {
        String material = items.getString(path + ".material");
        if (material == null) material = "STONE";
        ItemStack item = new ItemStack(Material.valueOf(material));
        ItemMeta meta = item.getItemMeta();

        String displayname = items.getString(path + ".displayname");
        if (displayname == null) displayname = "&cDisplayname not found.";
        meta.setDisplayName(BWChat.color(displayname));

        List<String> lore = new ArrayList<>();
        for (String line : items.getStringList(path + ".lore")) lore.add(BWChat.color(line));
        meta.setLore(lore);

        if (items.getBoolean(path + ".glowing")) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static List<String> getItemActions(ItemStack item) {
        FileConfiguration items = BungeeWorld.getInstance().getConfigs().getConfig(ConfigManager.Type.ITEMS);
        ConfigurationSection section = items.getConfigurationSection("");
        if (section == null) return new ArrayList<>();

        String displayname = item.getItemMeta().getDisplayName();
        List<String> lore = item.getItemMeta().getLore();
        String material = item.getType().toString();

        for (String path : section.getKeys(false)) {
            String pathDisplayname = items.getString(path + ".displayname");
            if (pathDisplayname == null || !displayname.equals(BWChat.color(pathDisplayname))) continue;

            List<String> pathLore = new ArrayList<>();
            for (String line : items.getStringList(path + ".lore")) pathLore.add(BWChat.color(line));
            if (!lore.equals(pathLore)) continue;

            String pathMaterial = items.getString(path + ".material");
            if (!material.equals(pathMaterial)) continue;

            return items.getStringList(path + ".actions");
        }
        return new ArrayList<>();
    }
}