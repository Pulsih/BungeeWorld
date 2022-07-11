package me.pulsi_.bungeeworld.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.ConfigManager;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (!Values.CONFIG.isIsolateChat()) return;
        e.setCancelled(true);

        String message = e.getMessage();
        Player p = e.getPlayer();
        World world = p.getWorld();

        String chatFormat = BungeeWorld.getInstance().getConfigs().getConfig(ConfigManager.Type.WORLDS).getString(world.getName() + ".chat");
        if (chatFormat == null) chatFormat = "&f<%player%> %message%";

        String format = BWChat.color(chatFormat.replace("%player%", p.getName()).replace("%message%", message));
        if (BungeeWorld.isPlaceholderApiHooked()) format = PlaceholderAPI.setPlaceholders(p, format);


        for (Player players : world.getPlayers()) players.sendMessage(format);
    }
}