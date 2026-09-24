# Validation matrix

## Target stack

| Component | Version |
| --- | --- |
| Minecraft | 1.16.5 |
| Forge | 36.2.34 |
| Project Red Core | 4.15.0 |
| Project Red Integration | 4.15.0 |
| Project Red Transmission | 4.15.0 when applicable |
| CodeChickenLib | 4.0.7.445 |
| Dedicated server Java | Java 8 |

These exact versions document the tested stack. Public-facing descriptions intentionally avoid repeating technical version detail that users do not need.

## 1.0.0 validation history

Version 1.0.0 was validated on a true Forge dedicated server with an unpatched client.

Without the patch, right-clicking affected Project Red configurable gates reproduced the server-side classloading error involving `net/minecraft/client/gui/screen/Screen` and `DEDICATED_SERVER`.

With the patch installed on the dedicated server:

- the client joined successfully
- Forge's multiplayer compatibility check accepted the server
- Timer, State Cell, Sequencer, and Counter GUIs opened
- the original dedicated-server client-class loading error did not recur
- server shutdown was clean

## 1.1.0 release validation

The core behavior required for the 1.1.0 release was runtime-tested successfully.

### Dedicated-server gate GUI regression

- [x] Dedicated server: Timer
- [x] Dedicated server: State Cell
- [x] Dedicated server: Sequencer
- [x] Dedicated server: Counter
- [x] Dedicated-server client does not have Project Red Fixes installed
- [x] No `invalid dist DEDICATED_SERVER` / client-class loading error

### Bus Converter

Baseline, without the patch:

- [x] Reproduce Project Red issue #1906 using a Bus Converter in its second mode
- [x] Connect neutral bundled wire
- [x] Connect black insulated wire
- [x] Power the setup
- [x] Confirm the original unpatched stack hangs/freezes under the #1906 reproduction

With the patch:

- [x] Exact #1906 setup no longer hangs or watchdog-crashes
- [x] Bus Converter still converts normally
- [x] Explicitly verify channel 15 / black / `0x8000`
- [x] Repeatedly power/unpower the setup

### CC:Tweaked bundled compatibility

With Project Red Transmission and CC:Tweaked installed:

- [x] Project Red bundled cable -> CC:Tweaked computer: read bundled input
- [x] CC:Tweaked computer -> Project Red bundled cable: write bundled output
- [x] Test more than one bundled color/channel
- [x] Test the high/black channel
- [x] Repeat the test using a different computer side
- [x] Test using neutral bundled cable

Automatic optional-mod behavior:

- [x] CC:Tweaked absent, Project Red Transmission present: server starts and CC compatibility Mixins remain disabled
- [x] CC:Tweaked absent, Project Red Transmission absent: server starts and optional Mixins remain disabled
- [x] CC:Tweaked present, Project Red Transmission present: compatibility Mixins apply
- [x] Dedicated server accepts a client without Project Red Fixes installed

### A/B control confirmation

On the same Project Red/Forge test stack, removing Project Red Fixes restored both original failures:

- configurable gate GUIs failed again on the dedicated server
- the Project Red #1906 Bus Converter reproduction froze the server when the black bundled channel was powered

Reinstalling the 1.1.0 test build restored the working behavior observed in the patched tests above.

### Core + Integration-only runtime control

With Project Red Transmission and CC:Tweaked removed from both server and client, and Project Red Fixes installed only on the dedicated server:

- the server loaded Project Red Core + Integration and the patch without either optional mod
- only the dedicated-server GUI Mixins applied; the optional CC/Transmission Mixins stayed disabled
- the server reached `Done`, an unpatched client joined successfully, and shutdown completed cleanly
- the reused disposable validation world produced expected missing-registry warnings for previously placed Transmission/CC:Tweaked content; those warnings were unrelated to optional-mod loading

## Additional coverage

The following are useful additional regression tests, but were not required to establish the specific fixes included in 1.1.0. They remain unchecked unless actually tested.

### General / GUI

- [ ] Single-player: Timer
- [ ] Single-player: State Cell
- [ ] Single-player: Sequencer
- [ ] Single-player: Counter
- [ ] Change GUI values/settings, close and reopen, and confirm persistence on 1.1.0
- [ ] Recheck the multiplayer server-list compatibility indicator on 1.1.0

### Bus Converter

- [ ] Test each bundled channel 0 through 15 individually
- [ ] Test representative multiple-channel masks
- [ ] Save/reload the world and retest

### CC:Tweaked bundled compatibility

- [ ] Test top and bottom computer orientations
- [ ] Test floor/wall/ceiling face-wire orientations as applicable
- [ ] Test a framed/center bundled cable path if applicable
- [ ] Verify ordinary Project Red bundled cable-to-cable behavior is unchanged
- [ ] Verify insulated-wire interaction is unchanged
- [ ] Save/reload and retest

## Build validation

- [x] ForgeGradle/Java 8 CI build succeeds for the 1.1.0 source branch
- [x] Built JAR targets Java class version 52
- [x] Built JAR contains the expected seven Mixin classes plus the automatic Mixin config plugin
