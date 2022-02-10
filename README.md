# MiniCraftLauncher
 a launcher for MiniCraft+ and some other variants of the game. Not Minecraft related.

Everything that the launcher downloads is in `<user folder>/Documents/MajickTek/minilauncher`. If your user folder doesn't have a Documents folder inside it for some reaason, it'll make one.

On \*NIX systems including macOS (probably) you should be able to find the right folder by navigating to `~\Documents\MajickTek`. On Windows this would be `C:/Users/%username%/Documents/MajickTek`.

As of now, the current minimum version of the launcher you should use is `Pre Release 4`. This version bumbs the minimum JRE version to 1.8 to make it more accessible (this may change in the future if necessary). Earlier versions require JRE 16 or newer.

## Current functionality:
- Load versions from /MiniCraftLauncherIndex
- Double-click to download a version
- Double-click already downloaded version to launch it
- The launcher keeps track of downloaded versions on restart
- Under the Edit menu, go into the Select Channel submenu to switch between Release, Pre-Release, and Mod channels.
- Insert/Add version profiles. These cannot be saved at the moment. GUI Options accessible via the Edit menu.
## How does it work?
When a channel is selected, it downloads a `.xml` file from https://github.com/MajickTek/MiniCraftLauncherIndex.
The format of this file is described over there.

## Roadmap
- Add menu to open launcher folder
  - Now that a context menu has been implemented, this is easier
- Add config file to load the indexes (so they aren't hardcoded and the user can make their own)
- (Future) Allow self-updating via GitHub Releases
  - I have an easy solution for this but it would be Windows-only unless I find a way to make it cross platform
- Fabric Support (with help from PseudoDistant on the various discords)
