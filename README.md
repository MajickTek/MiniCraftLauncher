# MiniCraftLauncher
A launcher for [MiniCraft+](https://github.com/MinicraftPlus/minicraft-plus-revived) and some other variants of the game. Not Minecraft related.

![MiniCraftLauncher 1.5 Hotfix](https://imgur.com/l4mlEdY.png)

Everything that the launcher downloads is in `<user folder>/Documents/MajickTek/minilauncher`. If your user folder doesn't have a Documents folder inside it for some reaason, it'll make one.

On \*NIX systems including macOS (probably) you should be able to find the right folder by navigating to `~/Documents/MajickTek`. On Windows this would be `C:\Users\%username%\Documents\MajickTek`.

As of now, the current minimum version of the launcher you can use is `Release 1.0`. The latest version is recommended. It is recommended to use the latest Java Runtime Environment available. As of now this is [JRE 17 LTS](https://www.azul.com/downloads/?version=java-17-lts&package=jre). The JDK FX package from that link is what is currently being used to develop this software. 

## Current functionality:
See the [wiki](https://github.com/MajickTek/MiniCraftLauncher/wiki).
## How does it work?
Ditto.

Although if you want to know how the implementation of the UI itself was thought up, you can see the [Zoom Manager](https://github.com/MajickTek/ZoomManager) repo. The earliest versions of this launcher (all below Release 1.4) were based directly on the ZoomManager code.

## Roadmap
- Add contextual icons for nodes in the tree
- Allow multiple versions of the index xmls so older releases don't break
  - Display indexes correctly in the Channel Selector
- (Future) Allow self-updating via GitHub Releases
  - This  is being worked on

## Credits
Downloading library used is https://github.com/MajickTek/file-downloader, an updated fork of some 10-year old code that still works
