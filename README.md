# Swing Speed (Fabric, Minecraft 1.21.11)

Client-side mod that changes how fast your arm swing animation plays.

Right Shift opens a menu with a slider and presets (0.5x/1x/2x/3x/5x). Right Shift again or Done closes it.

Keys (rebindable in Controls > Swing Speed):
  ]  faster (+0.25x)     [  slower (-0.25x)     \  reset to 1.0x
Range: 0.25x - 5.0x. Current value shows above your hotbar.

## Build
Requires JDK 21 and Gradle (or add the Gradle wrapper: `gradle wrapper`).
  gradle build
The jar lands in build/libs/. Drop it in your mods folder with Fabric Loader + Fabric API.

## Notes
- This changes the swing animation only. It does NOT change attack cooldown or damage;
  the server controls those, so this is safe on multiplayer servers.
- For other versions, update the versions in gradle.properties (see fabricmc.net/develop).
