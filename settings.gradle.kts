rootProject.name = "serverist"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.5.0"
        id("com.squareup.sqldelight") version "1.4.4"
    }
}

include(":domain", ":application", ":backend")