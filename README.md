# Project Red Fixes

**Project Red Fixes** is an unofficial compatibility patch collection for **Project Red on Minecraft 1.16.5**.

It provides small, targeted fixes for known Project Red bugs and compatibility problems without modifying or redistributing Project Red itself.

## Official download

Installable releases are published on **CurseForge**:

**[Download Project Red Fixes on CurseForge](https://www.curseforge.com/minecraft/mc-mods/project-red-fixes)**

> **Important:** GitHub's **Code → Download ZIP** option downloads the project source code, not the installable mod. Download the release JAR from CurseForge.

## Included fixes

### Dedicated-server gate GUIs

Fixes Timer, State Cell, Sequencer, and Counter configuration GUIs failing on dedicated servers because Project Red attempts to load client-only GUI classes on the server.

Upstream: [Project Red issue #1827](https://github.com/MrTJP/ProjectRed/issues/1827)

### Bus Converter server freeze

Fixes a server freeze involving the Bus Converter and high bundled-redstone channels, including the black / 16th bundled channel.

Upstream:

- [Project Red issue #1906](https://github.com/MrTJP/ProjectRed/issues/1906)
- [Project Red PR #1813](https://github.com/MrTJP/ProjectRed/pull/1813)
- [Later black-channel correction](https://github.com/MrTJP/ProjectRed/commit/ab9bd41bcf5ad4d811b9533ba590d9a277d3ab68)

### CC:Tweaked bundled redstone compatibility

Backports Project Red's later bundled-redstone compatibility fixes so Project Red bundled cables and CC:Tweaked computers can correctly exchange bundled signals.

This compatibility code activates automatically only when both **Project Red - Transmission** and **CC:Tweaked** are installed.

Upstream:

- [Project Red issue #1826](https://github.com/MrTJP/ProjectRed/issues/1826)
- [Project Red commit 41fe682](https://github.com/MrTJP/ProjectRed/commit/41fe682f5da9bc1c9ed52ff9def6980d1421e8f7)

## Requirements

Required:

- [Project Red - Core](https://www.curseforge.com/minecraft/mc-mods/project-red-core)
- [Project Red - Integration](https://www.curseforge.com/minecraft/mc-mods/project-red-integration)

Optional:

- [Project Red - Transmission](https://www.curseforge.com/minecraft/mc-mods/project-red-transmission) — used by the bundled-redstone fixes
- [CC:Tweaked](https://www.curseforge.com/minecraft/mc-mods/cc-tweaked) — used by the CC:Tweaked compatibility fix

The current release targets the final Project Red 1.16.5 line. Exact loader and dependency ranges are defined in the mod metadata and documented in [docs/VALIDATION.md](docs/VALIDATION.md).

## Installation

1. Install Project Red and its normal dependencies.
2. Place the Project Red Fixes JAR in the server's `mods` folder.
3. Start the server normally.

If upgrading from **Project Red Dedicated Server GUI Fix 1.0.0**, remove the old JAR first. Do not install both versions at the same time.

## Client and server installation

On dedicated servers, **Project Red Fixes can be installed server-side only**. Clients do not need the patch installed to connect.

For single-player, install the mod in the normal client `mods` folder because the integrated server runs inside the client.

## Automatic compatibility handling

There are no user-facing config switches.

- The dedicated-server GUI fix applies to the affected Project Red Integration code.
- The Bus Converter fix applies to the affected bundled-signal code.
- CC:Tweaked compatibility patches activate only when the relevant optional mods are present.

## Validation

The 1.1.0 release was runtime-tested on a true Forge dedicated server.

Release validation covered:

- the dedicated-server gate GUI regression
- the exact Bus Converter freeze reproduction
- the black / highest bundled channel
- CC:Tweaked bundled input and output
- optional-mod-present and optional-mod-absent startup behavior
- joining from a client without Project Red Fixes installed
- patched-versus-unpatched control testing for the original failures

Additional edge-case coverage is tracked separately and is not presented as verified unless it was actually tested.

See [docs/VALIDATION.md](docs/VALIDATION.md) for the detailed matrix.

## How it works

The project uses small Mixins rather than modifying or redistributing Project Red JARs.

- GUI calls are redirected to Project Red's existing server-safe packet channel.
- The Bus Converter bit scan is corrected using 16-bit masking and unsigned shifting.
- Bundled interaction calls use the corrected side calculation from newer Project Red code.
- The Transmission API bundled-input path delegates to Project Red's canonical bundled-input implementation.
- A Mixin config plugin skips optional compatibility patches when the relevant mods are absent.

## Building from source

This is a ForgeGradle 5.1 project targeting Java 8.

```bash
./gradlew build
```

## Issues and requests

Use the GitHub **Issues** tab for bug reports and narrowly scoped compatibility requests:

**[Report an issue or request a fix](https://github.com/zachsem/ProjectRed-Dedicated-Server-GUI-Fix/issues)**

## Credits

- **Project Red** — MrTJP, ChickenBones, covers1624, and contributors
- **Project Red Fixes** — zachsem

This is an independent, unofficial compatibility project and is not affiliated with or endorsed by the Project Red developers.

## License

MIT. See [LICENSE](LICENSE) and [NOTICE.md](NOTICE.md).
