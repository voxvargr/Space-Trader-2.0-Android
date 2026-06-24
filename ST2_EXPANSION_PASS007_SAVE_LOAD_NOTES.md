# Space Trader 2.0 - Expansion Pass 007

Focus: reliable modern Android save/load support.

## Why this pass exists
The OG Android port saved to `/sdcard/SpaceTrader/savegame.txt`. On modern Android devices that path can fail unless the app has runtime storage permission, so the game could install and run but then reopen at character creation because the save file was never successfully written or read.

## Changes

- Added app-internal saves using Android private app storage. These do not require external storage permission.
- Startup now tries to load the best available save automatically.
- Existing old external save files are still checked as a fallback.
- Autosave now writes both:
  - `autosave.st2`
  - compatibility `savegame.txt`
- Autosave now happens:
  - when a new game starts
  - when the app pauses
  - when changing normal in-game screens
  - on the existing save-on-arrival path
- New Game screen now has a `Continue Saved Game` button when a save exists.
- Options screen now has a `Save / Load` section:
  - Save Now / Update Autosave
  - Load Autosave
  - Save Slot 1 / 2 / 3
  - Load Slot 1 / 2 / 3
  - Delete All Local Saves
- Starting a new game from the game menu clears the current autosave but leaves manual slots alone.
- Destroyed-game cleanup clears autosave/compat save.

## Notes

- Manual save slots are stored inside the app's private data folder, so uninstalling the app will delete them.
- Android Studio/Gradle build could not be run in the sandbox because the Gradle wrapper needs network access to download Gradle. XML parsing and Java brace checks passed.
