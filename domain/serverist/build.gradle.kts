plugins {
    kotlin("multiplatform")
}

kotlin {
    targets {
        jvm()
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
        val commonTest by getting {
            dependencies {
                val kotestVersion: String by project
                implementation("io.kotest:kotest-assertions-core:$kotestVersion")
                implementation("io.kotest:kotest-framework-engine:$kotestVersion")
            }
        }
    }
}