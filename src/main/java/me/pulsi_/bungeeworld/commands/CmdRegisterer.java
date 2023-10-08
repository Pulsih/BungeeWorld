package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.commands.list.ReloadCmd;

public class CmdRegisterer {

    public void registerCmds() {
        new ReloadCmd("reload").register();
    }

    public void resetCmds() {
        MainCmd.commands.clear();
    }
}