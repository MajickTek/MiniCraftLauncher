# MiniCraftLauncher
A launcher for MiniCraft+ and some other variants of the game. Not Minecraft related.

Everything that the launcher downloads is in `<user folder>/Documents/MajickTek/minilauncher`. If your user folder doesn't have a Documents folder inside it for some reaason, it'll make one.

On \*NIX systems including macOS (probably) you should be able to find the right folder by navigating to `~\Documents\MajickTek`. On Windows this would be `C:/Users/%username%/Documents/MajickTek`.

As of now, the current minimum version of the launcher you can (and should) use is `Release 1.0`. This version's minimum JRE version is 1.8 (JRE 8) to make it more accessible (this may change in the future if necessary). Earlier versions require JRE 16 or newer and likely don't support the generally backwards-compatible [XML format](https://github.com/MajickTek/MiniCraftLauncherIndex).

## Current functionality:
See the [wiki](https://github.com/MajickTek/MiniCraftLauncher/wiki).
## How does it work?
Ditto.
## Roadmap
- Add contextual icons for nodes in the tree
- Add urls to the `options.txt` file so they aren't hardcoded
  - This will allow testing brances to use specific versions of the index files instead of all using the `main` branch and breaking older versions
- (Future) Allow self-updating via GitHub Releases
  - I have an easy solution for this but it would be Windows-only unless I find a way to make it cross platform
- Fabric Support (with help from PseudoDistant on the various discords)

## Credits
Downloading library used is https://github.com/MajickTek/file-downloader, an updated fork of some 10-year old code that still works
