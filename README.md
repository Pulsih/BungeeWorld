# worlds.yml

Default worlds file:
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
