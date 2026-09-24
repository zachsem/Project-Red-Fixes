# Contributing

Bug reports, compatibility findings, and narrowly scoped Project Red 1.16.5 fixes are welcome.

## Before opening an issue

- Reproduce the problem with the newest release of Project Red Fixes.
- Confirm the exact Project Red and Forge versions.
- Include Project Red Transmission and CC:Tweaked versions when they are relevant.
- Test on a true dedicated server when the problem involves server behavior.
- Keep the original Project Red JARs unmodified.

## Bug reports

Please attach `latest.log` or the relevant crash report and include clear reproduction steps.

Identify which area is affected:

- dedicated-server gate GUI
- Bus Converter
- bundled cable / CC:Tweaked compatibility
- other direct regression caused by this patch

## Pull requests

Keep changes narrowly focused on restoring intended Project Red 1.16.5 behavior.

Important constraints:

- do not add client-only `net.minecraft.client` references to common/server patch code
- preserve server-only client compatibility
- keep CC:Tweaked optional
- keep Project Red Transmission optional unless a future fix truly requires it
- preserve the existing technical mod ID
- prefer surgical Mixins/backports over copied upstream classes
- test optional-mod combinations before considering compatibility changes complete

All contributed code must be compatible with the project's MIT license.
