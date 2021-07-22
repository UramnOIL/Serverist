plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {

    }

    sourceSets {
        val coroutinesVersion: String by project
        val datetimeVersion: String by project
        val uuidVersion: String by project

        println(uuidVersion)

        val commonMain by getting {
            dependencies {

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

                implementation("com.benasher44:uuid:${uuidVersion}")
            }
        }
    }
}