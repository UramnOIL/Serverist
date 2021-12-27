plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.apollographql.apollo3")
    id("org.jetbrains.compose")
}

repositories {
    google()
}

kotlin {
    targets {
        val isForClient = Attribute.of("com.uramnoil.serverist.jvm.is_for_client", Boolean::class.javaObjectType)
        jvm("desktop") {
            attributes.attribute(isForClient, true)
        }
        js(IR) {
            browser()
            binaries.executable()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.datatime)


                implementation(libs.napier)
                implementation(libs.bundles.ktor.client)
                implementation(libs.apollo.api)
                implementation(libs.apollo.runtime)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.test.kotest.property)
                implementation(libs.test.ktor.client.mock)
            }
        }

        val clientMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(project(":application"))

            }
        }

        val composeMain by creating {
            dependsOn(clientMain)
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

apollo {
    packageName.set("com.uramnoil.serverist")
}