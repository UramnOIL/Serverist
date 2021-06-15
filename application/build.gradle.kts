plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {

    }

    sourceSets {
        val commonMain by getting {

            dependencies {
                val kotlinVersion: String by project
                val kodeinVersion: String by project
                val coroutinesVersion: String by project

                implementation(project(":domain"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.kodein.di:kodein-di-jvm:$kodeinVersion")
            }
        }
    }
}
