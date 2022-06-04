plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
}

repositories {
    google()
}

kotlin {
    targets {
        jvm("desktop")
        js(IR) {
            browser()
            binaries.executable()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":client"))

                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.datatime)

                implementation(libs.napier)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.test.kotest.property)
                implementation(libs.test.ktor.client.mock)
            }
        }

        val composeMain by creating {
            dependencies {
                dependencies {
                    implementation(compose.runtime)
                }
            }
        }

        val desktopMain by getting {
            dependsOn(composeMain)
            dependencies {
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.desktop.currentOs)
                implementation(compose.ui)
            }
        }

        val desktopTest by getting {
            dependsOn(desktopMain)
            dependencies {
                implementation(libs.test.kotest.property)
                implementation(libs.test.kotest.runner.junit5)
            }
        }

        val jsMain by getting {
            dependsOn(composeMain)
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
            }
        }
    }
}