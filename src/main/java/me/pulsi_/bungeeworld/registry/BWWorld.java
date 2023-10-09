package me.pulsi_.bungeeworld.registry;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BWWorld {

    private final HashMap<UUID, BWPlayer> players = new HashMap<>();
    private final String name;

    private Location spawn;
    private String denyCommandsMessage, deathMessage, killerDeathMessage, killerWeaponDeathMessage, joinMessage, quitMessage;
    private boolean teleportToLastLocation, teleportToSpawnOnJoin;
    private BWSecurity security;
    private List<String> denyCommandsStartsWith, denyCommandsSingle, actionsOnJoin, actionsOnQuit, actionsOnDeath, actionsOnRespawn, linkedWorlds;

    public BWWorld(String name) {
        this.name = name;
    }

    public HashMap<UUID, BWPlayer> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public Location getSpawn() {
        return spawn;
    }

    public String getDenyCommandsMessage() {
        return denyCommandsMessage;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public String getKillerDeathMessage() {
        return killerDeathMessage;
    }

    public String getKillerWeaponDeathMessage() {
        return killerWeaponDeathMessage;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public String getQuitMessage() {
        return quitMessage;
    }

    public boolean isTeleportToLastLocation() {
        return teleportToLastLocation;
    }

    public boolean isTeleportToSpawnOnJoin() {
        return teleportToSpawnOnJoin;
    }

    public BWSecurity getSecurity() {
        return security;
    }

    public List<String> getDenyCommandsStartsWith() {
        return denyCommandsStartsWith;
    }

    public List<String> getDenyCommandsSingle() {
        return denyCommandsSingle;
    }

    public List<String> getActionsOnJoin() {
        return actionsOnJoin;
    }

    public List<String> getActionsOnQuit() {
        return actionsOnQuit;
    }

    public List<String> getActionsOnDeath() {
        return actionsOnDeath;
    }

    public List<String> getActionsOnRespawn() {
        return actionsOnRespawn;
    }

    public List<String> getLinkedWorlds() {
        return linkedWorlds;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void setActionsOnDeath(List<String> actionsOnDeath) {
        this.actionsOnDeath = actionsOnDeath;
    }

    public void setActionsOnJoin(List<String> actionsOnJoin) {
        this.actionsOnJoin = actionsOnJoin;
    }

    public void setActionsOnQuit(List<String> actionsOnQuit) {
        this.actionsOnQuit = actionsOnQuit;
    }

    public void setActionsOnRespawn(List<String> actionsOnRespawn) {
        this.actionsOnRespawn = actionsOnRespawn;
    }

    public void setDeathMessage(String deathMessage) {
        this.deathMessage = deathMessage;
    }

    public void setDenyCommandsMessage(String denyCommandsMessage) {
        this.denyCommandsMessage = denyCommandsMessage;
    }

    public void setDenyCommandsSingle(List<String> denyCommandsSingle) {
        this.denyCommandsSingle = denyCommandsSingle;
    }

    public void setDenyCommandsStartsWith(List<String> denyCommandsStartsWith) {
        this.denyCommandsStartsWith = denyCommandsStartsWith;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    public void setKillerDeathMessage(String killerDeathMessage) {
        this.killerDeathMessage = killerDeathMessage;
    }

    public void setKillerWeaponDeathMessage(String killerWeaponDeathMessage) {
        this.killerWeaponDeathMessage = killerWeaponDeathMessage;
    }

    public void setLinkedWorlds(List<String> linkedWorlds) {
        this.linkedWorlds = linkedWorlds;
    }

    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }

    public void setSecurity(BWSecurity security) {
        this.security = security;
    }

    public void setTeleportToLastLocation(boolean teleportToLastLocation) {
        this.teleportToLastLocation = teleportToLastLocation;
    }

    public void setTeleportToSpawnOnJoin(boolean teleportToSpawnOnJoin) {
        this.teleportToSpawnOnJoin = teleportToSpawnOnJoin;
    }
}