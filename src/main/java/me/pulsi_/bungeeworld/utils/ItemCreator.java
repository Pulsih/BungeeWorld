package me.pulsi_.bungeeworld.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {

    public static ItemStack createItem(Material material, String displayName, int amount, boolean glowing, short damage, String... lore) {
        ItemStack item = new ItemStack(material, amount, damage);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(BWChat.color(displayName));

        List<String> list = new ArrayList<>();
        for (String line : lore) list.add(BWChat.color(line));
        meta.setLore(list);

        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createItem(Material material, String displayName, int amount, boolean glowing, boolean unbreakable, short damage, String... lore) {
        ItemStack item = new ItemStack(material, amount, damage);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(BWChat.color(displayName));

        List<String> list = new ArrayList<>();
        for (String line : lore) list.add(BWChat.color(line));
        meta.setLore(list);

        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (unbreakable) {
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createItem(Material material, String displayName, int amount, boolean glowing, short damage) {
        ItemStack item = new ItemStack(material, amount, damage);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(BWChat.color(displayName));

        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createItem(Material material, String displayName, int amount, boolean glowing, boolean unbreakable, short damage) {
        ItemStack item = new ItemStack(material, amount, damage);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(BWChat.color(displayName));

        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (unbreakable) {
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }

        item.setItemMeta(meta);

        return item;
    }
}