# AGENTS.md

## Project purpose

ProjectRed 1.16.5 Fixes is a narrowly scoped Forge 1.16.5 compatibility patch collection for ProjectRed 4.15.0.

Current fixes:

1. ProjectRed #1827 — dedicated-server gate GUI classloading failure
2. ProjectRed #1906 — Bus Converter freeze caused by signed bit shifting
3. ProjectRed #1826 — CC:Tweaked bundled-signal compatibility

Do not add unrelated gameplay changes, recipes, balance changes, or new content.

## Supported stack

- Minecraft 1.16.5
- Forge 36.2.34
- ProjectRed Core 4.15.0
- ProjectRed Integration 4.15.0
- ProjectRed Transmission 4.15.0 when the optional bundled/CC fix is relevant
- CodeChickenLib 4.0.7.445
- CBMultipart 3.0.4.123
- Java 8

Do not broaden compatibility claims without testing.

## Existing GUI patch

Affected gates:

- Timer
- State Cell
- Sequencer
- Counter

The patch redirects ProjectRed's static `TimerScreen.open` / `CounterScreen.open` calls to `ProjectRedGuiPacketBridge`.

The bridge must remain server-safe and must not import or reference `net.minecraft.client` classes.

It intentionally reuses ProjectRed's existing Integration network channel and packet IDs.

## Bus Converter patch

`BundledSignalsLibMixin` replaces the broken result path of `BundledSignalsLib.mostSignificantBit(int)` with 16-bit masked unsigned shifting.

Preserve the ProjectRed 1.16 `(int) -> int` method signature. Do not change ProjectRed's caller ABI.

The important high-bit case is a sign-extended Java `short` such as `0x8000`.

## CC:Tweaked compatibility patch

The backport follows upstream commit `41fe682f5da9bc1c9ed52ff9def6980d1421e8f7`.

It corrects:

- bundled interaction side calculation in `BundledGatePart`
- bundled interaction side calculation in `BundledCablePart`
- `TransmissionAPI#getBundledInput()`

The optional compatibility Mixins must remain gated by the Mixin plugin and should apply only when both:

- `computercraft`
- `projectred-transmission`

are present.

Do not add direct CC:Tweaked class references to the patch. CC:Tweaked must remain optional.

## Server-only compatibility

The release is intended to remain compatible with dedicated-server-only installation.

`ProjectRedServerGuiFix` registers Forge's `ExtensionPoint.DISPLAYTEST` with `FMLNetworkConstants.IGNORESERVERONLY`.

Do not remove or change this behavior without testing the multiplayer server-list compatibility indicator and joining with an unpatched client.

## Build

Use Java 8 and Gradle 7.6.1.

```bash
./gradlew build
```

ForgeGradle: 5.1.x.

## Required validation

Follow [docs/VALIDATION.md](docs/VALIDATION.md).

A successful compile is not sufficient to call a release ready. The dedicated-server GUI regression, exact Bus Converter reproduction, high bundled bit, CC read/write behavior, optional-mod combinations, and unpatched-client connection all need runtime validation.

## Scope

Prefer the smallest patch that restores intended ProjectRed behavior. Do not modify or redistribute ProjectRed jars.
