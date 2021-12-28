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
        val commonMain by getting {
            dependencies {
                implementation(project(":domain:common"))
                implementation(libs.kotlinx.coroutines)
            }
        }
        val commonTest by getting {
            dependencies {
                val kotestVersion: String by project
                implementation(libs.test.kotest.assertions.core)
            }
        }

        val jvmTest by getting {
            dependencies {
                val kotestVersion: String by project
                implementation(libs.test.kotest.runner.junit5)
            }
        }
    }
}
