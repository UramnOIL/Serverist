plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm("jvm-server") {
        dependencies {
            //val serverMain by sourceSets.getting
            //metadataImplementation(serverMain)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                val serializationVersion: String by project

                implementation(project(":domain:common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
    }
}
