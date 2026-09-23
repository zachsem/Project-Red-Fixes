# AGENTS.md

## Project purpose

ProjectRed Dedicated Server GUI Fix is a narrowly scoped Forge 1.16.5 patch for ProjectRed Integration 4.15.0.

The target bug is ProjectRed issue #1827: configurable gates can reference client-only GUI screen classes from dedicated-server interaction code.

## Supported stack

- Minecraft 1.16.5
- Forge 36.2.34
- ProjectRed Core 4.15.0
- ProjectRed Integration 4.15.0
- CodeChickenLib 4.0.7.445
- CBMultipart 3.0.4.123
- Java 8

Do not broaden compatibility claims without testing.

## Patch behavior

Affected gates:

- Timer
- State Cell
- Sequencer
- Counter

The patch redirects ProjectRed's static `TimerScreen.open` / `CounterScreen.open` calls to `ProjectRedGuiPacketBridge`.

The bridge must remain server-safe and must not import or reference `net.minecraft.client` classes.

It intentionally reuses:

- `IntegrationNetwork.NET_CHANNEL`
- `IntegrationNetwork.OPEN_TIMER_GUI_FROM_SERVER`
- `IntegrationNetwork.OPEN_COUNTER_GUI_FROM_SERVER`
- `IntegrationNetwork.writePartIndex(...)`

Do not add a replacement GUI or a second network protocol unless the existing ProjectRed protocol is proven insufficient.

## Server-only requirement

The release is intended to be installed on the dedicated server only.

`ProjectRedServerGuiFix` registers Forge's `ExtensionPoint.DISPLAYTEST` with `FMLNetworkConstants.IGNORESERVERONLY`.

Do not remove or change this behavior without testing the multiplayer server-list compatibility indicator and joining with an unpatched client.

## Build

Use Java 8 and Gradle 7.6.1.

```bash
gradle build
```

ForgeGradle: 5.1.x.

## Required validation

Before calling a change release-ready:

1. Start a true dedicated server.
2. Confirm the server reaches `Done`.
3. Join with a client that does not have this patch installed.
4. Confirm the multiplayer list does not mark the server incompatible.
5. Test Timer, State Cell, Sequencer, and Counter.
6. Change a setting in each GUI, close it, reopen it, and confirm the value persists.
7. Check the server log for `invalid dist DEDICATED_SERVER`, Mixin errors, and handshake errors.

Single-player testing is not a substitute for the dedicated-server test.

## Scope

Prefer the smallest patch that restores intended ProjectRed behavior. Avoid unrelated features, balance changes, recipes, or content.
