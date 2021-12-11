plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.apollographql.apollo")
}

kotlin {
    jvm()

    val ktorVersion: String by project
    val coroutinesVersion: String by project
    val serializationVersion: String by project
    val apolloVersion: String by project
    val kotestVersion: String by project

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":domain:common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("com.apollographql.apollo:apollo-api:$apolloVersion")
                implementation("com.apollographql.apollo:apollo-runtime-kotlin:$apolloVersion")
                implementation("com.apollographql.apollo:apollo-coroutines-support:$apolloVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                // mockk
                implementation("io.mockk:mockk:1.12.0")

                // kotest
                implementation("io.kotest:kotest-property:$kotestVersion")
                implementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")

                // ktor
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                // mockk
                implementation("io.mockk:mockk:1.12.0")

                // kotest
                implementation("io.kotest:kotest-property:$kotestVersion")
                implementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")

                // ktor
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
            }
        }
    }
}

apollo {
    generateKotlinModels.set(true)
}