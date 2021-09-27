# MiniCraftLauncher
 a launcher for MiniCraft+ and some other variants of the game. Not Minecraft related.

Everything that the launcher downloads is in `<user folder>/Documents/MajickTek/minilauncher`. If your user folder doesn't have a Documents folder inside it for some reaason, it'll make one.

## Current functionality:

- Load versions from /MiniCraftLauncherIndex
- Double-click to download a version
- Double-click already downloaded version to launch it
- The launcher keeps track of downloaded versions on restart
- Under the Edit menu, go into the Select Channel submenu to switch between Release, Pre-Release, and Mod channels.

## How does it work?
When a channel is selected, it downloads a `.index` file from https://github.com/MajickTek/MiniCraftLauncherIndex.
Inside this file is a list of versions, and the format is like this:
~~~~
<index> = <SemanticVersion>,<url>
~~~~
The SemanticVersion is often planted in the URL somewhere if the url contains the string `$version`.
## Roadmap
- Add menu to open launcher folder
- Add right-click menu on version to open version folder (to access saves and configs)
- (Future) Allow self-updating via GitHub Releases
