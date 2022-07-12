package me.pulsi_.bungeeworld.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {

    public static ItemStack getItem(String material, String displayname, List<String> lore, boolean glowing, int amount) {
        ItemStack item;
        if (!material.contains(":")) {
            try {
                item = new ItemStack(Material.valueOf(material));
            } catch (IllegalArgumentException e) {
                BWLogger.warn("Invalid material input for \"" + material + "\". Aborting the item creation...");
                return null;
            }
        } else {
            String[] mat = material.split(":");
            short damage;
            try {
                damage = Short.parseShort(mat[1]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                BWLogger.warn("Invalid data number for the material \"" + material + "\". Aborting the item creation...");
                return null;
            }
            item = new ItemStack(Material.valueOf(mat[0]), 1, damage);
        }
        if (amount > 1) item.setAmount(amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getDisplayname(displayname));
        List<String> newLore = new ArrayList<>();
        for (String line : lore) newLore.add(BWChat.color(line));
        meta.setLore(newLore);
        if (glowing) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getItem(String material, String displayname) {
        ItemStack item;
        if (!material.contains(":")) {
            try {
                item = new ItemStack(Material.valueOf(material));
            } catch (IllegalArgumentException e) {
                BWLogger.warn("Invalid material input for \"" + material + "\". Aborting the item creation...");
                return null;
            }
        } else {
            String[] mat = material.split(":");
            short damage;
            try {
                damage = Short.parseShort(mat[1]);
            } catch (NumberFormatException e) {
                BWLogger.warn("Invalid data number for the material \"" + material + "\". Aborting the item creation...");
                return null;
            }
            item = new ItemStack(Material.valueOf(mat[0]), 1, damage);
        }

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getDisplayname(displayname));
        item.setItemMeta(meta);

        return item;
    }

    private static String getDisplayname(String displayname) {
        String convertedDisplayname;
        if (displayname == null) convertedDisplayname = "&c&l*CANNOT FIND DISPLAYNAME*";
        else convertedDisplayname = displayname;
        return BWChat.color(convertedDisplayname);
    }
}