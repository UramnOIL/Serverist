plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.apollographql.apollo3")
    id("org.jetbrains.compose")
}

repositories {
    google()
}

kotlin {
    targets {
        val isForClient = Attribute.of("com.uramnoil.serverist.jvm.is_for_client", Boolean::class.javaObjectType)
        jvm("desktop") {
            attributes.attribute(isForClient, true)
        }
        js(IR) {
            browser()
            binaries.executable()
        }
    }

    val coroutinesVersion: String by project
    val serializationVersion: String by project
    val datetimeVersion: String by project

    val napierVersion: String by project
    val ktorVersion: String by project
    val apolloVersion: String by project
    val kotestVersion: String by project
    val koinVersion: String by project

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

                implementation("io.github.aakira:napier:$napierVersion")

                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("com.apollographql.apollo3:apollo-api:$apolloVersion")
                implementation("com.apollographql.apollo3:apollo-runtime:$apolloVersion")
            }
        }

        val commonTest by getting {
            dependencies {

                // kotest
                implementation("io.kotest:kotest-property:$kotestVersion")

                // ktor
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
            }
        }

        val clientMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(project(":application"))

            }
        }

        val composeMain by creating {
            dependsOn(clientMain)
            dependencies {
                dependencies {
                    implementation(compose.runtime)
                }
            }
        }

        val desktopMain by getting {
            dependsOn(composeMain)
            dependencies {
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.desktop.currentOs)
                implementation(compose.ui)
            }
        }

        val jsMain by getting {
            dependsOn(composeMain)
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
            }
        }
    }
}

apollo {
    packageName.set("com.uramnoil.serverist")
}