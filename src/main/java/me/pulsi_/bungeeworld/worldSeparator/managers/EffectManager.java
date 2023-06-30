package me.pulsi_.bungeeworld.worldSeparator.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.players.BWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.List;

public class EffectManager {

    private final BWPlayer player;
    private final Player p;

    public EffectManager(Player p) {
        this.player = BungeeWorld.INSTANCE.getPlayerRegistry().getPlayers().get(p.getUniqueId());
        this.p = p;
    }

    public boolean setEffects(boolean clear) {
        return setEffects(p.getWorld().getName(), clear);
    }

    public boolean setEffects(String worldName, boolean clear) {
        boolean needToSave = false;

        if (!player.effects.containsKey(worldName)) {
            Collection<PotionEffect> effects;
            FileConfiguration playerData = player.config;
            List<?> fileEffects = playerData.getList(worldName + ".effects");

            if (fileEffects == null) {
                if (clear) p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));

                effects = p.getActivePotionEffects();
                needToSave = true;
            } else effects = (Collection<PotionEffect>) fileEffects;

            player.effects.put(worldName, effects);
        }
        Collection<PotionEffect> effects = player.effects.get(worldName);
        if (effects.isEmpty()) p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));
        else effects.forEach(p::addPotionEffect);

        return needToSave;
    }

    public void loadAllEffectsToHashMap() {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            List<?> effects = player.config.getList(worldName + ".effects");
            if (effects != null) player.effects.put(worldName, (Collection<PotionEffect>) effects);
        }
    }

    public void loadEffectsToHashMap() {
        loadEffectsToHashMap(p.getWorld().getName());
    }

    public void loadEffectsToHashMap(World world) {
        loadEffectsToHashMap(world.getName());
    }

    public void loadEffectsToHashMap(String worldName) {
        player.effects.put(worldName, p.getActivePotionEffects());
    }

    public void saveAllEffectsToFile() {
        saveAllEffectsToFile(true);
    }

    public void saveAllEffectsToFile(boolean save) {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            if (!player.effects.containsKey(worldName)) continue;
            player.config.set(worldName + ".effects", player.effects.get(worldName));
        }

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }

    public void saveEffectsToFile(boolean save) {
        saveEffectsToFile(p.getWorld(), save);
    }

    public void saveEffectsToFile(World world, boolean save) {
        saveEffectsToFile(world.getName(), save);
    }

    public void saveEffectsToFile(String worldName, boolean save) {
        FileConfiguration playerData = player.config;

        if (player.effects.containsKey(worldName)) playerData.set(worldName + ".effects", player.effects.get(worldName));
        else playerData.set(worldName + ".effects", p.getActivePotionEffects());


        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }
}