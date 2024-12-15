import java.util.Properties

plugins {
    id("java-library")
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
version = "1.0.0" // Semantic version of the library

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"]) // Include Java and Kotlin classes in the library

            // Metadata for the publication
            groupId = "com.github.rikezero"
            artifactId = "mtgapi-kotlin-sdk"
            version = "1.0.0"

            pom {
                name.set("MTG API Kotlin SDK")
                description.set("Unofficial kotlin sdk for magicthegathering.io API")
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

    val envProps = Properties()
    file("environment.properties").takeIf { it.exists() }?.apply {
        inputStream().use { envProps.load(it) }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/rikezero/mtgapi-kotlin-sdk")
            credentials {
                username = envProps.getProperty("GH_USERNAME") ?: System.getenv("GH_USERNAME")
                password = envProps.getProperty("GH_TOKEN") ?: System.getenv("GH_TOKEN")
            }
        }
    }
}