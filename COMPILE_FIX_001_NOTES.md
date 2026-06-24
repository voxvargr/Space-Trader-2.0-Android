# Compile Fix 001

This package fixes the `cannot find symbol variable debugWarp` compile error in `Main.java`.

Cause:
- Pass002 added debug travel bypass logic.
- `debugWarp` was declared only inside `DoWarp(...)`, but three earlier methods also referenced it.

Fix:
- Replaced the three out-of-scope `debugWarp` checks with direct calls to `gameState.DebugCanTravelAnywhere()`.
- Left the local `debugWarp` variable inside `DoWarp(...)`, where it is correctly scoped and reused.

Use this package as the new base over the previous Pass002/JDK import fix.
