package me.pulsi_.bungeeworld.registry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class BWPlayer {

    private ItemStack[] inventory, enderChest;
    private Collection<PotionEffect> effects;
    private GameMode gameMode;
    private int health, maxHealth, hunger;
    private Location lastLocation;

    public ItemStack[] getInventory() {
        return inventory;
    }

    public ItemStack[] getEnderChest() {
        return enderChest;
    }

    public Collection<PotionEffect> getEffects() {
        return effects;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public int getHealth() {
        return health == 0 ? 20 : health;
    }

    public int getMaxHealth() {
        return maxHealth == 0 ? 20 : maxHealth;
    }

    public int getHunger() {
        return hunger;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    public void setEnderChest(ItemStack[] enderChest) {
        this.enderChest = enderChest;
    }

    public void setEffects(Collection<PotionEffect> effects) {
        this.effects = effects;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }
}