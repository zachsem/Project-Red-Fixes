# Contributing

Bug reports, compatibility findings, and narrowly scoped fixes are welcome.

## Before opening an issue

- Reproduce the problem with the newest release of ProjectRed Dedicated Server GUI Fix.
- Confirm the exact ProjectRed, Forge, CodeChickenLib, and CBMultipart versions.
- Test on a true dedicated server when the report involves server GUI behavior.
- Keep the original ProjectRed jars unmodified.

## Bug reports

Please attach the dedicated server's `latest.log` or relevant crash report and include clear reproduction steps.

If a specific gate is affected, identify whether it is the Timer, State Cell, Sequencer, or Counter.

## Pull requests

Keep changes narrowly focused on the ProjectRed 1.16.5 dedicated-server GUI issue or directly related compatibility problems.

Important constraints:

- Do not add client-only `net.minecraft.client` references to common/server patch code.
- Preserve server-only client compatibility.
- Reuse ProjectRed's existing networking where possible.
- Test with an unpatched client before considering a networking or metadata change complete.

All contributed code must be compatible with the project's MIT license.
