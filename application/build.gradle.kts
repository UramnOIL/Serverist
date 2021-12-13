plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm("server")
    jvm("client")

    val coroutinesVersion: String by project
    val serializationVersion: String by project
    val datetimeVersion: String by project
    val uuidVersion: String by project

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

                api("com.benasher44:uuid:$uuidVersion")
            }
        }
    }
}