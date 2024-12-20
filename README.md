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
Current SDK version: v1.0.0.71

## Installation

There are three methods you can choose from to install the MTG API Kotlin SDK into your project: via 
GitHub Packages or Maven Central (recommended) or by manually downloading the `.jar` file.

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

3. Add the dependency for the SDK:

    ```gradle
    dependencies {
        implementation 'com.rikezero:mtgapi-kotlin-sdk:v1.0.0.71'
    }
    ```

   Make sure to replace `v1.0.0.71` with the appropriate version number as needed.

3. To authenticate to GitHub Packages, create a personal access token on GitHub with the appropriate permissions 
4. and set the `gpr.user` and `gpr.token` properties in your project (or environment variables).

For more information about working with GitHub Packages, check out the 
official [GitHub Packages documentation](https://docs.github.com/pt/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry).

### Option 2: Maven Central (Recommended)

You can use GitHub Packages to easily install the SDK into your project using Gradle. Follow the instructions below:

1. Add the Maven Central repository to your `settings.gradle.kts` file:

   ```gradle
   pluginManagement {
      repositories {        
        mavenCentral()        
      }    
   }
   ```

2. Add the dependency for the SDK to your `build.gradle.kts`:

   ```gradle
   dependencies {
      implementation("io.github.rikezero:mtgapi-kotlin-sdk:1.0.0.71")
   }
   ```

Make sure to replace `v1.0.0.71` with the appropriate version number as needed.

### Option 3: Manual Installation

If you prefer not to use GitHub Packages, you can manually download the `.jar` file from the releases page of this repository.

1. Visit the [Releases page](https://github.com/rikezero/mtgapi-kotlin-sdk/releases) on GitHub.
2. Download the `.jar` file corresponding to the release you want to use.
3. Add the `.jar` file to your project's `libs` directory (or any folder where you store external dependencies).
4. In your `build.gradle` file, add the following to include the `.jar` file in your project:

    ```gradle
    dependencies {
        implementation files('libs/mtgapi-kotlin-sdk-v1.0.0.71.jar')
    }
    ```

Make sure to replace `mtgapi-kotlin-sdk-v1.0.0.71.jar` with the actual filename of the `.jar` file you downloaded.

## Initializing the Library in Koin

Once you have successfully installed the library, you can initialize it in your Koin setup.

1. **In your Application class (for Android)**:

    ```kotlin
    class MyApplication : Application() {
        override fun onCreate() {
            super.onCreate()

            // Initialize Koin when the app starts
            startKoin {
                androidContext(this@MyApplication)
                modules(initialModules)  // This could be your global/default modules
            }

            // Load the MTG API library modules after Koin is initialized
            startMtgApiLibrary()  // Ensure to call this after Koin initialization
        }
    }
    ```

2. **In a Pure Kotlin Project (non-Android)**:

    ```kotlin
    import org.koin.core.context.startKoin

    fun main() {
        // Initialize Koin with global/default modules
        startKoin {
            modules(initialModules)  // Define the modules you want to use globally
        }

        // Dynamically load the MTG API library modules
        startMtgApiLibrary()  // Ensure to call this after Koin initialization
    }
    ```

In both cases, ensure that `startMtgApiLibrary()` is called **after** initializing Koin (`startKoin()`), 
but **before** using any services or features provided by the MTG API library.