# Gradle Import Fix Notes

This build was updated because the old Codeberg/OG port used Gradle 6.5, which cannot run on the newer JVM bundled with current Android Studio.

Changes made:

- Updated Gradle wrapper from 6.5 to 8.13.
- Updated Android Gradle Plugin from 4.1.0 to 8.13.0.
- Moved repositories to `settings.gradle` and replaced `jcenter()` with `mavenCentral()`/`google()`.
- Added Android namespace `de.anderdonau.spacetrader`.
- Removed the manifest `package` attribute because modern AGP expects namespace in Gradle.
- Kept targetSdk at 26 for now to avoid changing runtime behavior while we are still expanding gameplay.
- Set `android.nonFinalResIds=false` because the OG Java code uses `case R.id...` switch statements.

If Android Studio offers to update AGP again, skip it for now until the project imports successfully once.
