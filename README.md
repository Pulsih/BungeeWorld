# ♻️ BungeeWorld Plugin ♻️
## Description
This plugin allows you to separate various player statistics trought worlds.
Every world will have his own inventory, effects and gamemode.
If you don't want to use this feature, you can edit it in the file `config.yml`.
It has more cool features like the ability to create as many items and guis as you want, using the `items.yml` and `guis.yml` files.

## Custom Actions
Here you can have a list of all custom actions available.
```yaml
# Make the player opening a gui using the identifier in the file guis.yml
[OPEN_GUI] <gui-identifier>

# Give the specified effect to the player.
[EFFECT] <effect-type> <duration> <amplifier>

# You can use %player% for the player name.
[CONSOLE] <command>

# Force a player to chat, it can be used to send messages or execute commands.
[PLAYER] <chat>

# Broadcast a message ( Will work only in the current world 
# if enabling the option isolate-chat in the config.yml )
[BROADCAST] <broadcast>

# Give a custom item to the selected player. ( Use %player% for the player name )
# First 2 args are essentials, the other 2 are optional.
# slot: Indicates the slot in the slot bar where the item will be placed, if the slot is not
# empty, it will add the item to the inventory.
# force: force the item to be placed in the specified slot. ( This will destroy the item in the slot )
[GIVE_CUSTOM_ITEM] <player-name> <custom-item> [slot] [force]
```




## Files
Default `worlds.yml` file:
```yaml
ExampleWorld:
  spawn: null
  chat: "&7%player%&8: &7%message%"
  security:
    # These settings will be overriden if a player
    # has the bungeeworld.admin permission.
    deny-message: "&c&lSorry! &7You can't do that here."
    disable-block-place: false
    disable-block-break: false
    disable-mob-spawning: false
    disable-explosions: false
    disable-player-actions: false
    disable-players-drops: false
    disable-players-pickup: false
    disable-fall-damage: false
    disable-pvp: false
  denied-commands:
    deny-message: 'Unknown command. Type "/help" for help.'
    # All commands that starts with one of these, will be blocked.
    starts-with:
      - "/commands-that-starts-like-that"
    # The exact command that will be blocked.
    single-command:
      - "/this-command-must-not-be-executed"
  actions-on-join:
    # You can see a list of all custom actions here:
    # https://github.com/Pulsih/BungeeWorld/blob/main/README.md
    - "[GIVE_CUSTOM_ITEM] %player% ServerSelector 5 true"
  actions-on-death:
    - "[BROADCAST] %player% died."
  actions-on-respawn:
    - "[EFFECT] %player% SPEED 999999999 1"
  death-message: "&a%player% &fdied."
  join-message: null # Use "null" to remove the message.
  quit-message: null
```
