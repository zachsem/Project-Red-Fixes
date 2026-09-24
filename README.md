# ProjectRed 1.16.5 Fixes

**ProjectRed 1.16.5 Fixes** is an unofficial compatibility patch collection for **ProjectRed 4.15.0 on Minecraft 1.16.5**.

It keeps the existing dedicated-server gate GUI fix and adds two additional backports for confirmed ProjectRed 1.16.5 bugs:

- dedicated-server configuration GUIs for Timer, State Cell, Sequencer, and Counter
- Bus Converter server freeze / black bundled-channel handling
- CC:Tweaked bundled-signal compatibility when CC:Tweaked and ProjectRed Transmission are installed

The fixes are isolated internally and activate automatically when relevant. There are no user-facing config toggles to manage.

## Official download

Installable releases are published on **CurseForge**:

**[Download ProjectRed 1.16.5 Fixes on CurseForge](https://www.curseforge.com/minecraft/mc-mods/projectred-dedicated-server-gui-fix)**

> **Important:** GitHub's **Code → Download ZIP** option downloads the project source code, not the installable mod. Download the release `.jar` from CurseForge and place that file in the appropriate `mods` folder.

## What this fixes

### Dedicated-server gate GUIs

ProjectRed Integration 4.15.0 references client-only GUI classes from common/server gate interaction code. On a true dedicated server, interacting with an affected gate can fail with:

```text
Attempted to load class net/minecraft/client/gui/screen/Screen for invalid dist DEDICATED_SERVER
```

This project redirects those GUI-open calls to a server-safe bridge that sends ProjectRed's existing GUI packets without loading client-only screen classes.

Upstream: [ProjectRed issue #1827](https://github.com/MrTJP/ProjectRed/issues/1827)

### Bus Converter server freeze

ProjectRed 4.15.0 uses signed right shifts in `BundledSignalsLib.mostSignificantBit()`. When a 16-bit bundled mask is sign-extended, the loop can fail to terminate and trigger the dedicated-server watchdog.

The patch keeps the original 1.16 method signature but backports the corrected 16-bit/unsigned-shift behavior from newer ProjectRed code.

Upstream:

- [ProjectRed issue #1906](https://github.com/MrTJP/ProjectRed/issues/1906)
- [ProjectRed PR #1813](https://github.com/MrTJP/ProjectRed/pull/1813)
- [later black-channel correction](https://github.com/MrTJP/ProjectRed/commit/ab9bd41bcf5ad4d811b9533ba590d9a277d3ab68)

### CC:Tweaked bundled-signal compatibility

ProjectRed 4.15.0 contains incorrect bundled-signal side/orientation handling and an obsolete Transmission API implementation that can prevent CC:Tweaked from correctly reading or driving ProjectRed bundled cables.

The patch backports the relevant ProjectRed 4.16 fixes in:

- `BundledGatePart`
- `BundledCablePart`
- `TransmissionAPI#getBundledInput()`

These compatibility mixins are enabled automatically only when **both CC:Tweaked and ProjectRed Transmission are installed**. CC:Tweaked remains optional.

Upstream:

- [ProjectRed issue #1826](https://github.com/MrTJP/ProjectRed/issues/1826)
- [ProjectRed commit 41fe682](https://github.com/MrTJP/ProjectRed/commit/41fe682f5da9bc1c9ed52ff9def6980d1421e8f7)

## Requirements

**Required**

- Minecraft 1.16.5
- Forge 36.2.34
- ProjectRed Core 4.15.0
- ProjectRed Integration 4.15.0
- CodeChickenLib 4.0.7.445
- CBMultipart 3.0.4.123

**Optional**

- ProjectRed Transmission 4.15.0 — needed for the bundled cable/API compatibility portion
- CC:Tweaked — when present together with ProjectRed Transmission, the CC compatibility fixes enable automatically

Compatibility claims are intentionally limited to the ProjectRed 4.15.0 stack above unless additional versions are tested.

## Installation

1. Install ProjectRed and its normal dependencies.
2. Place the **ProjectRed 1.16.5 Fixes** jar in the server's `mods` folder.
3. If upgrading from 1.0.0, remove the old `ProjectRedServerGuiFix-1.0.0.jar`; do not keep both versions installed.
4. Start the server normally.

Dedicated-server clients do not need this patch installed.

For single-player, install the patch in the normal client `mods` folder because the integrated server runs inside the client.

## Automatic fix activation

There are no per-fix config switches.

- The dedicated-server GUI fix is active with ProjectRed Integration 4.15.0.
- The Bus Converter fix is active with ProjectRed Core 4.15.0.
- The CC:Tweaked compatibility mixins activate only when the loader detects both `computercraft` and `projectred-transmission`.

This keeps installation hands-off while avoiding a hard dependency on CC:Tweaked or ProjectRed Transmission.

## Validation

Version 1.0.0's dedicated-server GUI fix was validated on a true Forge dedicated server with an unpatched client.

Version 1.1.0 adds the Bus Converter and CC:Tweaked fixes. The source builds successfully in CI; gameplay validation for the new fixes must be completed before the 1.1.0 release is published.

See [docs/VALIDATION.md](docs/VALIDATION.md) for the full release test matrix.

## How it works

The project uses small Mixins rather than modifying or redistributing ProjectRed jars.

- GUI calls are redirected to ProjectRed's existing server-safe packet channel.
- `mostSignificantBit()` is corrected at method entry with 16-bit masking and unsigned shifts.
- bundled-interaction calls use the corrected side calculation from newer ProjectRed.
- `TransmissionAPI#getBundledInput()` delegates to ProjectRed's canonical bundled-input implementation.
- a Mixin config plugin checks the Forge loading mod list and skips the CC:Tweaked compatibility Mixins unless the relevant optional mods are present.

## Building from source

This is a ForgeGradle 5.1 project targeting Java 8.

Recommended build environment:

- JDK 8
- Gradle 7.6.1

Run:

```bash
./gradlew build
```

The build resolves ProjectRed, CodeChickenLib, and CBMultipart from the Covers1624 Maven repositories.

## Reporting bugs and feature requests

Use the GitHub **Issues** tab. Separate forms are provided for bug reports and feature requests.

For bug reports, include the exact Minecraft, Forge, ProjectRed, CodeChickenLib, CBMultipart, patch, ProjectRed Transmission, and CC:Tweaked versions that apply to your setup, plus clear reproduction steps and `latest.log` or the relevant crash report.

## Credits

- **ProjectRed** — MrTJP, ChickenBones, covers1624, and contributors
- **ProjectRed 1.16.5 Fixes** — zachsem

This is an independent, unofficial compatibility project and is not an official ProjectRed release.

## License

MIT. See [LICENSE](LICENSE) and [NOTICE.md](NOTICE.md).
