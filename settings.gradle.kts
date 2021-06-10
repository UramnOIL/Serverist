rootProject.name = "serverist"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.4.31"
        id("com.squareup.sqldelight") version "1.4.4"
        id("com.github.johnrengelman.shadow") version "5.2.0"
    }
}

include(":domain", ":application", ":backend")