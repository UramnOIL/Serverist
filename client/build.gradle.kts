plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.apollographql.apollo3").version("3.3.0")
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
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.datatime)

                api(libs.uuid)
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
    }
}

apollo {
    packageName.set("com.uramnoil.serverist")
}