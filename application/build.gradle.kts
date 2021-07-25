plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":domain"))
            }
        }
        val jvmMain by getting {}

        val backendMain by creating {
            dependsOn(jvmMain)

            dependencies {
                api(project(":domain"))
            }
        }

        val frontendMain by creating {
            dependsOn(commonMain)

            dependencies {
                api(project(":domain"))
            }
        }
    }
}
