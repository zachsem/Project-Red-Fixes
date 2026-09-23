# Validation matrix

Version 1.0.0 was validated against the following stack:

| Component | Version |
| --- | --- |
| Minecraft | 1.16.5 |
| Forge | 36.2.34 |
| ProjectRed Core | 4.15.0 |
| ProjectRed Integration | 4.15.0 |
| CodeChickenLib | 4.0.7.445 |
| CBMultipart | 3.0.4.123 |
| Dedicated server Java | Java 8 |

## Baseline reproduction

Without the patch, right-clicking affected ProjectRed configurable gates on a true dedicated server reproduced the server-side classloading error involving:

```text
net/minecraft/client/gui/screen/Screen
```

and the `DEDICATED_SERVER` distribution.

## Patched validation

The following were tested successfully with the patch installed on the dedicated server:

- Timer
- State Cell
- Sequencer
- Counter

The client did not have ProjectRed Dedicated Server GUI Fix installed.

Observed results:

- Client joined successfully.
- Forge multiplayer compatibility check accepted the server.
- All four configuration GUIs opened.
- The original dedicated-server client-class loading error did not recur.
- Server shutdown was clean.

The release intentionally makes compatibility claims only for the exact dependency versions above.
