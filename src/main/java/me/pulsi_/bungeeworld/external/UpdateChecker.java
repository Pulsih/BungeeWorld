package me.pulsi_.bungeeworld.external;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWJson;
import me.pulsi_.bungeeworld.values.Values;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker implements Listener {

    private final boolean isUpToDate;
    private final BungeeWorld plugin;

    public UpdateChecker(BungeeWorld plugin) {
        boolean isUpdated;
        this.plugin = plugin;
        try {
            isUpdated = isPluginUpdated();
        } catch (IOException e) {
            isUpdated = true;
        }
        this.isUpToDate = isUpdated;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!Values.CONFIG.isUpdateChecker() || (!p.isOp() && !p.hasPermission("bungeeworld.admin")) || isUpToDate) return;

        BWJson text = new BWJson("%prefix% &aNew update available! ");

        TextComponent button = new BWJson("&b&l[CLICK HERE]")
                .setClickAction(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/%E2%99%BB%EF%B8%8F-bungeeworld-multiple-servers-in-a-single-server.103314/")
                .setHoverAction(HoverEvent.Action.SHOW_TEXT, "Click here to download it!").getText();

        text.addText(button);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            p.sendMessage("");
            text.send(p);
            p.sendMessage("");
        }, 80);
    }

    private boolean isPluginUpdated() throws IOException {
        final String currentVersion = new BufferedReader(new InputStreamReader(new URL("https://api.spigotmc.org/legacy/update.php?resource=103314").openConnection().getInputStream())).readLine();
        return plugin.getDescription().getVersion().equals(currentVersion);
    }
}