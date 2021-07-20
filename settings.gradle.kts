rootProject.name = "serverist"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val dokkaVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        id("org.jetbrains.dokka") version dokkaVersion
        id("com.github.johnrengelman.shadow") version "5.2.0"
    }
}

include(":backend")