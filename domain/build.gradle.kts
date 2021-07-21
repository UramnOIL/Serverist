plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {

    }

    sourceSets {
        val kotlinVersion: String by project
        val coroutinesVersion: String by project
        val uuidVersion: String by project

        println(uuidVersion)

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("com.benasher44:uuid:${uuidVersion}")
            }
        }
    }
}