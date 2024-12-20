import java.util.Properties

plugins {
    id("java-library")
    id("signing")
    alias(mtgsdk.plugins.jetbrains.kotlin.jvm)
    alias(mtgsdk.plugins.mavenDeployer)
    alias(mtgsdk.plugins.dokka)
    `maven-publish`
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
        kotlin.srcDirs(generateConfigFile.map { it.outputs.files.singleFile })
    }
}

// Metadata for the library
group = "com.github.rikezero" // GitHub-based group ID

val javadocs = tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("mtgapi-maven") {
            from(components["java"])
            groupId = group.toString()
            artifactId = "mtgapi-kotlin-sdk"
            version = project.version.toString()
        }
    }
}

deployer {
    verbose = true

    content {
        component {
            fromKotlinTarget(kotlin.target)
            kotlinSources()
            docs(javadocs)
        }
    }

    projectInfo {
        name.set("MTG API Kotlin SDK")
        description.set("Unofficial Kotlin SDK for MagicTheGathering.io API")
        url.set("https://github.com/rikezero/mtgapi-kotlin-sdk")
        license(MIT)
        developer {
            name.set("rikezero")
            organization.set("RikeZero")
            email.set("rikezero@egmail.com")
            url.set("https://github.com/rikezero/")
        }
        groupId.set("com.github.rikezero")
        artifactId.set("mtgapi-kotlin-sdk")
        scm {
            connection.set("scm:git:git://github.com/rikezero/mtgapi-kotlin-sdk.git")
            developerConnection.set("scm:git:ssh://github.com/rikezero/mtgapi-kotlin-sdk.git")
            url.set("https://github.com/rikezero/mtgapi-kotlin-sdk")
        }
    }

    localSpec {
        //Left empty
    }

    githubSpec {
        owner.set("rikezero")
        repository.set("mtgapi-kotlin-sdk")
        auth {
            user.set(secret("GH_USERNAME"))
            token.set(secret("GH_TOKEN"))
        }
    }

    centralPortalSpec {
        projectInfo.groupId.set("io.github.rikezero")
        auth {
            user.set(secret("OSSRH_USERNAME"))
            password.set(secret("OSSRH_PASSWORD"))
        }
        signing {
            key.set(secret("GPG_PRIVATE_KEY"))
            password.set(secret("GPG_PASSWORD"))
        }
    }
}

kotlin {
    jvmToolchain(17)
    /*compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }*/
}

// Task to update the version.properties file after a successful build
tasks.register("updateVersionFile") {
    doLast {
        versionProps.setProperty("version", baseVersion) // Keep MAJOR.MINOR.PATCH
        versionProps.store(versionFile.outputStream(), "Version updated after build")
    }
}