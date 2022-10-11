# ♻️ BungeeWorld Plugin ♻️
## Description
This plugin allows you to separate various player statistics trought worlds.
Every world will have his own inventory, effects and gamemode.
If you don't want to use this feature, you can edit it in the file `config.yml`.
It has more cool features like the ability to create as many items and guis as you want, using the `items.yml` and `guis.yml` files.

## Custom Actions
Here you can have a list of all custom actions available.
```yaml
# Play a sound for the player.
[SOUND] <sound-identifier> <volume> <pitch>

# Send to the player a title with an optional sub-title.
[TITLE] <title>,[sub-title]

# Make the player opening a gui using the identifier in the file guis.yml.
[OPEN_GUI] <gui-identifier>

# Give the specified effect to the player.
[EFFECT] <effect-type> <duration> <amplifier>

# You can use %player% for the player name.
[CONSOLE] <command>

# Force a player to chat, it can be used to send messages or execute commands.
[PLAYER] <chat>

# Broadcast a message ( Will work only in the current world
# if enabling the option isolate-chat in the config.yml ).
[BROADCAST] <broadcast>

# Give a custom item to the selected player.
# First 2 args are essentials, the other 2 are optional.
# slot: Indicates the slot in the slot bar where the item will be placed, if the slot is not
# empty, it will add the item to the inventory.
# force: force the item to be placed in the specified slot. ( This will destroy the item in the slot )
[GIVE_CUSTOM_ITEM] <custom-item> [slot] [force]
```
