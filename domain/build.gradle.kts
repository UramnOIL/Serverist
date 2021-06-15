plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {

    }

    sourceSets {
        val kotlinVersion: String by project
        val kodeinVersion: String by project
        val coroutinesVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.kodein.di:kodein-di-jvm:$kodeinVersion")
            }
        }
    }
}