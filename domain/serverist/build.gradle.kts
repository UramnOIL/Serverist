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
                implementation(project(":domain:common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }
    }
}