rootProject.name = "serverist"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val serializationVersion: String by settings
        val dokkaVersion: String by settings
        val kotestVersion: String by settings
        val apolloVersion: String by settings
        val composeJbVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version "1.5.31"
        id("org.jetbrains.dokka") version dokkaVersion
        id("com.github.johnrengelman.shadow") version "5.2.0"
        id("io.kotest.multiplatform") version "5.0.0.5"
        id("com.apollographql.apollo") version apolloVersion
        id("org.jetbrains.compose") version composeJbVersion
    }
}

include(
    ":domain",
    ":domain:common",
    ":domain:auth",
    ":domain:serverist",
)

include(
    ":backend",
    ":clientCommon",
)