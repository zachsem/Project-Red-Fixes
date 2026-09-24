# Changelog

## 1.1.0

- Expands the project branding to **ProjectRed 1.16.5 Fixes** while preserving the existing mod ID.
- Keeps the existing dedicated-server GUI fix for Timer, State Cell, Sequencer, and Counter.
- Adds the ProjectRed #1906 Bus Converter server-freeze fix.
- Backports unsigned 16-bit bundled-mask handling so the highest/black channel resolves correctly instead of hanging or producing an invalid bit index.
- Adds the ProjectRed #1826 bundled-signal compatibility backport for CC:Tweaked.
- Corrects bundled interaction side/orientation handling in `BundledGatePart` and `BundledCablePart`.
- Corrects `TransmissionAPI#getBundledInput()` by delegating to ProjectRed's canonical bundled-input helper.
- Automatically enables the CC:Tweaked compatibility Mixins only when both CC:Tweaked and ProjectRed Transmission are installed.
- Keeps CC:Tweaked and ProjectRed Transmission optional.
- Renames the built jar to `ProjectRed1165Fixes-1.1.0.jar`.

## 1.0.0

- Fixes ProjectRed Integration 4.15.0 gate configuration GUIs on dedicated servers.
- Fixes Timer GUI opening.
- Fixes State Cell GUI opening.
- Fixes Sequencer GUI opening.
- Fixes Counter GUI opening.
- Reuses ProjectRed's existing GUI packet protocol instead of adding a new networking layer.
- Supports server-only installation; clients do not need the patch.
- Marks the mod as server-only-compatible for Forge's multiplayer compatibility check.
