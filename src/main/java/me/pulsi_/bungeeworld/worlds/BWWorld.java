package me.pulsi_.bungeeworld.worlds;

import java.util.List;

public class BWWorld {

    public final String name;

    public BWWorld(String name) {
        this.name = name;
    }

    public String spawn;
    public boolean teleportToLastLocation, teleportToSpawnOnJoin;
    public BWSecurity security;
    public String denyCommandsMessage;
    public List<String> denyCommandsStartsWith, denyCommandsSingle, actionsOnJoin, actionsOnQuit, actionsOnDeath, actionsOnRespawn;
    public String deathMessage, killerDeathMessage, killerWeaponDeathMessage;
    public String joinMessage, quitMessage;
    public List<String> linkedWorlds;

}