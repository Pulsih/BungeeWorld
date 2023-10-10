package me.pulsi_.bungeeworld.registry;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.MapUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class WorldReader {

    private final String worldName;
    private final BWWorld world;
    
    public WorldReader(String worldName) {
        this.worldName = worldName;
        this.world = BungeeWorld.INSTANCE.getWorldsRegistry().getWorlds().get(worldName);
    }

    public boolean canBroke(Player p) {
        if (world.getSecurity().disabledBlockBreak && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public boolean canPlace(Player p) {
        if (world.getSecurity().disabledBlockPlace && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public boolean canUse(Player p) {
        if (world.getSecurity().disabledPlayerActions && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public boolean canDrop(Player p) {
        if (world.getSecurity().disabledPlayerDrops && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public boolean canPickup() {
        return !world.getSecurity().disabledPlayerPickup;
    }

    public boolean canPvP(Entity e) {
        if (world.getSecurity().disabledPvP) {
            denyMessage(e);
            return false;
        }
        return true;
    }

    public boolean canSpawnMob() {
        return !world.getSecurity().disabledMobSpawning;
    }

    public boolean canExplode() {
        return !world.getSecurity().disabledExplosions;
    }

    public boolean canFallDamage() {
        return !world.getSecurity().disabledFallDamage;
    }

    public boolean isLinkedWorld(String worldName) {
        return this.world.getLinkedWorlds().contains(worldName);
    }

    public void denyMessage(Entity p) {
        if (world.getSecurity().denyMessage.isEmpty()) return;

        HashMap<UUID, Long> cooldown = MapUtils.denyMessageCooldown;
        if (cooldown.containsKey(p.getUniqueId()) && System.currentTimeMillis() < cooldown.get(p.getUniqueId())) return;

        p.sendMessage(BWChat.color(world.getSecurity().denyMessage));
        cooldown.put(p.getUniqueId(), System.currentTimeMillis() + 3000L);
    }

    public String getWorldName() {
        return worldName;
    }
    
    public BWWorld getWorld() {
        return world;
    }
}