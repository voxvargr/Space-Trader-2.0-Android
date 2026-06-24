# Launcher Icon Fix 001

This package fixes the Android install where the APK appeared in App Info but did not create a home/app-drawer launcher icon.

## Cause
After modernizing the old Android project, some devices/launchers can fail to treat the original activity intent filter as a valid launcher entry.

## Fix
`SpaceTrader/src/main/AndroidManifest.xml` now uses an explicit launcher-safe `activity-alias`:

- Main activity remains `de.anderdonau.spacetrader.Main`
- Main activity is internal/non-exported
- Launcher alias is exported and has the `MAIN` + `LAUNCHER` intent filter
- Alias points to `de.anderdonau.spacetrader.Main`

## Install note
Uninstall the previous APK from the Android device first, then install this build. That forces Android's launcher database to rebuild the app entry.
