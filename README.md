# Kotlin SDK for using the magicthegathering.io APIs

## Overview

This Kotlin SDK is built to interact with the [Magic: The Gathering API](https://magicthegathering.io), providing a clean,
Kotlin-centric interface to access the API’s data. The SDK is designed to offer a more modern and structured approach with
features such as UseCases, tailored for better flexibility and ease of use compared to other SDKs available.

The SDK was developed to offer a more modern alternative to the officially listed Kotlin SDK for the Magic: The Gathering API,
which also don't appear to be maintained anymore.
It aims to provide developers with a better, more organized way to interact with the API while being easy to integrate into
your Kotlin-based applications.

The [Magic: The Gathering API documentation](https://docs.magicthegathering.io/#documentationgetting_started) can be found here
for a deeper understanding of how the API works.

Feel free to use or fork it as per your needs!

**WIP** - Ongoing updates to complete development and polish features.

## Version
Current SDK version: v1.0.0.25

## Installation

There are two methods you can choose from to install the MTG API Kotlin SDK into your project: via GitHub Packages (recommended) or by manually downloading the `.jar` file.

### Option 1: GitHub Packages (Recommended)

You can use GitHub Packages to easily install the SDK into your project using Gradle. Follow the instructions below:

1. Add the GitHub Packages repository to your `build.gradle` file:

    ```gradle
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/rikezero/mtgapi-kotlin-sdk")
            credentials {
                username = project.findProperty("gpr.user") ?: System.getenv("USERNAME_GITHUB")
                password = project.findProperty("gpr.token") ?: System.getenv("TOKEN_GITHUB")
            }
        }
    }
    ```

2. Add the dependency for the SDK:

    ```gradle
    dependencies {
        implementation 'com.rikezero:mtgapi-kotlin-sdk:v1.0.0.25'
    }
    ```

   Make sure to replace `v1.0.0.25` with the appropriate version number as needed.

3. To authenticate to GitHub Packages, create a personal access token on GitHub with the appropriate permissions and set the `gpr.user` and `gpr.token` properties in your project (or environment variables).

For more information about working with GitHub Packages, check out the official [GitHub Packages documentation](https://docs.github.com/pt/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry).

### Option 2: Manual Installation

If you prefer not to use GitHub Packages, you can manually download the `.jar` file from the releases page of this repository.

1. Visit the [Releases page](https://github.com/rikezero/mtgapi-kotlin-sdk/releases) on GitHub.
2. Download the `.jar` file corresponding to the release you want to use.
3. Add the `.jar` file to your project's `libs` directory (or any folder where you store external dependencies).
4. In your `build.gradle` file, add the following to include the `.jar` file in your project:

    ```gradle
    dependencies {
        implementation files('libs/mtgapi-kotlin-sdk-v1.0.0.25.jar')
    }
    ```

Make sure to replace `mtgapi-kotlin-sdk-v1.0.0.25.jar` with the actual filename of the `.jar` file you downloaded.