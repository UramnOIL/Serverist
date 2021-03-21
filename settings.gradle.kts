rootProject.name = "serverist"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.4.31"
    }
}

include(":domain", ":application", ":web")