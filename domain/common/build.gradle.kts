plugins {
    kotlin("multiplatform")
    id("io.kotest.multiplatform")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kotlin {
    targets {
        jvm()
    }

    sourceSets {
        val coroutinesVersion: String by project
        val datetimeVersion: String by project
        val uuidVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                api(libs.kotlinx.datatime)
                api(libs.uuid)
            }
        }
        val commonTest by getting {
            dependencies {
                val kotestVersion: String by project
                implementation("io.kotest:kotest-assertions-core:$kotestVersion")
            }
        }

        val jvmTest by getting {
            dependencies {
                val kotestVersion: String by project
                implementation(libs.kotest.runner.junit5)
            }
        }
    }
}