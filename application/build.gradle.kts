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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                api(libs.kotlinx.datatime)
                api(libs.uuid)
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
