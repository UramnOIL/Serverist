plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        binaries.library()
    }

    val ktorVersion: String by project

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":application"))
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
    }
}