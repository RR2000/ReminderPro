# Reminder Pro

A small Android to-do app written in Kotlin in 2023. It is an early prototype that was never finished.

What it does today:

- shows the list of saved tasks (title, date and duration);
- adds a task from a simple form (title, description, start time `HH:mm`, duration in minutes);
- deletes a task when you tap it in the list.

Tasks are stored locally with [Room](https://developer.android.com/training/data-storage/room)
(tables `tasks` and `subtasks`; subtasks exist in the data model but not yet in the UI).
There is no backend, no account and no network access.

## Build

Requirements: JDK 17 and the Android SDK (API 34).

```sh
./gradlew assembleDebug
```

The APK ends up in `app/build/outputs/apk/debug/`. The GitHub Actions workflow in
`.github/workflows/build.yml` builds the same debug APK on every push and keeps it as a build artifact.

Stack: Kotlin, AndroidX Navigation, View Binding, RecyclerView, Room (kapt), Material Components;
minSdk 26, targetSdk 34; Android Gradle Plugin 8.13, Gradle 8.14.
