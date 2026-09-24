# Validation matrix

## Target stack

| Component | Version |
| --- | --- |
| Minecraft | 1.16.5 |
| Forge | 36.2.34 |
| ProjectRed Core | 4.15.0 |
| ProjectRed Integration | 4.15.0 |
| ProjectRed Transmission | 4.15.0 when applicable |
| CodeChickenLib | 4.0.7.445 |
| CBMultipart | 3.0.4.123 |
| Dedicated server Java | Java 8 |

## 1.0.0 validation history

Version 1.0.0 was validated on a true Forge dedicated server with an unpatched client.

Without the patch, right-clicking affected ProjectRed configurable gates reproduced the server-side classloading error involving `net/minecraft/client/gui/screen/Screen` and `DEDICATED_SERVER`.

With the patch installed on the dedicated server:

- the client joined successfully
- Forge's multiplayer compatibility check accepted the server
- Timer, State Cell, Sequencer, and Counter GUIs opened
- the original dedicated-server client-class loading error did not recur
- server shutdown was clean

## 1.1.0 release validation

Do not mark 1.1.0 release-ready until all applicable items below have been completed.

### Existing GUI fix

- [ ] Single-player: Timer
- [ ] Single-player: State Cell
- [ ] Single-player: Sequencer
- [ ] Single-player: Counter
- [x] Dedicated server: Timer
- [x] Dedicated server: State Cell
- [x] Dedicated server: Sequencer
- [x] Dedicated server: Counter
- [ ] Change values/settings, close and reopen, and confirm persistence
- [x] Dedicated-server client does not have the patch installed
- [ ] Multiplayer server list reports the server as compatible
- [x] No `invalid dist DEDICATED_SERVER` / client-class loading error

### Bus Converter

Baseline, without the patch:

- [x] Reproduce ProjectRed issue #1906 using a Bus Converter in its second mode
- [x] Connect neutral bundled wire
- [x] Connect black insulated wire
- [x] Power the setup
- [x] Confirm the original unpatched stack hangs/freezes under the #1906 reproduction

With the patch:

- [x] Exact #1906 setup no longer hangs or watchdog-crashes
- [x] Bus Converter still converts normally
- [ ] Test each bundled channel 0 through 15 individually
- [x] Explicitly verify channel 15 / black / `0x8000`
- [ ] Test representative multiple-channel masks
- [x] Repeatedly power/unpower the setup
- [ ] Save/reload the world and retest

### CC:Tweaked bundled compatibility

With ProjectRed Transmission and CC:Tweaked installed:

- [x] ProjectRed bundled cable -> CC:Tweaked computer: read bundled input
- [x] CC:Tweaked computer -> ProjectRed bundled cable: write bundled output
- [x] Test multiple colors/channels
- [x] Test the high/black channel
- [x] Test different horizontal sides
- [ ] Test top and bottom orientations
- [ ] Test floor/wall/ceiling face-wire orientations as applicable
- [ ] Test neutral bundled cable
- [ ] Test a framed/center bundled cable path if applicable
- [ ] Verify ordinary ProjectRed bundled cable-to-cable behavior is unchanged
- [ ] Verify insulated-wire interaction is unchanged
- [ ] Save/reload and retest

Automatic optional-mod behavior:

- [x] CC:Tweaked absent, ProjectRed Transmission present: server starts and CC compatibility Mixins remain disabled
- [x] CC:Tweaked absent, ProjectRed Transmission absent: server starts and optional Mixins remain disabled
- [x] CC:Tweaked present, ProjectRed Transmission present: compatibility Mixins apply
- [x] Dedicated server still accepts a client without ProjectRed Fixes installed

## Build validation

- [x] ForgeGradle/Java 8 CI build succeeds for the 1.1.0 source branch
- [x] Built jar targets Java class version 52
- [x] Built jar contains the expected seven Mixin classes plus the automatic Mixin config plugin

### A/B control confirmation

On the same ProjectRed/Forge test stack, removing ProjectRed Fixes restored both original failures:

- configurable gate GUIs failed again on the dedicated server
- the ProjectRed #1906 Bus Converter reproduction froze the server when the black bundled channel was powered

Reinstalling the 1.1.0 test build restored the working behavior observed in the patched tests above. This provides a direct patched-vs-unpatched control for the two non-CC fixes.

### Core + Integration-only runtime control

With ProjectRed Transmission and CC:Tweaked removed from both server and client, and ProjectRed Fixes 1.1.0 installed only on the dedicated server:

- the server loaded ProjectRed Core + Integration and the patch without either optional mod
- only the three dedicated-server GUI Mixins were applied; the optional CC/Transmission Mixins stayed disabled
- the server reached `Done`, an unpatched client joined successfully, and shutdown completed cleanly
- the test reused the disposable validation world, so Forge emitted expected missing-registry warnings for previously placed Transmission/CC:Tweaked content; those warnings are unrelated to the patch's optional-mod loading behavior
