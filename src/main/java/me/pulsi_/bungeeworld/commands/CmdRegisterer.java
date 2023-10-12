package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.commands.list.*;

public class CmdRegisterer {

    public void registerCmds() {
        MainCmd.commands.clear();

        new ReloadCmd("reload").register();
        new SendCmd("send").register();
        new SetCmd("set").register();
        new SetHubCmd("setHub").register();
        new SetSpawnCmd("setSpawn").register();
        new TpCmd("tp", "go").register();
        new WorldsCmd("worlds").register();
    }
}