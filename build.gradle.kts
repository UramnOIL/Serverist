group = "com.uramnoil"
version = "0.0.1"

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("multiplatform") apply false
    kotlin("plugin.serialization") apply false
    kotlin("jvm") apply false
    id("org.jetbrains.dokka")
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    plugins.apply("org.jetbrains.dokka")
}

tasks.getByName<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>("dokkaHtmlMultiModule") {
    outputDirectory.set(buildDir.resolve("dokkaHtmlMultiModuleOutput"))
}