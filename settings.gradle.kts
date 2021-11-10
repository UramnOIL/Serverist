rootProject.name = "serverist"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val serializationVersion: String by settings
        val dokkaVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version "1.5.31"
        id("org.jetbrains.dokka") version dokkaVersion
        id("com.github.johnrengelman.shadow") version "5.2.0"
    }
}

include(
    ":domain",
    ":domain:common",
    ":domain:auth",
    ":domain:serverist",
)

include(
    ":application",
)

include(
    ":backend"
)