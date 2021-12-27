import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val ktorVersion: String by project
        val napierVersion: String by project

        val jsMain by getting {
            dependencies {
                implementation(project(":clientCommon"))
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.logging)

                implementation(libs.napier)
            }
        }
    }
}

compose {

}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = "16.0.0"
}