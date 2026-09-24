# Changelog

## 1.1.0

### Added

- Fix for the Project Red Bus Converter server freeze involving high bundled-redstone channels.
- Backported Project Red CC:Tweaked bundled-redstone compatibility fixes.
- Automatic optional-mod detection so compatibility patches activate only when the relevant mods are installed.

### Changed

- Rebranded **Project Red Dedicated Server GUI Fix** as **Project Red Fixes**.

## 1.0.0

- Fixes Project Red Integration gate configuration GUIs on dedicated servers.
- Fixes Timer GUI opening.
- Fixes State Cell GUI opening.
- Fixes Sequencer GUI opening.
- Fixes Counter GUI opening.
- Reuses Project Red's existing GUI packet protocol instead of adding a new networking layer.
- Supports server-only installation; clients do not need the patch.
- Marks the mod as server-only-compatible for Forge's multiplayer compatibility check.
