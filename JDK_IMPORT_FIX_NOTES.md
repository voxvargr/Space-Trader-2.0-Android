# JDK Import Fix

This package removes the old IntelliJ/Android Studio project metadata (`.idea` and `*.iml`) that was still pointing to an invalid legacy Project JDK.

Why this fixes the warning:
- The previous archive contained old IDE metadata from the original port, including a ProjectRootManager entry for `project-jdk-name="1.8"`.
- Modern Android Studio may translate that into an invalid `#USE_PROJECT_JDK` Gradle JVM setting when no matching Project JDK exists.
- Removing the old metadata forces Android Studio to re-import from Gradle using the wrapper and its own Embedded JDK / JetBrains Runtime.

Recommended import:
1. Extract this zip.
2. Open Android Studio.
3. Use File > Open and select the extracted project folder that contains `settings.gradle`.
4. Let Android Studio use the Embedded JDK.
5. Let it download Gradle 8.13 and Android Gradle Plugin 8.13.0 during sync.

Do not copy this over an already-imported folder that still has an old `.idea` directory. Extract to a fresh folder.
