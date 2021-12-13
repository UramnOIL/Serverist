plugins {
    kotlin("multiplatform")
}

kotlin {
    targets {
        val isForClient = Attribute.of("com.uramnoil.serverist.jvm.is_for_client", Boolean::class.javaObjectType)
        jvm {
            attributes.attribute(isForClient, true)
        }
        jvm("serverJvm") {
            attributes.attribute(isForClient, false)
        }
        ios()
        js(IR) {
            browser()
            binaries.library()
        }
    }

    val coroutinesVersion: String by project
    val serializationVersion: String by project
    val datetimeVersion: String by project
    val uuidVersion: String by project

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

                api("com.benasher44:uuid:$uuidVersion")
            }
        }

        val clientMain by creating {
            dependsOn(commonMain)
        }

        val jvmMain by getting {
            dependsOn(clientMain)
        }

        val iosMain by getting {
            dependsOn(clientMain)
        }

        val jsMain by getting {
            dependsOn(clientMain)
        }

        val serverMain by creating {
            dependsOn(commonMain)
        }

        val serverJvmMain by getting {
            dependsOn(serverMain)
        }
    }
}