package me.pulsi_.bungeeworld.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

public class BWJson {

    private final TextComponent text;

    public BWJson(String text) {
        this.text = new TextComponent(BWMessages.addPrefix(text));
    }

    public BWJson setClickAction(ClickEvent.Action action, String value) {
        text.setClickEvent(new ClickEvent(action, value));
        return this;
    }

    public BWJson setHoverAction(HoverEvent.Action action, String value) {
        text.setHoverEvent(new HoverEvent(action, new ComponentBuilder(value).color(ChatColor.GRAY).create()));
        return this;
    }

    public BWJson addText(TextComponent text) {
        this.text.addExtra(text);
        return this;
    }

    public BWJson send(Player p) {
        p.spigot().sendMessage(text);
        return this;
    }

    public TextComponent getText() {
        return text;
    }
}