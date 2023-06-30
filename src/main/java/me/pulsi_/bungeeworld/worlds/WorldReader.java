package me.pulsi_.bungeeworld.worlds;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.MapUtils;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class WorldReader {

    private final String worldName;
    private final BWWorld world;
    
    public WorldReader(String worldName) {
        this.worldName = worldName;
        this.world = BungeeWorld.INSTANCE.getWorldsRegistry().getWorlds().get(worldName);
    }

    public BWWorld getWorld() {
        return world;
    }

    public boolean teleportToLastLocation() {
        return world.teleportToLastLocation;
    }

    public boolean teleportToSpawnOnJoin() {
        return world.teleportToSpawnOnJoin;
    }

    public boolean canBroke(Player p) {
        if (world.security.disabledBlockBreak && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public boolean canPlace(Player p) {
        if (world.security.disabledBlockPlace && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public boolean canUse(Player p) {
        if (world.security.disabledPlayerActions && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public boolean canDrop(Player p) {
        if (world.security.disabledPlayerDrops && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public boolean canPickup() {
        return !world.security.disabledPlayerPickup;
    }

    public boolean canPvP(Entity e) {
        if (world.security.disabledPvP) {
            denyMessage(e);
            return false;
        }
        return true;
    }

    public boolean canSpawnMob() {
        return !world.security.disabledMobSpawning;
    }

    public boolean canExplode() {
        return !world.security.disabledExplosions;
    }

    public boolean canFallDamage() {
        return !world.security.disabledFallDamage;
    }

    public List<String> getActionsOnJoin() {
        return world.actionsOnJoin;
    }

    public List<String> getActionsOnQuit() {
        return world.actionsOnQuit;
    }

    public List<String> getActionsOnDeath() {
        return world.actionsOnDeath;
    }

    public List<String> getActionsOnRespawn() {
        return world.actionsOnRespawn;
    }

    public String getDeathMessage1() {
        return world.deathMessage == null ? "" : world.deathMessage;
    }

    public String getDeathMessage2() {
        return world.killerDeathMessage == null ? "" : world.killerDeathMessage;
    }

    public String getDeathMessage3() {
        return world.killerWeaponDeathMessage == null ? "" : world.killerWeaponDeathMessage;
    }

    public String getJoinMessage() {
        return world.joinMessage == null ? "" :  world.joinMessage;
    }

    public String getQuitMessage() {
        return world.quitMessage == null ? "" : world.quitMessage;
    }

    public String getSpawn() {
        return world.spawn == null ? "" : world.spawn;
    }

    public List<String> getLinkedWorlds() {
        return world.linkedWorlds;
    }

    public boolean isLinkedWorld(World world) {
        return getLinkedWorlds().contains(world.getName());
    }

    public boolean isLinkedWorld(String world) {
        return getLinkedWorlds().contains(world);
    }

    public void denyMessage(Entity p) {
        if (world.security.denyMessage == "") return;

        HashMap<UUID, Long> cooldown = MapUtils.denyMessageCooldown;
        if (cooldown.containsKey(p.getUniqueId()) && System.currentTimeMillis() < cooldown.get(p.getUniqueId())) return;

        p.sendMessage(BWChat.color(world.security.denyMessage));
        cooldown.put(p.getUniqueId(), Long.valueOf(System.currentTimeMillis() + 3000L));
    }
}