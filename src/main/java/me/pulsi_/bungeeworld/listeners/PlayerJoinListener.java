package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.registry.BWWorld;
import me.pulsi_.bungeeworld.registry.PlayerUtils;
import me.pulsi_.bungeeworld.registry.WorldsRegistry;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.utils.BWSounds;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final WorldsRegistry registry;

    public PlayerJoinListener(BungeeWorld plugin) {
        this.registry = plugin.getWorldsRegistry();
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);

        Player p = e.getPlayer();
        if (Values.CONFIG.isClearChat()) BWUtils.clearChat(p);

        World world = p.getWorld();
        String worldName = world.getName();
        boolean tpSpawn = true;

        String hubName = Values.GLOBAL.getHub();
        if (hubName != null) {
            World hub = Bukkit.getWorld(Values.GLOBAL.getHub());
            if (hub != null && Values.CONFIG.isTeleportHubWhenJoin() && !worldName.equals(hub.getName())) {
                Location hubSpawn = BWUtils.getLocation(hub.getName());
                if (hubSpawn != null) {
                    p.teleport(hubSpawn);

                    world = p.getWorld();
                    worldName = world.getName();

                    tpSpawn = false;
                }
            }
        }

        PlayerUtils playerUtils = new PlayerUtils(p);
        registry.loadAllPlayerStatistics(p);
        playerUtils.applyStatistics(worldName, !registry.isPlayerRegistered(p));
        registry.registererPlayer(p);

        BWWorld bwWorld = registry.getWorlds().get(worldName);
        Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> ActionProcessor.executeActions(p, bwWorld.getActionsOnJoin()), 5L);

        if (bwWorld.isTeleportToSpawnOnJoin() && tpSpawn) {
            Location spawn = bwWorld.getSpawn();
            if (spawn != null) p.teleport(spawn);
        }

        if (Values.CONFIG.isJoinSendTitle()) BWUtils.sendTitle(p, Values.CONFIG.getJoinTitle());
        if (Values.CONFIG.isJoinPlaySound()) BWSounds.playSound(p, Values.CONFIG.getJoinSound());
        playerUtils.joinMessage(world);
    }
}