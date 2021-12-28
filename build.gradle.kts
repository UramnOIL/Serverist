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
    id("com.diffplug.spotless")
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

subprojects {
    buildscript {
        plugins.apply("com.diffplug.spotless")
        plugins.apply("org.jetbrains.dokka")
    }

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt", "bin/**/*.kt")
            ktlint("0.43.2").userData(mapOf("indent_size" to "4", "continuation_indent_size" to "2"))
        }
        kotlinGradle {
            ktlint()
        }
    }
}

tasks.getByName<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>("dokkaHtmlMultiModule") {
    outputDirectory.set(buildDir.resolve("dokkaHtmlMultiModuleOutput"))
}
