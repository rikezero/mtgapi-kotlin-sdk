import java.util.Properties

plugins {
    id("java-library")
    id("signing")
    alias(mtgsdk.plugins.jetbrains.kotlin.jvm)
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

// Load version from version.properties
val versionFile = file("version.properties")
val versionProps = Properties()
if (versionFile.exists()) {
    versionProps.load(versionFile.inputStream())
}

// Extract the base version (MAJOR.MINOR.PATCH)
val baseVersion = versionProps.getProperty("version", "1.0.0") // Default to 1.0.0
val runNumber = System.getenv("GITHUB_RUN_NUMBER") ?: "0" // Default to 0 if not in CI

// Set the full version using GITHUB_RUN_NUMBER as the BUILD number
val fullVersion = "$baseVersion.$runNumber"
version = fullVersion

dependencies {
    // Kotlin
    implementation(mtgsdk.kotlinx.coroutines.core)
    // Retrofit2 and OkHttp3 dependencies
    implementation(mtgsdk.retrofit2)
    implementation(mtgsdk.retrofit2.converter.gson)
    implementation(mtgsdk.okhttp3)
    implementation(mtgsdk.okhttp3.logging.interceptor)
    // Koin
    implementation(mtgsdk.kotlin.stdlib)
    implementation(mtgsdk.koin.core)
    implementation(mtgsdk.kotlin.reflect)
    // Signing
    implementation(mtgsdk.bouncycastle.bcpg)
}

// Task to generate BuildConfig class
val generateConfigFile = tasks.register("generateConfigFile") {
    val outputDir = layout.buildDirectory.dir("generated/source/mtgapi_kotlin_sdk/config")
    outputs.dir(outputDir)

    doLast {
        val properties = Properties()
        val propertiesFile = file("product.properties")
        if (propertiesFile.exists()) {
            properties.load(propertiesFile.inputStream())
        }

        val fileContent = buildString {
            appendLine("package com.rikezero.mtgapi_kotlin_sdk.config")
            appendLine()
            appendLine("object BuildConfig {")
            properties.forEach { (key, value) ->
                appendLine("    const val ${key.toString().uppercase()} = \"$value\"")
            }
            appendLine("}")
        }

        val outputFile = outputDir.get().file("BuildConfig.kt").asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText(fileContent)
    }
}

sourceSets {
    named("main") {
        java.srcDirs(generateConfigFile.map { it.outputs.files.singleFile })
    }
}

// Metadata for the library
group = "com.github.rikezero" // GitHub-based group ID
version = version // Dynamically set version


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"]) // Include Java and Kotlin classes in the library

            // Metadata for the publication
            groupId = "com.github.rikezero"
            artifactId = "mtgapi-kotlin-sdk"
            version = version // Dynamic version

            pom {
                name.set("MTG API Kotlin SDK")
                description.set("Unofficial Kotlin SDK for MagicTheGathering.io API")
                url.set("https://github.com/rikezero/mtgapi-kotlin-sdk")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("rikezero")
                        name.set("RikeZero")
                        email.set("rikezero@egmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/rikezero/mtgapi-kotlin-sdk.git")
                    developerConnection.set("scm:git:ssh://github.com/rikezero/mtgapi-kotlin-sdk.git")
                    url.set("https://github.com/rikezero/mtgapi-kotlin-sdk")
                }
            }
        }
    }

    signing {
        useInMemoryPgpKeys(
            System.getenv("GPG_KEY_ID"),
            System.getenv("GPG_SECRET_KEY"),
            System.getenv("GPG_PASSPHRASE")
        )
        sign(publishing.publications["mavenJava"])
    }

    repositories {
        // Github Packages
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/rikezero/mtgapi-kotlin-sdk")
            credentials {
                username = System.getenv("GH_USERNAME") // GitHub username
                password = System.getenv("GH_TOKEN") // GitHub personal access token
            }
        }

        // Maven Central
        maven {
            name = "MavenCentral"
            url = uri(
                if (version.toString().endsWith("SNAPSHOT"))
                    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                else
                    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            )
            credentials {
                username = System.getenv("OSSRH_USERNAME") // Sonatype username
                password = System.getenv("OSSRH_PASSWORD") // Sonatype password
            }
        }
    }
}

// Task to update the version.properties file after a successful build
tasks.register("updateVersionFile") {
    doLast {
        versionProps.setProperty("version", baseVersion) // Keep MAJOR.MINOR.PATCH
        versionProps.store(versionFile.outputStream(), "Version updated after build")
    }
}