plugins {
    kotlin("multiplatform")
    id("com.apollographql.apollo")
}

kotlin {
    jvm()

    val ktorVersion: String by project
    val coroutinesVersion: String by project
    val apolloVersion: String by project

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":application"))
                implementation("io.ktor:ktor-client-core:1.6.5")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("com.apollographql.apollo:apollo-runtime-kotlin:$apolloVersion")
            }
        }
    }
}