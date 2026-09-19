# NPBS-2 App (নাটোর পল্লী বিদ্যুৎ সমিতি-২)

An Android information app for the members of Natore Palli Bidyut Samity-2 (NPBS-2), a rural
electricity cooperative under the Bangladesh Rural Electrification Board (BREB) in Natore,
Bangladesh. The app gives members the services, officials, contact numbers, office locations,
bill instructions and official notices of 20+ offices, and it works offline.

I built it on my own initiative while I was Assistant General Manager (IT) at the cooperative,
as the only software engineer on the team. The UI is in Bengali.

## Status

- Published on Google Play as `com.galib.natorepbs2` from 2022 to 2023. Last published
  version on this branch: 1.1.2 (versionCode 7). 100+ users across 20+ offices. Crash rate
  under 0.1 percent over 18 months, measured with Firebase Crashlytics.
- Later removed from Google Play for inactivity after I left the organization.
- A 2.0 rewrite with Kotlin DSL, a version catalog and Compose is in progress on the branch
  `dev_2.0.0`. It is not a release state.
- This branch, `main`, is the 1.x line as it shipped, plus this README, the license and the
  removal of two files that must never sit in a repository (see Security).

## Features

- Home menu with favorite items that the user can pin.
- Offices and contacts: office information, contact list, other office contacts, officers,
  junior officers, office heads, board members.
- Notices and tenders, achievements, at-a-glance figures, vision and mission, our services.
- Electricity connection instructions, bill instructions, bill calculator, bill payment step
  images (bank app and USSD), bill messages, connection messages, bank information.
- Complaint centre list, opinion and complaint form, power outage contacts, interruption entry.
- Awareness content, social media links, websites, related apps, an in-app PDF viewer and a
  WebView for external pages.
- Settings, about the cooperative, about the app.

## How it works

### Offline-first data

Room holds all content: 8 entities (`Achievement`, `ComplainCentre`, `Employee`,
`Information`, `Instruction`, `MyMenuItem`, `NoticeInformation`, `OfficeInformation`) behind
DAOs that return `Flow`. Fragments observe ViewModels; ViewModels read `NPBS2Repository`.
The app opens with the last synced data and needs no connection to show it.

### Sync without a backend API

The cooperative had no API, only a public website. The `sync` package solves that:

- `SyncConfig` downloads two JSON files from GitHub Gist URLs: a config file with the URL and
  the CSS selector for each content type, and a data file. The scraping targets change
  without an app release.
- `Sync` fetches each page with jsoup, selects the elements with the configured selector, and
  writes notices, achievements, at-a-glance figures, offices, employees and complaint centres
  to Room.
- `SyncManager` runs the sync on `Dispatchers.IO`, keeps the last sync time and a counter of
  failed attempts, and skips the sync when the data is fresh.

### Interruption entry through Google Sheets (unfinished)

`InterruptionEntryFragment` signs the user in with Google Sign-In and reads rows from a Google
Sheet with the Sheets API v4 (`gsheet` package). The code still points at Google's sample
spreadsheet: the feature was added in June 2023 and never finished. The 2.0 line removes it.

### Other parts

- One Activity with Fragments and Jetpack Navigation; layouts with DataBinding.
- Play In-App Updates through `UpdateManager`; notifications through `NotificationManager`.
- Firebase Crashlytics for crash reports; a Firebase Analytics dependency is in the build.

## Tech stack

Kotlin 1.8, Android Gradle Plugin 8.0, Gradle 8.0, minSdk 21, targetSdk 33.
AndroidX AppCompat, Activity, Lifecycle (ViewModel, LiveData), Navigation 2.5, Room 2.5 with
kapt, DataBinding, ConstraintLayout, Material Components, Flexbox, SplashScreen, WebKit.
Kotlin Coroutines. jsoup 1.16, OkHttp, Picasso, android-pdf-viewer. Firebase BoM 32
(Crashlytics, Analytics). Google Play In-App Updates. Google Sign-In and Google Sheets API v4.
JUnit, Mockito, MockK and Espresso are declared; the tests are the templates only.

## Build

1. Install Android Studio with JDK 17. Android Gradle Plugin 8.0 needs JDK 17.
2. Create a Firebase project for the package `com.galib.natorepbs2`, download its
   `google-services.json`, and place it at `app/google-services.json`. The file
   `app/google-services.json.example` shows the expected shape. The build fails without it.
3. Open the project and run the `app` configuration, or run `./gradlew assembleDebug`.

There is no CI. Release builds were signed by hand.

## Third-party code in `customui`

- `carouselview`: adapted from the CarouselView library by Sayyam (Apache-2.0). The original
  headers were not kept when the code was copied; the class names match that library.
- `touchimageview`: adapted from TouchImageView by Mike Ortiz (MIT), Kotlin version.

## Security

On 2026-09-19 the release signing keystore and `app/google-services.json` were removed from
the whole history of this repository, and `.gitignore` now blocks both. The keystore is treated
as exposed and will not sign any future release. The Firebase API key from the old file must
be restricted or rotated in the Google Cloud console.

## License

The source code is under the MIT License, see [LICENSE](LICENSE). The texts, logos and images
of Natore Palli Bidyut Samity-2 and the Bangladesh Rural Electrification Board in the
resources and assets belong to those organizations.
