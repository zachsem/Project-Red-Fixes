# ProjectRed Dedicated Server GUI Fix

**ProjectRed Dedicated Server GUI Fix** is an unofficial server-side patch for **ProjectRed Integration 4.15.0 on Minecraft 1.16.5**.

It fixes a dedicated-server classloading bug that prevents several configurable ProjectRed gates from opening their GUIs. The affected gates are:

- Timer
- State Cell
- Sequencer
- Counter

The patch is installed on the **dedicated server only**. Clients do not need this mod.

## What this fixes

ProjectRed Integration 4.15.0 references client-only GUI classes from common/server gate interaction code. On a true dedicated server, interacting with an affected gate can fail with an error similar to:

```text
Attempted to load class net/minecraft/client/gui/screen/Screen for invalid dist DEDICATED_SERVER
```

This project redirects those GUI-open calls to a server-safe bridge that sends ProjectRed's existing GUI packets without loading any client-only screen classes.

The original bug is tracked upstream in [ProjectRed issue #1827](https://github.com/MrTJP/ProjectRed/issues/1827).

## Requirements

- Minecraft 1.16.5
- Forge 36.2.34
- ProjectRed Core 4.15.0
- ProjectRed Integration 4.15.0
- CodeChickenLib 4.0.7.445
- CBMultipart 3.0.4.123

The initial release is intentionally pinned to the exact versions above because those are the versions used for validation.

## Installation

1. Install ProjectRed and its required dependencies on the dedicated server as usual.
2. Place `ProjectRedServerGuiFix-1.0.0.jar` in the server's `mods` folder.
3. Start the server.
4. Leave the patch off normal clients.

Clients with the normal ProjectRed 1.16.5 setup can connect without installing this patch.

## Validation

Version 1.0.0 was tested on a true Forge dedicated server with an unpatched client.

Validated behavior:

- Timer GUI opens and updates correctly.
- State Cell GUI opens and updates correctly.
- Sequencer GUI opens and updates correctly.
- Counter GUI opens and updates correctly.
- An unpatched client can connect normally.
- The server is reported as compatible in the multiplayer list.
- The original dedicated-server `Screen` classloading error no longer occurs.

See [docs/VALIDATION.md](docs/VALIDATION.md) for the exact test matrix.

## How it works

ProjectRed already contains the networking needed to open these GUIs. The problem in 4.15.0 is where the packet-send helper lives: the helper is attached to client screen classes, so a dedicated server can attempt to resolve `net.minecraft.client` classes while processing a gate interaction.

This patch uses Mixins to redirect the affected calls to a small common/server-safe packet bridge. It reuses ProjectRed's existing packet channel and packet IDs rather than creating a new GUI or networking protocol.

## Building from source

This is a ForgeGradle 5.1 project targeting Java 8.

Recommended build environment:

- JDK 8
- Gradle 7.6.1

Run:

```bash
gradle build
```

The build resolves ProjectRed, CodeChickenLib, and CBMultipart from the Covers1624 Maven repositories.

## Reporting bugs

Use the GitHub **Issues** tab and include the exact Minecraft, Forge, ProjectRed, CodeChickenLib, CBMultipart, and patch versions; the affected gate; clear reproduction steps; and the dedicated server's `latest.log` or crash report.

Because this patch is specifically for the final ProjectRed 1.16.5 release, reports involving other ProjectRed branches may be outside scope.

## Credits

- **ProjectRed** — MrTJP, ChickenBones, covers1624, and contributors
- **ProjectRed Dedicated Server GUI Fix** — zachsem

A later ProjectRed change, [PR #1796](https://github.com/MrTJP/ProjectRed/pull/1796), addressed the same class of dedicated-server GUI separation on newer code. This project applies the server-safe packet approach to ProjectRed 4.15.0 on Minecraft 1.16.5.

This is an independent, unofficial compatibility project and is not an official ProjectRed release.

## License

MIT. See [LICENSE](LICENSE) and [NOTICE.md](NOTICE.md).
