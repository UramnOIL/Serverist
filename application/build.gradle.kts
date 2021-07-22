plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                val coroutinesVersion: String by project
                val datetimeVersion: String by project
                val uuidVersion: String by project

                api(project(":domain"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

                implementation("com.benasher44:uuid:${uuidVersion}")
            }
        }
    }
}
