# Building

In Eclipse, you can `Right click project -> Export -> runnable jar file` and make sure the output location is this `out` folder.

I always make sure to check "extract required libraries into generated jar`, although you can use the `package` option or have the lib raries in a seperate folder the way `FabricBootstrap` does.

If you are distributing a build, make sure to include the `FabricBootstrap.jar` and the `FabricBootstrap_lib` folder unless you don't want Fabric support in your version.

The `start.bat` is unnecessary but it's good to provide it.

## Distributing

Of course, the license for the launcher, `FabricBootstrap.jar`, and `Updater.jar` is GPLV3. Meaning credit and distribution of your modified source code is basically required.

A good way to do this is simply fork the repository and host your builds in the `Releases` section of the repo.

Some packaged libraries have different licenses. It's a good idea to look at each one seperately to get an idea of the legal side of things. All included libraries have very lax licenses so there should be no issues.

Again, if you fork this code I would love to receive credit. I have spent a long time working on this project by myself, so even if you completely overhaul the app with a new UI I would love to know!

<3
